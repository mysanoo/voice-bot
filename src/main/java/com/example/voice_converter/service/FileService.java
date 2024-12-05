package com.example.voice_converter.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

@Service
@AllArgsConstructor
public class FileService {

    private final DefaultAbsSender sender;

    private static final String token = "7539584164:AAGUtBBZxX76ySDbyfXFfI16RjtT9oTiijk";

    @SneakyThrows
    public Path getFile(String fileId){
        File file = sender.execute(
                GetFile.builder()
                        .fileId(fileId)
                        .build()
        );

        var urlDownloadFile = file.getFileUrl(token);
        return getByteArrayFromUrl(urlDownloadFile);
    }

    @SneakyThrows
    private Path getByteArrayFromUrl(String urlDownloadFile){
        URL url = new URI(urlDownloadFile).toURL();
        Path path = Path.of("D:\\G19\\bots\\voice-converter\\user-voices\\voice.mp3");
        try(
            InputStream inputStream = url.openStream();
            OutputStream os = new FileOutputStream(path.toFile())
        ){

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

            return path;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
