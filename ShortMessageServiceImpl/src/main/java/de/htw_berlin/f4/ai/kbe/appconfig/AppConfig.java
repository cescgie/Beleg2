package de.htw_berlin.f4.ai.kbe.appconfig;

import de.htw_berlin.f4.ai.kbe.appconfig.ShortMessageServiceJpaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@ComponentScan("de.htw_berlin")
@Configuration
public class AppConfig
{
    @Autowired
    ShortMessageServiceJpaConfig dataConfiguration;
} 