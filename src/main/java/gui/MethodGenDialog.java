package main.java.gui;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.MessageType;
import com.intellij.psi.PsiClass;
import main.java.MethodPrototype;
import main.java.PopupCreator;
import main.java.PsiUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
        getRootPane().setDefaultButton(generateButton);
        setContentPane(contentPane);
        Dimension dimension = new Dimension(500,400);
        setSize(dimension);
        setMinimumSize(dimension);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {dispose();}
        });

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PsiUtils.writeMethodsToFiles(psiClass,methodPrototype);
                } catch (Exception ex) {
                    PopupCreator.createPopup(actionEvent,ex.getMessage(),MessageType.ERROR);
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
                    methodPrototype = new MethodPrototype(text);
                    methodPreview.setText(methodPrototype.constructMethod());
                    testMethodPreview.setText(methodPrototype.constructTestMethod(psiClass));
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

