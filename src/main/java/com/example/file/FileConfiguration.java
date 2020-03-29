package com.example.file;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class FileConfiguration
{
    @Bean
    @ConfigurationProperties("file")
    FileStorageProperties fileProperties()
    {
        return new FileStorageProperties();
    }
}
