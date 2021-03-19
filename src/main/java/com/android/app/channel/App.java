package com.android.app.channel;

import com.android.app.channel.config.ApkChannelProperties;
import com.android.app.channel.config.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({
        StorageProperties.class,
        ApkChannelProperties.class
})
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
