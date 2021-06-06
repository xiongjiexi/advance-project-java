package org.example;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(SchoolProperties.class)
@Configuration
@ComponentScan
public class SchoolConfiguration {


}
