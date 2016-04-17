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
 * Created by Marek Bruchatý on 04/04/16.
 */
public class PopupCreator {

    public static void createPopup(AnActionEvent e, String str, MessageType messageType) {
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(DataKeys.PROJECT.getData(e.getDataContext()));

        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(str, messageType, null)
                .setFadeoutTime(2500)
                .createBalloon()
                .show(RelativePoint.getCenterOf(statusBar.getComponent()), Balloon.Position.atLeft);
    }

}
