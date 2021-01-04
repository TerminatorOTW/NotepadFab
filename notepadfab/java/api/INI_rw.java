
// Developed by the TerminatorOTW

package notepadfab.java.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Syntax to follow so that this can read your,
 * INI file correctly :
 * (a). header and key names aren't case sensitive,
 *      so don't put two headers or key having same names,
 *      in a single INI file.
 * (b). you can add comments by using ';' in the beginning of a line.
 * (c). spaces in key names will be ignored, but spaces in values
 *      won't be ignored.
 * (d). header should always start from the starting of a line,
 *      not in the middle.
 * (e). never put line break character between lines ex: (\n).
 */
public class INI_rw {
    
    public static boolean setKeyValue(File iniFile, String header, String key, String value) {
        INI_rw rw = new INI_rw();
        if (rw.setKeyVal(iniFile, header, key, value))
            return true;
        else
            return false;
    }
    
    public static String getKeyValue(File iniFile, String header, String key) {
        INI_rw rw = new INI_rw();
        return rw.getKeyVal(iniFile, header, key);
    }
    
    /**
     * Main methods of this class
     */
    private boolean setKeyVal(File iniFile, String header, String key, String value) {
        
        if (iniFile == null || (!iniFile.isFile()) || (!isINIFile(iniFile)) || header.isEmpty() || key.isEmpty())
            return false;
            
        String text = this.getText(iniFile);
        int headerEndPos = this.getHeaderEndPos(text, header);
        
        if (headerEndPos < 0) {
            return false;
        } else {
            int keyEndPos = this.getKeyEndPos(text, key, headerEndPos);
            
            if (keyEndPos < 0) {
                return false;
            } else {
                return this.setValue(text, value, keyEndPos, iniFile);
            }
        }
    }
    
    private String getKeyVal(File iniFile, String header, String key) {
        
        if (iniFile == null || (!iniFile.isFile()) || (!isINIFile(iniFile)) || header.isEmpty() || key.isEmpty())
            return "";
        
        String text = this.getText(iniFile);
        int headerEndPos = this.getHeaderEndPos(text, header);
        
        if (headerEndPos > 0) {
            int keyEndPos = this.getKeyEndPos(text, key, headerEndPos);
            
            if (keyEndPos > 0) {
                return this.getValue(text, keyEndPos);
            }
        }
        return "Doesn't exits";
    }
    /* end */
    
    private int getHeaderEndPos(String text, String header) {
        
        final int length = text.length();
        int i = 0;
        char c;
        
        while (i < length) {
            c = text.charAt(i);
            
            // sidestepping comment
            if (c == ';') {
                i = this.skipLine(i, text);
                
                if (i < length)
                    c = text.charAt(i);
                else
                    break;
            }
            
            /**
             * if the line doesn't start from the header declaration then
             * skip the line
             */
            if (c == '[') {
                i++;
                String foundHeader = "";
                while (i < length && (c=text.charAt(i)) != ']') {
                    foundHeader += c;
                    i++;
                }
                
                if (i < length) {
                    if (header.toLowerCase().equals(foundHeader.toLowerCase()))
                        return this.skipLine(i, text);
                } else {
                    break;
                }
            } else {
                i = this.skipLine(i, text);
            }
        }
        
        return -1;
    }
    
    private int getKeyEndPos(String text, String key, int headerEndPos) {
        
        final int length = text.length();
        int i = headerEndPos;
        char c;
        String foundKey;
        
        while (i < length) {
            
            c = text.charAt(i);
            
            // sidestepping comment
            if (c == ';') {
                i = this.skipLine(i, text);
                
                if (i < length)
                    c = text.charAt(i);
                else
                    break;
            }
            
            // this header's space ended
            if (c == '[')
                break;
            
            /**
             * if the line doesn't start from the key declaration then
             * skip the line
             */
            if (c != '\n' && c != ' ') {
                foundKey = "";
                while (i < length && (c=text.charAt(i)) != '=' && c != ':') {
                    foundKey += c;
                    i++;
                }

                if (i < length) {
                    foundKey = foundKey.replace(" ", "");
                    if (key.toLowerCase().equals(foundKey.toLowerCase())) {
                        return (i+1);
                    } else {
                        i = this.skipLine(i, text);
                    }
                } else {
                    break;
                }
            } else {
                i = this.skipLine(i, text);
            }
        }
        
        return -1;
    }
    
    private String getValue(String text, int keyEndPos) {
        
        final int length = text.length();
        String key = "";
        int i = keyEndPos;
        char c;
        
        while (i < length && (c = text.charAt(i)) != '\n') {
            if (c == '\n')
                break;
            else
                key += c;
            i++;
        }
        
        return key;
    }
    
    private boolean setValue(String text, String value, int valStartPos, File file) {
        
        final int length = text.length();
        int valLength = 0;
        
        for (int i = valStartPos; i < length; i++) {
            if (text.charAt(i) == '\n')
                break;
            else
                valLength++;
        }
        
        if (valLength > 0) {
            text = replaceStringAtPos(valStartPos, valStartPos+valLength, text, value);
            return setText(text, file);
        }
        
        return false;
    }
    
    private String getText(File file) {
        
        if (file == null)
            return "";
        
        String text = "";
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                text += line + '\n';
            }
            
            if (!text.isEmpty()) {
                if (text.charAt(text.length() - 1) == '\n') {
                    text = text.substring(0, text.length() - 1);
                }
            }
        } catch (Exception e) {}
        
        return text;
        
    }
    
    private boolean setText(String text, File file) {
        
        if (file == null)
            return false;
        else
            text = text.replace("\n", System.lineSeparator());
        
        try (BufferedWriter br = new BufferedWriter(new FileWriter(file))) {
            br.write(text);
            br.flush();
            br.close();
            return true;
        } catch (Exception e) {}
        
        return false;
    }
    
    private String replaceStringAtPos(int start, int end, String text, String value) {
        
        if (start < 0 || end > text.length() || start > end)
            return text;
        
        String text1 = text.substring(0, start);
        String text2 = text.substring(end, text.length());
        
        return text1 + value + text2;
    }
    
    private boolean isINIFile(File file) {
        return file.getName().toLowerCase().contains(".ini");
    }
    
    private int skipLine(int startPos, String text) {
        while (startPos < text.length() && text.charAt(startPos) != '\n')
            startPos++;
        return (startPos+1);
    }
    
}