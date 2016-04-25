package main.java;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.pom.Navigatable;
import main.java.gui.ClassGenDialog;

public class TestClassAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {

        Navigatable navigatable = e.getData(CommonDataKeys.NAVIGATABLE);
        String projectPath = e.getData(CommonDataKeys.PROJECT).getBasePath();
        String directoryPath = navigatable.toString().replaceAll("^PsiDirectory:","");

        if (!directoryPath.toLowerCase().contains("/test")) {
            PopupCreator.createPopup(e,"No TEST parent directory found.", MessageType.WARNING);
        } else {
            new ClassGenDialog(directoryPath, projectPath, e);
        }
    }

    @Override
    public void update(AnActionEvent e) {
        e.getPresentation().setEnabled(navigableIsDirectory(e));
    }

    private boolean navigableIsDirectory(AnActionEvent e) {
        final Project project = e.getData(CommonDataKeys.PROJECT);
        final Navigatable navigatable = e.getData(CommonDataKeys.NAVIGATABLE);
        if (project == null || navigatable == null) {
            return false;
        }
        return navigatable.toString().startsWith("PsiDirectory:");
    }
}
