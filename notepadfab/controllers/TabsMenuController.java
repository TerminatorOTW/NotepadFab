
// Developed by the TerminatorOTW

package notepadfab.java.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import notepadfab.ResourceLoader;
import notepadfab.java.api.File_rw;
import notepadfab.java.api.TooltipFab;
import notepadfab.java.functions.FileFn;
import notepadfab.java.layouts.TabsMenu;

public class TabsMenuController {
    
    private final TabsMenu tabsMenu;
    private final MainController mainCtrl;
    private final ResourceLoader resLoader = new ResourceLoader();
    private final File_rw fileRW = new File_rw();
    private final FileFn fileFn = new FileFn();
    
    // loading images
    private final Image closeIcon = resLoader.loadImages("closeIcon.png");
    private final Image closeIconHover = resLoader.loadImages("closeIconHover.png");
    private final Image unsavedIcon = resLoader.loadImages("circle.png");
    
    private File currentFile = null;
    
    private final double[] caretPos = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};

    // blah blah
    private int openTabs = 0;
    private int currentTab = 0;
    
    public TabsMenuController(TabsMenu tM, MainController mC) {
        // loading layouts
        this.tabsMenu = tM;
        this.mainCtrl = mC;
    }
    
    /**
     * setup method for this controller
     * @param fileArr
     */
    public void setup(File[] fileArr) {
        
        tabsMenu.getTabButtonsArr().addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change c) {
                openTabs = tabsMenu.getTabButtonsArr().size();
                mainCtrl.getStatusBarCtrl().setTabsCount(openTabs);
                if (openTabs <= 0) {
                    mainCtrl.getEditorCtrl().clearText();
                    mainCtrl.getEditorCtrl().setEditable(false);
                    mainCtrl.getStatusBarCtrl().setBlankStatusBar(true);
                    mainCtrl.getLineBarController().setDisable(true);
                } else if (openTabs > 0 && (!mainCtrl.getEditorCtrl().isEditable())) {
                    mainCtrl.getEditorCtrl().setEditable(true);
                    mainCtrl.getStatusBarCtrl().setBlankStatusBar(false);
                    mainCtrl.getLineBarController().setDisable(false);
                }
            }
        });
        
        if (fileArr.length > 0) {
            for (File file : fileArr) {
                String text = "";
                
                if (file != null) {
                    text = fileRW.getText(file);
                }

                addTab(file, text);
            }
        } else {
            addTab(null, "");
        }
    }
    
    /**
     * public methods for use in other classes
     */
    public int getCurrentTabPos() {
        return currentTab;
    }
    
    public int getTabsCount() {
        return openTabs;
    }
    
    public File getCurrentFile() {
        return currentFile;
    }
    
    public File getFile(int pos) {
        return tabsMenu.getFilesArr(pos);
    }
    
    public boolean addNewTab(File file, String text) {
        if (openTabs < 5) {
            int pos = -1;
            if ((pos = checkIfFileIsAlreadyOpen(file)) >= 0) {
                switchTab(pos, false, false);
                return false;
            } else {
                addTab(file, text);
                return true;
            }
        } else {
            // do nothing
            return false;
        }
    }
    
    public void deleteTab(int pos, boolean deleteAnyway) {
        if (pos >= 0 && pos < openTabs) {
            if (checkSavedOnPos(pos) || deleteAnyway) {
                removeTab(pos);
            } else {
                mainCtrl.getAlertCtrl().showWarning(
                    tabsMenu.getTabButtonsArr(pos).getText(), pos
                );
            }
        }
    }
    
    public void deleteAllTab() {
        if (openTabs > 0)
            tabsMenu.removeAllTab();
    }
    
    public void launchPropertiesWindow() {
        mainCtrl.getPropertiesCtrl().launch(
            tabsMenu.getFilesArr(currentTab), currentTab
        );
    }
    
    public void launchPropertiesWindow(int pos) {
        mainCtrl.getPropertiesCtrl().launch(
            tabsMenu.getFilesArr(pos), pos
        );
    }
    
    public void updateCurrentText(String text) {
        tabsMenu.getCurrentTextArr(currentTab).setText(text);
    }
    
    public String getCurrentText() {
        return tabsMenu.getCurrentTextArr(currentTab).getText();
    }
    
    public String getFromCurrentTextsArr(int pos) {
        return tabsMenu.getCurrentTextArr(pos).getText();
    }
    
    public void updateSavedText(String text) {
        tabsMenu.getSavedTextArr(currentTab).setText(text);
    }
    
    public String getSavedText() {
        return tabsMenu.getSavedTextArr(currentTab).getText();
    }
    
    public String getFromSavedTextsArr(int pos) {
        return tabsMenu.getSavedTextArr(pos).getText();
    }
    
    public boolean isAllSaved() {
        boolean allSaved = true;
        
        for (int i=0; i < openTabs; i++) {
            if (!checkSavedOnPos(i)) {
                allSaved = false;
                break;
            }
        }
        
        return allSaved;
    }
    
    public void saveAt(int pos) {
        if (pos >= 0 && pos < openTabs) {
            mainCtrl.getMenuCtrl().saveFile(
                tabsMenu.getFilesArr(pos),
                tabsMenu.getCurrentTextArr(pos).getText(),
                pos
            );
        }
    }
    
    public void saveAsAt(int pos) {
        if (pos >= 0 && pos < openTabs) {
            mainCtrl.getMenuCtrl().saveAs(
                tabsMenu.getCurrentTextArr(pos).getText(),
                tabsMenu.getTabButtonsArr(pos).getText()
            );
        }
    }
    
    public void saveAll() {
        for(int i = 0; i < openTabs; i++) {
            if (!checkSavedOnPos(i)) {
                String text = tabsMenu.getCurrentTextArr(i).getText();
                File file = tabsMenu.getFilesArr(i);
                mainCtrl.getMenuCtrl().saveFile(file, text, i);
            }
        }
    }
    
    public int checkIfFileIsAlreadyOpen(File file) {
        if(file != null && tabsMenu.getFilesArr().contains(file))
            return tabsMenu.getFilesArr().indexOf(file);
        return -1;
    }
    
    public void checkSavedOnHoverEnter(int pos) {
        tabsMenu.getCloseIconViewArr(pos).setImage(closeIconHover);
    }
    
    public void checkSavedOnHoverExited(int pos) {
        if(!checkSavedOnPos(pos)) {
            tabsMenu.getCloseIconViewArr(pos).setImage(unsavedIcon);
        } else {
            tabsMenu.getCloseIconViewArr(pos).setImage(closeIcon);
        }
    }
    
    public void checkSavedOnCurrentTab() {
        checkSavedOnPos(currentTab);
    }
    
    public boolean checkSavedOnPos(int pos) {
        
        if (tabsMenu.getFilesArr(pos) != null && getFromCurrentTextsArr(pos).equals(getFromSavedTextsArr(pos))) {
            tabsMenu.getCloseIconViewArr(pos).setImage(closeIcon);
            return true;
        } else if (tabsMenu.getFilesArr(pos) == null && getFromCurrentTextsArr(pos).equals("")) {
            tabsMenu.getCloseIconViewArr(pos).setImage(closeIcon);
            return true;
        } else {
            tabsMenu.getCloseIconViewArr(pos).setImage(unsavedIcon);
            return false;
        }
        
    }
    
    public void switchTab(int switchToTab, boolean switchAnyway, boolean forcedSwitch) {
        if (switchAnyway || forcedSwitch || (switchToTab != currentTab && switchToTab < openTabs && switchToTab >= 0)) {
            caretPos[currentTab] = mainCtrl.getEditorCtrl().getCaretPos();
            currentTab = switchToTab;
            currentFile = tabsMenu.getFilesArr(currentTab);
            mainCtrl.getStatusBarCtrl().setCurrentFileType(tabsMenu.getFilesTypeArr(currentTab));
            setFocusToTabStyle(openTabs, currentTab);
            loadText(currentTab, switchAnyway);
        }
    }
    
    public void setFocusToTabStyle(int openTabs, int currentTab) {
        
        for (int i = 0; i < openTabs; i++) {
            if (i == currentTab) {
                tabsMenu.getTabButtonsArr(i).setStyle("-fx-background-color: white; -fx-text-fill: rgb(81, 81, 81)");
                tabsMenu.getCloseButtonsArr(i).setStyle("-fx-background-color: white");
                tabsMenu.getTabButtonsArr(i).setBorder(new Border(
                    new BorderStroke(
                        Paint.valueOf("#fff"), Paint.valueOf("#fff"), Paint.valueOf("#818181"), Color.TRANSPARENT,
                        BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        new BorderWidths(1, 1, 2, 1),
                        new Insets(0, 0, 0, 0)
                    )
                ));
                tabsMenu.getCloseButtonsArr(i).setBorder(new Border(
                    new BorderStroke(
                        Paint.valueOf("#fff"), Paint.valueOf("#fff"), Paint.valueOf("#818181"), Color.TRANSPARENT,
                        BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        new BorderWidths(1, 1, 2, 1),
                        new Insets(0, 0, 0, 0)
                    )
                ));
            } else {
                if (i == 0) {
                    tabsMenu.getTabButtonsArr(i).setStyle("-fx-background-color: rgb(236, 236, 236); -fx-text-fill: rgb(108, 108, 108)");
                    tabsMenu.getCloseButtonsArr(i).setStyle("-fx-background-color: rgb(236, 236, 236)");
                    tabsMenu.getTabButtonsArr(i).setBorder(new Border(
                        new BorderStroke(
                            Color.rgb(236, 236, 236),
                            BorderStrokeStyle.SOLID,
                            CornerRadii.EMPTY,
                            new BorderWidths(1, 1, 2, 1)
                        )
                    ));
                    tabsMenu.getCloseButtonsArr(i).setBorder(new Border(
                        new BorderStroke(
                            Color.rgb(236, 236, 236),
                            BorderStrokeStyle.SOLID,
                            CornerRadii.EMPTY,
                            new BorderWidths(1, 1, 2, 1)
                        )
                    ));
                } else {
                    tabsMenu.getTabButtonsArr(i).setStyle("-fx-background-color: rgb(236, 236, 236); -fx-text-fill: rgb(108, 108, 108)");
                    tabsMenu.getCloseButtonsArr(i).setStyle("-fx-background-color: rgb(236, 236, 236)");
                    tabsMenu.getTabButtonsArr(i).setBorder(new Border(
                        new BorderStroke(
                            Paint.valueOf("#ececec"), Paint.valueOf("#ececec"), Paint.valueOf("#ececec"), Paint.valueOf("#fff"),
                            BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                            CornerRadii.EMPTY,
                            new BorderWidths(1, 1, 2, 1),
                            new Insets(0, 0, 0, 0)
                        )
                    ));
                    tabsMenu.getCloseButtonsArr(i).setBorder(new Border(
                        new BorderStroke(
                            Color.rgb(236, 236, 236),
                            BorderStrokeStyle.SOLID,
                            CornerRadii.EMPTY,
                            new BorderWidths(1, 1, 2, 1)
                        )
                    ));
                }
            }
        }
        
    }
    
    public void loadText(int pos, boolean switchAnyway) {
        if (pos < openTabs && pos >= 0) {
            String text = tabsMenu.getCurrentTextArr(pos).getText();
            mainCtrl.getEditorCtrl().setText(text);
            Platform.runLater(()->{
                if(switchAnyway) {
                    mainCtrl.getEditorCtrl().positionCaret(text.length());
                } else {
                    mainCtrl.getEditorCtrl().positionCaret((int) caretPos[pos]);
                }
            });
        }
    }
    
    public void updateTabWhenSaved(int pos, File file, boolean autoSave) {
        
        File oldFile = tabsMenu.getFilesArr(pos);
        tabsMenu.getSavedTextArr(pos).setText(tabsMenu.getCurrentTextArr(pos).getText());
        
        if (!autoSave) {
            if (oldFile != file) {
                addTabOverExistingTab(file, pos);
                if (pos == currentTab) {
                    mainCtrl.getStatusBarCtrl().setCurrentFileType(fileFn.getFileType(file));
                    currentFile = file;
                    loadText(currentTab, true);
                }
            }
        
            checkSavedOnPos(pos);
        }
    }
    
    public void addTabOverExistingTab(File file, int pos) {
        if (pos < openTabs && pos >= 0) {
            String text = "";
            String fileName = "";
            String fileType = "";
            Tooltip tooltip;
            if (file != null && file.isFile()) {
                text = fileRW.getText(file);
                fileName = file.getName();
                fileType = fileFn.getFileType(file);
                tooltip = new Tooltip(file.getPath());
            } else {
                text = "";
                fileName = "Untitled.txt";
                tooltip = new Tooltip("Untitled.txt");
                fileType = "Plain Text";
            }
            
            ImageView fileTypeIcon = fileFn.getViewAccordingToType(fileType);
            
            tabsMenu.getTabButtonsArr(pos).setText(fileName);
            tabsMenu.getTabButtonsArr(pos).setGraphic(fileTypeIcon);
            tabsMenu.getTabButtonsArr(pos).setTooltip(tooltip);
            
            tabsMenu.getFilesArr().set(pos, file);
            tabsMenu.getFilesTypeArr().set(pos, fileType);
            tabsMenu.getSavedTextArr().set(pos, new Label(text));
            tabsMenu.getCurrentTextArr().set(pos, new Label(text));
        }
    }
    
    public void renameCurrentTab(File newFile) {
        renameTab(currentTab, newFile);
    }
    
    public void renameTab(int pos, File newFile) {

        if (newFile != null) {
            
            String name = newFile.getName();
            String path = newFile.getPath();
            String fileType = fileFn.getFileType(newFile);
            
            Button tab = tabsMenu.getTabButtonsArr(pos);
            tab.setText(name);
            tab.setGraphic(fileFn.getViewAccordingToType(fileType));
            tab.setTooltip(new Tooltip(path));

            tabsMenu.getFilesArr().set(pos, newFile);
            tabsMenu.getFilesTypeArr().set(pos, fileType);

            if (pos == currentTab) {
                currentFile = newFile;
                mainCtrl.getStatusBarCtrl().setCurrentFileType(fileType);
            }
        }
        
    }
    
    /**
     * private methods
     */
    private void addTab(File file, String text) {
        
        int pos;

        if (openTabs == 1 && tabsMenu.getFilesArr(0) == null && file != null && mainCtrl.getEditorCtrl().isEmpty()) {
            addTabOverExistingTab(file, 0);
            switchTab(0, true, false);
        } else {
            String name;
            String path;
            String fileType;

            if (file == null) {
                name = "Untitled.txt";
                path = "Untitled.txt";
                fileType = "Plain Text";
            } else {
                name = file.getName();
                path = file.getPath();
                fileType = fileFn.getFileType(file);
            }

            Button tab = new Button();
            tab.setText(name);
            tab.setGraphic(fileFn.getViewAccordingToType(fileType));
            tab.setTooltip(new Tooltip(path));
            tab.setPadding(new Insets(5, 0, 5, 5));
            tab.setGraphicTextGap(7);

            ImageView closeIconView = new ImageView();
            closeIconView.setImage(closeIcon);
            closeIconView.setFitHeight(8);
            closeIconView.setFitWidth(9);

            Button closeButton = new Button();
            closeButton.setPadding(new Insets(6, 7, 4, 7));
            closeButton.setGraphic(closeIconView);
            closeButton.setTooltip(new Tooltip("Close (Ctrl+F4)"));

            tab.setOnAction(e->{
                switchTab(tabsMenu.getTabButtonsArr().indexOf(tab), false, false);
                mainCtrl.getEditorCtrl().requestFocus();
            });

            tab.setOnMouseClicked(e->{
                mainCtrl.getEditorCtrl().requestFocus();
            });

            closeButton.setOnAction(e->{
                deleteTab(tabsMenu.getTabButtonsArr().indexOf(tab), false);
                mainCtrl.getEditorCtrl().requestFocus();
            });

            closeButton.setOnMouseEntered(e->{
                if (tabsMenu.getTabButtonsArr().contains(tab)) {
                    checkSavedOnHoverEnter(tabsMenu.getTabButtonsArr().indexOf(tab));
                }
            });
            closeButton.setOnMouseExited(e->{
                if (tabsMenu.getTabButtonsArr().contains(tab)) {
                    checkSavedOnHoverExited(tabsMenu.getTabButtonsArr().indexOf(tab));
                }
            });

            // context menu
            ContextMenu contextMenu = new ContextMenu();

            MenuItem closeItem = new MenuItem("Close");
            closeItem.setAccelerator(KeyCombination.valueOf("Ctrl+F4"));
            closeItem.setOnAction(e->{
                int x = tabsMenu.getTabButtonsArr().indexOf(tab);
                deleteTab(tabsMenu.getTabButtonsArr().indexOf(tab), false);
                mainCtrl.getEditorCtrl().requestFocus();
            });

            MenuItem closeAllItem = new MenuItem("Close All");
            closeAllItem.setAccelerator(KeyCombination.valueOf("Ctrl+Shift+F4"));
            closeAllItem.setOnAction(e->{
                if (openTabs > 1)
                    tabsMenu.removeAllTab();
            });

            MenuItem saveItem = new MenuItem("Save");
            saveItem.setAccelerator(KeyCombination.valueOf("Ctrl+S"));
            saveItem.setOnAction(e->{
                if (contextMenu.isShowing()) {
                    int x = tabsMenu.getTabButtonsArr().indexOf(tab);
                    saveAt(x);
                }
            });

            MenuItem renameItem = new MenuItem("Rename File");
            renameItem.setAccelerator(KeyCombination.valueOf("Ctrl+R"));
            renameItem.setOnAction(e->{
                if (contextMenu.isShowing()) {
                    int x = tabsMenu.getTabButtonsArr().indexOf(tab);
                    launchPropertiesWindow(x);
                }
            });

            MenuItem switchForward = new MenuItem("Switch Forward");
            switchForward.setAccelerator(KeyCombination.valueOf("Ctrl+SHIFT+F"));
            switchForward.setOnAction(e->{
                int i = currentTab+1;
                if (i < openTabs)
                    switchTab(i, false, false);
            });

            MenuItem switchBackward = new MenuItem("Switch Backward");
            switchBackward.setAccelerator(KeyCombination.valueOf("Ctrl+SHIFT+B"));
            switchBackward.setOnAction(e->{
                if (currentTab > 0)
                    switchTab(currentTab-1, false, false);
            });

            MenuItem saveAs = new MenuItem("Save As...");
            saveAs.setAccelerator(KeyCombination.valueOf("Ctrl+SHIFT+S"));
            saveAs.setOnAction(e->{
                if (contextMenu.isShowing()) {
                    int x = tabsMenu.getTabButtonsArr().indexOf(tab);
                    saveAsAt(x);
                }
            });

            contextMenu.getItems().addAll(saveItem, saveAs, renameItem, switchForward, switchBackward, closeItem, closeAllItem);

            contextMenu.setOnShowing(e->{
                int x = tabsMenu.getTabButtonsArr().indexOf(tab);
                boolean isFileNull = getFile(x) == null;
                boolean savedOnCurrTab = checkSavedOnPos(x);
                boolean isTextEmptyOnPos = getFromCurrentTextsArr(x).isEmpty();
                boolean isNextTabNull = x >= (openTabs-1);
                boolean isPrevTabNull = x == 0;
                
                saveItem.setDisable(savedOnCurrTab);
                saveAs.setDisable(isFileNull && isTextEmptyOnPos);
                renameItem.setDisable(isFileNull);
                closeAllItem.setDisable(openTabs == 1);
                
                if (x != currentTab) {
                    switchForward.setDisable(true);
                    switchBackward.setDisable(true);
                } else {
                    switchForward.setDisable(isNextTabNull);
                    switchBackward.setDisable(isPrevTabNull);
                }
            });
            
            contextMenu.setOnHiding(e->{
                saveItem.setDisable(false);
                saveAs.setDisable(false);
                renameItem.setDisable(false);
                closeAllItem.setDisable(false);
                switchForward.setDisable(false);
                switchBackward.setDisable(false);
            });

            tab.setContextMenu(contextMenu);

            // setting main datas
            tabsMenu.addNewTabData(tab, closeButton, closeIconView, file, fileType, text);
            switchTab((openTabs - 1), true, false);
        }
    }
    
    private void removeTab(int pos) {
        
        if (tabsMenu.removeTab(pos)) {
            
            if (pos == currentTab) {
                if (openTabs > 0) {
                    if (pos == openTabs) {
                        switchTab(currentTab-1, false, true);
                    } else {
                        switchTab(currentTab, false, true);
                    }
                }
            } else {
                if (openTabs > 0) {
                    if (pos < currentTab) {
                        switchTab(currentTab-1, false, true);
                    } else if (pos > currentTab) {
                        switchTab(currentTab, false, false);
                    }        
                }
            }
        }
        
    }
    
}
