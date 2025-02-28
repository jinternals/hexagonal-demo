package com.jinternals.demo.infrastructure.configuration;

import com.couchbase.client.core.msg.kv.DurabilityLevel;
import com.couchbase.client.java.env.ClusterEnvironment;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Duration;

@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties(CouchbaseProperties.class)
@AllArgsConstructor
public class CouchbaseConfiguration extends AbstractCouchbaseConfiguration {
    private CouchbaseProperties couchbaseProperties;

    @Override
    public String getConnectionString() {
        return couchbaseProperties.getConnectionString();
    }

    @Override
    public String getUserName() {
        return couchbaseProperties.getUsername();
    }

    @Override
    public String getPassword() {
        return couchbaseProperties.getPassword();
    }

    @Override
    public String getBucketName() {
        return "orders";
    }

    @Override
    protected void configureEnvironment(final ClusterEnvironment.Builder builder) {
        builder
                .transactionsConfig(bldr -> bldr.durabilityLevel(DurabilityLevel.NONE)
                        .timeout(Duration.ofSeconds(30))
                        .build());
    }

}