package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class ManagerController {
    private Main main;
    private Stage dialogStage;
    String hostName = "localhost";
    int portNumber = 2345;

    @FXML
    private TextField add_station_city;
    @FXML
    private TextField add_station_name;
    @FXML
    private TextField delete_station;
    @FXML
    private TextField old_name;
    @FXML
    private TextField new_name;
    @FXML
    private TextField seat_trainid;
    @FXML
    private TextField add_type_name;
    @FXML
    private TextField add_coe;
    @FXML
    private TextField add_Num;
    @FXML
    private TextField mod_old_name;
    @FXML
    private TextField mod_new_name;
    @FXML
    private TextField mod_coe;
    @FXML
    private TextField mod_num;
    @FXML
    private TextField del_name;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    private void handleLogOut() {
        dialogStage.close();
        main.showinit();
    }

    @FXML
    private void handle_addStation() {
        String city = add_station_city.getText();
        String name = add_station_name.getText();
        String command = "ADDSTATION;" + city + ";" + name;
        sendMsg(command);
        add_station_city.setText("");
        add_station_name.setText("");
    }

    @FXML
    private void handleDeleteStation() {
        String command = "DELETESTATION;" + delete_station.getText();
        sendMsg(command);
        delete_station.setText("");
    }

    @FXML
    private void handleModifyStation(){
        String command = "CHANGESTATION;"+old_name.getText()+";"+new_name.getText();
        sendMsg(command);
        old_name.setText("");
        new_name.setText("");
    }

    @FXML
    private void handleAddSeatType(){
        String command = "ADDSEAT;"+seat_trainid.getText()+";"+add_type_name.getText()+";"+add_coe.getText()+";"+add_Num.getText();
        sendMsg(command);
        seat_trainid.setText("");
        add_type_name.setText("");
        add_coe.setText("");
        add_Num.setText("");
    }

    @FXML
    private void handleModSeat(){
        String command = "MODSEAT;"+seat_trainid.getText()+";"+mod_old_name.getText()+";"
                +mod_new_name.getText()+";"+mod_coe.getText()+";"+mod_num.getText();
        sendMsg(command);
        seat_trainid.setText("");
        mod_old_name.setText("");
        mod_new_name.setText("");
        mod_coe.setText("");
        mod_num.setText("");
    }

    @FXML
    private void handleDeleteSeat(){
        String command = "DELSEAT;"+seat_trainid.getText()+";"+del_name.getText();
        sendMsg(command);
        seat_trainid.setText("");
        del_name.setText("");
    }

    @FXML
    private void handleAdd(){
        main.setAction(1);
        main.showLineManager();
    }

    @FXML
    private void handleMod(){
        main.setAction(2);
        main.showLineManager();
    }


    private void sendMsg(String command) {
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

}
