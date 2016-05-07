package main.java.gui;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.ui.MessageType;
import main.java.ClassPair;
import main.java.FileUtils;
import main.java.PopupCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class ClassGenDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonGenerate;
    private JButton buttonCancel;
    private JTextField className;
    private JTextField classPath;
    private JTextField testName;
    private JTextField testPath;
    private JTextPane previewPane;

    private ClassPair classPair;
    private static final String title = "New test/production class pair";
    private Color defForegroundColor;

    public ClassGenDialog(String testDir, String projectDir, AnActionEvent actionEvent) {
        initDialog();
        createClassPair(testDir, projectDir);
        defForegroundColor = testName.getForeground();
        initFields();

        buttonGenerate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createFile(actionEvent,classPair.getTestClass(),classPair.getTestClassBody());
                createFile(actionEvent,classPair.getMainClass(),classPair.getMainClassBody());
                actionEvent.getData(CommonDataKeys.PROJECT).getBaseDir().refresh(false,true);
                dispose();
            }
        });

        testName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if (!FileUtils.isValidJavaClass(testName.getText())) {
                    signalBadFormat("\"" + testName.getText() + "\" is not valid Java class name.");
                } else if (!testName.getText().toLowerCase().endsWith("test")){
                    signalBadFormat("\"Test\" suffix missing: \"" + testName.getText() + "\"");
                } else {
                    try {
                        classPair.setTestClass(testName.getText()+".java");
                        signalReady(classPair.getTestClassBody());
                    } catch (Exception ex) {
                        signalBadFormat(ex.getMessage());
                    }
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
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
    }

    private void createClassPair(String testDir, String projectDir) {
        try {
            classPair = new ClassPair(testDir, projectDir);
        } catch (Exception e) {
            previewPane.setText(e.toString());
        }
    }

    private void signalBadFormat(String preview) {
        testName.setForeground(Color.RED);
        className.setForeground(Color.RED);
        previewPane.setText(preview);
        className.setText("");
        buttonGenerate.setEnabled(false);
    }

    private void signalReady(String preview) {
        testName.setForeground(defForegroundColor);
        className.setForeground(defForegroundColor);
        previewPane.setText(preview);
        className.setText(classPair.getMainClass().getName().replace(".java",""));
        buttonGenerate.setEnabled(true);
    }

    private void initDialog() {
        setVisible(true);
        setTitle(title);
        setLocation(ViewUtils.getCurrentWindowCenter(contentPane));
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonGenerate);
        Dimension dimension = new Dimension(500,400);
        setSize(dimension);
        setMinimumSize(dimension);
    }

    private void initFields() {
        testPath.setText(classPair.getTestDirectory().toString());
        testName.setText("");
        classPath.setText(classPair.getMainDirectory().toString());
        className.setText("");
    }

    public void createFile(AnActionEvent actionEvent, File classFile, String classBody) {
        try {
            FileUtils.createFile(classFile,classBody);
            PopupCreator.createPopup(actionEvent,classPair.getTestClass().getName() +
                    " and " + classPair.getMainClass().getName() + " created successfully.", MessageType.INFO);
        } catch (Exception ex) {
            PopupCreator.createPopup(actionEvent,ex.getMessage(), MessageType.ERROR);
        }
    }

}
