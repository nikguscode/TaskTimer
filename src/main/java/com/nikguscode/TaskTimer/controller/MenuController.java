package com.nikguscode.TaskTimer.controller;

import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
public class MenuController {



    public void mainRedirecting(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/hello":
                    break;
                default:
                    System.out.println("Error");
                    break;
            }

        }
    }
}
