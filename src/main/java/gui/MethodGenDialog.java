package main.java.gui;

import main.java.MethodPrototype;
import main.java.Parser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Marek Bruchatý on 06/04/16.
 */
public class MethodGenDialog extends JDialog{
    private JPanel panel1;
    private JButton cancelButton;
    private JButton generateButton;
    private JTextField methodPrototype;
    private JTextPane methodPreview;
    private JTextPane testMethodPreview;

    public MethodGenDialog() {
        setContentPane(panel1);
        setVisible(true);
        setTitle("Method generator");
        setModal(true);
        setSize(500,400);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });


        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        methodPrototype.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                String text = methodPrototype.getText();

                System.out.print(text + "\n");
                try {
                    MethodPrototype mp = Parser.parseMethodPrototype(text);
                    methodPreview.setText(mp.constructMethod());
                    testMethodPreview.setText(mp.constructTestMethod());
                } catch (Exception exception) {
                    methodPreview.setText("Wrong format\n" + exception.getMessage());
                    testMethodPreview.setText("");
                }
            }

        });
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
