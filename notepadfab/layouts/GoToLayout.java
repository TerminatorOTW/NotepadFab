
// Developed by the TerminatorOTW

package notepadfab.java.layouts;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import notepadfab.ResourceLoader;

public class GoToLayout extends HBox {
    
    TextField goToField;
    Button closeButton;
    
    public GoToLayout() {
        this.getStyleClass().add("findBar");
        this.setAlignment(Pos.CENTER_RIGHT);
        this.setGoToLayout();
    }
    
    private void setGoToLayout() {
        
        Image closeImage = new ResourceLoader().loadImages("closeIcon.png");
        
        // GoToBar
        goToField = new TextField();
        goToField.getStyleClass().add("findBox");
        goToField.setPrefWidth(200);
        goToField.setPromptText("Go to Line No : ");
        
        // closeButton
        closeButton = new Button();
        HBox.setMargin(closeButton, new Insets(0, 0, 0, 5));
        closeButton.getStyleClass().add("findBarCloseButton");
        closeButton.setGraphic(new ImageView(closeImage));
        
        this.getChildren().addAll(goToField, closeButton);
    }
    
    public void setAction(EventHandler<ActionEvent> e) {
        goToField.setOnAction(e);
    }
    
    public void setCloseAction(EventHandler<ActionEvent> e) {
        closeButton.setOnAction(e);
    }
    
    public void setText(String text) {
        goToField.setText(text);
    }
    
    public String getText() {
        return goToField.getText();
    }
    
    public void getFocus() {
        goToField.requestFocus();
    }

}
