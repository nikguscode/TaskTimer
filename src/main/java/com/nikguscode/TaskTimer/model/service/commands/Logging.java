package com.nikguscode.TaskTimer.model.service.commands;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Service
public class Logging {

    public static final String notFoundedCommand = """
                            ❌ Кажется, указанная команда не найдена.\s
                            ❓ Используйте "/start\"""";
    public void receivedUndefinedCommand() {
        Class<?> currentClass = getClass();
        log.info("Не найдена команда в " + currentClass.getName());
    }

    public void debugMessage(String message) {
        log.debug("Полученное сообщение: " + message);
    }

    public void getMessage(String message) {
        log.info("Полученное сообщение: " + message);
    }

}
