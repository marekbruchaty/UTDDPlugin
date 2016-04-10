package main.java;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import main.java.gui.ClassGenDialog;
import static main.java.PopupCreator.createPopup;

/**
 * Created by Marek Bruchatý on 18/03/16.
 */
public class TestClassAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        String[] strlst = { "/src/main/Java/" };

        Object navigable = e.getData(CommonDataKeys.NAVIGATABLE);
        Object navigable2 = e.getData(CommonDataKeys.NAVIGATABLE_ARRAY);
        System.out.print(navigable2);
        if (navigable != null) {
            String dirPath = navigable.toString().replaceAll("^PsiDirectory:","");

            ClassGenDialog dialog = new ClassGenDialog(new String[]{dirPath});
            dialog.pack();
            dialog.setVisible(true);

        }

        createPopup(e,"", MessageType.INFO);

        //Messages.showInfoMessage("Something","Haha");

    }

    @Override
    public void update(AnActionEvent e) {
        final Project project = e.getData(CommonDataKeys.PROJECT);
        if (project == null)
            return;
        Object navigatable = e.getData(CommonDataKeys.NAVIGATABLE);
        String projectPath = navigatable.toString();
        System.out.println(projectPath);
        Boolean isDirectory = projectPath.startsWith("PsiDirectory:");
//        e.getPresentation().setEnabledAndVisible(isDirectory);
        e.getPresentation().setEnabled(isDirectory);
    }
}
