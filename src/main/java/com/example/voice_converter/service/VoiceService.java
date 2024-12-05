package com.example.voice_converter.service;

import com.example.voice_converter.domain.User;
import com.example.voice_converter.domain.Voice;
import com.example.voice_converter.repository.VoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class VoiceService {

    private final VoiceRepository voiceRepository;

    public Voice createVoice(User user, String path){
        return voiceRepository.save(Voice.builder()
                        .path(path)
                        .user(user)
                .build());
    }
}
