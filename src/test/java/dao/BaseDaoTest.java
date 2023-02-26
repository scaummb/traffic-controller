package dao;

import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@Import({TestMysqlConfig.class})
@ContextConfiguration(classes = BaseDaoTest.class)
@ComponentScan(value = "com.bryant.*")
@MapperScan(basePackages = {"com.bryant.traffic"}, sqlSessionTemplateRef = "sqlSessionTemplate")
class BaseDaoTest extends AbstractJUnit4SpringContextTests {

}
