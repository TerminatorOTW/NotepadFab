
// Developed by the TerminatorOTW

package notepadfab.java.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

public class File_rw {
    
    public static byte[] getByteData(File file) {
        byte[] byteData = new byte[(int) file.length()];
        
        try {
            InputStream is = new FileInputStream(file);
            is.read(byteData);
            is.close();
        } catch (IOException f) {}
     
        return byteData;
    }
    
    public static void setByteData(File file, byte[] byteData) {
            
        try {
            OutputStream os = new FileOutputStream(file);
            os.write(byteData);
            os.close();
        } catch (IOException f) {}
            
    }
    
    public static String getText(File file) {
        
        if (file == null)
            return "";
        
        String text = "";
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                text += line + '\n';
            }
            
            if (!text.isEmpty()) {
                if (text.charAt(text.length() - 1) == '\n') {
                    text = text.substring(0, text.length() - 1);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return text;
    }
    
    public static String getText(InputStream is) {
        
        String text = "";
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                text += line + '\n';
            }
            
            if (!text.isEmpty()) {
                if (text.charAt(text.length() - 1) == '\n') {
                    text = text.substring(0, text.length() - 1);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return text;
    }
    
    public static boolean setText(String text, File file) {
        
        if (file == null)
            return false;
        
        try (BufferedWriter br = new BufferedWriter(new FileWriter(file))) {
            br.write(text);
            br.flush();
            br.close();
            return true;
        } catch (Exception e) {}
        
        return false;
    }
    
    public static boolean setText(String text, File file, String os) {
        
        if (file == null)
            return false;
        
        switch(os) {
            case "windows":
                text = text.replace("\n", "\r\n");
                break;
            case "unix":
                break;
            case "mac":
                text = text.replace("\n", "\r");
                break;
            default:
                text = text.replace("\n", System.lineSeparator());
                break;
        }
        
        try (BufferedWriter br = new BufferedWriter(new FileWriter(file))) {
            br.write(text);
            br.flush();
            br.close();
            return true;
        } catch (Exception e) {}
        
        return false;
    }
    
}
