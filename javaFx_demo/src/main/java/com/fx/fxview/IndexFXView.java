package com.fx.fxview;

import de.felixroske.jfxsupport.FXMLView;
import javafx.fxml.Initializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@FXMLView(value = "/App.fxml")
public abstract class IndexFXView implements Initializable {

//    @FXML
//    protected AnchorPane paneRoot;
//    @FXML
//    protected AnchorPane tabPane;
//    @FXML
//    protected TreeView<String> treeRoot;
//    @FXML
//    protected TextArea textArea;
}
