package main.java;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Author: Marek
 * Date: 20/04/16.
 */
public class PsiUtils {

    private static String ORG_JUNIT = "org.junit";
    private static String TEST = "Test";
    private static String ASSERT = "Assert";
    private static String ASSERTEQUALS = "assertEquals";
    private static String ASSERTNOTEQUALS = "assertNotEquals";
    private static String ASSERTTRUE = "assertTrue";
    private static String ASSERTFALSE = "assertFalse";

    /**
     * Method adds import statement on demand for supplied import statement
     * @param psiClass import is created to this class
     * @param importClass this statement would be imported on demand
     * */
    private static void addImportToPsiClass(PsiClass psiClass, String importPackage, String importClass) {
        PsiClass psiClassInPackage = findPsiClassInPackage(psiClass, importPackage, importClass);
        addImportToPsiClass(psiClass, psiClassInPackage);
    }

    /**
     * Method adds import statement for supplied PsiClass
     * @param psiClass import is created to this class
     * @param importClass this class would be imported
     * */
    private static void addImportToPsiClass(PsiClass psiClass, PsiClass importClass) {
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiClass.getProject());
        PsiImportStatement psiImportStatement = elementFactory.createImportStatement(importClass);
        if (!hasImport(psiClass,psiImportStatement.getQualifiedName())) writeImportToPsiClass(psiClass,psiImportStatement);
    }

    /**
     * Method adds static import statement for supplied PsiClass
     * @param psiClass import is created to this class
     * */
    private static void addStaticImportToPsiClass(PsiClass psiClass, String importPackage, String importClass, String importMethod) {
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiClass.getProject());

        PsiClass classInPackage = findPsiClassInPackage(psiClass, importPackage, importClass);
        PsiImportStaticStatement importStaticStatement = elementFactory.createImportStaticStatement(classInPackage, importMethod);
        if (!hasImport(psiClass,importStaticStatement.getReferenceName())) writeImportToPsiClass(psiClass,importStaticStatement);
    }

    private static PsiClass findPsiClassInPackage(PsiClass psiClass, String desiredPackage, String classShortName) {
        PsiPackage psiPackage = JavaPsiFacade.getInstance(psiClass.getProject()).findPackage(desiredPackage);
        PsiClass[] classes = psiPackage.getClasses();
        Optional<PsiClass> psiClassOptional = Arrays.stream(classes).filter(x -> x.getName().equalsIgnoreCase(classShortName)).findFirst();
        if (psiClassOptional.isPresent()) return psiClassOptional.get();
        else return null;
    }

    /**
     * Safely adds import statement to psiClass
     * @param psiClass import statement is added to this class
     * @param psiImportStatement import statement to be added
     * */
    private static void writeImportToPsiClass(PsiClass psiClass, PsiImportStatementBase psiImportStatement){
        new WriteCommandAction.Simple(psiClass.getProject(), psiClass.getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                PsiElement psiElement = ((PsiJavaFile) psiClass.getContainingFile()).getImportList().add(psiImportStatement);
                JavaCodeStyleManager.getInstance(psiClass.getProject()).shortenClassReferences(psiElement);
            }
        }.execute();
    }

    /**
     * Checks, if import statement isn't already present in PsiClass
     * @param psiClass import list of this class is examined
     * @param importStatement imports are checked against this import statement
     * */
    private static boolean hasImport(PsiClass psiClass, String importStatement) {
        PsiImportList importList = ((PsiJavaFile) psiClass.getContainingFile()).getImportList();
        Stream<String> importStream = Arrays.stream(importList.getImportStatements()).map(x -> x.getQualifiedName());
        Iterator<String> importIterator = importStream.iterator();
        while (importIterator.hasNext()) {
            if (importIterator.next().equalsIgnoreCase(importStatement)) return true;
        }

        Stream<String> staticImportStream = Arrays.stream(importList.getImportStaticStatements()).map(x -> x.getReferenceName());
        Iterator<String> staticImportIterator = staticImportStream.iterator();
        while (staticImportIterator.hasNext()) {
            if (staticImportIterator.next().equalsIgnoreCase(importStatement)) return true;
        }

        return false;
    }

    public static void writeMethodsToFiles(PsiClass psiClass, MethodPrototype methodPrototype) throws Exception {
        Path testClassPath = Paths.get(psiClass.getContainingFile().getOriginalFile().getVirtualFile().getCanonicalPath());
        Path projectBasePath = Paths.get(psiClass.getProject().getBasePath());

        Path mainClassPath = FileUtils.swapDirectory(projectBasePath, testClassPath, "test", "main");
        String mainPath = mainClassPath.toString().replace("Test.java", ".java");

        VirtualFile f = VfsUtil.findFileByIoFile(new File(mainPath), true);
        if (f != null && f.exists()) {
            PsiFile file = PsiManager.getInstance(psiClass.getProject()).findFile(f);
            PsiClass[] psiClasses = ((PsiClassOwner) file).getClasses();

            if (psiClasses.length > 1) throw new Exception("Unable to find main class. More than one class found.");

            String testMethod = methodPrototype.constructTestMethod(psiClass, false);
            PsiMethod psiTestMethod = getPsiMethod(psiClass, testMethod);

            PsiMethod psiMethod = getPsiMethod(psiClass, methodPrototype.constructMethod());
            if (!psiClassContainsMethod(psiClasses[0],psiMethod)) {
                addMethodToPsiClass(psiClasses[0], psiMethod);
            }

            while (psiClassContainsMethodName(psiClass, psiTestMethod.getName())) {
                if (methodPrototype.getName().matches(".*_[0-9]+$")) {
                    int suffix = Integer.parseInt(methodPrototype.getName().substring(methodPrototype.getName().lastIndexOf("_")+1))+1;
                    methodPrototype.setName(methodPrototype.getName().replaceAll("_[0-9]+$","_"+suffix));
                } else {
                    methodPrototype.setName(methodPrototype.getName() + "_2");
                }
                testMethod = methodPrototype.constructTestMethod(psiClass, true);
                psiTestMethod = getPsiMethod(psiClass, testMethod);
            }

            addMethodToPsiClass(psiClass, psiTestMethod);

            addImportToPsiClass(psiClass, psiClasses[0]);
            addImportToPsiClass(psiClass,ORG_JUNIT, TEST);

            TypeValuePair ret = methodPrototype.getReturnType();
            boolean isEq = methodPrototype.getComparativeSign().matches("=="),
                    isTrue = ret.getValue().toLowerCase().equalsIgnoreCase("true");
            if (ret.getType() == PrimitiveType.BOOLEAN) {
                if ((isEq && isTrue) || (isEq && !isTrue)) addStaticImportToPsiClass(psiClass, ORG_JUNIT, ASSERT, ASSERTTRUE);
                else addStaticImportToPsiClass(psiClass, ORG_JUNIT, ASSERT, ASSERTFALSE);
            } else {
                if (isEq) addStaticImportToPsiClass(psiClass, ORG_JUNIT, ASSERT, ASSERTEQUALS);
                else addStaticImportToPsiClass(psiClass, ORG_JUNIT, ASSERT, ASSERTNOTEQUALS);
            }

        } else throw new Exception("Unable to add methods. Main class with this name doesn't exist.");
    }

    /**
     * Method for adding PsiMethod to PsiClass.
     * @param psiClass  The PsiMethod is added to this PsiClass
     * @param method PsiMethod to add to the PsiClass.
     * */
    private static void addMethodToPsiClass(PsiClass psiClass, PsiMethod method) throws Exception {
        new WriteCommandAction.Simple(psiClass.getProject(), psiClass.getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                PsiElement psiElement = psiClass.add(method);
                JavaCodeStyleManager.getInstance(psiClass.getProject()).shortenClassReferences(psiElement);
            }
        }.execute();
    }

    /**
     * Returns true, if psiClass contains psiMethod. False otherwise.
     * @param psiClass this class is used to search for methods
     * @param psiMethod this method is used for the comparison
     * */
    private static boolean psiClassContainsMethod(PsiClass psiClass, PsiMethod psiMethod) {
        PsiMethod[] psiClassMethods = psiClass.getMethods();
        for (PsiMethod pm : psiClassMethods) {
            if (psiMethodsEquals(pm, psiMethod)) {
                return true;
            }
        }
        return false;
    }

    private static PsiMethod getPsiMethod(PsiClass psiClass, String newMethod) {
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiClass.getProject());
        PsiMethod method = elementFactory.createMethodFromText(newMethod, psiClass);
        return method;
    }

    /**
     * Returns true if methods are equal, or false if not. Methods are equal if they have the same name and same
     * parameters types, including parameters order.
     * @param a first method for comparison
     * @param b second method used for comparison
     * */
    private static boolean psiMethodsEquals(PsiMethod a, PsiMethod b) {
        if (a.getName().compareTo(b.getName()) == 0) {
            PsiParameter[] a_parameters = a.getParameterList().getParameters();
            PsiParameter[] b_parameters = b.getParameterList().getParameters();
            if (a_parameters.length == b_parameters.length) {
                int count = 0;
                for (int i = 0; i < a_parameters.length; i++) {
                    if (a_parameters[i].getType() == b_parameters[i].getType()) {
                        System.out.println(a_parameters[i] +" == "+b_parameters[i]);
                        count++;
                    } else return false;
                }
                if (count == a_parameters.length) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean psiClassContainsMethodName(PsiClass psiClass, String name) {
        PsiMethod[] psiClassMethods = psiClass.getMethods();
        for (PsiMethod pm : psiClassMethods) {
            if (pm.getName().compareTo(name)==0) {
                return true;
            }
        }
        return false;
    }

}
