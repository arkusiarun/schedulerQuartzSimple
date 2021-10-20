package com.airtel.scheduler.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author Arun Singh
 */

@Configuration
@RefreshScope
public class MongoProperties {

    @Value("${scheduler.mongodb.max.read.timeout}")
    private int readTimeOut;

    @Value("${scheduler.mongodb.database}")
    private String primaryDatabase;

    @Value("${scheduler.mongodb.connect.core.connection}")
    private int coreConnection;

    @Value("${scheduler.mongodb.connect.min.connection}")
    private int minConnection;

    @Value("${scheduler.mongodb.max.wait.time}")
    private int maxWaitTime;

    @Value("${scheduler.mongodb.max.connect.timeout}")
    private int connectTimeout;

    @Value("${scheduler.mongodb.max.connect.idle.timeout}")
    private int connectIdleTimeout;

    @Value("${scheduler.mongodb.max.connect.life.timeout}")
    private int connectLifeTimeout;

    @Value("${scheduler.mongodb.uri}")
    private String uri;

    @Value("${scheduler.mongodb.socketKeepAlive}")
    private boolean socketKeepAlive;

    @Value("${scheduler.mongodb.auth:}")
    private String auth;

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public String getPrimaryDatabase() {
        return primaryDatabase;
    }

    public void setPrimaryDatabase(String primaryDatabase) {
        this.primaryDatabase = primaryDatabase;
    }

    public int getCoreConnection() {
        return coreConnection;
    }

    public void setCoreConnection(int coreConnection) {
        this.coreConnection = coreConnection;
    }

    public int getMinConnection() {
        return minConnection;
    }

    public void setMinConnection(int minConnection) {
        this.minConnection = minConnection;
    }

    public int getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(int maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getConnectIdleTimeout() {
        return connectIdleTimeout;
    }

    public void setConnectIdleTimeout(int connectIdleTimeout) {
        this.connectIdleTimeout = connectIdleTimeout;
    }

    public int getConnectLifeTimeout() {
        return connectLifeTimeout;
    }

    public void setConnectLifeTimeout(int connectLifeTimeout) {
        this.connectLifeTimeout = connectLifeTimeout;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isSocketKeepAlive() {
        return socketKeepAlive;
    }

    public void setSocketKeepAlive(boolean socketKeepAlive) {
        this.socketKeepAlive = socketKeepAlive;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
}
