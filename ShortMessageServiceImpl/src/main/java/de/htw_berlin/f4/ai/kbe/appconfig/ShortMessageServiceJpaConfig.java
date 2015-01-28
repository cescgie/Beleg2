package de.htw_berlin.f4.ai.kbe.appconfig;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@EnableTransactionManagement // für Annotation @Transactional 
@PropertySource("classpath:META-INF/database.properties") // externe properties file koennen verwendet werden.
@Configuration("ShortMessageServiceJpaConfig") // Sagt Spring, wo die beans liegen
public class ShortMessageServiceJpaConfig
{
    // durch Spring injizieren lassen
    @Autowired
    Environment environment;

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername(environment.getProperty("db.username"));
        dataSource.setPassword(environment.getProperty("db.password"));
        dataSource.setUrl(environment.getProperty("db.url"));
        return dataSource;
    }
   
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setPackagesToScan("de.htw_berlin.aStudent.model");
        return localContainerEntityManagerFactoryBean;
    }
    
    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(Boolean.parseBoolean(environment.getProperty("hibernate.show_sql")));
        hibernateJpaVendorAdapter.setGenerateDdl(Boolean.parseBoolean(environment.getProperty("hibernate.generateDdl")));
        hibernateJpaVendorAdapter.setDatabasePlatform(environment.getProperty("hibernate.dialect"));
        return hibernateJpaVendorAdapter;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(entityManagerFactory().getObject());
        return jpaTransactionManager;
    }
    
    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator(){
        return new HibernateExceptionTranslator();
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

   
}