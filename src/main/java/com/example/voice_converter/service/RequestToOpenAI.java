package com.example.voice_converter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RequestToOpenAI {

    private static final String url = "https://api.openai.com/v1/chat/completions";

    private final RestTemplate restTemplate = new RestTemplate();

    public String requestToChatGPT(String question){
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Bearer " + "TextToSpeech.token");
        headers.add("Content-Type", "application/json");

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "user", "content", question));

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("model", "gpt-4");
        requestBody.put("max_tokens", 200);
        requestBody.put("messages", messages);
        requestBody.put("temperature", 0.7);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
        );

        return getJsonFromResponse(response.toString());
    }


    private String getJsonFromResponse(String response){
        return response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1);
    }
}
