
// Developed by the TerminatorOTW

package notepadfab.java.controllers;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;
import notepadfab.java.layouts.PropertiesLayouts;

public class PropertiesController {
    
    private final MainController mainCtrl;
    private final PropertiesLayouts ptLayouts;
    
    public PropertiesController(MainController mC, PropertiesLayouts pL) {
        this.mainCtrl = mC;
        this.ptLayouts = pL;
    }
    
    public void launch(File file, int pos) {
        
        Stage s = ptLayouts.returnStage();
        
        if (file != null) {
            ptLayouts.setRenameFieldText(file.getName());
            ptLayouts.positionCaret(file.getName().length());
        } else {
            ptLayouts.setRenameFieldText("Untitled.txt");
            ptLayouts.positionCaret(12);
        }
        
        ptLayouts.setRenameFieldAction(e->{
            if (file != null) {
                String name = ptLayouts.getText();
                File newFile = new File(file.getParent() + File.separator + name);
                
                if (file.renameTo(newFile)) {
                    mainCtrl.getTabsController().renameTab(pos, newFile);
                }
                s.close();
            } else {
                s.close();
                mainCtrl.getMenuCtrl().saveFile(
                    file, ptLayouts.getText(),
                    mainCtrl.getTabsController().getFromCurrentTextsArr(pos), pos
                );
            }
        });
        
        s.show();
        
        double[] x = new double[]{s.getWidth(), s.getHeight()};
        
        s.close();
        
        s.setX(mainCtrl.getStage().getX() + (mainCtrl.getStage().getWidth() / 2) - (x[0]/2));
        s.setY(mainCtrl.getStage().getY() + (mainCtrl.getStage().getHeight() / 2) - (x[1]/2));
        
        s.setOpacity(1);
        
        s.showAndWait();
    }
    
}
