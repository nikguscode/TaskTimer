package com.nikguscode.TaskTimer.model.service.strategy.crudStrategy;

import com.nikguscode.TaskTimer.model.service.crud.Update;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotData;
import org.springframework.stereotype.Service;

@Service
public class SetActiveCategory {

    private BotData botData;
    private GetActiveCategory getActiveCategory;
    private Update update;

    public SetActiveCategory(BotData botData,
                             GetActiveCategory getActiveCategory,
                             Update update) {
        this.botData = botData;
        this.getActiveCategory = getActiveCategory;
        this.update = update;
    }

    public void transaction(String categoryName) {
        getActiveCategory.transaction(botData.getChatId());

        if (getActiveCategory.getActiveCategory() != null) {
            update.setActive(
                    getActiveCategory.getActiveCategory(),
                    botData.getChatId(),
                    false
            );

            update.setActive(
                    categoryName,
                    botData.getChatId(),
                    true
            );
        } else {
            update.setActive(
                    categoryName,
                    botData.getChatId(),
                    true
            );
        }
    }


}
