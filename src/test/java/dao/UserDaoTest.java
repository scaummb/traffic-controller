package dao;

import com.bryant.traffic.mapper.UserMapper;
import com.bryant.traffic.model.User;
import dao.BaseDaoTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDaoTest extends BaseDaoTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testCreate(){
        User user = new User();
        user.setId(10L);
        user.setAge(10);
        user.setName("10");
        userMapper.create(user);
    }

    @Test
    public void testDelete(){
        userMapper.deleteById(10L);
    }
}
