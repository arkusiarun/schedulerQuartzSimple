package com.airtel.scheduler.rmq;

import com.airtel.scheduler.constants.CommonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.util.StopWatch;

import java.util.Optional;


/**
 * @author Arun Singh
 */

public class RmqListner implements MessageListener {

    private ListnerAdaptor listnerAdaptor;

    private Logger logger = LoggerFactory.getLogger(RmqListner.class);

    public RmqListner(ListnerAdaptor adapter) {
        this.listnerAdaptor = adapter;
    }

    @Override
    public void onMessage(Message message) {
        StopWatch watch = new StopWatch();
        watch.start();
        String trackingId = (String) message.getMessageProperties().getHeaders().get(CommonConstants.TRACKING_ID);
        MDC.put(CommonConstants.TRACKING_ID, trackingId);
        Optional<byte[]> optional = Optional.ofNullable(message).map(msg -> msg.getBody());
        if (optional.isPresent()) {
            listnerAdaptor.processMessage(optional);
        } else {
            logger.info("Empty Message Body");
        }
        watch.stop();
        logger.info("Total time taken for Task Completion :: {}", watch.getTotalTimeMillis());
    }
}
