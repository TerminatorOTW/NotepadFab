
// Developed by the TerminatorOTW

package notepadfab.java.controllers;

import javafx.scene.paint.Color;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import notepadfab.java.layouts.LineNoBar;

public class LineBarController {
    
    private final MainController mainCtrl;
    private final LineNoBar lineBar;
    
    private int lineCount = 1;
    private int currLine = 0;
    /*private int highlightedLine = 0;
    private final Color defaultColor = Color.rgb(35, 120, 160);
    private final Color highlightColor = Color.rgb(60, 145, 185);*/
    
    public LineBarController(MainController m, LineNoBar lnb) {
        this.mainCtrl = m;
        this.lineBar = lnb;
        this.addLine(lineCount + "");
    }
    
    public LineNoBar getLineBar() {
        return lineBar;
    }
    
    public void setDisable(boolean v) {
        lineBar.setVisible(!v);
    }
    
    public void setLineCount(int count) {
        if (count > lineCount)
            addLines(count);
        else if (count < lineCount)
            deleteLines(count);
    }
    
    public void setCurrLine(int cL) {
        if(currLine != cL) {
            setHighlightedLine(cL);
        }
    }
    
    private void setHighlightedLine(int pos) {
        
        if (checkIfInRange((currLine-1), lineBar.getBar().getChildren())) {
            ((Label) lineBar.getBar().getChildren().get(currLine-1)).setStyle(
                "-fx-text-fill: rgb(35, 120, 160);"
                + "-fx-background-color: transparent"
            );
        }
        
        currLine = pos;
        
        if (checkIfInRange((pos-1), lineBar.getBar().getChildren())) {
            ((Label) lineBar.getBar().getChildren().get(pos-1)).setStyle(
                "-fx-text-fill: #fff;"
                + "-fx-background-color: rgb(35, 120, 160);"
            );
        }
    }
    
    private boolean checkIfInRange(int pos, ObservableList<Node> nodes) {
        if (nodes.size() > 0 && pos < nodes.size() && pos >= 0)
            return true;
        else
            return false;
    }
    
    private void addLines(int lines) {
        String text1 = lineCount + "";
        int length1 = text1.length();
        String text2 = lines + "";
        int length2 = text2.length();
        
        if (length1 != length2) {
            lineCount = 0;
            lineBar.getBar().getChildren().clear();
            for (int i = 0; i < lines; i++) {
                String x = (i+1) + "";
                for (int j = x.length(); j < length2; j++) {
                    x = "0" + x;
                }
                addLine(x);
                lineCount++;
            }
        } else {
            
            for (int i=lineCount; i < lines; i++) {
                lineCount++;
                this.addLine(lineCount + "");
            }
            
        }
    }
    
    private void addLine(String text) {
        lineBar.getBar().getChildren().add(new Label(text));
    }
    
    private void deleteLines(int currentLineCount) {
        
        String x = currentLineCount + "";
        int length = x.length();
        
        lineCount = 0;
        lineBar.getBar().getChildren().clear();
        
        for (int i = 0; i < currentLineCount; i++) {
            String y = (i+1) + "";
            for (int j = y.length(); j < length; j++) {
                y = "0" + y;
            }
            addLine(y);
            lineCount++;
        }
        
    }
    
}
