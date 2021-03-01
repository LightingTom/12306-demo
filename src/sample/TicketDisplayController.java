package sample;

import Model.TicketInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TicketDisplayController {
    private Main main;
    private Stage dialogStage;
    private boolean finish = false;
    String hostName = "localhost";
    int portNumber = 2345;

    @FXML
    private ListView<TicketInfo> listView;

    @FXML
    private void initialize(){
        listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> main.setSelection(listView.getSelectionModel().getSelectedIndex()));
    }

    @FXML
    private void handleBuy(){
        String command = "BUY;"+main.getCurrentUserID()+";"+main.getTicketInfos().get(main.getSelection()).ticketID;
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
            System.out.println(main.getCurrentOrderID());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        main.showTicket();
        dialogStage.close();
    }
    @FXML
    private void handleDetail(){
        String command = "LIST;"+main.getTicketInfos().get(main.getSelection()).trainID;
        try (Socket sock = new Socket(hostName, portNumber);
             //set autoFlush true
             PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));) {
            out.println(command);
            String fromServer = in.readLine();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setResizable(true);
            alert.setTitle("Stations on this train");
            alert.setContentText(fromServer);
            alert.setHeaderText("Station information");
            alert.show();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
        main.showPassenger();
    }

    public void setMain(Main main){
        this.main = main;
        listView.setItems(main.getTicketInfos());
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public boolean isFinish(){
        return finish;
    }
}
