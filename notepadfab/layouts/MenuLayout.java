
// Developed by the TerminatorOTW

package notepadfab.java.layouts;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import notepadfab.java.functions.PrefSave;

public final class MenuLayout extends MenuBar {
    
    private Menu fileMenu, editMenu, formatMenu, viewMenu;
    
    private Menu eolItem;
    
    /**
     * these menus and sub-menus aren't used in this version :(
     */
    private Menu toolsMenu, helpMenu;
    private Menu themeItem, clipboardItem;
    /* the end */
    
    private MenuItem newItem, openItem, openFolderItem, saveItem, saveAsItem, saveAllItem,
            renameItem, exitItem,
            undoItem, redoItem, cutItem, copyItem, pasteItem, deleteItem, findItem,
            findNextItem, replaceItem, gotoItem, selectAllItem, timeDateItem,
            fontItem, helpItem, aboutItem, checkForUpdatesItem;
    private Menu openRecentItem, autoSaveItem, wordWrapItem, statusBarItem, aotItem, appearanceItem;
    private MenuItem autoSaveOnOff, wordWrapOnOff, statusOnOff, aotItemOnOff;
    
    private MenuItem showContent, clearContent;
    
    private CheckMenuItem eolWindows, eolUnix, eolMac, lightTheme, darkTheme;
    
    public MenuLayout() {
        this.getStyleClass().add("menuLayout");
        this.setMenuLayout();
    }
   
    private void setMenuLayout() {
        
        /* file menu */
        fileMenu = new Menu("File");
        
            /* menuItems */
            newItem = new MenuItem("New");
            newItem.setAccelerator(KeyCombination.valueOf("Ctrl+N"));
            
            openItem = new MenuItem("Open...");
            openItem.setAccelerator(KeyCombination.valueOf("Ctrl+O"));
            
            openFolderItem = new MenuItem("Open Folder...");
            openFolderItem.setAccelerator(KeyCombination.valueOf("Ctrl+Shift+O"));
            
            openRecentItem = new Menu("Open Recent");
            
            saveItem = new MenuItem("Save");
            saveItem.setAccelerator(KeyCombination.valueOf("Ctrl+S"));
            
            saveAsItem = new MenuItem("Save As...");
            saveAsItem.setAccelerator(KeyCombination.valueOf("Ctrl+Shift+S"));
            
            saveAllItem = new MenuItem("Save All...");
            saveAllItem.setAccelerator(KeyCombination.valueOf("Ctrl+Alt+S"));
            
            renameItem = new MenuItem("Rename As...");
            renameItem.setAccelerator(KeyCombination.valueOf("Ctrl+R"));
            
            autoSaveItem = new Menu("Auto Save...");
            
                /* sub-menuItems */
                autoSaveOnOff = new MenuItem("Turn on!");
                autoSaveOnOff.setAccelerator(KeyCombination.valueOf("Ctrl+Alt+A"));
                
                // adding items to the sub-menu
                autoSaveItem.getItems().add(autoSaveOnOff);
            
            exitItem = new MenuItem("Exit");
            exitItem.setAccelerator(KeyCombination.valueOf("Alt+F4"));
            
            // adding items to the menu
            fileMenu.getItems().addAll(
                newItem, openItem, openFolderItem, openRecentItem,
                saveItem, saveAsItem, saveAllItem, renameItem, autoSaveItem, exitItem
            );
        
        editMenu = new Menu("Edit");
        
            /* menuItems */
            undoItem = new MenuItem("Undo");
            undoItem.setAccelerator(KeyCombination.valueOf("Ctrl+Z"));
            
            redoItem = new MenuItem("Redo");
            redoItem.setAccelerator(KeyCombination.valueOf("Ctrl+Y"));
            
            cutItem = new MenuItem("Cut");
            cutItem.setAccelerator(KeyCombination.valueOf("Ctrl+X"));
            
            copyItem = new MenuItem("Copy");
            copyItem.setAccelerator(KeyCombination.valueOf("Ctrl+C"));
            
            pasteItem = new MenuItem("Paste");
            pasteItem.setAccelerator(KeyCombination.valueOf("Ctrl+V"));
            
            deleteItem = new MenuItem("Delete");
            deleteItem.setAccelerator(KeyCombination.valueOf("Del"));
            
            findItem = new MenuItem("Find");
            findItem.setAccelerator(KeyCombination.valueOf("Ctrl+F"));
            
            findNextItem = new MenuItem("Find Next");
            findNextItem.setAccelerator(KeyCombination.valueOf("F3"));
            
            replaceItem = new MenuItem("Replace");
            replaceItem.setAccelerator(KeyCombination.valueOf("Ctrl+H"));
            
            gotoItem = new MenuItem("Go To");
            gotoItem.setAccelerator(KeyCombination.valueOf("Ctrl+G"));
            
            selectAllItem = new MenuItem("Select All");
            selectAllItem.setAccelerator(KeyCombination.valueOf("Ctrl+A"));
            
            timeDateItem = new MenuItem("Time/Date");
            timeDateItem.setAccelerator(KeyCombination.valueOf("F5"));
        
            // adding items to the menu
            editMenu.getItems().addAll(
                undoItem, redoItem, cutItem, copyItem, pasteItem,
                deleteItem, findItem, findNextItem, replaceItem,
                gotoItem, selectAllItem, timeDateItem
            );
            
        formatMenu = new Menu("Format");
        
            /* menuItems */
            wordWrapItem = new Menu("Word Wrap");
            
                /* sub-menuItems */
                wordWrapOnOff = new MenuItem("Turn on!");
                wordWrapOnOff.setAccelerator(KeyCombination.valueOf("Ctrl+Alt+W"));
                
                // adding items to the sub-menu
                wordWrapItem.getItems().add(wordWrapOnOff);
            
            eolItem = new Menu("EOL Conversion");
            
                /* sub-menuItems */
                eolWindows = new CheckMenuItem("Windows (CRLF)");
                eolUnix = new CheckMenuItem("Unix (LF)");
                eolMac = new CheckMenuItem("Macintosh (CR)");
                
                // adding items to the sub-menu
                eolItem.getItems().addAll(eolWindows, eolUnix, eolMac);
            
            // adding items to the menu
            formatMenu.getItems().addAll(
                wordWrapItem, eolItem
            );
        
        viewMenu = new Menu("View");
        
            /* menuItems */
            fontItem = new MenuItem("Font");
            fontItem.setDisable(true);
            
            statusBarItem = new Menu("Status Bar");
            
                /* sub-menuItems */
                statusOnOff = new MenuItem("Turn on!");
                statusOnOff.setAccelerator(KeyCombination.valueOf("Ctrl+Alt+S"));
                
                // adding items to the sub-menu
                statusBarItem.getItems().add(statusOnOff);
            
            aotItem = new Menu("Always On Top");
            
                /* sub-menuItems */
                aotItemOnOff = new MenuItem("Turn on!");
                aotItemOnOff.setAccelerator(KeyCombination.valueOf("Ctrl+Alt+T"));
                
                // adding items to the sub-menu
                aotItem.getItems().add(aotItemOnOff);
            
            appearanceItem = new Menu("Appearance");
                
            /* not used in this version*/
            themeItem = new Menu("Theme");
            themeItem.setDisable(true);

                // sub-menuItems 
                lightTheme = new CheckMenuItem("Light Theme");
                darkTheme = new CheckMenuItem("Dark Theme");

                // adding items to the sub-menu
                themeItem.getItems().addAll(lightTheme, darkTheme);
            /**/
            
            // adding items to the menu
            viewMenu.getItems().addAll(
                fontItem, statusBarItem, aotItem, appearanceItem, themeItem
            );
        
            
        /* not used in this version */
        toolsMenu = new Menu("Tools");
        toolsMenu.setId("toolsMenu");

            // menuItems
            clipboardItem = new Menu("Clipboard");
            clipboardItem.setDisable(true);

                // sub-menuItems
                showContent = new MenuItem("Show Content");
                clearContent = new MenuItem("Clear Content");

                // adding items to the sub-menu
                clipboardItem.getItems().addAll(showContent, clearContent);

            // adding items to the menu
            toolsMenu.getItems().addAll(
                clipboardItem
            );
        /* */
            
        /* this is not used for now */
        helpMenu = new Menu("Help");
        helpMenu.setId("helpMenu");

            // menuItems
            helpItem = new MenuItem("Help");
            helpItem.setDisable(true);

            aboutItem = new MenuItem("About");
            aboutItem.setDisable(true);

            checkForUpdatesItem = new MenuItem("Check For Updates!");
            checkForUpdatesItem.setDisable(true);

            // adding items to the menu
            helpMenu.getItems().addAll(
                helpItem, aboutItem, checkForUpdatesItem
            );
        /**/
        
        // adding menus to menuBar
        this.getMenus().addAll(
            fileMenu, editMenu, formatMenu, viewMenu, toolsMenu, helpMenu
        );
    }
    
    public Menu getFileMenu() {
        return fileMenu;
    }
    
    public Menu getEditMenu() {
        return editMenu;
    }
    
    public MenuItem getNewItem() {
        return newItem;
    }
    
    public MenuItem getOpenItem() {
        return openItem;
    }
    
    public MenuItem getOpenFolderItem() {
        return openFolderItem;
    }
    
    public Menu getOpenRecentItem() {
        return openRecentItem;
    }
    
    public MenuItem getSaveItem() {
        return saveItem;
    }
    
    public MenuItem getSaveAsItem() {
        return saveAsItem;
    }
    
    public MenuItem getSaveAllItem() {
        return saveAllItem;
    }
    
    public MenuItem getRenameItem() {
        return renameItem;
    }
    
    public MenuItem getAutoSaveSubItem() {
        return autoSaveOnOff;
    }
    
    public MenuItem getExitItem() {
        return exitItem;
    }
    
    public MenuItem getUndoItem() {
        return undoItem;
    }
    
    public MenuItem getRedoItem() {
        return redoItem;
    }
    
    public MenuItem getCutItem() {
        return cutItem;
    }
    
    public MenuItem getCopyItem() {
        return copyItem;
    }
    
    public MenuItem getPasteItem() {
        return pasteItem;
    }
    
    public MenuItem getDeleteItem() {
        return deleteItem;
    }
    
    public MenuItem getFindItem() {
        return findItem;
    }
    
    public MenuItem getFindNextItem() {
        return findNextItem;
    }
    
    public MenuItem getReplaceItem() {
        return replaceItem;
    }
    
    public MenuItem getGoToItem() {
        return gotoItem;
    }
    
    public MenuItem getSelectAllItem() {
        return selectAllItem;
    }
    
    public MenuItem getTimeDateItem() {
        return timeDateItem;
    }
    
    public MenuItem getWordWrapItem() {
        return wordWrapItem;
    }
    
    public MenuItem getWordWrapOnOffSubItem() {
        return wordWrapOnOff;
    }
    
    public MenuItem getFontItem() {
        return fontItem;
    }
    
    public CheckMenuItem getWinEolSubItem() {
        return eolWindows;
    }
    
    public CheckMenuItem getUnixEolSubItem() {
        return eolUnix;
    }
    
    public CheckMenuItem getMacEolSubItem() {
        return eolMac;
    }
    
    public MenuItem getStatusBarItem() {
        return statusBarItem;
    }
    
    public MenuItem getStatusBarOnOffSubItem() {
        return statusOnOff;
    }
    
    public MenuItem getAotItem() {
        return aotItem;
    }
    
    public MenuItem getAotOnOffSubItem() {
        return aotItemOnOff;
    }
    
    /* not used in this version
        public void setThemeItemAction(EventHandler<ActionEvent> event) {
            themeItem.setOnAction(event);
        }
        public void fireThemeItem() {
            themeItem.fire();
        }
    */
    
    public MenuItem getHelpItem() {
        return helpItem;
    }
    
    public MenuItem getAboutItem() {
        return aboutItem;
    }
    
}
