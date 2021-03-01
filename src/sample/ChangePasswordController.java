package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChangePasswordController {
    @FXML
    private PasswordField old;
    @FXML
    private PasswordField new_pass;
    @FXML
    private PasswordField confirm;

    private Main main;
    private Stage dialogStage;
    String hostName = "localhost";
    int portNumber = 2345;
    private String userID;

    @FXML
    private void handleOK(){
        String n_pass = new_pass.getText();
        String c_pass = confirm.getText();
        if (!n_pass.equals(c_pass)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("The password for new password and confirm is not the same");
            alert.showAndWait();
        }

        try (Socket sock = new Socket(hostName, portNumber);
             //set autoFlush true
             PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));) {
            String command = "CHANGEPASS;"+main.getCurrentUserID()+";"+old.getText()+";"+new_pass.getText();
            out.println(command);
            String fromServer = in.readLine();
            if (fromServer.equals("0"))
                dialogStage.close();
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Some unknown error happened");
                alert.showAndWait();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

    public void setMain(Main main){this.main = main;}

    public void setDialogStage(Stage dialogStage){this.dialogStage = dialogStage;}
}
