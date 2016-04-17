package main.java;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;
import main.java.gui.MethodGenDialog;

/**
 * Created by Marek Bruchatý on 30/03/16.
 *
 */
public class TestMethodAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        MethodGenDialog methodGenDialog = new MethodGenDialog();
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        PsiClass psiClass = getPsiClassFromContext(e);

        addMethodToPsiClass(psiClass,methodGenDialog.getMethodPrototype().constructTestMethod());
        FileType fileType = psiFile.getFileType();
        System.out.print(fileType.getName());

    }

    @Override
    public void update(AnActionEvent e) {
        PsiClass psiClass = getPsiClassFromContext(e);
        if (psiClass == null) {
            e.getPresentation().setEnabled(false);
            return;
        }

        super.update(e);
    }

    private void addMethodToPsiClass(PsiClass psiClass, String newMethod) {
        new WriteCommandAction.Simple(psiClass.getProject(), psiClass.getContainingFile()) {

            @Override
            protected void run() throws Throwable {
                PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiClass.getProject());
                PsiMethod method = elementFactory.createMethodFromText(newMethod, psiClass);
                PsiElement psiElement = psiClass.add(method);
                JavaCodeStyleManager.getInstance(psiClass.getProject()).shortenClassReferences(psiElement);
            }

        }.execute();
    }

    private PsiClass getPsiClassFromContext(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (psiFile == null || editor == null) {
            return null;
        }
        int offset = editor.getCaretModel().getOffset();
        PsiElement elementAt = psiFile.findElementAt(offset);
        return PsiTreeUtil.getParentOfType(elementAt, PsiClass.class);
    }
}
