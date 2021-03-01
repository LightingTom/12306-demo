package sample;

import Model.Order;
import Model.TicketInfo;
import Model.TrainAndStation;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public Stage primaryStage;
    private int selection = -1;
    private int orderSelect = -1;
    private AnchorPane initial;
    private ObservableList<TicketInfo> ticketInfos = FXCollections.observableArrayList();
    private ObservableList<TrainAndStation> lines = FXCollections.observableArrayList();
    private TrainAndStation currentChoice;
    private String d_s;
    private String a_s;
    private String dateChoice;
    private int ticketChoice1;
    private int ticketChoice2;
    private int action;

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getTicketChoice1() {
        return ticketChoice1;
    }

    public void setTicketChoice1(int ticketChoice1) {
        this.ticketChoice1 = ticketChoice1;
    }

    public int getTicketChoice2() {
        return ticketChoice2;
    }

    public void setTicketChoice2(int ticketChoice2) {
        this.ticketChoice2 = ticketChoice2;
    }

    public int getOrderSelect() {
        return orderSelect;
    }

    public void setOrderSelect(int orderSelect) {
        this.orderSelect = orderSelect;
    }

    public String getD_s() {
        return d_s;
    }

    public void setD_s(String d_s) {
        this.d_s = d_s;
    }

    public String getA_s() {
        return a_s;
    }

    public void setA_s(String a_s) {
        this.a_s = a_s;
    }

    public String getDateChoice() {
        return dateChoice;
    }

    public void setDateChoice(String dateChoice) {
        this.dateChoice = dateChoice;
    }

    public TrainAndStation getCurrentChoice() {
        return currentChoice;
    }

    public void setCurrentChoice(TrainAndStation currentChoice) {
        this.currentChoice = currentChoice;
    }

    public ObservableList<TrainAndStation> getLines() {
        return lines;
    }

    public void setLines(ObservableList<TrainAndStation> lines) {
        this.lines = lines;
    }

    private String currentUserID;

    public int getCurrentOrderID() {
        return currentOrderID;
    }

    public void setCurrentOrderID(int currentOrderID) {
        this.currentOrderID = currentOrderID;
    }

    private int currentOrderID;

    public void setCurrentUserID(String UID){this.currentUserID = UID;}

    public String getCurrentUserID(){return currentUserID;}

    public void setSelection(int selection){
        this.selection = selection;
    }

    public int getSelection(){
        return selection;
    }

    public void setTicketInfos(ObservableList<TicketInfo> ticketInfos){
        this.ticketInfos = ticketInfos;
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;

        primaryStage.setTitle("12306");
        showinit();
    }

    public ObservableList<TicketInfo> getTicketInfos(){
        return ticketInfos;
    }

    public void showinit(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("InitScene.fxml"));
            initial = (AnchorPane) loader.load();
            Scene scene = new Scene(initial);
            primaryStage.setScene(scene);
            // Give the controller access to the main app.
            InitSceneController controller = loader.getController();
            controller.setMain(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean showLogIn(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("Load.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("LogIn");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);


            LoadController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMain(this);

            dialogStage.show();

            return controller.isFinish();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean showSignUp(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("SignUp.fxml"));
            GridPane pane = (GridPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Sign Up");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            SignUpController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            controller.setMain(this);
            dialogStage.showAndWait();

            return controller.isFinish();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showPassenger(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("Passenger.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("12306");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            PassengerController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            controller.setMain(this);
            dialogStage.show();

            return controller.isFinish();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showTicketDisplay(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("TicketDisplay.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Tickets");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            TicketDisplayController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            controller.setMain(this);
            dialogStage.show();

            return controller.isFinish();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showTicket(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("Ticket.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ticket");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            TicketController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            controller.setMain(this);
            dialogStage.show();

            return controller.isFinish();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showChangePass(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("ChangePassword.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Change password");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            ChangePasswordController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            controller.setMain(this);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLines(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("LineDisplay.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Line Recommendation");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            LineDisplayController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            controller.setMain(this);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDetail(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("LineDetail.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Line Detail");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            LineDetailController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            controller.setMain(this);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showManager(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("Manager.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Manager");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            ManagerController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            controller.setMain(this);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLineManager(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("LineManager.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("LineManager");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            LineManagerController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            controller.setMain(this);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
