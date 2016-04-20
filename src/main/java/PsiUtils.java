package main.java;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import org.jetbrains.java.generate.psi.PsiAdapter;

/**
 * Author: Marek
 * Date: 20/04/16.
 */
public class PsiUtils {

    /**
     * Method for adding import statements for containing file of PsiClass
     *
     * @param psiClass  PsiClass used to add import statement to its containing file
     * */
    public static void addJunitImportToPsiClass(PsiClass psiClass) {
        new WriteCommandAction.Simple(psiClass.getProject(), psiClass.getContainingFile()) {

            @Override
            protected void run() throws Throwable {
                final String junitImport = "org.junit";
                // org.junit.Test need to be imported as org.junit.* because addBefore method can't process on demand statements
                PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiClass.getProject());
                PsiImportStatement importStatement = elementFactory.createImportStatementOnDemand(junitImport);
                PsiElement psiElement = psiClass.getContainingFile().addBefore(importStatement,psiClass);
                JavaCodeStyleManager.getInstance(psiClass.getProject()).shortenClassReferences(psiElement);
            }
        }.execute();
    }

    /**
     * Method for adding PsiMethod to PsiClass created from string
     *
     * @param psiClass Method is added to this PsiClass
     * @param newMethod String containing input method
     * */
    public static void addMethodToPsiClass(PsiClass psiClass, String newMethod) {
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

}
