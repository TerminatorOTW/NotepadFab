
// Developed by the TerminatorOTW

package notepadfab.java.layouts;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import notepadfab.ResourceLoader;
import notepadfab.java.functions.PrefSave;

public class PropertiesLayouts {
    
    private final Stage windows;
    private Stage propertiesStage;
    private Scene scene;
    
    private TextField renameField;
    private Label renameLabel;
    
    public PropertiesLayouts(Stage stage) {
        windows = stage;
        this.setLayout();
    }
    
    public Stage returnStage() {
        propertiesStage.setOpacity(0);
        return propertiesStage;
    }
    
    private void setLayout() {
        
        // stage
        this.propertiesStage = new Stage();
        propertiesStage.initOwner(windows);
        propertiesStage.setResizable(false);
        propertiesStage.initModality(Modality.APPLICATION_MODAL);
        propertiesStage.initStyle(StageStyle.DECORATED);
        propertiesStage.getIcons().add(new ResourceLoader().loadImages("logo.png"));
        propertiesStage.setTitle("File Properties");
        
        // mainLayout
        final VBox mainLayout = new VBox();
        mainLayout.getStyleClass().add("mainLayout");
        
        // subLayouts
        final HBox renameLayout = new HBox();
        renameLayout.getStyleClass().add("renameLayout");
        renameLayout.setAlignment(Pos.CENTER_LEFT);
        
            // sub-sub layout
            renameLabel = new Label("File Name : ");
            
            renameField = new TextField();
            
            renameLayout.getChildren().addAll(renameLabel, renameField);
            
        mainLayout.getChildren().addAll(renameLayout);
        
        scene = new Scene(mainLayout);
        scene.getStylesheets().add(
            new PrefSave().getStylesheetPath("propertiesBox").toExternalForm()
        );
        propertiesStage.setScene(scene);
        
        propertiesStage.setOnShowing(e->{
            renameField.requestFocus();
        });
        
        scene.setOnKeyReleased((KeyEvent e) -> {
            switch(e.getCode()) {
                case ENTER:
                    try {
                        ((Button) scene.getFocusOwner()).fire();
                    } catch (Exception f) {}
                    break;
                default:
                    break;
            }
        });
        
    }
    
    public void setRenameFieldAction(EventHandler<ActionEvent> e) {
        renameField.setOnAction(e);
    }
    public void positionCaret(int pos) {
        renameField.positionCaret(pos);
    }
    public String getText() {
        return renameField.getText();
    }
    public void setRenameFieldText(String text){
        renameField.setText(text);
    }

}
