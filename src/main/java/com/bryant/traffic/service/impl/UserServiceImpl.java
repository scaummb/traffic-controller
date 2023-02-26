package com.bryant.traffic.service.impl;

import com.bryant.traffic.mapper.UserMapper;
import com.bryant.traffic.model.User;
import com.bryant.traffic.service.UserService;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void create() throws Exception {
        // 随机取100以下的id，冲突概率
        Long id = Long.valueOf(new Random().nextInt(1000));
        User user = new User();
        user.setId(id);
        user.setAge(10);
        user.setName("10");
        userMapper.create(user);

        try {
            user.setId(null);
            userMapper.create(user);
        } catch (Exception ex) {
            log.error("sql error..");
        }


        // 随机删除一条数据
        userMapper.deleteById(id);

        if (id % 10 == 0) {
            throw new Exception("% 10 exception..");
        }
    }
}
