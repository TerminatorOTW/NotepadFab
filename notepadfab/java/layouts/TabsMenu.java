
// Developed by the TerminatorOTW

package notepadfab.java.layouts;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;

public final class TabsMenu extends HBox {
    
    private final ObservableList<Button> tabButtonsArr = FXCollections.observableArrayList(new ArrayList<>());
    private final List<Button> closeButtonsArr = new ArrayList<>();
    private final List<ImageView> closeImagesArr = new ArrayList<>();
    private final List<File> filesArr = new ArrayList<>();
    private final List<String> filesTypeArr = new ArrayList<>();
    private final List<Label> savedTextArr = new ArrayList<>();
    private final List<Label> textLabelArr = new ArrayList<>();
    
    public TabsMenu() {
        // tab-menu
        this.setSpacing(0);
        this.getStyleClass().add("tabsLayout");
        this.setBorder(Border.EMPTY);
    }
    
    public void addNewTabData(
        Button tabButton, Button closeButton,
        ImageView closeImage, File file, String fileType,
        String text
    ) {
        tabButtonsArr.add(tabButton);
        closeButtonsArr.add(closeButton);
        closeImagesArr.add(closeImage);
        filesArr.add(file);
        filesTypeArr.add(fileType);
        savedTextArr.add(new Label(text));
        textLabelArr.add(new Label(text));
        this.getChildren().addAll(tabButton, closeButton);
    }
    
    public boolean addNewTabData(
        Button tabButton, Button closeButton,
        ImageView closeImage, File file, String fileType,
        String text, int pos
    ) {
        if (pos < tabButtonsArr.size() && pos >= 0) {
            tabButtonsArr.add(pos, tabButton);
            closeButtonsArr.add(pos, closeButton);
            closeImagesArr.add(pos, closeImage);
            filesArr.add(pos, file);
            filesTypeArr.add(pos, fileType);
            savedTextArr.add(pos, new Label(text));
            textLabelArr.add(pos, new Label(text));
            return true;
        } else {
            return false;
        }
    }
    
    public boolean replaceTabData(
        Button tabButton, Button closeButton,
        ImageView closeImage, File file, String fileType,
        String text, int pos
    ) {
        if (pos < tabButtonsArr.size() && pos >= 0) {
            tabButtonsArr.set(pos, tabButton);
            closeButtonsArr.set(pos, closeButton);
            closeImagesArr.set(pos, closeImage);
            filesArr.set(pos, file);
            filesTypeArr.set(pos, fileType);
            savedTextArr.set(pos, new Label(text));
            textLabelArr.set(pos, new Label(text));
            return true;
        } else {
            return false;
        }
    }
    
    public boolean removeTab(int pos) {
        if (pos < tabButtonsArr.size() && pos >= 0) {
            tabButtonsArr.remove(pos);
            closeButtonsArr.remove(pos);
            closeImagesArr.remove(pos);
            filesArr.remove(pos);
            filesTypeArr.remove(pos);
            savedTextArr.remove(pos);
            textLabelArr.remove(pos);

            final int tabToDelete = (pos * 2);

            this.getChildren().remove(tabToDelete);
            this.getChildren().remove(tabToDelete);
            return true;
        } else {
            return false;
        }
    }
    
    public void removeAllTab() {
        tabButtonsArr.clear();
        closeButtonsArr.clear();
        closeImagesArr.clear();
        filesArr.clear();
        filesTypeArr.clear();
        savedTextArr.clear();
        textLabelArr.clear();

        this.getChildren().clear();
    }
    
    public ObservableList<Button> getTabButtonsArr() {
        return tabButtonsArr;
    }
    public Button getTabButtonsArr(int pos) {
        if (pos < tabButtonsArr.size() && pos >= 0)
            return tabButtonsArr.get(pos);
        else
            return null;
    }
    
    public List<Button> getCloseButtonsArr() {
        return closeButtonsArr;
    }
    public Button getCloseButtonsArr(int pos) {
        if (pos < closeButtonsArr.size() && pos >= 0)
            return closeButtonsArr.get(pos);
        else
            return null;
    }
    
    public List<ImageView> getCloseIconViewArr() {
        return closeImagesArr;
    }
    public ImageView getCloseIconViewArr(int pos) {
        if (pos < closeImagesArr.size() && pos >= 0)
            return closeImagesArr.get(pos);
        else
            return null;
    }
    
    public List<File> getFilesArr() {
        return filesArr;
    }
    public File getFilesArr(int pos) {
        if (pos < filesArr.size() && pos >= 0)
            return filesArr.get(pos);
        else
            return null;
    }
    
    public List<String> getFilesTypeArr() {
        return filesTypeArr;
    }
    public String getFilesTypeArr(int pos) {
        if (pos < filesTypeArr.size() && pos >= 0)
            return filesTypeArr.get(pos);
        else
            return null;
    }
    
    public List<Label> getSavedTextArr() {
        return savedTextArr;
    }
    public Label getSavedTextArr(int pos) {
        if (pos < savedTextArr.size() && pos >= 0)
            return savedTextArr.get(pos);
        else
            return null;
    }
    
    public List<Label> getCurrentTextArr() {
        return textLabelArr;
    }
    public Label getCurrentTextArr(int pos) {
        if (pos < textLabelArr.size() && pos >= 0)
            return textLabelArr.get(pos);
        else
            return null;
    }
}
