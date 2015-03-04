package org.screwdriver.config;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.googlecode.flyway.core.Flyway;

@Configuration
@EnableJpaRepositories("org.screwdriver.repository")
@PropertySource("classpath:screwdriver.properties")
@EnableTransactionManagement
public class DataSourceConfig {
	@Value("${database.driver}")
	private String databaseDriver;
	@Value("${database.url}")
	private String databaseUrl;
	@Value("${database.username}")
	private String databaseUsername;
	@Value("${database.password}")
	private String databasePassword;
	@Value("${database.show.sql}")
	private boolean databaseShowSql;
	@Value("${database.type}")
	private Database databaseType;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(databaseDriver);
		dataSource.setUrl(databaseUrl);
		dataSource.setUsername(databaseUsername);
		dataSource.setPassword(databasePassword);
		return dataSource;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory()
				.getObject());
		return transactionManager;
	}

	@Bean(initMethod = "migrate")
	public Flyway flyway() {
		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource());
		return flyway;
	}

	@Bean
	@DependsOn("flyway")
	public SessionFactory sessionFactory() throws HibernateException {
		return new LocalSessionFactoryBuilder(dataSource())
				.buildSessionFactory();
	}

	@Bean
	public EntityManager entityManger() {
		return entityManagerFactory().getObject().createEntityManager();
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setShowSql(databaseShowSql);
		vendorAdapter.setGenerateDdl(false);
		vendorAdapter.setDatabase(databaseType);

		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
		entityManagerFactory.setPackagesToScan("org.screwdriver.entity");

		return entityManagerFactory;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
