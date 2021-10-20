package com.airtel.scheduler.rmq;

import com.airtel.scheduler.constants.CommonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Arun Singh
 */
@Component
public class RmqProducer {

    private final Logger logger = LoggerFactory.getLogger(RmqProducer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Publishes messages with delay and retry count
     *
     * @param request
     * @param retryCount
     */
    public void publishMessage(Object request, int retryCount, int delay, String exchange, String routingKey) {
        logger.debug("Sendig message in queue with delay:{}", delay);
        rabbitTemplate.convertAndSend(exchange, routingKey, request,
                message -> {
                    message.getMessageProperties().setHeader(CommonConstants.TRACKING_ID,
                            MDC.get(CommonConstants.TRACKING_ID));
                    return message;
                });
    }
}
