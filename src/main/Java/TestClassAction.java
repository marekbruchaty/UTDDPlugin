package main.java;

import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiFile;
import main.java.gui.ClassGenDialog;

public class TestClassAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here

        Navigatable navigatable = e.getData(CommonDataKeys.NAVIGATABLE);
        String projectPath = e.getData(CommonDataKeys.PROJECT).getBasePath();
        String directoryPath = navigatable.toString().replaceAll("^PsiDirectory:","");
        new ClassGenDialog(directoryPath, projectPath);

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
