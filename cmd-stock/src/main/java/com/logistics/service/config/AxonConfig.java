package com.logistics.service.config;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.common.Registration;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Autowired
    Registration registerInterceptors(CommandBus commandBus) {
        return commandBus.registerHandlerInterceptor(new BeanValidationInterceptor<Message<?>>());
    }

    @Bean
    SpringAggregateSnapshotterFactoryBean springAggregateSnapshotterFactoryBean() {
        return new SpringAggregateSnapshotterFactoryBean();
    }

}
