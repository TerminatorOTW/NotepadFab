
// Developed by the TerminatorOTW

package notepadfab.java.layouts;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import notepadfab.ResourceLoader;

public class SideBar extends HBox {
    
    private final ResourceLoader resLoader = new ResourceLoader();
    
    private final VBox bar, content;
    
    private Button fileExplorer, searchFn, recentFiles, netExplorer;
    
    private final Image fileEx, search;
    
    public SideBar() {
        this.getStyleClass().add("sideBar");
        
        // loadImages
        this.fileEx = resLoader.loadImages("fileExplorerIcon.png");
        this.search = resLoader.loadImages("searchIcon.png");
        
        // set Layout
        this.bar = new VBox();
        
        this.content = new VBox();
        
        this.setLayout();
    }
    
    public HBox getLayout() {
        return this;
    }
    
    public Button getFileExplorer() {
        return fileExplorer;
    }
    
    private void setLayout() {
        
        fileExplorer = new Button();
        fileExplorer.setGraphic(new ImageView(fileEx));
        
        searchFn = new Button();
        searchFn.setGraphic(new ImageView(search));
        
        /*recentFiles = new Button();
        
        netExplorer = new Button();*/
        
        bar.getChildren().addAll(fileExplorer/*, searchFn, recentFiles, netExplorer*/);
        
        this.getChildren().addAll(bar, content);
    }
    
}
