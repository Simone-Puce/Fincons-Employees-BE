package com.fincons.config;

import com.fincons.dto.EmployeeDTO;
import com.fincons.dto.FileDTO;
import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Employee;
import com.fincons.entity.File;
import com.fincons.entity.Project;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@EnableSchedulerLock(defaultLockAtMostFor = "PT5M")// Abilita ShedLock per i lock sulle attività schedulate, con un lock massimo di 5 minuti
@PropertySource("classpath:retryConfig.properties")
@PropertySource("classpath:email.properties")
@PropertySource("classpath:scheduler.properties")
@PropertySource("classpath:template.properties")
public class AppConfig {

    // Metodo per configurare il LockProvider utilizzando JdbcTemplate
    @Bean
    public LockProvider lockProvider(final DataSource dataSource) {
        return new JdbcTemplateLockProvider(dataSource);
    }



    @Bean
    public ModelMapper modelMapperStandard() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Employee, EmployeeDTO>() {
            @Override
            protected void configure() {
                skip(destination.getProjects());
                skip(destination.getFileList());
            }
        });
        return modelMapper;
    }
    @Bean
    public ModelMapper modelMapperSkipEmployeesAndFileInProjects() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<Project, ProjectDTO>() {
            @Override
            protected void configure() {
                skip(destination.getEmployees());
            }
        });
        modelMapper.addMappings(new PropertyMap<File, FileDTO>() {
            @Override
            protected void configure(){
                skip(destination.getFile64());
                skip(destination.getEmpSsn());
            }
        });
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper;
    }
    @Bean
    public ModelMapper modelMapperSkipFile64() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<File, FileDTO>() {
            @Override
            protected void configure(){
                skip(destination.getFile64());
            }
        });
        return modelMapper;
    }
}
