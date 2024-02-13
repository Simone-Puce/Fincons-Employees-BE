package com.fincons.config;

import com.fincons.dto.RoleDTO;
import com.fincons.dto.UserDTO;
import com.fincons.entity.Role;
import com.fincons.entity.User;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@Configuration
@EnableScheduling //Abilita la schedulazione delle attività
@EnableSchedulerLock(defaultLockAtMostFor = "PT5M")// Abilita ShedLock per i lock sulle attività schedulate, con un lock massimo di 5 minuti
@PropertySource("classpath:retryConfig.properties")
@PropertySource("classpath:email.properties")
@PropertySource("classpath:scheduler.properties")
@PropertySource("classpath:template.properties")
public class AppConfig {

    // Metodo per configurare il LockProvider utilizzando JdbcTemplate
    @Bean
    public LockProvider lockProvider( final DataSource dataSource) {
        return new JdbcTemplateLockProvider(dataSource);
    }


}
