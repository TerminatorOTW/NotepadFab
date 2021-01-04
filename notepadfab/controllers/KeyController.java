
// Developed by the TerminatorOTW

package notepadfab.java.controllers;

import javafx.event.Event;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import notepadfab.java.layouts.MenuLayout;
import notepadfab.java.layouts.editor.TextEditorFx;

public class KeyController {
    
    private final MainController mainCtrl;
    
    private boolean controlIsOn = false;
    private boolean shiftIsOn = false;
    private boolean altIsOn = false;
    
    public KeyController(MainController mC) {
        this.mainCtrl = mC;
        
        // methods to run
        this.setKeyHandler();
    }
    
    private void setKeyHandler() {
        
        mainCtrl.getEditorCtrl().setOnKeyPressed(e->{
            
            if (controlIsOn) {
                if (shiftIsOn) {
                    controlShiftFunctions(e, e.getCode());
                } else if (altIsOn) {
                    controlAltFunctions(e, e.getCode());
                } else {
                    controlFunctions(e, e.getCode());
                }
            }
            
            switch (e.getCode()) {
                case CONTROL:
                    controlIsOn = true;
                    break;
                case SHIFT:
                    shiftIsOn = true;
                    break;
                case ALT:
                    altIsOn = true;
                    break;
                case TAB:
                    e.consume();
                    break;
                default:
                    break;
            }
        });
        
        mainCtrl.getEditorCtrl().setOnKeyReleased(e->{
            switch (e.getCode()) {
                case CONTROL:
                    controlIsOn = false;
                    break;
                case SHIFT:
                    shiftIsOn = false;
                    break;
                case ALT:
                    altIsOn = false;
                    break;
                case TAB:
                    e.consume();
                    if (shiftIsOn)
                        mainCtrl.getEditorCtrl().reverseTabFn();
                    else
                        mainCtrl.getEditorCtrl().tabFn();
                    break;
                case F5:
                    e.consume();
                    mainCtrl.getMenuCtrl().fireTimeDateItem();
                    break;
                default:
                    break;
            }
        });
        
    }
    
    private void controlShiftFunctions(Event e, KeyCode key) {
        
        switch (key) {
            case S:
                e.consume();
                mainCtrl.getMenuCtrl().fireSaveAsItem();
                controlIsOn = false;
                shiftIsOn = false;
                break;
            default:
                break;
        }
        
    }
    
    private void controlAltFunctions(Event e, KeyCode key) {
        
        switch(key) {
            case S:
                e.consume();
                mainCtrl.getMenuCtrl().fireSaveAllItem();
                controlIsOn = false;
                shiftIsOn = false;
                break;
            default:
                break;
        }
        
    }
    
    private void controlFunctions(Event e, KeyCode key) {
        
        switch (key) {
            case F4:
                mainCtrl.getTabsController().deleteTab(
                    mainCtrl.getTabsController().getCurrentTabPos(), false
                );
                break;
            case S:
                e.consume();
                mainCtrl.getMenuCtrl().fireSaveItem();
                controlIsOn = false;
                break;
            case R:
                e.consume();
                mainCtrl.getMenuCtrl().fireRenameItem();
                controlIsOn = false;
                break;
            default:
                break;
        }
        
    }
    
}
