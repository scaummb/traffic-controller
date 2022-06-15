package com.bryant.traffic.controller;

import com.bryant.traffic.service.LimitService;
import com.bryant.traffic.service.SystemService;
import com.bryant.traffic.service.UserService;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;

@Controller
@RequestMapping("/app")
@Slf4j
public class TestController extends BaseController{

    @Autowired
    private SystemService systemService;
    @Autowired
    private LimitService limitService;
    @Autowired
    private UserService userService;

    @GetMapping("/test")
    @ResponseBody
    public String testDemo() throws Exception {
        StopWatch clock = new StopWatch("stop watch test");
        clock.start("test1");
        systemService.step1();
        clock.stop();

        clock.start("test2");
        systemService.step2();
        clock.stop();

        clock.start("test3");
        systemService.step3();
        clock.stop();

        int time = new Random().nextInt(1000);
        Thread.sleep(time);
        log.debug(MessageFormat.format("log:{0}", clock.prettyPrint()));
        System.out.println(clock.prettyPrint());
        return "Hello World!";
    }

    @GetMapping("/limit")
    @ResponseBody
    public void limit(){
        limitService.limit();
    }

    @PostMapping("/db_test")
    @ResponseBody
    public void dbTeset() throws Exception {
        userService.create();
    }
}
