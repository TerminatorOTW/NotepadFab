
// Developed by the TerminatorOTW

package notepadfab.java.controllers;

import java.io.File;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.IndexRange;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import notepadfab.java.api.File_rw;
import notepadfab.java.api.String_op;
import notepadfab.java.layouts.StatusBar;
import notepadfab.java.layouts.TabsMenu;
import notepadfab.java.layouts.editor.TextEditorFx;

public class EditorController {
    
    private final TextEditorFx editor;
    private final MainController mainCtrl;
    private final File_rw fileRw = new File_rw();
    
    public EditorController(TextEditorFx t, MainController mC) {
        this.editor = t;
        this.mainCtrl = mC;
        
        // do it grunt
        this.handler();
    }
    
    public TextEditorFx getEditor() {
        return editor;
    }
    
    public void setOnKeyPressed(EventHandler<KeyEvent> e) {
        editor.setOnKeyPressed(e);
    }
    
    public void setOnKeyReleased(EventHandler<KeyEvent> e) {
        editor.setOnKeyReleased(e);
    }
    
    public void setText(String string) {
        editor.setText(string);
    }
    
    public String getText() {
        return editor.getText();
    }
    
    public int getLineCount() {
        
        int lineCount = 0;
        final String text = getText();
        final int length = text.length();

        for (int i = 0; i < length; i++) {
            if (text.charAt(i) == '\n')
                lineCount++;
        }

        return lineCount + 1;
    }
    
    public int getCaretLinePos() {
        
        int lineCount = 0;
        final int caretPos = getCaretPos();
        final String text = getText();

        for (int i = 0; i < caretPos; i++) {
            if (text.charAt(i) == '\n')
                lineCount++;
        }

        return lineCount += 1;
    }
    
    public int getCaretColPos() {
        
        int colCount = 0;
        final int caretPos = getCaretPos();
        final String text = getText();

        int lastIndex = caretPos - 1;

        for (int i = (lastIndex); i >= 0; i--) {
            if (text.charAt(i) == '\n')
                break;
            colCount++;
        }

        return colCount += 1;
    }
    
    public void tabFn() {
        
        if (isSelectionEmpty()) {
            positionCaret(tabIt(getCaretPos()));
        } else {
            String text = getText();
            String selection = getSelectedText();
            IndexRange ir = editor.getSelection();
            int startPos = ir.getStart() + 0;
            int endPos = ir.getEnd() + 0;
            
            for (int i = startPos; i >= 0; i--) {
                if (text.charAt(i) == '\n') {
                    startPos = i + 1;
                    break;
                } else if (i == 0) {
                    startPos = 0;
                    break;
                }
            }
            
            startPos = tabIt(startPos);
            endPos += 4;
            
            String x,y,z;
            
            x = getText().substring(0, startPos);
            y = getText().substring(startPos, endPos).replace("\n", "\n    ");
            z = getText().substring(endPos, getText().length());
            
            setText(x + y + z);
            
            positionCaret(x.length() + y.length());
            
        }
    }
    
    public void reverseTabFn() {
        if (isSelectionEmpty()) {
            positionCaret(reverseTabIt(getCaretPos()));
        } else {
            String selection = getSelectedText();
            IndexRange ir = editor.getSelection();
            int startPos = ir.getStart() + 0;
            int endPos = ir.getEnd() + 0;
            
            int a = reverseTabIt(startPos);
            
            if (a < startPos)
                endPos -= 4;
            
            String x,y,z;
            
            x = getText().substring(0, startPos);
            y = getText().substring(startPos, endPos).replace("\n    ", "\n");
            z = getText().substring(endPos, getText().length());
            
            setText(x + y + z);
            
            positionCaret(x.length() + y.length());
            
        }
    }
    
    public void appendText(String string) {
        editor.appendText(string);
    }
    
    public void setTextWrapping(boolean value) {
        editor.setWrapText(value);
    }
    
    public void setFont(Font font) {
        editor.setFont(font);
    }
    
    public Font getFont() {
        return editor.getFont();
    }
    
    public void setEditable(boolean value) {
        editor.setEditable(value);
    }
    
    public void clearText() {
        editor.clear();
    }
    
    public void setDisable(boolean value) {
        editor.setDisable(value);
    }
    
    public void selectAll() {
        editor.selectAll();
    }
    
    public void deletePrevChar() {
        editor.deletePreviousChar();
    }
    
    public void pasteText() {
        editor.paste();
    }
    
    public void copyText() {
        editor.copy();
    }
    
    public void cutText() {
        editor.cut();
    }
    
    public void undo() {
        editor.undo();
    }
    
    public void redo() {
        editor.redo();
    }
    
    public void requestFocus() {
        editor.requestFocus();
    }
    
    public double[] getScrollPos() {
        return new double[]{editor.getScrollTop(), editor.getScrollLeft()};
    }
    public void setScrollPos(double[] v) {
        editor.setScrollTop(v[0]);
        editor.setScrollLeft(v[1]);
    }
    
    public int getCaretPos() {
        return editor.getCaretPosition();
    }
    
    public void positionCaret(int pos) {
        editor.positionCaret(pos);
    }
    
    public boolean isTextWrapped() {
        return editor.isWrapText();
    }
    
    public boolean isDisabled() {
        return editor.isDisabled();
    }
    
    public boolean isEditable() {
        return editor.isEditable();
    }
    
    public boolean isEmpty() {
        return editor.getText().isEmpty();
    }
    
    public String getSelectedText() {
        String x = "";
        try {
            x = editor.getSelectedText();
            return x;
        } catch (Exception e) {
            return x;
        }
    }
    
    public boolean isSelectionEmpty() {
        boolean x = false;
        try {
            x = getSelectedText().isEmpty();
            return x;
        } catch (Exception e) {
            return x;
        }
    }
    
    public double getVScrollPos() {
        ScrollPane s = (ScrollPane) editor.lookup(".scroll-pane");
        if (s != null)
            return s.getVvalue();
        else
            return 0;
    }
    
    public void setVScrollPos(double pos) {
        ScrollPane s = (ScrollPane) editor.lookup(".scroll-pane");
        if (s != null)
            s.setVvalue(pos);
    }
    
    private int tabIt(int pos) {
        final String_op s = new String_op();
        setText(
            s.replaceStringAtPos(
                pos, pos,
                mainCtrl.getEditorCtrl().getText(),
                "    "
            )
        );
        return pos + 4;
    }
    
    private int reverseTabIt(int pos) {
        
        int x = 0;
        
        for (int i = (pos-1); i >= 0; i--) {
            if (getText().charAt(i) == '\n') {
                x += i + 1;
                break;
            }
        }
        
        boolean result = true;
        
        for (int i = x; i < (x+4); i++) {
            if (i >= getText().length()) {
                result = false;
                break;
            }
            if (getText().charAt(i) != ' ') {
                result = false;
                break;
            }
        }
        
        if (result) {
            final String_op s = new String_op();
            setText(
                s.replaceStringAtPos(
                    x, x+4,
                    mainCtrl.getEditorCtrl().getText(),
                    ""
                )
            );
            return pos - 4;
        }
        
        return pos;
    }
    
    private void handler() {
        
        editor.textProperty().addListener(e->{
            if (mainCtrl.getTabsController().getTabsCount() > 0) {
                String text = editor.getText();
                mainCtrl.getTabsController().updateCurrentText(text);
                if (mainCtrl.getMenuCtrl().isAutoSaveEnabled()) {
                    File file = mainCtrl.getTabsController().getCurrentFile();
                    if (file != null) {
                        fileRw.setText(
                            text, file, mainCtrl.getMenuCtrl().getEol()
                        );
                        mainCtrl.getTabsController().updateTabWhenSaved(
                            mainCtrl.getTabsController().getCurrentTabPos(), file, true
                        );
                    }
                }
                mainCtrl.getTabsController().checkSavedOnCurrentTab();
                mainCtrl.getStatusBarCtrl().setCharacterCount(editor.getText().length());
                int lineCount = getLineCount();
                mainCtrl.getLineBarController().setLineCount(lineCount);
            }
        });
        
        editor.getHScrollBar().visibleProperty().addListener(e->{
            if (editor.isHScrollBarVisible()) {
                Platform.runLater(()->{
                    mainCtrl.getMainLayout().setHScrollBar(editor.getHScrollBar());
                });
            } else {
                Platform.runLater(()->{
                    mainCtrl.getMainLayout().setHScrollBar(null);
                });
            }
        });
        
        editor.caretPositionProperty().addListener(e->{
            int x = 0;
            final int lineNo = getCaretLinePos();
            
            if (!isSelectionEmpty())
                x = editor.getSelectedText().length();
            
            mainCtrl.getStatusBarCtrl().setLineColCaretPos(
                lineNo, getCaretColPos(), x
            );
            
            mainCtrl.getLineBarController().setCurrLine(lineNo);
        });
        
        editor.selectionProperty().addListener(e->{
            mainCtrl.getStatusBarCtrl().setLineColCaretPos(
                getCaretLinePos(), getCaretColPos(),
                editor.getSelectedText().length()
            );
        });
        
    }
    
}
