package com.android.app.channel.domain.channel.facade;

import com.android.app.channel.common.CommonException;
import com.android.app.channel.config.ApkChannelProperties;
import com.android.app.channel.config.StorageProperties;
import com.android.app.channel.domain.channel.entity.ApkChannelEntity;
import com.android.app.channel.domain.channel.model.request.AddChannelRequest;
import com.android.app.channel.domain.channel.model.request.GetChannelRequest;
import com.android.app.channel.domain.channel.model.response.AddChannelResponse;
import com.android.app.channel.domain.channel.model.response.GetChannelResponse;
import com.android.app.channel.domain.channel.service.ApkChannelService;
import com.android.app.channel.utils.ZipUtil;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

@Controller
public class ApkChannelFacade {

    @Autowired
    private ApkChannelService channelService;

    @Autowired
    private StorageProperties storageProperties;

    @Autowired
    private ApkChannelProperties channelProperties;

    @PostMapping("/channel/add-channels")
    public ResponseEntity<AddChannelResponse> addChannel(@RequestBody AddChannelRequest request) throws IOException {
        // zip all channel apk folder
        String path = Paths.get("").toFile().getAbsolutePath();
        File zipOutFolder = new File(path, storageProperties.getChannelApkFolder());
        if (!zipOutFolder.exists() && !zipOutFolder.mkdirs()) {
            throw CommonException.createCommonException("创建目录失败");
        }
        // add channel
        ApkChannelEntity entity = ApkChannelEntity.builder()
                .apkPath(request.getPath())
                .channels(request.getChannels())
                .destFolder(zipOutFolder.getAbsolutePath())
                .build();
        ApkChannelEntity result = channelService.doChannel(entity);
        // resign
        File channeledFolder = new File(result.getDestFolder());
        if (!channeledFolder.exists()) {
            throw CommonException.createDefaultException();
        }
        File[] files = channeledFolder.listFiles();
        if (files == null) {
            throw CommonException.createDefaultException();
        }
        File baseZipApkFolder = new File(Paths.get("").toFile().getAbsolutePath(), storageProperties.getZipApkFolder());
        // compress to zip file
        try {
            if(!baseZipApkFolder.exists() && !baseZipApkFolder.mkdirs()){
                throw CommonException.createCommonException("创建文件夹失败");
            }
            FileUtils.cleanDirectory(baseZipApkFolder);
            File zipFile = new File(baseZipApkFolder, "channels.zip");
            if(zipFile.exists() && !zipFile.delete()){
                throw CommonException.createCommonException("压缩文件失败");
            }
            return ResponseEntity.ok(AddChannelResponse.builder()
                    .url(ZipUtil.zip(Arrays.asList(files), zipFile).getAbsolutePath())
                    .build());
        } catch (IOException e) {
            throw CommonException.createDefaultException();
        }
    }

    @PostMapping("/get-all-channel")
    public ResponseEntity<GetChannelResponse> channels(GetChannelRequest request) {
        return ResponseEntity.ok(GetChannelResponse.builder()
                .channels(channelProperties.getChannels())
                .build());
    }

}
