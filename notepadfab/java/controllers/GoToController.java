
// Developed by the TerminatorOTW

package notepadfab.java.controllers;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import notepadfab.ResourceLoader;
import notepadfab.java.layouts.GoToLayout;

public class GoToController {
    
    private final GoToLayout layout;
    private final MainController mainCtrl;
    private final Scene scene;
    
    public GoToController(GoToLayout goToLayout, MainController mC) {
        
        layout = goToLayout;
        mainCtrl = mC;
        
        scene = new Scene(layout);/*
        scene.getStylesheets().add(
            new ResourceLoader().loadStylesheet("findBarStyle.css").toExternalForm()
        );*/
        
        layout.setAction(e->{
            try {
                jumpTo(Integer.parseInt(layout.getText()), mainCtrl.getEditorCtrl().getEditor());
            } catch (NumberFormatException f) {
                layout.setText("");
            }
        });
        layout.setCloseAction(e->{
            hideLayout();
        });
    }
    
    public void showlayout() {
        mainCtrl.getMainLayout().showDialog(scene);
        layout.getFocus();
    }
    
    public void hideLayout() {
        mainCtrl.getMainLayout().clearDialog();
        layout.setText("");
    }
    
    private void jumpTo(int lineNo, TextArea editor) {
        
        String text = editor.getText();
        int length = text.length();
        
        int match = 0;
        
        for (int i = 0; i < length; i++) {
            if (text.charAt(i) == '\n')
                match++;
            
            if (match == lineNo) {
                editor.requestFocus();
                editor.positionCaret(i);
                break;
            }
                
        }
        
    }
    
}
