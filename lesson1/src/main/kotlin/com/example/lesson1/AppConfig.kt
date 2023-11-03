package com.example.lesson1

import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.core.io.ClassPathResource
import java.nio.file.Path

@Configuration
@ComponentScan("com.example")
class AppConfig {
    companion object {
        @Bean
        fun properties(): PropertySourcesPlaceholderConfigurer {
            val configurer = PropertySourcesPlaceholderConfigurer()
            val yamlPropertiesFactoryBean = YamlPropertiesFactoryBean()
            yamlPropertiesFactoryBean.setResources(ClassPathResource("application.yml"))
            yamlPropertiesFactoryBean.`object`?.let { configurer.setProperties(it) }

            return configurer
        }
    }

    @Bean
    fun contactService(@Value("\${app.contacts.path}") path: String): ContactService {
        return ContactService(Path.of(path))
    }
}
