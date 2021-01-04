
// Developed by the TerminatorOTW

package notepadfab.java.functions;

import java.io.File;
import javafx.scene.image.ImageView;
import notepadfab.ResourceLoader;

public class FileFn {
    
    public String getFileType(File file) {
        if (file != null)
            return returnFileType(file.getName());
        else
            return "Plain Text";
    }
    
    public String getFileType(String fileName) {
        return returnFileType(fileName);
    }
    
    public String getFileExtension(File file) {
        if (file != null)
            return returnOriginalFileType(file.getName());
        else
            return "null";
    }
    
    public String getFileExtension(String fileName) {
        return returnOriginalFileType(fileName);
    }
    
    public ImageView getViewAccordingToType(String fileType) {
        return returnImageAccordingToType(fileType);
    }
    
    /**
     * Private methods
     */
    private String returnFileType(String fileName) {
        int startPoint = -1;
        final int length = fileName.length();
        String type = "";
        
        if (fileName.contains(".")) {
            for(int i=(length-1); i >= 0; i--) {
                if(fileName.charAt(i) == '.')
                    startPoint = i;
            }
            
            if (startPoint >= 0 && startPoint < length) {
                type = fileName.substring(startPoint + 1);
            } else {
                type = "null";
            }
        } else {
            type = "null";
        }
            
        switch(type.toLowerCase()) {
            case "txt":
                return "Plain Text";
            case "bat":
                return "Batch File";
            case "ini":
                return "INI File";
            case "html":
                return "HTML File";
            case "css":
                return "CSS File";
            case "js":
                return "Javascript File";
            case "java":
                return "Java File";
            case "cpp":
                return "C++ File";
            case "py":
                return "Python File";
            default:
                return "Unknown File";
        }
    }
    
    private String returnOriginalFileType(String fileName) {
        int startPoint = -1;
        final int length = fileName.length();
        String type = "";
        
        if (fileName.contains(".")) {
            for(int i=(length-1); i >= 0; i--) {
                if(fileName.charAt(i) == '.')
                    startPoint = i;
            }
            
            if (startPoint >= 0 && startPoint < length) {
                return fileName.substring(startPoint);
            } else {
                return "null";
            }
        } else {
            return "null";
        }
    }
    
    private ImageView returnImageAccordingToType(String fileType) {
        
        final ResourceLoader resLoader = new ResourceLoader();
        
        ImageView fileIcon = new ImageView();
        fileIcon.setFitHeight(10);
        fileIcon.setFitWidth(12);
        
        switch(fileType) {
            case "Plain Text":
                fileIcon.setImage(resLoader.loadImages("plainTextIcon.png"));
                return fileIcon;
            case "Batch File":
                fileIcon.setImage(resLoader.loadImages("batchIcon.png"));
                return fileIcon;
            case "HTML File":
                fileIcon.setImage(resLoader.loadImages("htmlIcon.png"));
                return fileIcon;
            case "CSS File":
                fileIcon.setImage(resLoader.loadImages("cssIcon.png"));
                return fileIcon;
            case "Python File":
                fileIcon.setImage(resLoader.loadImages("pythonIcon.png"));
                return fileIcon;
            default:
                fileIcon.setImage(resLoader.loadImages("plainTextIcon.png"));
                return fileIcon;
        }
    }
    
}
