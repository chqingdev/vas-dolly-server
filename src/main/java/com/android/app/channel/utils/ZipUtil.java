package com.android.app.channel.utils;

import com.android.app.channel.common.CommonException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

public class ZipUtil {

    public static File zip(List<File> sourceFiles, File destFolder) throws IOException {
        try (ArchiveOutputStream outputStream = new ZipArchiveOutputStream(new FileOutputStream(destFolder))) {
            for (File each : sourceFiles) {
                outputStream.putArchiveEntry(outputStream.createArchiveEntry(each, each.getName()));
                if (each.isFile()) {
                    try (InputStream stream = Files.newInputStream(each.toPath())) {
                        IOUtils.copy(stream, outputStream);
                    }
                }
                outputStream.closeArchiveEntry();
            }

            outputStream.finish();
        } catch (IOException e) {
            throw CommonException.createDefaultException();
        }

        return destFolder;
    }
}
