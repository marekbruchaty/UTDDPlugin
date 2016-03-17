package main.Java;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import main.Java.gui.TestPairDialog;

/**
 * Created by Marek Bruchatý on 17/03/16.
 */
public class TestPairAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        String[] strlst = { "/src/main/Java/" };
        TestPairDialog.main(strlst);
    }
}
