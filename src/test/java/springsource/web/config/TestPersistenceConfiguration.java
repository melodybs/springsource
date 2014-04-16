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
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Configuration
@EnableTransactionManagement
@PropertySource( {"classpath:dataSource.properties"} )
@ComponentScan(basePackages = {"springsource.web.service", 
		"springsource.web.repository", "springsource.web.domain.support"} )
public class TestPersistenceConfiguration {
	
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
		emfb.setPersistenceUnitName("springsourceTest");
		
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
	
	/* H2 Database Test Config */
    @Bean
    public DataSource dataSource() {
        
    	EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        
    	builder.setType(EmbeddedDatabaseType.H2);
        
    	return builder.build();
    } 
	
	/* MySQL Local Test Config 
 	@Bean
	public DataSource dataSource() {
		
		BasicDataSource dataSource = new BasicDataSource();
		
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbc.testUrl"));
		dataSource.setUsername(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.pass"));
		
		return dataSource;
	} */
}
