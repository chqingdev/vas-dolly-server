package com.android.app.channel.utils;

import java.io.IOException;
import java.io.InputStream;

public class CommandHelper {

    public static void execCommand(String... command) throws IOException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(command);
        System.out.printf("command -> %s%n", builder.command());
        Process process = null;
        try {
            process = builder.start();
        } catch (Exception e) {
            if (process != null) {
                InputStream errorStream = process.getErrorStream();
                byte[] buff = new byte[1024];
                int readBytes;
                while ((readBytes = errorStream.read(buff)) > 0) {
                    String log = new String(buff, 0, readBytes);
                    System.out.println(log);
                }
            }
        }
        InputStream inputStream = process.getInputStream();
        byte[] buff = new byte[1024];
        int readBytes;
        while ((readBytes = inputStream.read(buff)) > 0) {
            String log = new String(buff, 0, readBytes);
            System.out.println(log);
        }
        if (!process.isAlive()) {
            InputStream errStream = process.getErrorStream();
            byte[] errBuff = new byte[1024];
            int errReadBytes;
            StringBuilder errorBuilder = new StringBuilder();
            while ((errReadBytes = errStream.read(errBuff)) > 0) {
                errorBuilder.append(new String(errBuff, 0, errReadBytes));
            }

            // throw new CommandExecuteException(errorBuilder.toString());
        }
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
