package com.android.app.channel.domain.channel.service;

import com.android.app.channel.common.CommonException;
import com.android.app.channel.domain.channel.entity.ApkChannelEntity;
import com.leon.channel.command.Util;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ApkChannelService {

    public ApkChannelEntity doChannel(ApkChannelEntity entity) {
        // create apks folder
        File destFolder = new File(entity.getDestFolder());
        if (!destFolder.exists() && !destFolder.mkdirs()) {
            throw CommonException.createCommonException("创建目录失败");
        }
        try {
            FileUtils.cleanDirectory(destFolder);
        } catch (IOException e) {
            throw CommonException.createCommonException("清空目录失败");
        }
        // write channels
        Util.writeChannel(new File(entity.getApkPath()), entity.getChannels(),
                destFolder.getAbsoluteFile(), false, false);
        return entity;
    }

}
