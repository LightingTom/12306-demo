package sample;

import Model.TicketInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TicketController {
    private Main main;
    private boolean finish;
    private Stage dialogStage;
    private TicketInfo info;

    String hostName = "localhost";
    int portNumber = 2345;

    @FXML
    private AnchorPane pane;
    @FXML
    private Label trainID;
    @FXML
    private Label depart_station;
    @FXML
    private Label depart_time;
    @FXML
    private Label arrive_station;
    @FXML
    private Label price;
    @FXML
    private Label seatNum;
    @FXML
    private Label seat_type;

    @FXML
    public void handlePay(){
        String command = "PAY;"+main.getCurrentOrderID();
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
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("You have successfully buy the ticket");
                alert.showAndWait();
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
    public void handleCancel(){
        dialogStage.close();
        main.showPassenger();
    }

    public void setMain(Main main) {
        this.main = main;
        info = main.getTicketInfos().get(main.getSelection());
        trainID.setText(info.trainID + "次");
        depart_station.setText(info.depart_station);
        arrive_station.setText(info.arrive_station);
        depart_time.setText(info.depart_time);
        price.setText("￥" + info.price);
        seatNum.setText("2车33号");
        seat_type.setText(info.seatType);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isFinish() {
        return finish;
    }


}
