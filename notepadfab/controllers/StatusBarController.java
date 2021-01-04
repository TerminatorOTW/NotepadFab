
// Developed by the TerminatorOTW

package notepadfab.java.controllers;

import javafx.scene.control.Label;
import notepadfab.java.layouts.StatusBar;

public class StatusBarController {
    
    private final StatusBar statusBar;
    private final MainController mainCtrl;
    
    private boolean isShowing = true;
    
    public StatusBarController(StatusBar sB, MainController mC, boolean statusBarShowing) {
        this.statusBar = sB;
        this.mainCtrl = mC;
        this.isShowing = statusBarShowing;
    }
    
    public StatusBar getStatusBarLayout() {
        return statusBar;
    }
    
    public boolean isShowing() {
        return isShowing;
    }
    public void setShowing(boolean v) {
        isShowing = v;
    }
    
    public void setTabsCount(int count) {
        Label label = statusBar.getStatusBarItem(4);
        if(label != null)
            label.setText("Tabs : " + count);
    }
    
    public void setCharacterCount(int count) {
        Label label = statusBar.getStatusBarItem(1);
        if(label != null)
            label.setText("Character : " + count);
    }
    
    public void setLineColCaretPos(int lineCount, int colCount, int selectedTextLen) {
        Label label = statusBar.getStatusBarItem(0);
        if(label != null) {
            if (selectedTextLen > 0)
                label.setText("Ln " + lineCount + ", Col " + colCount + " (" + selectedTextLen + " selected)");
            else
                label.setText("Ln " + lineCount + ", Col " + colCount);
        }
    }
    
    public void setCurrentFileType(String text) {
        Label label = statusBar.getStatusBarItem(3);
        if(label != null)
            label.setText(text);
    }
    
    public void setCurrentEol(String endOfLine) {
        Label label = statusBar.getStatusBarItem(2);
        if(label != null)
            label.setText(endOfLine);
    }
    
    public void setBlankStatusBar(boolean value) {
        if (value) {
            statusBar.getChildren().remove(0, 4);
        } else {
            statusBar.getChildren().clear();
            statusBar.getChildren().addAll(statusBar.getStatusBarItems());
        }
    }
    
}
