package main.java.gui;

import main.java.MethodPrototype;
import main.java.Parser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Marek Bruchatý on 06/04/16.
 */
public class MethodGenDialog extends JDialog{
    private JPanel contentPane;
    private JButton cancelButton;
    private JButton generateButton;
    private JTextField methodPrototype;
    private JTextPane methodPreview;
    private JTextPane testMethodPreview;

    public MethodGenDialog() {
        setVisible(true);
        setTitle("Method generator");
        setLocation(ViewUtils.getCurrentWindowCenter(contentPane));
        setContentPane(contentPane);
        Dimension dimension = new Dimension(500,400);
        setSize(dimension);
        setMinimumSize(dimension);

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
                    methodPrototype.setForeground(Color.LIGHT_GRAY);
                    MethodPrototype mp = Parser.parseMethodPrototype(text);
                    methodPreview.setText(mp.constructMethod());
                    testMethodPreview.setText(mp.constructTestMethod());
                } catch (Exception exception) {
                    methodPrototype.setForeground(Color.RED);
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
