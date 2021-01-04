
// Developed by the TerminatorOTW

package notepadfab.java.layouts;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import notepadfab.java.layouts.editor.TextEditorFx;

public class LineNoBar extends ScrollPane {
    
    private final VBox vBox;
    
    public LineNoBar(TextEditorFx t) {
        
        this.getStyleClass().add("lineNoBar");
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollBarPolicy.NEVER);
        this.setPrefViewportWidth(50);
        
        Platform.runLater(()->{
            ScrollPane sP = (ScrollPane) t.lookup(".scroll-pane");
            
            this.vvalueProperty().bindBidirectional(sP.vvalueProperty());
            
        });
        
        // content
        vBox = new VBox();
        vBox.setSpacing(3);
        vBox.getStyleClass().add("v-box");
        vBox.setAlignment(Pos.CENTER);
        
        this.setContent(vBox);
    }
    
    public VBox getBar() {
        return vBox;
    }
    
}
