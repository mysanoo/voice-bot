package com.example.voice_converter.steps;

import com.example.voice_converter.domain.User;
import com.example.voice_converter.domain.enums.Voices;
import com.example.voice_converter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ChangeVoice {

    private UserService userService;

    private static final Voices[] voices = Voices.values();

    public List<KeyboardRow> sendVoicesButtons(User user){
        List<KeyboardRow> result = new ArrayList<>();
        for (Voices voice : voices) {
            if(!user.getVoice().equals(voice)){
                KeyboardRow row = new KeyboardRow();
                row.add(new KeyboardButton(voice.toString() + " (" + voice.getDescription() + ")"));
                result.add(row);
            }
        }
        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("cancel"));
        result.add(row);
        return result;
    }
}
