package main.java;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;

/**
 * Author: Marek Bruchatý
 * Date: 04/04/16.
 */

public class PopupCreator {

    /**
     * Method creates popup notification for current project extracted from AnActionEvent
     * @param anActionEvent AnActionEvent instance used to get current project
     * @param text Notification message text
     * @param messageType Type of message
     * */
    public static void createPopup(AnActionEvent anActionEvent, String text, MessageType messageType) {
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(DataKeys.PROJECT.getData(anActionEvent.getDataContext()));

        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(text, messageType, null)
                .setFadeoutTime(2500)
                .createBalloon()
                .show(RelativePoint.getCenterOf(statusBar.getComponent()), Balloon.Position.atLeft);
    }

}
