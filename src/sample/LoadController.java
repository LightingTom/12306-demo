package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class LoadController {
    String hostName = "localhost";
    int portNumber = 2345;
    private boolean finish = false;
    private Stage dialogStage;

    @FXML
    private TextField field;
    @FXML
    private PasswordField password;
    @FXML
    private Button ok;
    @FXML
    private Button sign;

    private Main main;

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleOK() {
        String command = "LOGIN;";
        command += field.getText();
        command += ";";
        command += password.getText();

        try (Socket sock = new Socket(hostName, portNumber);
             //set autoFlush true
             PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));) {
            out.println(command);
            String fromServer = in.readLine();
            int msg = Integer.parseInt(fromServer);
            if (msg == -1){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Invalid information");
                alert.show();
            }else if(msg == 1){
                finish = true;
                dialogStage.close();
                main.setCurrentUserID(field.getText());
                main.showPassenger();
            }else{
                finish = true;
                dialogStage.close();
                main.showManager();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setMain(Main main){this.main = main;}

    public boolean isFinish(){
        return finish;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
