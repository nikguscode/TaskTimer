    package com.nikguscode.TaskTimer.controller;

    import com.nikguscode.TaskTimer.controller.keyboardControllers.CategoryController;
    import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.InlineController;
    import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.ReplyController;
    import com.nikguscode.TaskTimer.model.dal.AddCategory;
    import com.nikguscode.TaskTimer.model.service.TelegramData;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.beans.factory.annotation.Qualifier;
    import org.springframework.stereotype.Service;
    import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
    import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
    import org.telegram.telegrambots.meta.api.objects.Update;

    @Service
    public class MasterController {

        private final TelegramData telegramData;
        private final ReplyController menuReplyController;
        private final ReplyController categoryReplyController;
        private final ReplyController taskReplyController;
        private ReplyController currentReplyController;
        private final InlineController categoryInlineController;
        private InlineController currentInlineController;
        private final AddCategory addCategory;
        private SendMessage sendMessage;

        @Autowired
        public MasterController(TelegramData telegramData,
                                @Qualifier("menuController") ReplyController menuReplyController,
                                @Qualifier("categoryController") ReplyController categoryReplyController,
                                @Qualifier("categoryController") InlineController categoryInlineController,
                                @Qualifier("taskController") ReplyController taskReplyController,
                                AddCategory addCategory) {
            this.telegramData = telegramData;
            this.menuReplyController = menuReplyController;
            this.taskReplyController = taskReplyController;
            this.categoryReplyController = categoryReplyController;
            this.currentReplyController = menuReplyController;
            this.categoryInlineController = categoryInlineController;
            this.currentInlineController = categoryInlineController;
            this.addCategory = addCategory;
        }

        public void setController() {

            if (telegramData.getMessageText().equals("\uD83D\uDCC1 Управление типами")) {
                currentReplyController = taskReplyController;
            }

            if (telegramData.getMessageText().equals("\uD83C\uDFE0 Вернуться в главное меню")) {
                currentReplyController = menuReplyController;
            }

            if (telegramData.getMessageText().equals("\uD83D\uDCC4 Список категорий")) {
                currentReplyController = categoryReplyController;
            }

            currentReplyController.handleCommands();
        }

        public void setCallbackController(Update update) {
            if (update.hasCallbackQuery()) {

                if (currentReplyController == categoryReplyController) {
                    currentInlineController = categoryInlineController;
                    currentInlineController.handleCommands(update);
                }

            }
        }

        public void setDatabaseController(Update update) {

            if (update.hasMessage()) {
                sendMessage = new SendMessage();
                sendMessage.setChatId(telegramData.getChatId());

                if (currentReplyController instanceof CategoryController) {
                    CategoryController categoryController = (CategoryController) currentReplyController;
                    if (categoryController.isAddCtgSelected()) {
                        addCategory.transaction();
                        categoryController.setAddCtgSelected(false); // указывает на add_ctg callback

                        if (addCategory.isTransacted()) {
                            currentReplyController = menuReplyController;
                            sendMessage.setText("Успешно");
                            addCategory.setTransacted(false); // указывает на проверку повторяющегося category_name
                        } else {
                            sendMessage.setText("Данная категория уже создана, попробуйте указать другое имя");
                            categoryController.setAddCtgSelected(true); // повторный запуск сценария для создания категории
                        }

                    }
                }

            }

        }

        public SendMessage getSendMessage() {
            return currentReplyController.sendMessage();
        }

        public EditMessageText editMessageText() {
            return currentInlineController.sendEditMessage();
        }

        public SendMessage sendMessage() {
            return sendMessage;
        }

    }
