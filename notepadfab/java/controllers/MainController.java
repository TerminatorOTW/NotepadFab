
// Developed by the TerminatorOTW

package notepadfab.java.controllers;

import java.io.File;
import javafx.stage.Stage;
import notepadfab.java.functions.AutoSaveThread;
import notepadfab.java.functions.PrefSave;
import notepadfab.java.layouts.MainLayout;

public class MainController {
    
    private final MainLayout mainLayout;
    private final EditorController editorCtrl;
    private final KeyController keyCtrl;
    private final MenuController menuCtrl;
    private final StatusBarController statusBarCtrl;
    private final TabsMenuController tabsMenuCtrl;
    private final ExitController exitCtrl;
    private final PropertiesController propertiesCtrl;
    private final AlertController alertCtrl;
    private final LineBarController lineBarCtrl;
    private final SideBarController sideBarCtrl;
    private final GoToController goToCtrl;
    private final Stage stage;
    
    public MainController(MainLayout mL, Stage s, File[] passedFile) {
        
        this.mainLayout = mL;
        
        this.editorCtrl = new EditorController(mainLayout.getTextEditor(), this);
        this.menuCtrl = new MenuController(mainLayout.getMenuBar(), this);
        this.keyCtrl = new KeyController(this);
        
        boolean v = new PrefSave().getSavedValue("menuData", "View", "statusBar").equals("true");
        this.statusBarCtrl = new StatusBarController(mainLayout.getStatusBar(), this, v);
        
        this.tabsMenuCtrl = new TabsMenuController(mainLayout.getTabsMenu(), this);
        this.exitCtrl = new ExitController(this, mainLayout.getAlertLayout());
        this.propertiesCtrl = new PropertiesController(this, mainLayout.getPropertiesLayouts());
        this.alertCtrl = new AlertController(this, mainLayout.getAlertLayout());
        this.lineBarCtrl = new LineBarController(this, mainLayout.getLineNoBar());
        this.sideBarCtrl = new SideBarController(this, mainLayout.getSideBar());
        
        this.goToCtrl = new GoToController(mainLayout.getGoToLayout(), this);
        
        this.stage = s;
        
        this.tabsMenuCtrl.setup(passedFile);
        this.menuCtrl.actionHandlers();
        this.menuCtrl.setDefaultValues();
    }
    
    public MainLayout getMainLayout() {
        return mainLayout;
    }
    
    public EditorController getEditorCtrl() {
        return editorCtrl;
    }
    
    public KeyController getKeyCtrl() {
        return keyCtrl;
    }
    
    public MenuController getMenuCtrl() {
        return menuCtrl;
    }
    
    public StatusBarController getStatusBarCtrl() {
        return statusBarCtrl;
    }
    
    public TabsMenuController getTabsController() {
        return tabsMenuCtrl;
    }
    
    public ExitController getExitCtrl() {
        return exitCtrl;
    }
    
    public PropertiesController getPropertiesCtrl() {
        return propertiesCtrl;
    }
    
    public AlertController getAlertCtrl() {
        return alertCtrl;
    }
    
    public LineBarController getLineBarController() {
        return lineBarCtrl;
    }
    
    public SideBarController getSideBarController() {
        return sideBarCtrl;
    }
    
    public GoToController getGoToController() {
        return goToCtrl;
    }
    
    public Stage getStage() {
        return stage;
    }
    
}
