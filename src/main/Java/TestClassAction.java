package main.Java;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.graph.io.gml.ObjectEncoder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.impl.SystemDock;
import main.Java.gui.TestPairDialog;

/**
 * Created by Marek Bruchatý on 18/03/16.
 */
public class TestClassAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        String[] strlst = { "/src/main/Java/" };

        Object navigatable = e.getData(CommonDataKeys.NAVIGATABLE);
        Object navigatable2 = e.getData(CommonDataKeys.NAVIGATABLE_ARRAY);
        System.out.print(navigatable2);
        if (navigatable != null) {
//            Messages.showDialog(navigatable.toString(), "Selected Element:", new String[]{"OK"}, -1, null);
            String dirPath = navigatable.toString().replaceAll("^PsiDirectory:","");

            TestPairDialog dialog = new TestPairDialog(new String[]{dirPath});
            dialog.pack();
            dialog.setVisible(true);

        }
    }

    @Override
    public void update(AnActionEvent e) {
        final Project project = e.getData(CommonDataKeys.PROJECT);
        if (project == null)
            return;
        Object navigatable = e.getData(CommonDataKeys.NAVIGATABLE);
        //navigatable = "PsiDirectory:/Users/Marek/Workspace/Java/UTDDPluginTesting/src/main"
        String projectPath = navigatable.toString();
        Boolean isDirectory = projectPath.startsWith("PsiDirectory:");
        e.getPresentation().setEnabledAndVisible(isDirectory);
    }
}
