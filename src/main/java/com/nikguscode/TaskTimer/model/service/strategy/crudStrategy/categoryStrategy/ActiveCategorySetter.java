package com.nikguscode.TaskTimer.model.service.strategy.crudStrategy.categoryStrategy;

import com.nikguscode.TaskTimer.model.service.crud.Update;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotData;
import org.springframework.stereotype.Service;

@Service
public class ActiveCategorySetter {

    private BotData botData;
    private ActiveCategoryGetter activeCategoryGetter;
    private Update update;

    public ActiveCategorySetter(BotData botData,
                                ActiveCategoryGetter activeCategoryGetter,
                                Update update) {
        this.botData = botData;
        this.activeCategoryGetter = activeCategoryGetter;
        this.update = update;
    }

    public void transaction(String categoryName) {
        activeCategoryGetter.transaction(botData.getChatId());

        if (activeCategoryGetter.getActiveCategory() != null) {
            update.setActive(
                    activeCategoryGetter.getActiveCategory(),
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
