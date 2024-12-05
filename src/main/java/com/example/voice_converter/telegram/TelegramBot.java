package com.example.voice_converter.telegram;

import com.example.voice_converter.domain.User;
import com.example.voice_converter.domain.Voice;
import com.example.voice_converter.domain.enums.UserSteps;
import com.example.voice_converter.dto.json.Model;
import com.example.voice_converter.service.RequestToOpenAI;
import com.example.voice_converter.service.TextToSpeech;
import com.example.voice_converter.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

@Component
@AllArgsConstructor
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final TextToSpeech textToSpeech;
    private final RequestToOpenAI requestToOpenAI;
    private final UserService userService;

    private final ObjectMapper objectMapper;

//    private static final String token = "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";



    @Override
    public String getBotUsername() {
        return "gayratakabot";
    }


    @Override
    public String getBotToken() {
        return "7646446800:AAE5RxUAQEVlVBJ9RsoitNUXm4Cb-4gn6Ps";
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()){
            User user = userService.checkUser(update);
            log.info("USER => {}", user);
            Message message = update.getMessage();

            if(update.getMessage().hasText()){

                switch (user.getStep()){
                    case NEW -> {
                        sendMessage(user.getTelegramId(), "Botga xush kelibsiz!");
                        user.setStep(UserSteps.ANSWER);
                        userService.update(user);
                    }
                    case ANSWER -> {
                        String text = message.getText();
                        Model model = objectMapper.readValue(requestToOpenAI.requestToChatGPT(text), Model.class);
                        String answer = model.getChoices().get(0).getMessage().getContent();
                        Voice voice = textToSpeech.sendToOpenAI(answer, user);
                        sendAudio(user.getTelegramId(), textToSpeech.getVoice(voice.getPath()));
                        sendMessage(user.getTelegramId(), answer);
//                        user.setStep(UserSteps.QUESTION);
//                        userService.update(user);
                    }
                    case QUESTION -> {

                    }
                }
            }

            if(message.hasVoice()){
                System.out.println(message.getAudio().getFileId());
            }
        }
    }

    private void sendMessage(Long userId, String message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(userId);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendAudio(Long id, InputFile file){
        SendAudio sendAudio = new SendAudio();

        sendAudio.setChatId(id);
        sendAudio.setAudio(file);

        try {
            execute(sendAudio);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public Path getFile(String fileId){
        File file = execute(
                GetFile.builder()
                        .fileId(fileId)
                        .build()
        );

        var urlDownloadFile = file.getFileUrl("token");
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
