/**
 * <b>Данный пакет содержит классы и интерфейсы, используемые паттерном "Стратегия".</b>
 * <br>- {@link com.nikguscode.TaskTimer.controller.strategy.interfaces.InlineStrategy InlineStrategy} - интерфейс, который используется
 * для Inline контроллеров.
 * <br>- {@link com.nikguscode.TaskTimer.controller.strategy.interfaces.ReplyStrategy ReplyStrategy} - интерфейс, который используется
 * для Reply контроллеров.
 * <p>
 * <br>- {@link com.nikguscode.TaskTimer.controller.strategy.MenuStrategy MenuStrategy} стратегия для
 * {@link com.nikguscode.TaskTimer.controller.keyboardControllers.MenuController MenuController},
 * реализует {@link com.nikguscode.TaskTimer.controller.strategy.interfaces.InlineStrategy InlineStrategy} и
 * {@link com.nikguscode.TaskTimer.controller.strategy.interfaces.ReplyStrategy ReplyStrategy}.
 * <br>- {@link com.nikguscode.TaskTimer.controller.strategy.TaskStrategy TaskStrategy} стратегия для
 * {@link com.nikguscode.TaskTimer.controller.keyboardControllers.TaskController TaskController}, реализует
 * {@link com.nikguscode.TaskTimer.controller.strategy.interfaces.ReplyStrategy ReplyStrategy}.
 * <br>- {@link com.nikguscode.TaskTimer.controller.strategy.CategoryStrategy CategoryStrategy} стратегия для
 * {@link com.nikguscode.TaskTimer.controller.keyboardControllers.CategoryController CategoryController},
 * реализует {@link com.nikguscode.TaskTimer.controller.strategy.interfaces.InlineStrategy InlineStrategy} и
 * {@link com.nikguscode.TaskTimer.controller.strategy.interfaces.ReplyStrategy ReplyStrategy}.
 * <br>- {@link com.nikguscode.TaskTimer.controller.strategy.CategoryEditStrategy CategoryEditStrategy} стратегия для
 * {@link com.nikguscode.TaskTimer.controller.keyboardControllers.CategoryEditController CategoryEditController}, реализует
 * {@link com.nikguscode.TaskTimer.controller.strategy.interfaces.InlineStrategy InlineStrategy}.
 * <br>- {@link com.nikguscode.TaskTimer.controller.strategy.CategoryListStrategy CategoryListStrategy} стратегия для
 * {@link com.nikguscode.TaskTimer.controller.keyboardControllers.CategoryListController CategoryListController}, реализует
 * {@link com.nikguscode.TaskTimer.controller.strategy.interfaces.InlineStrategy InlineStrategy}
 * </p>
 */


package com.nikguscode.TaskTimer.controller.strategy;