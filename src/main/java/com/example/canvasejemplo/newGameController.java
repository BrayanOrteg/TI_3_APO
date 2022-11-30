package com.example.canvasejemplo;

import com.example.canvasejemplo.model.User;
import com.example.canvasejemplo.model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;




public class newGameController implements Initializable {

    @FXML
    private ImageView fondo;

    @FXML
    private Button newBttn;

    @FXML
    private TextField jp1,jp2;



    @FXML
    public void onNewGameClick (){

        Stage stage = (Stage) newBttn.getScene().getWindow();

        Users users=Users.getInstance();

        users.setJP1(jp1.getText());
        users.setJP2(jp2.getText());

        HelloApplication.showWindow("hello-view.fxml");
        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String uri = "file:"+HelloApplication.class.getResource("fondo2.gif").getPath();
        Image i = new Image((uri));
        fondo.setImage(i);

        newBttn.setStyle("-fx-padding: 8 15 15 15;\n" +
                "    -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;\n" +
                "    -fx-background-radius: 8;\n" +
                "    -fx-background-color: \n" +
                "        linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%),\n" +
                "        #9d4024,\n" +
                "        #d86e3a,\n" +
                "        radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-font-size: 1.1em;");
    }
}
