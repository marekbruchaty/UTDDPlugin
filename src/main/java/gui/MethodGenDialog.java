package main.java.gui;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.MessageType;
import com.intellij.psi.PsiClass;
import main.java.MethodPrototype;
import main.java.PopupCreator;
import main.java.PsiUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MethodGenDialog extends JDialog{
    private JPanel contentPane;
    private JButton cancelButton;
    private JButton generateButton;
    private JTextField methodPrototypeTextField;
    private JTextPane methodPreview;
    private JTextPane testMethodPreview;
    private MethodPrototype methodPrototype;

    private static final String title = "New test/production method pair";
    private Color defForegroundColor;

    public MethodGenDialog(AnActionEvent actionEvent, PsiClass psiClass) {
        setVisible(true);
        setTitle(title);
        setLocation(ViewUtils.getCurrentWindowCenter(contentPane));
        getRootPane().setDefaultButton(generateButton);
        setContentPane(contentPane);
        Dimension dimension = new Dimension(500,400);
        setSize(dimension);
        setMinimumSize(dimension);
        defForegroundColor = methodPrototypeTextField.getForeground();

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {dispose();}
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PsiUtils.writeMethodsToFiles(psiClass,methodPrototype);
                } catch (Exception ex) {
                    if (!ex.getMessage().isEmpty()) {
                        PopupCreator.createPopup(actionEvent, ex.getMessage(), MessageType.ERROR);
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
                    methodPrototypeTextField.setForeground(defForegroundColor);
                    methodPrototype = new MethodPrototype(text);
                    methodPreview.setText(methodPrototype.constructMethod());
                    testMethodPreview.setText(methodPrototype.constructTestMethod(psiClass, false));
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

