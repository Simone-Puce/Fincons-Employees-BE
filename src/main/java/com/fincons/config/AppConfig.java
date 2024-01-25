package com.fincons.config;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@Configuration
@EnableScheduling //Abilita la schedulazione delle attività
@EnableSchedulerLock(defaultLockAtMostFor = "PT5M")// Abilita ShedLock per i lock sulle attività schedulate, con un lock massimo di 5 minuti
@PropertySource("retryConfig.properties")
@PropertySource("email.properties")
@PropertySource("scheduler.properties")
@PropertySource("template.properties")
public class AppConfig {

    // Metodo per configurare il LockProvider utilizzando JdbcTemplate
    @Bean
    public LockProvider lockProvider( final DataSource dataSource) {
        return new JdbcTemplateLockProvider(dataSource);
    }

    @Bean
    public ModelMapper modelMapperEmployee() {
        return new ModelMapper();
    }
    @Bean
    public ModelMapper modelMapperProject() {
        return new ModelMapper();
    }
    @Bean
    public ModelMapper modelMapperDepartment() {
        return new ModelMapper();
    }
    @Bean
    public ModelMapper modelMapperPosition() {
        return new ModelMapper();
    }



}
