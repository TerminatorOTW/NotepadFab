
// Developed by the TerminatorOTW

package notepadfab.java.layouts;

import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import notepadfab.ResourceLoader;
import notepadfab.java.NotepadFab;
import notepadfab.java.controllers.MenuController;
import notepadfab.java.controllers.EditorController;
import notepadfab.java.controllers.KeyController;
import notepadfab.java.controllers.MainController;
import notepadfab.java.controllers.PropertiesController;
import notepadfab.java.functions.PrefSave;
import notepadfab.java.layouts.editor.TextEditorFx;

public class MainLayout {
    
    private final Stage windows;
    private Stage alertStage;
    
    private BorderPane mainLayout;
    private BorderPane subLayout;
    
    private MenuLayout menuLayout;
    private TabsMenu tabsMenu;
    private FindBar findBar;
    private GoToLayout goToLayout;
    private final AlertLayout alertLayout;
    private final PropertiesLayouts propertiesLayouts;
    private final StatusBar statusBar = new StatusBar();
    private final TextEditorFx editor = new TextEditorFx();
    private final LineNoBar lineBar = new LineNoBar(editor);
    private final SideBar sideBar = new SideBar();
    
    private final PrefSave prefSave = new PrefSave();
    
    private boolean statusBarVisibility;
    
    private final File[] passedFile;
    
    public MainLayout(Stage stage, File[] file) {
        this.windows = stage;
        this.passedFile = file;
        this.alertLayout = new AlertLayout(stage);
        this.propertiesLayouts = new PropertiesLayouts(stage);
    }
    
    /**
     * This method returns the layout
     */
    private BorderPane returnLayout() {
        
        // main layout
        mainLayout = new BorderPane();
        mainLayout.getStyleClass().add("mainLayout");
        
        // status bar layout which will sit on the bottom of the mainlayout
        if(prefSave.getSavedValue("menuData", "View", "statusBar").equals("true")) {
            mainLayout.setBottom(statusBar);
            statusBarVisibility = true;
        } else {
            statusBarVisibility = false;
        }
        
        // menu layout which will sit on the top of the mainlayout
        menuLayout = new MenuLayout();
        mainLayout.setTop(menuLayout);
        
        subLayout = new BorderPane();
        subLayout.getStyleClass().add("subLayout");
        
            // text editor
            subLayout.setCenter(editor);
            
            // line bar
            subLayout.setLeft(lineBar);
            
            // hScrollBar
            subLayout.setBottom(editor.getHScrollBar());
            
            // Tabs layout
            tabsMenu = new TabsMenu();
            subLayout.setTop(tabsMenu);
            
            // findBar
            findBar = new FindBar(editor, subLayout);
            
            // goToBar
            goToLayout = new GoToLayout();
            
        mainLayout.setCenter(subLayout);
        
        //mainLayout.setLeft(sideBar);
        
        mainLayout.getStylesheets().add(prefSave.getStylesheetPath("main").toExternalForm());
        
        // setting controllers
        final MainController mainController = new MainController(
            this, windows, passedFile
        );
        
        return mainLayout;
    }
        /* public getter method */
        public BorderPane getMainLayout() {
            return returnLayout();
        }
    /* end */
        
    public void setBottom(Node node) {
        mainLayout.setBottom(node);
    }
    
    public void showDialog(Scene scene) {
        if (alertStage == null) {
            alertStage = new Stage();
            alertStage.initOwner(windows);
            alertStage.initStyle(StageStyle.TRANSPARENT);
            alertStage.setScene(scene);
            alertStage.centerOnScreen();
            alertStage.showAndWait();
        }
    }
    
    public void clearDialog() {
        if (alertStage != null && alertStage.isShowing()) {
            alertStage.close();
            alertStage = null;
        }
    }
    
    public void setHScrollBar(Node node) {
        subLayout.setBottom(node);
    }
    
    public TextEditorFx getTextEditor() {
        return editor;
    }
        
    public MenuLayout getMenuBar() {
        return menuLayout;
    }
    
    public StatusBar getStatusBar() {
        return statusBar;
    }
    
    public TabsMenu getTabsMenu() {
        return tabsMenu;
    }
    
    public FindBar getFindBar() {
        return findBar;
    }
    
    public GoToLayout getGoToLayout() {
        return goToLayout;
    }
    
    public AlertLayout getAlertLayout() {
        return alertLayout;
    }
    
    public PropertiesLayouts getPropertiesLayouts() {
        return propertiesLayouts;
    }
    
    public LineNoBar getLineNoBar() {
        return lineBar;
    }
    
    public SideBar getSideBar() {
        return sideBar;
    }
        
}
