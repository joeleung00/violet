package com.learn.translation.deepLearnEnglish.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

@Component
@Data
@PropertySource("classpath:application.properties")
public class ChannelWrapper {
    private ManagedChannel channel;

    @Autowired
    private Environment env;
    private String targetUrl;


    @PostConstruct
    private void postConstruct() {
        targetUrl = env.getProperty("mygrpc.config.target-url");
        this.channel = ManagedChannelBuilder.forTarget(this.targetUrl)
                .usePlaintext()
                .build();
    }

    public ManagedChannel getChannel() {
        return this.channel;
    }

    @PreDestroy
    public void preDestroy() throws Exception {
        this.channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }
}
