package com.android.app.channel.domain.channel.service;

import com.android.app.channel.config.ApkChannelProperties;
import com.android.app.channel.domain.channel.entity.ApkChannelEntity;
//import com.leon.channel.reader.ChannelReader;
import com.leon.channel.reader.ChannelReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ApkChannelServiceTest {

    @Autowired
    private ApkChannelService channelService;

    @Autowired
    private ApkChannelProperties properties;

    @Test
    public void testDoChannel() throws Exception {
        File testFile = ResourceUtils.getFile("classpath:signed-doctor-qa-app-177.apk");
        assertNotNull(testFile);
        assertTrue(testFile.exists());
        File folder = new File(Paths.get("").toFile().getAbsoluteFile(), "apks");
        channelService.doChannel(ApkChannelEntity.builder()
                .apkPath(testFile.getAbsolutePath())
                .destFolder(folder.getAbsolutePath())
                .channels(properties.getChannels())
                .build());
    }

    @Test
    public void testChannel() throws Exception {
        String channel = ChannelReader.getChannelByV1(ResourceUtils.getFile("classpath:huawei-test.apk"));
        System.out.println(channel);
    }

}