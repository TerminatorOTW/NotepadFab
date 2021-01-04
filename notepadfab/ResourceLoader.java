
// Developed by the TerminatorOTW

package notepadfab;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javafx.scene.image.Image;

public class ResourceLoader {
    
    public URL loadStylesheet(String name) {
        
        URL filePath = null;
        
        try {
            filePath = getClass().getResource(
                "resources/css/" + name
            );
        } catch (Exception e) {
            System.out.println("Error!");;
        }
        
        return filePath;
    }
    
    public InputStream loadStylesheetAsIS(String name) {
        InputStream is = null;
        try {
            is = getClass().getResource("resources/css/" + name).openStream();
        } catch (IOException e) {}
        return is;
    }
    
    public Image loadImages(String name) {
        
        Image image = null;
        
        try {
            image = new Image(getClass().getResourceAsStream(
                "resources/images/" + name
            ));
        } catch (Exception e) {}
        
        return image;
    }
    
    public InputStream loadDataFile(String dataFileName) {
        InputStream is = null;
        try {
            is = getClass().getResource("resources/datas/" + dataFileName + ".ini").openStream();
        } catch (IOException e) {}
        return is;
    }
    
}
