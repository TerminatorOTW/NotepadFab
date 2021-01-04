
// Developed by the TerminatorOTW

package notepadfab.java.controllers;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCombination;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import notepadfab.java.api.File_rw;
import notepadfab.java.api.String_op;
import notepadfab.java.functions.FileFn;
import notepadfab.java.functions.PrefSave;
import notepadfab.java.layouts.FindBar;
import notepadfab.java.layouts.MenuLayout;

public class MenuController {
    
    private final MainController mainCtrl;
    private final MenuLayout menuLayout;
    
    private final FileChooser fileChooserOpen = new FileChooser();
    private final DirectoryChooser dirChooserOpen = new DirectoryChooser();
    private final FileChooser fileChooserSave = new FileChooser();
    private final FileChooser fileChooserSaveAs = new FileChooser();
    
    // file extension filter
    FileChooser.ExtensionFilter fileExtensionsText = new FileChooser.ExtensionFilter(
        "Text Documents (*.txt)", "*.txt"
    );
    FileChooser.ExtensionFilter fileExtensionAll = new FileChooser.ExtensionFilter(
        "All Files", "*"
    );
    
    private final PrefSave prefSave = new PrefSave();
    private final File_rw fileRW = new File_rw();
    private final FileFn fileFn = new FileFn();
    
    private String eol = "";
    private boolean autoSave = false;
    
    private File[] recentFiles = new File[5];
    
    public MenuController(MenuLayout mL, MainController mC) {
        this.menuLayout = mL;
        this.mainCtrl = mC;
    }
    
    public MenuLayout getMenuLayout() {
        return menuLayout;
    }
    
    public void actionHandlers() {
        
        mainCtrl.getStage().setOnCloseRequest(e->{
            e.consume();
            menuLayout.getExitItem().fire();
        });
        
        fileChooserOpen.setTitle("Open File");
        fileChooserOpen.getExtensionFilters().addAll(
            fileExtensionsText, fileExtensionAll
        );
        
        dirChooserOpen.setTitle("Open Folder");
        
        fileChooserSave.setTitle("Save File");
        fileChooserSave.getExtensionFilters().add(
            fileExtensionsText
        );
        
        fileChooserSaveAs.setTitle("Save As File");
        
        /**
         * File menu items action
         */
        menuLayout.getNewItem().setOnAction(newAction());
        menuLayout.getOpenItem().setOnAction(openAction());
        menuLayout.getOpenFolderItem().setOnAction(openFolderAction());
        menuLayout.getSaveItem().setOnAction(saveAction());
        menuLayout.getSaveAsItem().setOnAction(saveAsAction());
        menuLayout.getSaveAllItem().setOnAction(saveAllAction());
        menuLayout.getRenameItem().setOnAction(e->{
            mainCtrl.getTabsController().launchPropertiesWindow();
        });
        menuLayout.getAutoSaveSubItem().setOnAction(autoSaveAction());
        menuLayout.getExitItem().setOnAction(exitAction());
        
        /**
         * Edit menu items action
         */
        menuLayout.getUndoItem().setOnAction(undoAction());
        menuLayout.getRedoItem().setOnAction(redoAction());
        menuLayout.getCutItem().setOnAction(cutAction());
        menuLayout.getCopyItem().setOnAction(copyAction());
        menuLayout.getPasteItem().setOnAction(pasteAction());
        menuLayout.getDeleteItem().setOnAction(deleteAction());
        menuLayout.getFindItem().setOnAction(findAction());
        menuLayout.getReplaceItem().setOnAction(replaceAction());
        menuLayout.getGoToItem().setOnAction(goToAction());
        menuLayout.getSelectAllItem().setOnAction(selectAll());
        menuLayout.getTimeDateItem().setOnAction(timeDateAction());
        
        /**
         * Format menu items action
         */
        menuLayout.getWordWrapItem().setOnAction(wordWrapAction());
        menuLayout.getWinEolSubItem().setOnAction(eolWindowAction());
        menuLayout.getUnixEolSubItem().setOnAction(eolUnixAction());
        menuLayout.getMacEolSubItem().setOnAction(eolMacAction());
        
        menuLayout.getStatusBarItem().setOnAction(statusBarAction());
        menuLayout.getAotItem().setOnAction(aotAction());
        
        /*
        lightTheme.setOnAction(e->{
            e.consume();
            darkTheme.setSelected(false);
            theme = "light";
            prefSave.setSavedValue("menuData", "View", "theme", theme);
        });
        darkTheme.setOnAction(e->{
            e.consume();
            lightTheme.setSelected(false);
            theme = "dark";
            prefSave.setSavedValue("menuData", "View", "theme", theme);
        });*/
        
        /**
         * Some listeners on menus
         */
        menuLayout.getFileMenu().setOnShowing(e->{
            
            int tabsCount = mainCtrl.getTabsController().getTabsCount();
            boolean tabIsEmpty = tabsCount == 0;
            boolean currFileIsNull = false;
            boolean currFileIsSaved = true;
            boolean allFilesAreSaved = true;
            boolean currTextIsEmpty = mainCtrl.getEditorCtrl().isEmpty();
            
            if (!tabIsEmpty) {
                currFileIsNull = mainCtrl.getTabsController().getCurrentFile() == null;
                currFileIsSaved = mainCtrl.getTabsController().checkSavedOnPos(
                    mainCtrl.getTabsController().getCurrentTabPos()
                );
                allFilesAreSaved = mainCtrl.getTabsController().isAllSaved();
            }
            
            if (tabsCount == 5) {
                menuLayout.getNewItem().setDisable(true);
                menuLayout.getOpenItem().setDisable(true);
                menuLayout.getOpenFolderItem().setDisable(true);
                MenuItem a = menuLayout.getOpenRecentItem().getItems().get(0);
                a.setDisable(true);
            }
            
            menuLayout.getSaveItem().setDisable(tabIsEmpty || currFileIsSaved);
            menuLayout.getSaveAllItem().setDisable(tabIsEmpty || allFilesAreSaved);;
            menuLayout.getSaveAsItem().setDisable(tabIsEmpty || (currFileIsNull && currTextIsEmpty));
            menuLayout.getRenameItem().setDisable(tabIsEmpty || currFileIsNull);
            
            refreshRecentItems();
        });
        
        menuLayout.getFileMenu().setOnHiding(e->{
            menuLayout.getNewItem().setDisable(false);
            menuLayout.getOpenItem().setDisable(false);
            menuLayout.getOpenFolderItem().setDisable(false);
            menuLayout.getOpenRecentItem().setDisable(false);
            menuLayout.getSaveItem().setDisable(false);
            menuLayout.getSaveAsItem().setDisable(false);
            menuLayout.getSaveAllItem().setDisable(false);
            menuLayout.getRenameItem().setDisable(false);
        });
        
        menuLayout.getEditMenu().setOnShowing(e->{
            
            boolean isEmpty = mainCtrl.getEditorCtrl().isEmpty();
            boolean isSelecEmpty = mainCtrl.getEditorCtrl().isSelectionEmpty();
            boolean isDisabled = !mainCtrl.getEditorCtrl().isEditable();
            
            Clipboard clipboard = Clipboard.getSystemClipboard();
            String clipText = "";
            
            if (clipboard != null && clipboard.hasString())
                clipText = clipboard.getString();
            
            menuLayout.getCopyItem().setDisable(isDisabled || isEmpty || isSelecEmpty);
            menuLayout.getCutItem().setDisable(isDisabled || isEmpty || isSelecEmpty);
            menuLayout.getPasteItem().setDisable(isDisabled || clipText.isEmpty());
            menuLayout.getDeleteItem().setDisable(isDisabled || isEmpty);
            menuLayout.getFindItem().setDisable(isDisabled || isEmpty);
            menuLayout.getFindNextItem().setDisable(isDisabled || isEmpty || isSelecEmpty);
            menuLayout.getReplaceItem().setDisable(isDisabled || isEmpty);
            menuLayout.getGoToItem().setDisable(isDisabled || isEmpty);
            menuLayout.getSelectAllItem().setDisable(isDisabled || isEmpty);
            menuLayout.getTimeDateItem().setDisable(isDisabled);
        });
        
        menuLayout.getEditMenu().setOnHiding(e->{
            menuLayout.getCopyItem().setDisable(false);
            menuLayout.getCutItem().setDisable(false);
            menuLayout.getPasteItem().setDisable(false);
            menuLayout.getDeleteItem().setDisable(false);
            menuLayout.getFindItem().setDisable(false);
            menuLayout.getFindNextItem().setDisable(false);
            menuLayout.getReplaceItem().setDisable(false);
            menuLayout.getGoToItem().setDisable(false);
            menuLayout.getSelectAllItem().setDisable(false);
            menuLayout.getTimeDateItem().setDisable(false);
        });
    }
    
    /* Menu-item actions */
    public File[] getRecentFiles() {
        return recentFiles;
    }
    
    public String getEol() {
        return eol;
    }
    
    public void checkWordWrapItem(boolean value) {
        String x = "";
        if (value)
            x = "Turn Off!";
        else
            x = "Turn on!";
        menuLayout.getWordWrapOnOffSubItem().setText(x);
    }
    
    public void checkEol(boolean eolWin, boolean eolUnix, boolean eolMac) {
        menuLayout.getWinEolSubItem().setSelected(eolWin);
        menuLayout.getUnixEolSubItem().setSelected(eolUnix);
        menuLayout.getMacEolSubItem().setSelected(eolMac);
    }
    
    public void checkStatusBarItem(boolean value) {
        String x = "";
        if (value)
            x = "Turn Off!";
        else
            x = "Turn on!";
        menuLayout.getStatusBarOnOffSubItem().setText(x);
    }
    
    public void checkAotItem(boolean value) {
        String x = "";
        if (value)
            x = "Turn Off!";
        else
            x = "Turn on!";
        menuLayout.getAotOnOffSubItem().setText(x);
    }
    
    public void checkAutoSaveItem(boolean value) {
        String x = "";
        if (value)
            x = "Turn Off!";
        else
            x = "Turn on!";
        menuLayout.getAutoSaveSubItem().setText(x);
    }
    
    /**
     * checking booleans
     */
    public boolean isAutoSaveEnabled() {
        return autoSave;
    }
    
    /**
     *  fire methods
     */
    public void fireSaveItem() {
        menuLayout.getSaveItem().fire();
    }
    public void fireSaveAsItem() {
        menuLayout.getSaveAsItem().fire();
    }
    public void fireSaveAllItem() {
        menuLayout.getSaveAllItem().fire();
    }
    public void fireRenameItem() {
        menuLayout.getRenameItem().fire();
    }
    public void fireTimeDateItem() {
        menuLayout.getTimeDateItem().fire();
    }
    
    public void setDefaultValues() {
        
        checkStatusBarItem(mainCtrl.getStatusBarCtrl().isShowing());
        
        boolean[] v = new boolean[]{
            prefSave.getSavedValue("menuData", "Format", "wordWrap").equals("true"),
            prefSave.getSavedValue("menuData", "View", "aot").equals("true"),
            prefSave.getSavedValue("menuData", "File", "autoSave").equals("true")
        };

        checkWordWrapItem(v[0]);
        mainCtrl.getEditorCtrl().setTextWrapping(v[0]);
            
        checkAotItem(v[1]);
        mainCtrl.getStage().setAlwaysOnTop(v[1]);
        
        autoSave = v[2];
        checkAutoSaveItem(autoSave);
        
        String eolType = prefSave.getSavedValue("menuData", "Format", "eol");
        
        if (eolType.isEmpty()) {
            switch (System.lineSeparator()) {
                case "\r\n":
                    eolType = "windows";
                    break;
                case "\r":
                    eolType = "mac";
                    break;
                default:
                    eolType = "unix";
                    break;
            }
        }
        
        switch(eolType) {
            case "windows":
                menuLayout.getWinEolSubItem().fire();
                eol = "windows";
                break;
            case "mac":
                menuLayout.getMacEolSubItem().fire();
                eol = "mac";
                break;
            default:
                menuLayout.getUnixEolSubItem().fire();
                eol = "unix";
                break;
        }

        /**
         * adding recent menu items
         */
        loadRecentItems();
        
    }
    
    public boolean openFile(File file) {
        if (mainCtrl.getTabsController().getTabsCount() < 5) {
            if(file != null) {
                String str = fileRW.getText(file);
                if (mainCtrl.getTabsController().addNewTab(file, str)) {
                    prefSave.setSavedValue("docsData", "File", "lastDir", file.getParent());
                    addRecentFile(file);
                    return true;
                }
            } else {
                File path = new File(prefSave.getSavedValue("docsData", "File", "lastDir"));

                if (path.isDirectory())
                    fileChooserOpen.setInitialDirectory(path);
                else
                    fileChooserOpen.setInitialDirectory(new File(System.getProperty("user.home")));

                file = fileChooserOpen.showOpenDialog(mainCtrl.getStage());

                if(file != null) {
                    String str = fileRW.getText(file);
                    if (mainCtrl.getTabsController().addNewTab(file, str)) {
                        prefSave.setSavedValue("docsData", "File", "lastDir", file.getParent());
                        addRecentFile(file);
                        return true;
                    }
                } else {}
            }
        }
        return false;
    }
    
    public void openFolder(String path) {
        if (mainCtrl.getTabsController().getTabsCount() < 5) {
            File folderPath = new File(path);

            if (!folderPath.isDirectory()) {
                folderPath = new File(prefSave.getSavedValue("docsData", "File", "lastDir"));

                if (folderPath.isDirectory())
                    dirChooserOpen.setInitialDirectory(folderPath);
                else
                    dirChooserOpen.setInitialDirectory(new File(System.getProperty("user.home")));

                folderPath = dirChooserOpen.showDialog(mainCtrl.getStage());
            }

            File[] fileList = null;

            try {
                fileList = folderPath.listFiles((File dir, String name) -> 
                    name.toLowerCase().endsWith(".txt")
                );
            } catch (Exception g) {}

            if (fileList != null && fileList.length > 0) {
                prefSave.setSavedValue("docsData", "File", "lastDir", folderPath.getParent());

                for (int i = 0; i < fileList.length; i++) {
                    if (!openFile(fileList[i]))
                        break;
                }
            }
        }
    }
    
    public void saveFile(File file, String text, int pos) {
        saveFile(file, "Untitled", text, pos);
    }
    
    public void saveFile(File file, String fileName, String text, int pos) {

        if (file != null) {
            fileRW.setText(text, file, eol);
            mainCtrl.getTabsController().updateTabWhenSaved(pos, file, false);
        } else {
            File newFile = null;

            File path = new File(prefSave.getSavedValue("docsData", "File", "lastDir"));

            if (path.isDirectory())
                fileChooserSave.setInitialDirectory(path);
            else
                fileChooserSave.setInitialDirectory(new File(System.getProperty("user.home")));

            fileChooserSave.setInitialFileName(fileName);
            newFile = fileChooserSave.showSaveDialog(mainCtrl.getStage());

            if (newFile != null) {
                fileRW.setText(text, newFile, eol);
                mainCtrl.getTabsController().updateTabWhenSaved(pos, newFile, false);
                prefSave.setSavedValue("docsData", "File", "lastDir", newFile.getParent());
                addRecentFile(newFile);
            } else {}
        }
    }
    
    public void saveAs(String text, String preFileName) {
        
        File file = null;

        File path = new File(prefSave.getSavedValue("docsData", "File", "lastDir"));

        if (path.isDirectory())
            fileChooserSaveAs.setInitialDirectory(path);
        else
            fileChooserSaveAs.setInitialDirectory(new File(System.getProperty("user.home")));

        fileChooserSaveAs.setInitialFileName(preFileName);
        
        String fileType = fileFn.getFileExtension(preFileName);
        String fileTypeHint = fileFn.getFileType(preFileName);
        
        if (fileType.equals("null")) {
            fileType = ".txt";
        }
        
        FileChooser.ExtensionFilter fE = new FileChooser.ExtensionFilter(
            fileTypeHint + "(*" + fileType + ")", "*" + fileType
        );
        
        fileChooserSaveAs.getExtensionFilters().clear();
        
        fileChooserSaveAs.getExtensionFilters().addAll(
            fE, fileExtensionAll
        );
        
        file = fileChooserSaveAs.showSaveDialog(mainCtrl.getStage());

        if (file != null) {
            fileRW.setText(text, file, eol);
            mainCtrl.getTabsController().addNewTab(file, text);
            prefSave.setSavedValue("docsData", "File", "lastDir", file.getParent());
            addRecentFile(file);
        } else {}
        
    }
    
    public void refreshRecentItems() {
        int k = 1;
        menuLayout.getOpenRecentItem().getItems().clear();
        for (int i = 0; i < 5; i++) {
            final File file = recentFiles[i];
            if (file != null) {
                MenuItem item = new MenuItem(file.getName() + " (" + file.getPath() + ")");
                item.setAccelerator(KeyCombination.valueOf("Ctrl+Shift+" + k));
                item.setOnAction(f->{
                    openFile(file);
                });     
                menuLayout.getOpenRecentItem().getItems().add(item);
                k++;
            }
        }
    }
    
    public void loadRecentItems() {
        int k = 1;
        
        for (int i=0; i<5; i++) {
            final File file = new File(prefSave.getSavedValue("docsData", "Recent", "file" + (i+1)));

            if (file.isFile()) {
                MenuItem item = new MenuItem(file.getName() + " (" + file.getPath() + ")");
                item.setAccelerator(KeyCombination.valueOf("Ctrl+Shift+" + k));
                item.setOnAction(f->{
                    openFile(file);
                });     
                menuLayout.getOpenRecentItem().getItems().add(item);
                recentFiles[k-1] = file;
                k++;
            }
        }
    }
    
    public void addRecentFile(File file) {
        
        for (int i = 0; i < 5; i++) {
            if (recentFiles[i] != null && recentFiles[i].getPath().equals(file.getPath())) {
                break;
            }
            if (recentFiles[i] == null) {
                recentFiles[i] = file;
                break;
            } else if (i == 4 && recentFiles[i] != null) {
                File[] fileArr = new File[]{file, recentFiles[0], recentFiles[1], recentFiles[2], recentFiles[3]};
                recentFiles = fileArr;
                break;
            }
        }
    }
    
    /**
     * File menu items action
     */
    private EventHandler<ActionEvent> newAction() {
        EventHandler<ActionEvent> event = e -> {
            mainCtrl.getTabsController().addNewTab(null, "");
        };
        return event;
    }
    
    private EventHandler<ActionEvent> openAction() {
        EventHandler<ActionEvent> event = e-> {
            openFile(null);
        };
        return event;
    }
    
    private EventHandler<ActionEvent> openFolderAction() {
        EventHandler<ActionEvent> event = e-> {
            openFolder("");
        };
        return event;
    }
    
    private EventHandler<ActionEvent> saveAction() {
        
        EventHandler<ActionEvent> event = e-> {
            File openedFile = mainCtrl.getTabsController().getCurrentFile();
            String text = mainCtrl.getEditorCtrl().getText();
            saveFile(openedFile, text, mainCtrl.getTabsController().getCurrentTabPos());
        };
        return event;
    }
    
    private EventHandler<ActionEvent> saveAsAction() {

        EventHandler<ActionEvent> event = e-> {
            File file = mainCtrl.getTabsController().getCurrentFile();
            String name = "Untitled.txt";
            if (file != null)
                name = file.getName();
            saveAs(mainCtrl.getTabsController().getCurrentText(), name);
        };
        return event;
    }
    
    private EventHandler<ActionEvent> saveAllAction() {
        
        EventHandler<ActionEvent> event = e-> {
            mainCtrl.getTabsController().saveAll();
        };
        return event;
    }
    
    private EventHandler<ActionEvent> autoSaveAction() {
        EventHandler<ActionEvent> event = e-> {
            
            if (autoSave) {
                autoSave = false;
            } else {
                autoSave = true;
            }
            
            checkAutoSaveItem(autoSave);
            prefSave.setSavedValue("menuData", "File", "autoSave", String.valueOf(autoSave));
            
        };
        return event;
    }
    
    private EventHandler<ActionEvent> exitAction() {
        EventHandler<ActionEvent> event = e-> {
            mainCtrl.getExitCtrl().exit();
        };
        return event;
    }
    
    /**
     * Edit menu items action
     */
    private EventHandler<ActionEvent> undoAction() {
        EventHandler<ActionEvent> event = e-> {
            mainCtrl.getEditorCtrl().undo();
        };
        return event;
    }
    private EventHandler<ActionEvent> redoAction() {
        EventHandler<ActionEvent> event = e-> {
            mainCtrl.getEditorCtrl().redo();
        };
        return event;
    }
    
    private EventHandler<ActionEvent> cutAction() {
        EventHandler<ActionEvent> event = e-> {
            if(!mainCtrl.getEditorCtrl().isSelectionEmpty())
                mainCtrl.getEditorCtrl().cutText();
        };
        return event;
    }
    
    private EventHandler<ActionEvent> copyAction() {
        EventHandler<ActionEvent> event = e-> {
            if(!mainCtrl.getEditorCtrl().isSelectionEmpty())
                mainCtrl.getEditorCtrl().copyText();
        };
        return event;
    }
    
    private EventHandler<ActionEvent> pasteAction() {
        EventHandler<ActionEvent> event = e-> {
            mainCtrl.getEditorCtrl().pasteText();
        };
        return event;
    }
    
    private EventHandler<ActionEvent> deleteAction() {
        EventHandler<ActionEvent> event = e-> {
            mainCtrl.getEditorCtrl().deletePrevChar();
        };
        return event;
    }
    
    private EventHandler<ActionEvent> findAction() {
        EventHandler<ActionEvent> event = e-> {
        };
        return event;
    }
    
    private EventHandler<ActionEvent> replaceAction() {
        EventHandler<ActionEvent> event = e-> {
        };
        return event;
    }
    
    private EventHandler<ActionEvent> goToAction() {
        EventHandler<ActionEvent> event = e-> {
            mainCtrl.getGoToController().showlayout();
        };
        return event;
    }
    
    private EventHandler<ActionEvent> selectAll() {
        EventHandler<ActionEvent> event = e-> {
            if (!mainCtrl.getEditorCtrl().isEmpty())
                mainCtrl.getEditorCtrl().selectAll();
        };
        return event;
    }
    
    private EventHandler<ActionEvent> timeDateAction() {
        EventHandler<ActionEvent> event = e-> {
            if (mainCtrl.getEditorCtrl().isEditable()) {
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("hh:mm a dd/MM/yyyy");

                final String_op s = new String_op();

                int x = mainCtrl.getEditorCtrl().getCaretPos();
                String string = dateFormat.format(date);

                mainCtrl.getEditorCtrl().setText(
                    s.replaceStringAtPos(
                        x, x,
                        mainCtrl.getEditorCtrl().getText(),
                        string
                    )
                );

                mainCtrl.getEditorCtrl().positionCaret(x + string.length());
            }
        };
        return event;
    }
    
    /**
     * Format menu items action
     */
    private EventHandler<ActionEvent> wordWrapAction() {
        EventHandler<ActionEvent> event = e-> {
            String wordWrap = "";
            if(mainCtrl.getEditorCtrl().isTextWrapped()) {
                mainCtrl.getEditorCtrl().setTextWrapping(false);
                checkWordWrapItem(false);
                wordWrap = "false";
            } else {
                mainCtrl.getEditorCtrl().setTextWrapping(true);
                checkWordWrapItem(true);
                wordWrap = "true";
            }
            prefSave.setSavedValue("menuData", "Format", "wordWrap", wordWrap);
        };
        return event;
    }
    
    private EventHandler<ActionEvent> statusBarAction() {
        EventHandler<ActionEvent> event = e-> {
            if(mainCtrl.getStatusBarCtrl().isShowing()) {
                mainCtrl.getStatusBarCtrl().setShowing(false);
                mainCtrl.getMainLayout().setBottom(null);
                checkStatusBarItem(false);
            } else {
                mainCtrl.getStatusBarCtrl().setShowing(true);
                mainCtrl.getMainLayout().setBottom(mainCtrl.getStatusBarCtrl().getStatusBarLayout());
                checkStatusBarItem(true);
            }
            prefSave.setSavedValue("menuData", "View", "statusBar", mainCtrl.getStatusBarCtrl().isShowing() + "");
        };
        return event;
    }
    
    private EventHandler<ActionEvent> aotAction() {
        EventHandler<ActionEvent> event = e-> {
            Stage windows = mainCtrl.getStage();
            String alwaysOnTop = "";
            if(windows.isAlwaysOnTop()) {
                windows.setAlwaysOnTop(false);
                checkAotItem(false);
                alwaysOnTop = "false";
            } else {
                windows.setAlwaysOnTop(true);
                checkAotItem(true);
                alwaysOnTop = "true";
            }
            prefSave.setSavedValue("menuData", "View", "aot", alwaysOnTop);
        };
        return event;
    }
    
    private EventHandler<ActionEvent> eolWindowAction() {
        EventHandler<ActionEvent> event = e-> {
            checkEol(true, false, false);
            mainCtrl.getStatusBarCtrl().setCurrentEol("Windows (CRLF)");
            eol = "windows";
            prefSave.setSavedValue("menuData", "Format", "eol", eol);
        };
        return event;
    }
    private EventHandler<ActionEvent> eolUnixAction() {
        EventHandler<ActionEvent> event = e-> {
            checkEol(false, true, false);
            mainCtrl.getStatusBarCtrl().setCurrentEol("Unix (LF)");
            eol = "unix";
            prefSave.setSavedValue("menuData", "Format", "eol", eol);
        };
        return event;
    }
    private EventHandler<ActionEvent> eolMacAction() {
        EventHandler<ActionEvent> event = e-> {
            checkEol(false, false, true);
            mainCtrl.getStatusBarCtrl().setCurrentEol("Macintosh (CR)");
            eol = "mac";
            prefSave.setSavedValue("menuData", "Format", "eol", eol);
        };
        return event;
    }
    
}
