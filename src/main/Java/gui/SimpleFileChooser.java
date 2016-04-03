package main.java.gui;

import javax.swing.*;
import java.io.File;

/**
 * Created by Marek Bruchatý on 18/03/16.
 */
public class SimpleFileChooser extends JPanel {

    JFileChooser fileChooser;

    public SimpleFileChooser(String title) {
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
        int result = fileChooser.showOpenDialog(fileChooser);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }

    }

}
