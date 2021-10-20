package com.airtel.scheduler.config;

import com.airtel.scheduler.constants.CommonConstants;
import com.airtel.scheduler.properties.MongoProperties;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Arun Singh
 */

@Configuration
public class MongoClientConfig extends AbstractMongoConfiguration {

    @Autowired
    private MongoProperties mongoProperties;

    @Bean(name = "mongoTemplate")
    public MongoTemplate primaryMongoTemplate(MongoDbFactory primaryMongoDBFactory,
                                              MappingMongoConverter converter) throws Exception {
        return new MongoTemplate(primaryMongoDBFactory, converter);
    }

    @Override
    @Bean(name = "primaryMongoDbClient")
    public MongoClient mongoClient() {
        String authModString = StringUtils.isNotBlank(mongoProperties.getAuth()) ? (mongoProperties.getAuth() + CommonConstants.MONGO_URI_AUTH_SEPARATOR) : "";
        String modUri = CommonConstants.MONGO_URI_PREFIX + authModString + mongoProperties.getUri();
        return new MongoClient(new MongoClientURI(modUri,
                MongoClientOptions.builder().minConnectionsPerHost(mongoProperties.getMinConnection()).connectionsPerHost(mongoProperties.getCoreConnection())
                        .connectTimeout(mongoProperties.getConnectTimeout()).maxConnectionIdleTime(mongoProperties.getConnectIdleTimeout())
                        .maxConnectionLifeTime(mongoProperties.getConnectLifeTimeout()).maxWaitTime(mongoProperties.getMaxWaitTime())
                        .socketKeepAlive(mongoProperties.isSocketKeepAlive()).socketTimeout(mongoProperties.getReadTimeOut())));
    }

    @Bean(name = "primaryMongoDBFactory")
    public MongoDbFactory primaryMongoDBFactory(MongoClient primaryMongoDbClient) throws Exception {
        return new SimpleMongoDbFactory(primaryMongoDbClient, mongoProperties.getPrimaryDatabase());
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        return Collections.singleton(CommonConstants.BASE_PACKAGE);
    }

    @Override
    protected String getDatabaseName() {
        return mongoProperties.getPrimaryDatabase();
    }
}
