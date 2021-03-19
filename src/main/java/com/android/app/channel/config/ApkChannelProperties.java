package com.android.app.channel.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "forge.channel")
public class ApkChannelProperties {

    List<String> channels;

}
