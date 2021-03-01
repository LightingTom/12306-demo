package sample;

import Model.TicketInfo;
import Model.TrainAndStation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class LineDetailController {
    static String hostName = "localhost";
    static int portNumber = 2345;
    TrainAndStation choice;
    private ObservableList<TicketInfo> tickets1 = FXCollections.observableArrayList();
    private ObservableList<TicketInfo> tickets2 = FXCollections.observableArrayList();

    @FXML
    private ListView<TicketInfo> train1;
    @FXML
    private ListView<TicketInfo> train2;

    private Main main;
    private Stage dialogStage;

    public void setMain(Main main){
        this.main = main;
        choice = main.getCurrentChoice();
        tickets1 = getTicket(choice.getTrain1(),main.getD_s(),choice.getTransfer(),main.getDateChoice());
        tickets2 = getTicket(choice.getTrain2(),choice.getTransfer(),main.getA_s(),main.getDateChoice());
        train1.setItems(tickets1);
        train2.setItems(tickets2);
    }

    @FXML
    private void initialize(){
        train1.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> main.setTicketChoice1(train1.getSelectionModel().getSelectedIndex()));
        train2.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> main.setTicketChoice2(train2.getSelectionModel().getSelectedIndex()));
    }

    private static ObservableList<TicketInfo> getTicket(String tid, String d_s, String a_s, String d_d){
        String command = "FINDTICKET;"+tid+";"+d_s+";"+a_s+";"+d_d;
        ObservableList<TicketInfo> result = FXCollections.observableArrayList();
        try (Socket sock = new Socket(hostName, portNumber);
             //set autoFlush true
             PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));) {
            out.println(command);
            String fromServer = in.readLine();
            if (fromServer.equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No available ticket found");
                alert.showAndWait();
            }
            String[] info = fromServer.split("/");
            for (int i = 0; i < info.length; i++) {
                String[] t_info = info[i].split(";");
                TicketInfo to_add = new TicketInfo(t_info[0], t_info[1], t_info[2], t_info[3], t_info[4],
                        Integer.parseInt(t_info[7]), Integer.parseInt(t_info[5]), t_info[6],Double.parseDouble(t_info[8]));
                result.add(to_add);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @FXML
    private void handlePay(){
        System.out.println(tickets1.get(main.getTicketChoice1()).ticketID);
        String command = "BUY;"+main.getCurrentUserID()+";"+tickets1.get(main.getTicketChoice1()).ticketID;
        String command2 = "BUY;"+main.getCurrentUserID()+";"+tickets2.get(main.getTicketChoice2()).ticketID;
        String command3 = "PAY;"+buy(command);
        String command4 = "PAY;"+buy(command2);
        pay(command3);
        pay(command4);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Successfully buy these tickets");
        alert.showAndWait();
        dialogStage.close();
        main.showPassenger();
    }

    private int buy(String command){
        try (Socket sock = new Socket(hostName, portNumber);
             //set autoFlush true
             PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));) {
            out.println(command);
            String fromServer = in.readLine();
            if (fromServer.equals("-1"))
                System.err.println("err");
            else
                main.setCurrentOrderID(Integer.parseInt(fromServer.trim()));
            return Integer.parseInt(fromServer.trim());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void pay(String command){
        try (Socket sock = new Socket(hostName, portNumber);
             //set autoFlush true
             PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));) {
            out.println(command);
            String fromServer = in.readLine();
            if (fromServer.equals("-1")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Some unexpected error happened! ");
                alert.show();
                dialogStage.close();
                main.showPassenger();
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
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
