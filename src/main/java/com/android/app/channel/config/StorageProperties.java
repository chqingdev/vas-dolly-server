package com.android.app.channel.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("forge.storage.upload-path")
public class StorageProperties {

	/**
	 * Folder location for storing files
	 */
	private String location ;

	private String channelApkFolder;

	private String zipApkFolder;

}
