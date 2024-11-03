package id.dojo.kriptografi;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemController implements Initializable {
    @FXML
    private Label method;

    @FXML
    private Label preview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setItemInfo(String method, String preview) {
        this.method.setText(method);
        this.preview.setText(preview);
    
    }
}
