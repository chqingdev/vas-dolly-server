package com.android.app.channel.domain.fileupload.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	String store(MultipartFile file);

}
