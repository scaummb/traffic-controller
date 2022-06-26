package com.bryant.traffic;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import javax.sql.DataSource;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan("com.bryant.traffic.*")
public class SpringBootdemoApplication {
 
	public static void main(String[] args) {
	    SpringApplication.run(SpringBootdemoApplication.class, args);
	}

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory createSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
		return createSessionFactory(dataSource);
	}

	@Bean("sqlSessionTemplate")
	public SqlSessionTemplate createSqlSessionTemplate(
			@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	public SqlSessionFactory createSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean.setVfs(SpringBootVFS.class);
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setLogImpl(StdOutImpl.class);
		sessionFactoryBean.setConfiguration(configuration);
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/**/*.xml"));
		return sessionFactoryBean.getObject();
	}

	@Bean("dataSource")
	@Primary
	public DataSource dataSource() throws Exception {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test?"
				+ "rewriteBatchedStatements=true&useServerPrepStmts=true&allowMultiQueries=true"
				+ "&allowPublicKeyRetrieval=true&useSSL=true&serverTimezone=UTC");
		dataSource.setUser("root");
		dataSource.setPassword("12345678");
		dataSource.setMinPoolSize(5);
		dataSource.setMaxPoolSize(5);
		dataSource.setInitialPoolSize(10);
		dataSource.setIdleConnectionTestPeriod(30);
		return dataSource;
	}
}
