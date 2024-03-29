package dao;

import com.google.common.util.concurrent.RateLimiter;
import dao.BaseDaoTest;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.Test;

/**
 * 容器限流
 */
public class RateLimitTest extends BaseDaoTest {

    @Test
    public void limitTest() {

        //限流器流速：2个请求/秒
        RateLimiter limiter = RateLimiter.create(2.0);

        //执行任务的线程池
        ExecutorService es = Executors.newFixedThreadPool(1);

        //测试执行20次
        for (int i=0; i<20; i++){
            final Long prev = System.nanoTime();
            //限流器限流
            limiter.acquire();
            //提交任务异步执行
            es.execute(()->{
                long cur=System.nanoTime();
                //打印时间间隔：毫秒
                System.out.println(
                        (cur-prev)/1000_000);
            });
        }
    }

}
