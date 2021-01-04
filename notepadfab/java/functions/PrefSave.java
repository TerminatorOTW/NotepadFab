
// Developed by the TerminatorOTW

package notepadfab.java.functions;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import notepadfab.ResourceLoader;
import notepadfab.java.api.File_rw;
import notepadfab.java.api.INI_rw;

public class PrefSave {
    
    private final ResourceLoader resLoader = new ResourceLoader();
    
    private final File mainFolder = new File(System.getProperty("user.home") + File.separator + ".notepadfab");
    private final File menuDataFile = new File(mainFolder.getPath() + File.separator + "menuData.ini");
    private final File sceneDataFile = new File(mainFolder.getPath() + File.separator + "sceneData.ini");
    private final File docsDataFile = new File(mainFolder.getPath() + File.separator + "docsData.ini");
    
    /**
     * application resources
     */
    private final File mainStyleFolder = new File(mainFolder.getPath() + File.separator + "themes");
    
    private final File defaultStyleFolder = new File(mainStyleFolder.getPath() + File.separator + "default");
    private final File mainSheet = new File(defaultStyleFolder.getPath() + File.separator + "main_style.css");
    private final File alertBoxSheet = new File(defaultStyleFolder.getPath() + File.separator + "alert_box_style.css");
    private final File propertiesBoxSheet = new File(defaultStyleFolder.getPath() + File.separator + "properties_box_style.css");
    
    /**
     * Main methods for getting and setting,
     * preference values
     * @param fileName
     * @param header
     * @param key
     * @return value
     */
    public String getSavedValue(String fileName, String header, String key) {
        
        String value = "";
        
        if (checkSaveData()) {
            switch(fileName) {
                case "menuData":
                    value = INI_rw.getKeyValue(menuDataFile, header, key);
                    break;
                case "sceneData":
                    value = INI_rw.getKeyValue(sceneDataFile, header, key);
                    break;
                case "docsData":
                    value = INI_rw.getKeyValue(docsDataFile, header, key);
                    break;
                default:
                    break;
            }
        }
        
        return value;
    }
    
    public void setSavedValue(String fileName, String header, String key, String value) {
        
        if (checkSaveData()) {
            switch(fileName) {
                case "menuData":
                    INI_rw.setKeyValue(menuDataFile, header, key, value);
                    break;
                case "sceneData":
                    INI_rw.setKeyValue(sceneDataFile, header, key, value);
                    break;
                case "docsData":
                    INI_rw.setKeyValue(docsDataFile, header, key, value);
                    break;
                default:
                    break;
            }
        }
    }
    
    public URL getStylesheetPath(String sheetName) {
        File file;
        switch (sheetName.toLowerCase()) {
            case "main":
                file = mainSheet;
                break;
            case "alertbox":
                file = alertBoxSheet;
                break;
            case "propertiesbox":
                file = propertiesBoxSheet;
                break;
            default:
                file = null;
                break;
        }
        
        if (checkIfStylesheetExits(file)) {
            try {
                return file.toURI().toURL();
            } catch (MalformedURLException ex) {}
        }
        return null;
    }
    /* end */
    
    private boolean checkSaveData() {
        
        if (!mainFolder.isDirectory()) {
            mainFolder.mkdir();
        }
        
        if (mainFolder.isDirectory()) {
            if (!menuDataFile.isFile()) {
                if (createNewFile(menuDataFile))
                    putDefaultData("menuData");
            }

            if (!sceneDataFile.isFile()) {
                if (createNewFile(sceneDataFile))
                    putDefaultData("sceneData");
            }

            if (!docsDataFile.isFile()) {
                if (createNewFile(docsDataFile))
                    putDefaultData("docsData");
            }
        } else {}
        
        if (menuDataFile.isFile() && sceneDataFile.isFile() && docsDataFile.isFile())
            return true;
        
        return false;
    }
    
    private void putDefaultData(String dataName) {
        String data = File_rw.getText(resLoader.loadDataFile(dataName));
        File file = new File(mainFolder.getPath() + File.separator + dataName + ".ini");
        File_rw.setText(data, file, System.lineSeparator());
    }
    
    private boolean checkIfStylesheetExits(File file) {
        if (defaultStyleFolder.isDirectory()) {
            if (!file.isFile()) {
                createNewFile(file);
                putDefaultDataInSheet(file.getName());
                return checkIfStylesheetExits(file);
            } else {
                return true;
            }
        } else {
            defaultStyleFolder.mkdirs();
            return checkIfStylesheetExits(file);
        }
    }
    
    private void putDefaultDataInSheet(String sheetName) {
        String data = File_rw.getText(resLoader.loadStylesheetAsIS(sheetName));
        File file = new File(defaultStyleFolder.getPath() + File.separator + sheetName);
        File_rw.setText(data, file, System.lineSeparator());
    }
    
    private boolean createNewFile(File file) {
        try {
            file.createNewFile();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
    
}
