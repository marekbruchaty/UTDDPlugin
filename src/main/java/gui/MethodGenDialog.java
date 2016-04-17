package main.java.gui;
import com.intellij.ui.JBColor;
import main.java.MethodPrototype;
import main.java.Parser;
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
    private boolean isOK;

    public MethodGenDialog() {
        setVisible(true);
        setTitle("New method pair");
        setLocation(ViewUtils.getCurrentWindowCenter(contentPane));
        setContentPane(contentPane);
        Dimension dimension = new Dimension(500,400);
        setSize(dimension);
        setMinimumSize(dimension);
        isOK = false;

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { onOK(); }
        });

        methodPrototypeTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String text = methodPrototypeTextField.getText();
                try {
                    methodPrototypeTextField.setForeground(JBColor.LIGHT_GRAY);
                    methodPrototype = Parser.parseMethodPrototype(text);
                    methodPreview.setText(methodPrototype.constructMethod());
                    testMethodPreview.setText(methodPrototype.constructTestMethod());
                } catch (Exception exception) {
                    methodPrototypeTextField.setForeground(JBColor.RED);
                    methodPreview.setText("Wrong format\n" + exception.getMessage());
                    testMethodPreview.setText("");
                }
            }
        });
    }

    public MethodPrototype getMethodPrototype() {
        return methodPrototype;
    }

    private void onOK() {
        isOK = true;
        dispose();
    }

    private void onCancel() {
        isOK = false;
        dispose();
    }

    public boolean isOK() {
        return isOK;
    }
}

