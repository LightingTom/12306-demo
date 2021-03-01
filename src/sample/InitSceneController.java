package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class InitSceneController {
    private Main main;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView imageView;
    @FXML
    private Button log;
    @FXML
    private Button sign;

    public void setMain(Main main){this.main = main;}

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleSign(){
        main.primaryStage.close();
        main.showSignUp();
    }

    @FXML
    private void handleLog(){
        main.primaryStage.close();
        main.showLogIn();
    }

}
