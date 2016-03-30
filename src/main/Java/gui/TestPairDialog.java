package main.Java.gui;

import com.intellij.openapi.actionSystem.ComputableActionGroup;
import com.intellij.openapi.application.ApplicationManager;
import javafx.stage.FileChooser;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.*;
import java.io.File;
import java.lang.reflect.ParameterizedType;

public class TestPairDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField className;
    private JTextField classPath;
    private JTextField testName;
    private JTextField testPath;
    private JTabbedPane TestMainClassGenerator;
    private JButton selectClassButton;
    private JButton selectTestButton;
    private JTextPane textPane1;

    public TestPairDialog(String[] args) {
        setContentPane(contentPane);
        setTitle("UTDDPlugin");
        setModal(true);
        setResizable(false);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        className.setText("");
        if (args.length > 0) classPath.setText(args[0]);
        testName.setText(className.getText() + "Test");
        testPath.setText(classPath.getText() + "Test");



        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        className.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                testName.setText(className.getText() + e.getKeyChar() + "Test");
            }
        });

        classPath.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);

            }
        });


        testName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }
        });


        testPath.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }
        });


        selectClassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                int result = fileChooser.showOpenDialog(fileChooser);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                }


            }
        });


        selectTestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SimpleFileChooser chooser = new SimpleFileChooser("Select bla bla");


            }
        });
    }


    public JTextField getClassName() {
        return className;
    }

    public void setClassName(JTextField className) {
        this.className = className;
    }

    public JTextField getClassPath() {
        return classPath;
    }

    public void setClassPath(JTextField classPath) {
        this.classPath = classPath;
    }

    public JTextField getTestName() {
        return testName;
    }

    public void setTestName(JTextField testName) {
        this.testName = testName;
    }

    public JTextField getTestPath() {
        return testPath;
    }

    public void setTestPath(JTextField testPath) {
        this.testPath = testPath;
    }

    private void onOK() {
// add your code here
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

}
