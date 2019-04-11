package com.taotao.spring.config.activemq;

import javax.jms.Destination;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class ActiveMQConfig{
	
	@Value("${topicName}")
    private String topicName;
	
	@Value("${spring.activemq.user}")
    private String usrName;

    @Value("${spring.activemq.password}")
    private  String password;

    @Value("${spring.activemq.broker-url}")
    private  String brokerUrl;

    @Bean
    @Scope(value="prototype")
    public Destination destination(){
        return new ActiveMQTopic(topicName);
    }
    
    @Bean
    @Scope(value="prototype")
    public ActiveMQConnectionFactory activeMQConnectionFactory(){
    	return new ActiveMQConnectionFactory(usrName, password, brokerUrl);
    }
    
    @Bean
    @Scope(value="prototype")
    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory connectionFactory){
    	SingleConnectionFactory singleConnectionFactory = new SingleConnectionFactory(connectionFactory);
    	return new JmsTemplate(singleConnectionFactory);
    }
	
}
