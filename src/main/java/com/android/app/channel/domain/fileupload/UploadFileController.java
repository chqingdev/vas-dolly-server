package com.android.app.channel.domain.fileupload;

import com.android.app.channel.domain.fileupload.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Paths;


@Controller
public class UploadFileController {

    private final StorageService storageService;

    @Autowired
    public UploadFileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload-file")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                   RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");


        return ResponseEntity.ok(storageService.store(file));
    }

    @GetMapping("/download-channels-file")
    public void downloadFile(HttpServletResponse response) {
        File zipFile = new File(Paths.get("").toFile().getAbsolutePath(), "zipApks/channels.zip");
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(zipFile))) {
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", zipFile.getName()));
            byte[] buff = new byte[1024 * 1024 * 4];
            int readBytes = inputStream.read(buff);
            try (OutputStream outStream = response.getOutputStream()) {
                while (readBytes > -1) {
                    outStream.write(buff, 0, readBytes);
                    outStream.flush();
                    readBytes = inputStream.read(buff);
                }
            }
        } catch (Exception e) {
            try {
                response.sendError(500);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            e.printStackTrace();
        }
    }

}