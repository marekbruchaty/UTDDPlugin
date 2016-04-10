package main.java;

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import main.java.gui.MethodGenDialog;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Marek Bruchatý on 30/03/16.
 */
public class TestMethodAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        MethodGenDialog methodGenDialog = new MethodGenDialog();

//        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
//        FileType fileType = psiFile.getFileType();
//        System.out.print(fileType.getName());

    }

    @Override
    public void update(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (psiFile == null || editor == null) {
            e.getPresentation().setEnabled(false);
            return;
        }
        int offset = editor.getCaretModel().getOffset();
        PsiElement psiElement = psiFile.findElementAt(offset);
        PsiClass psiClass = PsiTreeUtil.getParentOfType(psiElement, PsiClass.class);
        if (psiClass == null) {
            e.getPresentation().setEnabled(false);
            return;
        }

        super.update(e);
    }
}
