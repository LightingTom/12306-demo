package sample;

import Model.TicketInfo;
import Model.TrainAndStation;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class LineDisplayController {
    private Main main;
    private Stage dialogStage;

    @FXML
    private ListView<TrainAndStation> listView;

    @FXML
    private void initialize(){
        listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> main.setSelection(listView.getSelectionModel().getSelectedIndex()));
    }

    @FXML
    private void handleDetail(){
        main.setCurrentChoice(main.getLines().get(main.getSelection()));
        main.showDetail();
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
        main.showPassenger();
    }

    public void setMain(Main main){
        this.main = main;
        listView.setItems(main.getLines());
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
