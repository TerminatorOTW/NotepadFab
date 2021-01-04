
// Developed by the TerminatorOTW

package notepadfab.java.layouts;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public final class StatusBar extends HBox {
    
    // statusBarItems
    private Label lineCol, character, eol, fileType, tabsOpen;
  
    private final List<Label> statusBarItems = new ArrayList<>();
    
    /**
     * default constructor
     */
    public StatusBar() {
        this(Pos.CENTER_RIGHT, "Windows (CRLF)", 1);
    }
    
    /**
     * @param eolType
     *     specifies default end of line to show in the statusBar
     */
    public StatusBar(String eolType) {
        this(Pos.CENTER_RIGHT, eolType, 1);
    }
    
    /**
     * @param objectAlign
     *     specifies how elements are aligned in the statusBar
     * @param eolType
     *     specifies default end of line to show in the statusBar
     */
    public StatusBar(Pos objectAlign, String eolType) {
        this(objectAlign, eolType, 1);
    }
    
    /**
     * @param objectAlign
     *     specifies how elements are aligned in the statusBar
     * @param eolType
     *     specifies default end of line to show in the statusBar
     * @param tabsCount
     *     specifies how many tabs are open to show in the statusBar
     */
    private StatusBar(Pos objectAlign, String eolType, int tabsCount) {
        this.getStyleClass().add("statusBar");
        this.setAlignment(objectAlign);
        
            // statusBar items
            lineCol = new Label("Ln 1, Col 1");
            character = new Label("Character : 0");
            eol = new Label(eolType);
            fileType = new Label("Plain Text");
            tabsOpen = new Label("Tabs : " + tabsCount);
            
        // adding items to the statusBar
        Collections.addAll(statusBarItems,
            lineCol, character, eol, fileType, tabsOpen
        );
        
        this.getChildren().addAll(statusBarItems);
    }
    
    public List<Label> getStatusBarItems() {
        return statusBarItems;
    }
    
    public Label getStatusBarItem(int pos) {
        if (pos < statusBarItems.size() && pos >= 0)
            return statusBarItems.get(pos);
        else
            return null;
    }
        
}
