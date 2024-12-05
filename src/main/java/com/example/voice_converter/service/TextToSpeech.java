package com.example.voice_converter.service;

import com.example.voice_converter.domain.User;
import com.example.voice_converter.domain.Voice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TextToSpeech {

    private final VoiceService voiceService;
    public static final String token = "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";
    private static final String url = "https://api.openai.com/v1/audio/speech";
    private final RestTemplate restTemplate = new RestTemplate();


    private static final Path root = Paths.get(System.getProperty("user.dir"), "voices");

    private void init(){
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Voice sendToOpenAI(String text, User user){
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-Type", "application/json");

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("model", "tts-1");
        requestBody.put("input", text);
        requestBody.put("voice", user.getVoice().name());

        HttpEntity<Map<String, Object>> request =new HttpEntity<>(requestBody, headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.POST, request, byte[].class);

        return saveVoice(response.getBody(), user);
    }

    private Voice saveVoice(byte[] array, User user){
        String name = UUID.randomUUID() + ".mp3";
        if(!Files.exists(root)){
            init();
        }
        Path savedPath = root.resolve(name);

        try(OutputStream os = new FileOutputStream(savedPath.toFile())){
            os.write(array);
            return voiceService.createVoice(user, savedPath.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public InputFile getVoice(String path){
        InputFile inputFile = new InputFile();
        File mediaFile = new File(Path.of(path).toUri());
        inputFile.setMedia(mediaFile);
        return inputFile;
    }

}
