package com.springboot.blog.config;

import com.springboot.blog.utils.AppConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue postViewQueue() {
        return new Queue(AppConstants.POST_VIEW_QUEUE, true); // true để hàng đợi bền vững
    }

    @Bean
    public Queue postLikeQueue() {
        return new Queue(AppConstants.POST_LIKE_QUEUE, true);
    }

    @Bean
    public Queue postDislikeQueue() {
        return new Queue(AppConstants.POST_DISLIKE_QUEUE, true);
    }

    @Bean
    public Queue postNoLikeQueue() {
        return new Queue(AppConstants.POST_NO_LIKE_QUEUE, true);
    }

    @Bean
    public Queue postNoDislikeQueue() {
        return new Queue(AppConstants.POST_NO_DISLIKE_QUEUE, true);
    }

    @Bean
    public Queue commentOnPostQueue() {
        return new Queue(AppConstants.COMMENT_ON_POST_QUEUE, true);
    }

    @Bean
    public Queue deleteCommentOnPostQueue() {
        return new Queue(AppConstants.DELETE_COMMENT_ON_POST_QUEUE, true);
    }


    @Bean
    public Queue commentLikeQueue() {
        return new Queue(AppConstants.COMMENT_LIKE_QUEUE, true);
    }

    @Bean
    public Queue commentDislikeQueue() {
        return new Queue(AppConstants.COMMENT_DISLIKE_QUEUE, true);
    }

    @Bean
    public Queue commentNoLikeQueue() {
        return new Queue(AppConstants.COMMENT_NO_LIKE_QUEUE, true);
    }

    @Bean
    public Queue commentNoDislikeQueue() {
        return new Queue(AppConstants.COMMENT_NO_DISLIKE_QUEUE, true);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

//    @Bean
//    public MessageConverter jsonMessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }



    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(producerJackson2MessageConverter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                               Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jackson2JsonMessageConverter);
        return factory;
    }

//    @Bean
//    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
//        return new RabbitAdmin(connectionFactory);
//    }

//    @Bean
//    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(AppConstants.POST_VIEW_QUEUE, AppConstants.POST_LIKE_QUEUE, AppConstants.POST_DISLIKE_QUEUE);
//        container.setAutoStartup(true);
//        container.setAutoDeclare(false); // Tắt kiểm tra tự động hàng đợi
//        return container;
//    }
}
