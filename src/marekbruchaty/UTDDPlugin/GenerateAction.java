package marekbruchaty.UTDDPlugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.List;

/**
 * Created by Marek Bruchatý on 25/02/16.
 */
public class GenerateAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        PsiClass psiClass = getPsiClassFromContext(e);
        GenerateDialog dlg = new GenerateDialog(psiClass);
        dlg.show();
        if (dlg.isOK()) {
            generateCompareTo(psiClass, dlg.get);
        }
    }

    private void generateCompareTo(List<PsiField> fields) {
        new WriteCommandAction.Simple() {

            @Override
            protected void run() throws Throwable {

            }
        }.execute;
    }

    @Override
    public void update(AnActionEvent e) {
        PsiClass psiClass = getPsiClassFromContext(e);
        e.getPresentation().setEnabled(psiClass != null);

    }

    private PsiClass getPsiClassFromContext(AnActionEvent e) {
    /*Action is anabled when we are in context of Java class. Te get the data class, data key need to be retrieved*/
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        /*Get editor, information about where in the file we are*/
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (psiFile == null || editor == null) {
            e.getPresentation().setEnabled(false);
            return null;
        }
        /*Caret position, Where the cursor is*/
        int offset = editor.getCaretModel().getOffset();
        /*Psi tree is a abstract tree stored by intelij*/
        PsiElement elementAt = psiFile.findElementAt(offset);
        return PsiTreeUtil.getParentOfType(elementAt, PsiClass.class);
    }
}
