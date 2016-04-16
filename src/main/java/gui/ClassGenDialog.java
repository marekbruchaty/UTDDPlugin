package main.java.gui;

import main.java.FileOperations;
import main.java.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileFilter;

public class ClassGenDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonGenerate;
    private JButton buttonCancel;
    private JTextField className;
    private JTextField classPath;
    private JTextField testName;
    private JTextField testPath;
    private JButton selectClassButton;
    private JButton selectTestButton;
    private JTextPane PreviewPane;

    private String directoryPath;
    private String projectPath;

    public ClassGenDialog(String _directoryPath, String _projectPath) {
        directoryPath = _directoryPath;
        projectPath = _projectPath;
        initDialog();

        buttonGenerate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileOperations.createFile(classPath.getText());
                onOK();

            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

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
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if (!Utils.isValidJavaClass(className.getText())) {
                    className.setForeground(Color.RED);
                    PreviewPane.setText("Warning! " + className.getText() + " is not valid Java Class name.");
                    testName.setText("");
                } else {
                    className.setForeground(Color.LIGHT_GRAY);
                    PreviewPane.setText("Class name OK.");
                    testName.setText(className.getText()+"Test");
                }

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
                String directoryPath = directoryChooser();
                System.out.println("Selected directory: " + directoryPath);
            }
        });


        selectTestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String directoryPath = directoryChooser();
                System.out.println("Selected directory: " + directoryPath);
            }
        });
    }

    private void initDialog() {
        setVisible(true);
        setTitle("Test/Main Class Generator");
        setLocation(ViewUtils.getCurrentWindowCenter(contentPane));
        setContentPane(contentPane);
        Dimension dimension = new Dimension(500,400);
        setSize(dimension);
        setMinimumSize(dimension);
    }

    private String directoryChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(directoryPath));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(fileChooser);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            return selectedFile.getAbsolutePath();
        }
        return null;
    }

    private String getShortenPath() {
        return directoryPath.substring(projectPath.length());
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
