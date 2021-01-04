
// Developed by the TerminatorOTW

package notepadfab.java.functions;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import notepadfab.java.controllers.MainController;

public class AutoSaveThread {
    
    private final Timer timer;
    private final TimerTask task;
    
    public AutoSaveThread(MainController mainCtrl) {
        
        timer = new Timer();
        
        task = new TimerTask() {
            @Override
            public void run() {
                doIt(mainCtrl);
            }
        };
    }
    
    public void start() {
        timer.scheduleAtFixedRate(task, 10000, 10000);
    }
    
    public void stop() {
        timer.cancel();
    }
    
    private void doIt(MainController mainCtrl) {
        if (mainCtrl.getTabsController().getTabsCount() == 0 || mainCtrl.getTabsController().getCurrentFile() == null) {
        } else {
            Platform.runLater(()->{
                mainCtrl.getMenuCtrl().fireSaveItem();
            });
        }
    }
    
}
