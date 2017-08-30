package kml;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author DarkLBP
 * website https://krothium.com
 */
class Starter {
    public static void main(String[] args) throws IOException, FontFormatException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        if (existsResource()){
            bootFromResource(args);
        }
    }
    private static boolean existsResource() {
        File custom = new File("server.ini");
        return custom.exists() && custom.isFile();
    }
    private static void bootFromResource(String[] passedArgs) {
        File custom = new File("server.ini");
        if (custom.exists() && custom.isFile()){
            Properties p = new Properties();
            try {
                FileInputStream fin = new FileInputStream(custom);
                p.load(fin);
                File resource = new File(p.getProperty("path"));
                StubLauncher.load(resource, passedArgs);
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
