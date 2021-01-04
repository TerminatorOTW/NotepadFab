
// Developed by the TerminatorOTW

package notepadfab.java.controllers;

import javafx.stage.Stage;
import notepadfab.java.layouts.AlertLayout;

public class AlertController {
    
    private final MainController mainCtrl;
    private final AlertLayout alertLayout;
    
    public AlertController(MainController mC, AlertLayout aL) {
        // setup
        this.mainCtrl = mC;
        this.alertLayout = aL;
    }
    
    public void showWarning(String fileName, int filePos) {
        
        alertLayout.setAlertText("Do you want to save the changes\nyou made to "+ fileName + "?");
        
        Stage s = alertLayout.returnStage();
        s.show();
        
        double[] x = new double[]{s.getWidth(), s.getHeight()};
        
        s.close();
        
        s.setX(mainCtrl.getStage().getX() + (mainCtrl.getStage().getWidth() / 2) - (x[0]/2));
        s.setY(mainCtrl.getStage().getY() + (mainCtrl.getStage().getHeight() / 2) - (x[1]/2));
        
        s.setOpacity(1);
        
        alertLayout.setSaveBtnAction(e->{
            mainCtrl.getTabsController().saveAt(filePos);
            if (mainCtrl.getTabsController().checkSavedOnPos(filePos)) {
                mainCtrl.getTabsController().deleteTab(filePos, false);
                s.close();
            }
        });
        
        alertLayout.setDontSaveBtnAction(e->{
            mainCtrl.getTabsController().deleteTab(filePos, true);
            s.close();
        });
        
        s.showAndWait();
    }
    
}

