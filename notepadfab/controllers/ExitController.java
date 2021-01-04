
// Developed by the TerminatorOTW

package notepadfab.java.controllers;

import java.io.File;
import javafx.stage.Stage;
import notepadfab.java.functions.PrefSave;
import notepadfab.java.layouts.AlertLayout;

public class ExitController {
    
    private final MainController mainCtrl;
    private final AlertLayout alertLayout;
    
    public ExitController(MainController mC, AlertLayout aL) {
        // setup
        this.mainCtrl = mC;
        this.alertLayout = aL;
    }
    
    public void exit() {
        if (mainCtrl.getTabsController().isAllSaved())
            exitAndSaveData();
        else
            showExitWarning();
    }
    
    private void showExitWarning() {
        
        alertLayout.setAlertText("Do you want to save the changes\nyou made to the files?");
        
        Stage s = alertLayout.returnStage();
        s.show();
        
        double[] x = new double[]{s.getWidth(), s.getHeight()};
        
        s.close();
        
        s.setX(mainCtrl.getStage().getX() + (mainCtrl.getStage().getWidth() / 2) - (x[0]/2));
        s.setY(mainCtrl.getStage().getY() + (mainCtrl.getStage().getHeight() / 2) - (x[1]/2));
        
        s.setOpacity(1);
        
        alertLayout.setSaveBtnAction(e->{
            mainCtrl.getTabsController().saveAll();
            exitAndSaveData();
        });
        
        alertLayout.setDontSaveBtnAction(e->{
            exitAndSaveData();
        });
        
        s.showAndWait();
    }
    
    private void exitAndSaveData() {
        
        final Stage windows = mainCtrl.getStage();
        
        final PrefSave prefSave = new PrefSave();
        
        if (!windows.isMaximized()) {
            prefSave.setSavedValue("sceneData", "stage", "height", "" + ((int) windows.getHeight()));
            prefSave.setSavedValue("sceneData", "stage", "width", "" + ((int) windows.getWidth()));
        }
        
        prefSave.setSavedValue("sceneData", "stage", "xPos", "" + ((int) windows.getX()));
        prefSave.setSavedValue("sceneData", "stage", "yPos", "" + ((int) windows.getY()));
        
        prefSave.setSavedValue("sceneData", "stage", "maximized", "" + windows.isMaximized());
        
        /**
         * saving recent file paths
         */
        File[] recentFiles = mainCtrl.getMenuCtrl().getRecentFiles();
        
        if (recentFiles != null) {
            for (int i = 0; i < 5; i++) {
                if (recentFiles[i] != null) {
                    prefSave.setSavedValue("docsData", "Recent", "file" + (i+1), recentFiles[i].getPath());
                } else {
                    break;
                }
            }
        }
        
        windows.close();
        
    }
    
}
