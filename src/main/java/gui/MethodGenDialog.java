package main.java.gui;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassOwner;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import main.java.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MethodGenDialog extends JDialog{
    private JPanel contentPane;
    private JButton cancelButton;
    private JButton generateButton;
    private JTextField methodPrototypeTextField;
    private JTextPane methodPreview;
    private JTextPane testMethodPreview;
    private MethodPrototype methodPrototype;

    public MethodGenDialog(AnActionEvent actionEvent, PsiClass psiClass) {
        setVisible(true);
        setTitle("New method pair");
        setLocation(ViewUtils.getCurrentWindowCenter(contentPane));
        setContentPane(contentPane);
        Dimension dimension = new Dimension(500,400);
        setSize(dimension);
        setMinimumSize(dimension);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
            }
        });

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Path path = Paths.get(psiClass.getContainingFile().getOriginalFile().getVirtualFile().getCanonicalPath());
                Path basePath = Paths.get(psiClass.getProject().getBasePath());
                if (basePath == null) {
                    PopupCreator.createPopup(actionEvent,"Can't get project path.", MessageType.ERROR);
                } else if (basePath == null) {
                    PopupCreator.createPopup(actionEvent,"Can't get class path.", MessageType.ERROR);
                } else {
                    Path path_main = FileUtils.swapDirectory(basePath, path, "test", "main");
                    System.out.println(path_main.toString());
                    String mainPath = path_main.toString().replace("Test.java", ".java");
                    System.out.println(mainPath);
                    VirtualFile f = VfsUtil.findFileByIoFile(new File(mainPath), true);
                    if (f != null && f.exists()) {
                        PsiFile file = PsiManager.getInstance(psiClass.getProject()).findFile(f);
                        PsiClass[] psiClasses = ((PsiClassOwner) file).getClasses();
                        PsiUtils.addJunitImportToPsiClass(psiClass);
                        PsiUtils.addMethodToPsiClass(psiClass, methodPrototype.constructTestMethod());
                        PsiUtils.addMethodToPsiClass(psiClasses[0], methodPrototype.constructMethod());
                    } else {
                        PopupCreator.createPopup(actionEvent,"Main class doesn't exist or it has unknown name or location.", MessageType.ERROR);
                    }
                }

                dispose();
            }
        });

        methodPrototypeTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String text = methodPrototypeTextField.getText();
                try {
                    methodPrototypeTextField.setForeground(Color.LIGHT_GRAY);
                    methodPrototype = Parser.parseMethodPrototype(text);
                    methodPreview.setText(methodPrototype.constructMethod());
                    testMethodPreview.setText(methodPrototype.constructTestMethod());
                    generateButton.setEnabled(true);
                } catch (Exception exception) {
                    methodPrototypeTextField.setForeground(Color.RED);
                    methodPreview.setText("Wrong format\n" + exception.getMessage());
                    testMethodPreview.setText("");
                    generateButton.setEnabled(false);
                }
            }
        });
    }
}

