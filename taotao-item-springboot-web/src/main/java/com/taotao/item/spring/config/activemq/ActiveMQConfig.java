package com.taotao.item.spring.config.activemq;

import javax.jms.Destination;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.taotao.item.listener.ItemChangeMessageListener;

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
    @ConditionalOnMissingBean
    public Destination destination(){
        return new ActiveMQTopic(topicName);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public ActiveMQConnectionFactory activeMQConnectionFactory(){
    	return new ActiveMQConnectionFactory(usrName, password, brokerUrl);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory connectionFactory){
    	SingleConnectionFactory singleConnectionFactory = new SingleConnectionFactory(connectionFactory);
    	return new JmsTemplate(singleConnectionFactory);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public DefaultMessageListenerContainer defaultMessageListenerContainer(ActiveMQConnectionFactory connectionFactory
    									, ItemChangeMessageListener itemChangeMessageListener, Destination destination){
    	DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
    	defaultMessageListenerContainer.setConnectionFactory(new SingleConnectionFactory(connectionFactory));
    	defaultMessageListenerContainer.setDestination(destination);
    	defaultMessageListenerContainer.setMessageListener(itemChangeMessageListener);
    	System.out.println(">>>>>>>>>>" + itemChangeMessageListener);
    	return defaultMessageListenerContainer;
    }
	
}
