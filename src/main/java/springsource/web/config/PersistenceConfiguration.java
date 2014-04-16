package springsource.web.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource( {"classpath:dataSource.properties"} )
@ComponentScan(basePackages = {"springsource.web.service", 
		"springsource.web.repository", "springsource.web.domain.support"} )
public class PersistenceConfiguration {
	
	@Autowired
	private Environment env;
	
	@Autowired 
	private DataSource dataSource;
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Bean
	public FactoryBean<EntityManagerFactory> entityManagerFactory() {
		
		LocalContainerEntityManagerFactoryBean emfb = 
				new LocalContainerEntityManagerFactoryBean();
		
		emfb.setDataSource(this.dataSource);
		emfb.setJpaVendorAdapter(jpaVendorAdapter());
		emfb.setPersistenceUnitName("springsource");
		//emfb.setPackagesToScan(
		//		new String[] { "springsource.web.domain.SpringPosts" });
		
		return emfb;
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		
		JpaTransactionManager txManager = new JpaTransactionManager();
		
		txManager.setEntityManagerFactory(this.entityManagerFactory);
		txManager.setDataSource(this.dataSource);
		
		return txManager;
	}
	
	@Bean
	public DataSource dataSource() {
		
		BasicDataSource dataSource = new BasicDataSource();
		
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.pass"));
		
		return dataSource;
	}
	
/*	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		
		sessionFactory.setDataSource(restDataSource());
		sessionFactory.setPackagesToScan(
				new String[] { "springsource.web.domain.SpringPosts" });
		sessionFactory.setHibernateProperties(hibernateProperties());
		
		return sessionFactory;
	}
	
	@Bean
	public DataSource restDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.pass"));
		
		return dataSource;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		
		HibernateTransactionManager txManager = 
				new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory().getObject());
		
		return txManager;
	}
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	Properties hibernateProperties() {
		return new Properties() {
			{
				setProperty("hibernate.hbm2ddl.auto", 
						env.getProperty("hibernate.hbm2ddl.auto"));
				setProperty("hibernate.dialect", 
						env.getProperty("hibernate.dialect"));
				setProperty("hibernate.globally_quoted_identifiers", "true");
			}
		};
	}*/
}
