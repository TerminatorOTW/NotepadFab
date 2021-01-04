
// Developed by the TerminatorOTW

package notepadfab.java.api;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.control.Tooltip;

public class TooltipFab extends Tooltip {
    
    public TooltipFab() {
        this("");
    }
    
    public TooltipFab(String text) {
        this.setText(text);
    }
    
    public void setAutoHideDelay(int valInMillis) {
        
        this.setOnShowing(e->{
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        TooltipFab.this.hide();
                    });
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, valInMillis);
        });
        
    }
    
}
