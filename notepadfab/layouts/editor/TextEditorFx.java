
// Developed by the TerminatorOTW

package notepadfab.java.layouts.editor;

import notepadfab.java.layouts.editor.*;
import com.sun.javafx.scene.control.behavior.TextAreaBehavior;
import com.sun.javafx.scene.control.skin.TextAreaSkin;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Skin;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;

public final class TextEditorFx extends TextArea {
    
    //private final Skin skin;
    
    private final ScrollBar hScrollBar = new ScrollBar();
    private boolean scrollBarVisible = false;
    
    /**
     * default constructor
     */
    public TextEditorFx() {
        
        // setting style class
        this.getStyleClass().add("textEditorFx");
        
        Platform.runLater(()->{
            ScrollPane s = (ScrollPane) this.lookup(".scroll-pane");
            s.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            setHScrollBar(s);
        });
        /*
        // setting skin
        skin = new Skin(this);
        this.setSkin(skin);*/
        
        // hScrollBar
        hScrollBar.setOrientation(Orientation.HORIZONTAL);
        
    }
    /*
    public class Skin extends TextAreaSkin {

        public Skin(TextArea textArea) {
            super(textArea);
        }
        
        public Path getCaret() {
            return this.caretPath;
        }
        
        public void setContextMenu(ContextMenu cM) {
            this.populateContextMenu(cM);
        }
        
    }
    
    public class Behavior extends TextAreaBehavior {

        public Behavior(TextArea textArea) {
            super(textArea);
        }
        
    }
    
    public void setCaretColor(Color color) {
        Path s = skin.getCaret();
        
        if (s != null) {
            if (s.strokeProperty().isBound())
                s.strokeProperty().unbind();
            s.setStroke(color);
        } else {}
    }
    
    public Skin getAreaSkin() {
        return skin;
    }*/
    
    public ScrollBar getHScrollBar() {
        return hScrollBar;
    }
    
    public boolean isHScrollBarVisible() {
        return scrollBarVisible;
    }
    
    private void setHScrollBar(ScrollPane s) {
        
        DoubleProperty hVal = s.hvalueProperty();
        hScrollBar.valueProperty().bindBidirectional(hVal);
        
        DoubleProperty hMax = s.hmaxProperty();
        hScrollBar.maxProperty().bind(hMax);
        
        DoubleProperty hMin = s.hminProperty();
        hScrollBar.minProperty().bind(hMin);
        
        double tW = s.getContent().getLayoutBounds().getWidth();
        hScrollBar.setVisibleAmount(s.getHmax() * s.getViewportBounds().getWidth() / tW);
        
        if (tW == s.getViewportBounds().getWidth()) {
            scrollBarVisible = false;
            hScrollBar.setVisible(scrollBarVisible);
        } else {
            scrollBarVisible = true;
            hScrollBar.setVisible(scrollBarVisible);
        }
        
        s.getContent().layoutBoundsProperty().addListener(e->{
            double totalWidth = s.getContent().getLayoutBounds().getWidth();
            hScrollBar.setVisibleAmount(s.getHmax() * s.getViewportBounds().getWidth() / totalWidth);
            if (totalWidth == s.getViewportBounds().getWidth()) {
                scrollBarVisible = false;
                hScrollBar.setVisible(scrollBarVisible);
            } else {
                scrollBarVisible = true;
                hScrollBar.setVisible(scrollBarVisible);
            }
        });
    }
    
}
