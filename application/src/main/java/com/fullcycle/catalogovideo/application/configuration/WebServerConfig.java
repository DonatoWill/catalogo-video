package com.fullcycle.catalogovideo.application.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.fullcycle.catalogovideo")
@EntityScan("com.fullcycle.catalogovideo")
@ComponentScan("com.fullcycle.catalogovideo")
public class WebServerConfig {
}
