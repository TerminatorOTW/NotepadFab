
/* Developed by the TerminatorOTW */

package notepadfab.java;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import notepadfab.ResourceLoader;
import notepadfab.java.functions.PrefSave;
import notepadfab.java.layouts.MainLayout;

public class NotepadFab extends Application {
    
    private Stage windows;
    private Scene scene;
    
    public int app_width = 0;
    public int app_height = 0;
    
    private final List<String> filePaths = new ArrayList(){};
    
    @Override
    public void start(Stage stage) throws Exception {
        
        windows = stage;
        
        File[] file = new File[filePaths.size()];
        
        for (int i = 0; i < filePaths.size(); i++) {
            
            if (!filePaths.get(i).isEmpty()) {
                file[i] = new File(filePaths.get(i));
            } else {
                file[i] = null;
            }
        }
        
        final MainLayout mainLayout = new MainLayout(windows, file);
        
        final PrefSave prefSave = new PrefSave();
        
        /**
         * setting application's width and height
         */
        app_width = Integer.parseInt(prefSave.getSavedValue("sceneData", "stage", "width"));
        app_height = Integer.parseInt(prefSave.getSavedValue("sceneData", "stage", "height"));
        
        Rectangle2D rec = Screen.getPrimary().getBounds();
        
        if (app_width < 0 || app_height < 0) {
            app_width = (int) (rec.getWidth() * 0.6);
            app_height = (int) (rec.getHeight() * 0.8);
        }
        
        windows.setWidth(app_width);
        windows.setHeight(app_height);
        /* end */
        
        /**
         * setting x, y position of the application
         */
        int xPos = Integer.parseInt(prefSave.getSavedValue("sceneData", "Stage", "xPos"));
        int yPos = Integer.parseInt(prefSave.getSavedValue("sceneData", "Stage", "yPos"));
        
        if (xPos >= 0 && yPos >= 0) {
            windows.setX(xPos);
            windows.setY(yPos);
        } else {
            windows.setX((rec.getWidth()/2)-(app_width/2));
            windows.setY((rec.getHeight()/2)-(app_height/2));
        }
        /* end */
        
        /**
         * setting the application as maximized or minimized according to setting
         */
        if (prefSave.getSavedValue("sceneData", "Stage", "maximized").equals("true"))
            windows.setMaximized(true);
        
        // create a scene
        scene = new Scene(mainLayout.getMainLayout());
        
        windows.setScene(scene);
        
        windows.setTitle("NotepadFab-V1.0");
        windows.getIcons().add(new ResourceLoader().loadImages("logo.png"));
        
        windows.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void init() {
        filePaths.addAll(this.getParameters().getUnnamed());
    }
    
}