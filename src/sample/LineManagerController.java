package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class LineManagerController {
    private Main main;
    private Stage dialogStage;
    String hostName = "localhost";
    int portNumber = 2345;

    @FXML
    private TextField arr_time;
    @FXML
    private TextField dep_time;
    @FXML
    private TextField tid;
    @FXML
    private TextField station;
    @FXML
    private TextField day;
    @FXML
    private TextField mile;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    private void handleSubmit(){
        String command = "";
        if (main.getAction() == 1){
            command = "ADDLINE;";
            command += (tid.getText()+";");
            command += (station.getText()+";");
            command += (arr_time.getText()+";");
            command += (dep_time.getText()+";");
            command += (mile.getText()+";");
            command += (day.getText());
        }else if (main.getAction() == 2){
            command = "MODLINE;";
            command += (tid.getText()+";");
            command += (station.getText()+";");
            command += (arr_time.getText()+";");
            command += (dep_time.getText()+";");
            command += (mile.getText()+";");
            command += (day.getText());
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Some error happened");
            alert.show();
        }
        sendMsg(command);
        dialogStage.close();
    }

    private void sendMsg(String command){
        try (Socket sock = new Socket(hostName, portNumber);
             //set autoFlush true
             PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));) {
            out.println(command);
            String fromServer = in.readLine();
            if (fromServer.equals("OK")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Success!");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Some error happened");
                alert.show();
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
}
