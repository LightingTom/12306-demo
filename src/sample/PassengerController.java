package sample;

import Model.Order;
import Model.TicketInfo;
import Model.TrainAndStation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class PassengerController {
    private Main main;
    private Stage dialogStage;
    String hostName = "localhost";
    int portNumber = 2345;
    private boolean finish = false;
    private static ObservableList<Order> orderHistory = FXCollections.observableArrayList();

    @FXML
    AnchorPane pane;
    @FXML
    private DatePicker dp;
    @FXML
    private TextField depart;
    @FXML
    private TextField arrive;
    @FXML
    private TableView<Order> historyTable;
    @FXML
    private TableColumn<Order,String> UID;
    @FXML
    private TableColumn<Order,String> dep;
    @FXML
    private TableColumn<Order,String> arr;
    @FXML
    private TableColumn<Order,String> d_time;
    @FXML
    private TableColumn<Order,String> a_time;
    @FXML
    private TableColumn<Order,String> o_time;
    @FXML
    private TableColumn<Order,String> state;
    @FXML
    private TableColumn<Order, String> price;

    public void setMain(Main main){
        this.main = main;
        showHistory();
    }

    private void showHistory(){
        orderHistory.clear();
        String command = "HISTORY;"+main.getCurrentUserID();
        try (Socket sock = new Socket(hostName, portNumber);
             //set autoFlush true
             PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));) {
            out.println(command);
            String fromServer = in.readLine();
            if (fromServer.equals(""))
                return;
            if (fromServer != null){
                String[] ods = fromServer.split("/");
                for (int i = 0; i < ods.length; i++) {
                    String[] info = ods[i].split(";");
                    String[] o_time = info[5].split(":");
                    Order a = new Order(info[0],info[1],info[2],info[3],info[4],o_time[0]+":"+o_time[1]+":"+o_time[2].substring(0,2),info[6],info[7]);
                    a.setOrderID(Integer.parseInt(info[8]));
                    orderHistory.add(a);

                }
            }
            historyTable.setItems(orderHistory);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(){
        UID.setCellValueFactory(cellData->cellData.getValue().getUID());
        dep.setCellValueFactory(cellData->cellData.getValue().getDep());
        arr.setCellValueFactory(cellData->cellData.getValue().getArr());
        d_time.setCellValueFactory(cellData->cellData.getValue().getD_time());
        a_time.setCellValueFactory(cellData->cellData.getValue().getA_time());
        o_time.setCellValueFactory(cellData->cellData.getValue().getO_time());
        state.setCellValueFactory(cellData->cellData.getValue().getState());
        price.setCellValueFactory(cellData->cellData.getValue().getTicket_price());
        historyTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> main.setOrderSelect(historyTable.getSelectionModel().getSelectedIndex()));
    }
    @FXML
    public void handleOK(){
        String dep = depart.getText();
        String arr = arrive.getText();
        String date = dp.getValue().toString();
        main.setD_s(dep);
        main.setA_s(arr);
        main.setDateChoice(date);
        if (dep == null || arr == null || date == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Incomplete information");
            alert.setContentText("Please fill in the information and try again");
            alert.setTitle("Warning");
            alert.show();
        }

        String command = "QUERY1;"+dep+";"+arr+";"+date;
        try (Socket sock = new Socket(hostName, portNumber);
             //set autoFlush true
             PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));) {
            ObservableList<TicketInfo> ticketInfos = FXCollections.observableArrayList();
            ObservableList<TrainAndStation> lineinfo = FXCollections.observableArrayList();
            out.println(command);
            String fromServer = in.readLine();
            String[] separate = fromServer.split("%");
            if (separate[0].equals("NOCOMMON") && separate[1].equals("-9"))
                System.out.println("no train");
            else if (separate[0].equals("NOCOMMON") && (!separate[1].equals("-9"))){
                String info = separate[1];
                String[] lines = info.split("/");
                for (int i = 0; i < lines.length; i++) {
                    String[] line = lines[i].split(";");
                    TrainAndStation to_add = new TrainAndStation(line[0],line[1],Integer.parseInt(line[2]),line[3]);
                    lineinfo.add(to_add);
                }
                main.setLines(lineinfo);
                dialogStage.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("No direct train. We will recommend some lines to take");
                alert.showAndWait();
                main.showLines();
            }else {
                if (fromServer.equals("-4"))
                    System.out.println("no trains");
                else {
                    String[] trains = fromServer.split("/");
                    for (int i = 0; i < trains.length; i++) {
                        String[] info = trains[i].split(";");
                        TicketInfo to_add = new TicketInfo(info[0], info[1], info[2], info[3], info[4],
                                Integer.parseInt(info[7]), Integer.parseInt(info[5]), info[6],Double.parseDouble(info[8]));
                        ticketInfos.add(to_add);
                    }
                    main.setTicketInfos(ticketInfos);
                    dialogStage.close();
                    main.showTicketDisplay();
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleOnly(){
        String dep = depart.getText();
        String arr = arrive.getText();
        String date = dp.getValue().toString();
        main.setD_s(dep);
        main.setA_s(arr);
        main.setDateChoice(date);
        if (dep == null || arr == null || date == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Incomplete information");
            alert.setContentText("Please fill in the information and try again");
            alert.setTitle("Warning");
            alert.show();
        }

        String command = "QUERY2;"+dep+";"+arr+";"+date;
        try (Socket sock = new Socket(hostName, portNumber);
             //set autoFlush true
             PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));) {
            ObservableList<TicketInfo> ticketInfos = FXCollections.observableArrayList();
            ObservableList<TrainAndStation> lineinfo = FXCollections.observableArrayList();
            out.println(command);
            String fromServer = in.readLine();
            String[] separate = fromServer.split("%");
            if (separate[0].equals("NOCOMMON") && separate[1].equals("-9"))
                System.out.println("no train");
            else if (separate[0].equals("NOCOMMON") && (!separate[1].equals("-9"))){
                String info = separate[1];
                String[] lines = info.split("/");
                for (int i = 0; i < lines.length; i++) {
                    String[] line = lines[i].split(";");
                    TrainAndStation to_add = new TrainAndStation(line[0],line[1],Integer.parseInt(line[2]),line[3]);
                    lineinfo.add(to_add);
                }
                main.setLines(lineinfo);
                System.out.println(lineinfo.get(0).getTrain1());
                dialogStage.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("No direct train. We will recommend some lines to take");
                alert.showAndWait();
                main.showLines();
            }else {
                if (fromServer.equals("-4"))
                    System.out.println("no trains");
                else {
                    String[] trains = fromServer.split("/");
                    for (int i = 0; i < trains.length; i++) {
                        String[] info = trains[i].split(";");
                        TicketInfo to_add = new TicketInfo(info[0], info[1], info[2], info[3], info[4],
                                Integer.parseInt(info[7]), Integer.parseInt(info[5]), info[6],Double.parseDouble(info[8]));
                        ticketInfos.add(to_add);
                    }
                    main.setTicketInfos(ticketInfos);
                    dialogStage.close();
                    main.showTicketDisplay();
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleChangePass(){
        main.showChangePass();
    }

    @FXML
    private void handleLogOut(){
        dialogStage.close();
        main.showinit();
    }

    @FXML
    private void handlePay(){
        String command = "PAY;"+orderHistory.get(main.getOrderSelect()).getOrderID();
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
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("You have successfully buy the ticket");
                showHistory();
                alert.showAndWait();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDrop(){
        String command = "DROP;"+orderHistory.get(main.getOrderSelect()).getOrderID();
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
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("You have successfully drop the ticket");
                showHistory();
                alert.showAndWait();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public boolean isFinish(){
        return finish;
    }
}

