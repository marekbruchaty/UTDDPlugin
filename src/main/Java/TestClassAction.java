package main.java;

import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiFile;
import main.java.gui.ClassGenDialog;

import java.io.File;
import java.io.IOException;

public class TestClassAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here

        Navigatable navigatable = e.getData(CommonDataKeys.NAVIGATABLE);
        String projectPath = e.getData(CommonDataKeys.PROJECT).getBasePath();
        String directoryPath = navigatable.toString().replaceAll("^PsiDirectory:","");

        if (!directoryPath.toLowerCase().contains("/test")) {
            PopupCreator.createPopup(e,"No TEST parent directory found.", MessageType.WARNING);
        } else {
            new ClassGenDialog(directoryPath, projectPath, e);
        }

//        createPopup(e,"", MessageType.INFO);
//        Messages.showInfoMessage("Something","Haha");

    }

    @Override
    public void update(AnActionEvent e) {
        e.getPresentation().setEnabled(navigatableIsDirectory(e));
    }

    private boolean navigatableIsDirectory(AnActionEvent e) {
        final Project project = e.getData(CommonDataKeys.PROJECT);
        final Navigatable navigatable = e.getData(CommonDataKeys.NAVIGATABLE);
        if (project == null || navigatable == null) {
            return false;
        }
        return navigatable.toString().startsWith("PsiDirectory:");
    }
}
