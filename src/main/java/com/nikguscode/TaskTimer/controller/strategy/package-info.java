/**
 * This class contains strategy pattern for controllers.
 * If a class contains "CB" in the class name, it means that this class implements callback logic.
 * Other classes implement the response logic.
 * Strategy is only needed to switch between the following controllers:
 * - {@code MenuController}
 * - {@code TaskController}
 * - {@code CategoryController}
 */

package com.nikguscode.TaskTimer.controller.strategy;