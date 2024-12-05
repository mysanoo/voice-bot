package com.example.voice_converter;

import com.example.voice_converter.telegram.TelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class VoiceConverterApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(VoiceConverterApplication.class);
		try{
			TelegramBotsApi telegramBot = new TelegramBotsApi(DefaultBotSession.class);
			telegramBot.registerBot(context.getBean(TelegramBot.class));
		} catch (TelegramApiException e) {
			throw new RuntimeException(e);
		}
	}

}
