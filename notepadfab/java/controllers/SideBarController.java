
// Developed by the TerminatorOTW

package notepadfab.java.controllers;

import notepadfab.java.layouts.SideBar;

public class SideBarController {
    
    private final MainController mainCtrl;
    private final SideBar sideBar;
    
    public SideBarController(MainController mC, SideBar sB) {
        this.mainCtrl = mC;
        this.sideBar = sB;
    }
    
}
