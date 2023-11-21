package com.nikguscode.TaskTimer.controller.strategy.interfaces;

import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.EditMessage;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.UCommandHandler;

public interface InlineStrategy {
    UCommandHandler getUCommandHandler();
    EditMessage getEditSender();
}
