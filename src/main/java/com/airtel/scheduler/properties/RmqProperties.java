package com.airtel.scheduler.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;


/**
 * @author Arun Singh
 */

@Configuration
@RefreshScope
public class RmqProperties {

    @Value("${rmq.hostList}")
    private String hostList;

    @Value("${rmq.userName}")
    private String userName;

    @Value("${rmq.password}")
    private String password;

    @Value("${rmq.prefetchCount}")
    private Integer prefetchCount;

    @Value("${rmq.consumersPerQueue}")
    private Integer consumersPerQueue;

    @Value("${rmq.exchange}")
    private String exchange;

    @Value("${rmq.queueNames}")
    private String[] queueNames;

    public String getHostList() {
        return hostList;
    }

    public void setHostList(String hostList) {
        this.hostList = hostList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPrefetchCount() {
        return prefetchCount;
    }

    public void setPrefetchCount(Integer prefetchCount) {
        this.prefetchCount = prefetchCount;
    }

    public Integer getConsumersPerQueue() {
        return consumersPerQueue;
    }

    public void setConsumersPerQueue(Integer consumersPerQueue) {
        this.consumersPerQueue = consumersPerQueue;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String[] getQueueNames() {
        return queueNames;
    }

    public void setQueueNames(String[] queueNames) {
        this.queueNames = queueNames;
    }
}
