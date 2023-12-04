package com.nikguscode.TaskTimer.model.service.strategy.messageStrategy;

import com.nikguscode.TaskTimer.model.PhraseConstants;
import com.nikguscode.TaskTimer.model.repository.CategoryRepository;
import com.nikguscode.TaskTimer.model.service.crud.Add;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@Slf4j
public class CategoryAdder {

    private final BotData botData;
    private final Add add;
    private final SendMessage sendMessage;
    private final CategoryRepository categoryRepository;

    public CategoryAdder(BotData botData,
                         Add add,
                         CategoryRepository categoryRepository) {
        this.botData = botData;
        this.add = add;
        this.categoryRepository = categoryRepository;

        sendMessage = new SendMessage();
    }

    public SendMessage transaction(int length) {
        sendMessage.setChatId(botData.getChatId());

        if (length > 26) {
            log.warn("Длина создаваемой категории больше 26 символов");
            sendMessage.setText("Длина создаваемой категории не может быть больше 26 символов");
        } else if (categoryRepository.existsByCategoryNameAndUserId(botData.getCategoryName(), botData.getChatId())) {
            log.warn(
                    "Категория с названием: {}, уже существует для userId: {}",
                    botData.getCategoryName(),
                    botData.getChatId()
            );

            sendMessage.setText("Категория с таким названием уже существует");
        } else {
            add.addCategory(
                    botData.getCategoryName(),
                    botData.getCategoryDescription(),
                    botData.getChatId()
            );

            sendMessage.setText(PhraseConstants.CREATED_CATEGORY);
        }

        return sendMessage;
    }

}
