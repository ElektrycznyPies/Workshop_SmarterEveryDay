package pl.coderslab.app;

import javax.swing.*;
import java.io.File;

public class FileChooserUtil {
    public static String chooseFile(String title) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("/home/elpies/Workshop_SmarterEveryDay/src/main/webapp/images"));
        fileChooser.setDialogTitle("Choose file");
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
//            return selectedFile.getAbsolutePath();
            return selectedFile.getName();
        }
        return null;
    }
}