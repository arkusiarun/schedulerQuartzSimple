package com.airtel.scheduler.config;

import com.airtel.scheduler.properties.RmqProperties;
import com.airtel.scheduler.rmq.ListnerAdaptor;
import com.airtel.scheduler.rmq.RmqListner;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


/**
 * @author Arun Singh
 */

@Configuration
public class RmqConfig {

    @Autowired
    private RmqProperties rmqProperties;

    @Bean
    public RmqListner getWorkListener() {
        return new RmqListner(getListnerAdapter());
    }

    @Bean
    public ListnerAdaptor getListnerAdapter() {
        return new ListnerAdaptor();
    }

    @Bean
    @Primary
    public ConnectionFactory defaultConnectionFactory() {
        CachingConnectionFactory cf = new CachingConnectionFactory();
        cf.setAddresses(rmqProperties.getHostList());
        cf.setUsername(rmqProperties.getUserName());
        cf.setPassword(rmqProperties.getPassword());
        return cf;
    }

    @Bean
    @Primary
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter(new ObjectMapper());
    }

    @Bean
    @Primary
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Primary
    @Bean("messageListenerContainer")
    public DirectMessageListenerContainer listenerContainer(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        DirectMessageListenerContainer listenerContainer = new DirectMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setQueueNames(rmqProperties.getQueueNames());
        listenerContainer.setMessageConverter(jsonMessageConverter);
        listenerContainer.setMessageListener(getWorkListener());
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        listenerContainer.setPrefetchCount(rmqProperties.getPrefetchCount());
        listenerContainer.setConsumersPerQueue(rmqProperties.getConsumersPerQueue());
        listenerContainer.setDefaultRequeueRejected(false);
        return listenerContainer;
    }
}