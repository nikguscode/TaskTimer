package com.nikguscode.TaskTimer.model;

import com.nikguscode.TaskTimer.view.EmojiConstants;

public class PhraseConstants {

    // общие фразы
    public static final String SUCCESSFULLY = "✅ Успешно";
    public static final String CREATED_CATEGORY = "✅ Категория успешно создана";
    public static final String RESTART_BOT_CMD = "/start";
    public static final String NEXT_PAGE = "▶️";
    public static final String PREVIOUS_PAGE = "◀️";
    public static final String CURRENT_PAGE = EmojiConstants.LIST_ICON + "Страница: ";
    public static final String BACK = EmojiConstants.BACK_ICON + " Вернуться назад";
    public static final String SELECT_WHAT_TO_DO = EmojiConstants.REFLECTION_ICON + " Выберите операцию:";
    public static final String SELECT_WHAT_TO_EDIT = EmojiConstants.REFLECTION_ICON + " Выберите, что редактировать:";

    // команды menuController
    public static final String STATISTICS = EmojiConstants.STATISTICS_ICON + " Статистика";
    public static final String TYPE_MANAGEMENT = EmojiConstants.FOLDER_ICON + " Управление типами";
    public static final String START_TIMER = EmojiConstants.START_ICON + " Начать работу";
    public static final String STOP_TIMER = EmojiConstants.END_ICON + " Завершить работу";

    // таймер
    public static final String STARTED_TIMER = EmojiConstants.SUCCESFULLY_ICON + " Таймер запущен";
    public static final String ERROR_STARTED_TIMER = "❌ Ошибка. Кажется, таймер уже запущен";
    public static final String STOPPED_TIMER = EmojiConstants.SUCCESFULLY_ICON + " Таймер остановлен, время работы: ";
    public static final String ERROR_STOPPED_TIMER = "❌ Ошибка. Кажется, Вы ещё не запускали таймер";

    // команды taskController
    public static final String ACTIVE_CATEGORY = EmojiConstants.ACTIVE_CTG_ICON + " Активная категория";
    public static final String CATEGORY_LIST = EmojiConstants.LIST_ICON + " Список категорий";
    public static final String BACK_TO_MENU = EmojiConstants.BACK_TO_MENU_ICON + " Вернуться в главное меню";

    // команды categoryController
    public static final String ADD_CATEGORY = EmojiConstants.PLUS_ICON + " Добавить категорию";
    public static final String LIST_OF_CATEGORIES = EmojiConstants.FOLDER_ICON + " Список созданных категорий";

    // команды для работы с категориями
    public static final String EDIT_CATEGORY = EmojiConstants.PENCIL_ICON + " Редактировать";
    public static final String DELETE_CATEGORY = EmojiConstants.DELETE_ICON + " Удалить";

    // команды для редактирования категорий
    public static final String EDIT_CATEGORY_NAME = "Название";
    public static final String EDIT_CATEGORY_DESCRIPTION = "Описание";
    public static final String EDIT_ACTIVE_CATEGORY = EmojiConstants.ACTIVE_CTG_ICON + " Сделать активной";

    // ответы бота
    public static final String SELECT_CATEGORY = EmojiConstants.SEARCH_ICON + " Выберите категорию:";
    public static final String SELECTED_TYPE_MANAGEMENT =
            EmojiConstants.TUNING_ICON +
            " Выбрана категория: управление типами";
    public static final String SELECTED_LIST_OF_CATEGORY =
            EmojiConstants.LIST_OF_CTG_ICON +
            " Выбрана категория: список категорий";
    public static final String ADD_CATEGORY_RESPONSE =
            """
                    Введите: "Название категории | описание категории" через "|".\s

                    Пример: Выполнение Д/З | Отсчитывает время выполнения Д/З""";

    // callback-идентификаторы
    public static final String CB_BACK_TO_MENU = "menu_btn";
    public static final String CB_CATEGORY_LIST = "list_of_ctg";
    public static final String CB_ADD_CATEGORY = "add_ctg";
    public static final String CB_NEXT_PAGE = "next_page";
    public static final String CB_PREVIOUS_PAGE = "previous_page";
    public static final String CB_BACK_1 = "back_btn_1";
    public static final String CB_BACK_2 = "back_btn_2";
    public static final String CB_BACK_3 = "back_btn_3";
    public static final String CB_EDIT_CATEGORY_NAME = "edit_category_name";
    public static final String CB_EDIT_CATEGORY_DESCRIPTION = "edit_category_description";
    public static final String CB_EDIT_ACTIVE_CTG = "edit_active_ctg";
    public static final String CB_EDIT_CATEGORY = "edit_category";
    public static final String CB_DELETE_CATEGORY = "delete_category";

}
