package com.nikguscode.TaskTimer.model.service;

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
    public void receivedUndefinedCommand(Class<?> currentClass) {
        log.warn("Не найдена команда в {}", currentClass.getName());
    }

    public void callbackUndefinedError(Class<?> currentClass) {
        log.warn("Не найден callback в {}", currentClass.getName());
    }

    public void getMessage(String message) {
        log.info("Полученное сообщение: {}", message);
    }

}
