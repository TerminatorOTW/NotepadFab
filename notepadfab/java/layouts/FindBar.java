
// Developed by the TerminatorOTW

package notepadfab.java.layouts;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import notepadfab.ResourceLoader;
import notepadfab.java.api.String_op;

public class FindBar extends VBox {
    
    private final ResourceLoader resLoader = new ResourceLoader();
    private final String_op stringOp = new String_op();
    
    private final HBox findBar;
    private final HBox replaceBar;
    
    private TextField findBox;
    private Button forwardButton, backwardButton;
    private Label searchHits;
    private CheckBox matchCaseBox, findInSelec;
    private Label matchCaseLabel, findInSelecLabel;
    private Button closeButton;
    
    private final Image closeImage;
    private final Image forwardIcon;
    private final Image backwardIcon;
    private final Image forwardIconActive;
    private final Image backwardIconActive;
    
    private TextField replaceBox;
    private Button replace;
    private Button replaceAll;
    
    private BorderPane showingLayout;
    
    private boolean matchCase = false;
    private boolean findInSelection = false;
    
    int[] matchesRange = null;
    int curSelecMatchPos = 0;
    
    private final TextArea t;
    private final BorderPane l;
    
    private boolean findLayoutShowing = false;
    private boolean replaceLayoutShowing = false;
    
    public FindBar(TextArea editor, BorderPane layout) {
        
        l = layout;
        t = editor;
        
        this.closeImage = resLoader.loadImages("closeIcon.png");
        this.forwardIcon = resLoader.loadImages("forwardIcon.png");
        this.backwardIcon = resLoader.loadImages("backwardIcon.png");
        this.forwardIconActive = resLoader.loadImages("forwardIconActive.png");
        this.backwardIconActive = resLoader.loadImages("backwardIconActive.png");
        
        // Find Bar
        findBar = new HBox(0);
        findBar.getStyleClass().add("findBar");
        findBar.setAlignment(Pos.CENTER_LEFT);
        
        this.setFindBar();
        
        // Replace Bar
        replaceBar = new HBox(0);
        replaceBar.getStyleClass().add("replaceBar");
        replaceBar.setAlignment(Pos.CENTER_LEFT);
        
        this.setReplaceBar();
        
        t.textProperty().addListener(e->{
            if (findLayoutShowing) {
                if (matchesRange != null) {
                    matchesRange = null;
                    searchHits.setText("No matches");
                }
            }
        });
        
    }
    
    public void showFindBar() {
        if (!findLayoutShowing) {
            this.getChildren().add(findBar);
            l.setBottom(this);
            findBox.requestFocus();
            findLayoutShowing = true;

            if (!t.getSelectedText().isEmpty()) {
                findBox.setText(t.getSelectedText());
                findBox.positionCaret(findBox.getText().length());
                findAndSelectFn();
            }
        }
    }
    
    public void hideFindBar() {
        this.getChildren().clear();
        l.setBottom(null);
        findLayoutShowing = false;
        matchesRange = null;
        curSelecMatchPos = 0;
        searchHits.setText("No matches");
        forwardButton.setGraphic(new ImageView(forwardIcon));
        backwardButton.setGraphic(new ImageView(backwardIcon));
        findBox.setText("");
        replaceBox.setText("");
        
        if (replaceLayoutShowing)
            hideReplaceBar();
    }
    
    public void showReplaceBar() {
        if (findLayoutShowing && (!replaceLayoutShowing)) {
            this.getChildren().add(replaceBar);
            replaceLayoutShowing = true;
            replaceBox.requestFocus();
        } else if (!replaceLayoutShowing) {
            showFindBar();
            showReplaceBar();
        }
    }
    
    public void hideReplaceBar() {
        this.getChildren().remove(replaceBar);
        replaceLayoutShowing = false;
    }
    
    private void setFindBar() {
        
        // SearchBox
        findBox = new TextField();
        findBox.getStyleClass().add("findBox");
        findBox.setPrefWidth(200);
        findBox.setPromptText("Find");
        
        findBox.setOnAction(e->{
            findAndSelectFn();
        });
        
        // find Next forward and backward
        forwardButton = new Button();
        HBox.setMargin(forwardButton, new Insets(0, 0, 0, 20));
        forwardButton.getStyleClass().add("findBarCloseButton");
        forwardButton.setGraphic(new ImageView(forwardIcon));
        
        forwardButton.setOnAction(e->{
            if (matchesRange != null && (matchesRange.length/2) > 1) {
                int matchesLength = (matchesRange.length / 2) - 1;
                if ((curSelecMatchPos + 1) <= matchesLength) {
                    curSelecMatchPos += 1;
                    selectAtPos(matchesRange[(curSelecMatchPos)*2],
                                matchesRange[((curSelecMatchPos)*2) + 1] + 1);
                    searchHits.setText((curSelecMatchPos + 1) + " of " + (matchesLength + 1));
                } else {
                    selectAtPos(matchesRange[0],
                                matchesRange[1] + 1);
                    curSelecMatchPos = 0;
                    searchHits.setText((curSelecMatchPos + 1) + " of " + (matchesLength + 1));
                }
            }
        });
        
        backwardButton = new Button();
        HBox.setMargin(backwardButton, new Insets(0, 0, 0, 20));
        backwardButton.getStyleClass().add("findBarCloseButton");
        backwardButton.setGraphic(new ImageView(backwardIcon));
        
        backwardButton.setOnAction(e->{
            if (matchesRange != null && (matchesRange.length/2) > 1) {
                int matchesLength = (matchesRange.length / 2) - 1;
                if ((curSelecMatchPos - 1) >= 0) {
                    curSelecMatchPos -= 1;
                    selectAtPos(matchesRange[(curSelecMatchPos)*2],
                                matchesRange[((curSelecMatchPos)*2) + 1] + 1);
                    searchHits.setText((curSelecMatchPos + 1) + " of " + (matchesLength + 1));
                } else {
                    selectAtPos(matchesRange[matchesLength*2],
                                matchesRange[(matchesLength*2) + 1] + 1);
                    curSelecMatchPos = matchesLength;
                    searchHits.setText((curSelecMatchPos + 1) + " of " + (matchesLength + 1));
                }
            }
        });
        
        // search hits
        searchHits = new Label("No matches");
        HBox.setMargin(searchHits, new Insets(0, 0, 0, 20));
        searchHits.getStyleClass().add("searchHits");
        
        // matchCase
        matchCaseBox = new CheckBox();
        HBox.setMargin(matchCaseBox, new Insets(0, 0, 0, 20));
        matchCaseBox.getStyleClass().add("matchBox");
        
        matchCaseLabel = new Label("Match Case");
        HBox.setMargin(matchCaseLabel, new Insets(0, 0, 0, 5));
        matchCaseLabel.getStyleClass().add("searchHits");
        
        // findInSelection
        findInSelec = new CheckBox();
        HBox.setMargin(findInSelec, new Insets(0, 0, 0, 20));
        findInSelec.getStyleClass().add("matchBox");
        
        findInSelecLabel = new Label("Find in selection");
        HBox.setMargin(findInSelecLabel, new Insets(0, 0, 0, 5));
        findInSelecLabel.getStyleClass().add("searchHits");
        
        // closeButton
        closeButton = new Button();
        HBox.setMargin(closeButton, new Insets(0, 0, 0, 20));
        closeButton.getStyleClass().add("findBarCloseButton");
        closeButton.setGraphic(new ImageView(closeImage));
        
        closeButton.setOnAction(e->{
            hideFindBar();
        });
        
        // add items to the find bar
        findBar.getChildren().addAll(
            findBox, forwardButton, backwardButton,
            searchHits, matchCaseBox, matchCaseLabel,
            findInSelec, findInSelecLabel, closeButton
        );
        
    }
    
    private void setReplaceBar() {
        
        // ReplaceBox
        replaceBox = new TextField();
        replaceBox.getStyleClass().add("findBox");
        replaceBox.setPrefWidth(200);
        replaceBox.setPromptText("Replace");
        
        replaceBox.setOnAction(e->{
            replace();
        });
        
        replace = new Button("Replace");
        HBox.setMargin(replace, new Insets(0, 0, 0, 20));
        replace.getStyleClass().add("replaceButton");
        
        replace.setOnAction(e->{
            replace();
        });
        
        replaceAll = new Button("Replace All");
        HBox.setMargin(replaceAll, new Insets(0, 0, 0, 20));
        replaceAll.getStyleClass().add("replaceButton");
        
        replaceAll.setOnAction(e->{
            replaceAllMatches();
        });
        
        // add items to the find bar
        replaceBar.getChildren().addAll(
            replaceBox, replace, replaceAll
        );
        
    }
    
    private void findAndSelectFn() {
        matchCase = matchCaseBox.isSelected();
        findInSelection = findInSelec.isSelected();
        
        String value = findBox.getText();
        
        if (value.length() > 1 && (!findInSelection)) {
            matchesRange = stringOp.getAllMatchesIndexRange(t.getText(), value, matchCase);

            if ((matchesRange.length/2) > 1) {
                forwardButton.setGraphic(new ImageView(forwardIconActive));
                backwardButton.setGraphic(new ImageView(backwardIconActive));
            } else  {
                forwardButton.setGraphic(new ImageView(forwardIcon));
                backwardButton.setGraphic(new ImageView(backwardIcon));
            }

            if ((matchesRange.length/2) > 0) {
                selectAtPos(matchesRange[0], matchesRange[1] + 1);
                curSelecMatchPos = 0;
                searchHits.setText((curSelecMatchPos + 1) + " of " + (matchesRange.length/2));
            } else {
                searchHits.setText("No matches");
                forwardButton.setGraphic(new ImageView(forwardIcon));
                backwardButton.setGraphic(new ImageView(backwardIcon));
                matchesRange = null;
                t.deselect();
            }
        } else if (value.length() > 1 && findInSelection) {
            IndexRange iR = t.getSelection();
            int[] range = stringOp.getAllMatchesIndexRange(t.getSelectedText(), value, matchCase);
            matchesRange = new int[range.length];
            
            for (int i=0; i < range.length; i++) {
                matchesRange[i] = range[i] + iR.getStart();
            }
            
            if ((matchesRange.length/2) > 1) {
                forwardButton.setGraphic(new ImageView(forwardIconActive));
                backwardButton.setGraphic(new ImageView(backwardIconActive));
            } else  {
                forwardButton.setGraphic(new ImageView(forwardIcon));
                backwardButton.setGraphic(new ImageView(backwardIcon));
            }

            if ((matchesRange.length/2) > 0) {
                selectAtPos(matchesRange[0], matchesRange[1] + 1);
                curSelecMatchPos = 0;
                searchHits.setText((curSelecMatchPos + 1) + " of " + (matchesRange.length/2));
            } else {
                searchHits.setText("No matches");
                forwardButton.setGraphic(new ImageView(forwardIcon));
                backwardButton.setGraphic(new ImageView(backwardIcon));
                matchesRange = null;
                t.deselect();
            }
        } else {
            t.deselect();
        }
    }
    
    private void replace() {
        if (matchesRange == null) {
            findBox.requestFocus();
        } else {
            String newText = stringOp.replaceStringAtPos(
                matchesRange[curSelecMatchPos*2],
                matchesRange[(curSelecMatchPos*2) + 1] + 1,
                t.getText(), replaceBox.getText()
            );
            t.setText(newText);
            findAndSelectFn();
        }
    }
    
    private void replaceAllMatches() {
        if (matchesRange == null && findBox.getText().length() > 0) {
            findBox.requestFocus();
        } else {
            while (true) {
                int[] range = stringOp.getIndexRange(t.getText(), findBox.getText(), matchCase);
                if (range[0] != -1 && range[1] != -1) {
                    String newText = stringOp.replaceStringAtPos(range[0], range[1] + 1, t.getText(), replaceBox.getText());
                    t.setText(newText);
                } else {
                    break;
                }
            }
        }
    }
    
    private void selectAtPos(int start, int end) {
        t.selectRange(start, end);
    }
}
