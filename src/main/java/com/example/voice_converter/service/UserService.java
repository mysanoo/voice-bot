package com.example.voice_converter.service;

import com.example.voice_converter.domain.User;
import com.example.voice_converter.domain.enums.UserSteps;
import com.example.voice_converter.domain.enums.Voices;
import com.example.voice_converter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User checkUser(Update update){
        Optional<User> user = userRepository.findByTelegramId(update.getMessage().getChatId());

        return user.orElseGet(() -> userRepository.save(
                User.builder()
                    .telegramId(update.getMessage().getChatId())
                    .step(UserSteps.NEW)
                    .voice(Voices.NOVA)
                .build()));
    }

    public User update(User user){
        return userRepository.saveAndFlush(user);
    }
}
