
// Developed by the TerminatorOTW

package notepadfab.java.layouts;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import notepadfab.ResourceLoader;
import notepadfab.java.functions.PrefSave;

public class AlertLayout {
    
    private Stage alertStage;
    private Scene scene;
    
    private final Image alertIcon = new ResourceLoader().loadImages("alertIcon.png");
    
    private Label alertLabel1;
    private Button save, dontSave;
    
    public AlertLayout(Stage windows) {
        this.setLayout(windows);
    }
    
    public Stage returnStage() {
        alertStage.setOpacity(0);
        return alertStage;
    }
    
    private void setLayout(Stage windows) {
        
        // stage
        this.alertStage = new Stage();
        alertStage.initOwner(windows);
        alertStage.setResizable(false);
        alertStage.initModality(Modality.APPLICATION_MODAL);
        alertStage.setOnCloseRequest(e->{
            // do nothing
            e.consume();
        });
        alertStage.initStyle(StageStyle.DECORATED);
        alertStage.getIcons().add(new ResourceLoader().loadImages("logo.png"));
        alertStage.setTitle("NotepadFab-V1.0");
        
        // vbox
        final VBox mainLayout = new VBox();
        mainLayout.setSpacing(0);
        mainLayout.getStyleClass().add("mainLayout");
        
        // hBoxes
        HBox alertBox1 = new HBox();
        alertBox1.getStyleClass().add("alertBox1");
        HBox alertBox2 = new HBox();
        alertBox2.getStyleClass().add("alertBox2");
        HBox buttonBox = new HBox();
        buttonBox.getStyleClass().add("buttonBox");
        
        // alertBox1 items
        ImageView view = new ImageView();
        view.setFitHeight(40);
        view.setFitWidth(40);
        view.setImage(alertIcon);
        
        alertLabel1 = new Label("");
        alertLabel1.getStyleClass().add("alertLabel1");
        
        alertBox1.getChildren().addAll(view, alertLabel1);
        
        // alertBox2 items
        Label alertLabel2 = new Label("Your changes will be lost if you don't save them.");
        alertLabel2.getStyleClass().add("alertLabel2");
        
        alertBox2.getChildren().add(alertLabel2);
        
        // buttonBox items
        save = new Button("Save");
        
        dontSave = new Button("Dont Save");
        
        Button cancel = new Button("Cancel");
        
        cancel.setOnAction(e->{
            alertStage.close();
        });
        
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(save, dontSave, cancel);
        
        // adding layouts to the menu
        mainLayout.getChildren().addAll(
            alertBox1, alertBox2, buttonBox
        );
        
        scene = new Scene(mainLayout);
        scene.getStylesheets().add(
            new PrefSave().getStylesheetPath("alertBox").toExternalForm()
        );
        alertStage.setScene(scene);
        
        alertStage.setOnShowing(e->{
            save.requestFocus();
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
    
    public void setAlertText(String text) {
        alertLabel1.setText(text);
    }
    
    public void setSaveBtnAction(EventHandler<ActionEvent> e) {
        save.setOnAction(e);
    }
    
    public void setDontSaveBtnAction(EventHandler<ActionEvent> e) {
        dontSave.setOnAction(e);
    }
    
}

