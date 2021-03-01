package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class SignUpController {
    private static String hostName = "localhost";
    private static int portNumber = 2345;
    private boolean finish = false;
    private Stage dialogStage;
    private Main main;

    @FXML
    private GridPane pane;
    @FXML
    private TextField userName;
    @FXML
    private TextField userID;
    @FXML
    private PasswordField password;
    @FXML
    private TextField IDCard;
    @FXML
    private TextField PhoneNumber;
    @FXML
    private Button sign;
    @FXML
    private PasswordField key;

    @FXML
    private void initialize() {
    }

    @FXML
    public void handleSign(){
        String UID = userID.getText();
        String name = userName.getText();
        String pass = password.getText();
        String IDca = IDCard.getText();
        String phone = PhoneNumber.getText();
        String mkey = key.getText();
        int identity = 1;
        if (name == null || pass == null || IDca == null || phone == null || UID == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Incomplete Information");
            alert.setContentText("Please fill in the information");
            alert.show();
        }else{
            if (mkey.equals("XCQLYC")){
                identity = 2;
            }
            String command = "SIGNUP;"+name+";"+pass+";"+identity+";"+IDca+";"+phone+";"+UID;
            try (Socket sock = new Socket(hostName, portNumber);
                 //set autoFlush true
                 PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));) {
                out.println(command);
                System.out.println(command);
                String fromServer = in.readLine();
                System.out.println(fromServer);
                if (fromServer.equals("3")){
                    finish = true;
                    dialogStage.close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Sign Up Success");
                    alert.setContentText("Sign Up Success, please log in");
                    alert.showAndWait();
                    main.showLogIn();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Unknown Error");
                    alert.setContentText("Some unkonwn error happened");
                    alert.show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
        main.showinit();
    }

    public boolean isFinish(){
        return finish;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMain(Main main){this.main = main;}
}
