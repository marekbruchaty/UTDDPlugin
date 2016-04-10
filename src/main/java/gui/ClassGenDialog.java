package main.java.gui;

import main.java.FileOperations;

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
    private JTabbedPane TestMainClassGenerator;
    private JButton selectClassButton;
    private JButton selectTestButton;
    private JTextPane textPane1;
    private String currentPath;

    public ClassGenDialog(String[] args) {
        FileOperations fc = new FileOperations();
        currentPath = args[0];
        setLocation(ViewUtils.getCurrentWindowCenter(contentPane));
        //setLocation(MouseInfo.getPointerInfo().getLocation());
        setContentPane(contentPane);
        setTitle("Test/Main Class Generator");
        setModal(true);
        getRootPane().setDefaultButton(buttonGenerate);
        setSize(400,300);

        buttonGenerate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fc.createFile(classPath.getText());
                onOK();

            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        className.setText("");
        if (args.length > 0) classPath.setText(currentPath);
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
                fileChooser.setCurrentDirectory(new File(currentPath));
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


                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(currentPath));
                int result = fileChooser.showOpenDialog(fileChooser);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                }

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
