package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.TextFieldTableCell;

public class Controller {
    @FXML
    private TableView<TextRobot> tableView;
    @FXML
    private TableColumn<TextRobot, String> col;
    @FXML
    private Button buttonCorrectText;
    @FXML
    private Button buttonAddRow;
    @FXML
    private TextArea textArea;
    private int count = 0;
    ObservableList<TextRobot> items = FXCollections.observableArrayList();
    public void initialize() {
        tableView.itemsProperty().setValue(items);
        tableView.setEditable(true);
        int countRow = 10;
        for (int i = 0; i < countRow; i++) {
            items.add(new TextRobot(""));
        }
        col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getWord()));
        col.setCellFactory(TextFieldTableCell.forTableColumn());
        col.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TextRobot, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TextRobot, String> event) {
                ((TextRobot) event.getTableView().getItems().get(event.getTablePosition().getRow())).setWord(event.getNewValue());
            }
        });
        String TEXT = "Наступила весна. Ну, многие конечно скажут, дескать, время любви и мечтаний, время расцветания и добра. Цветочки на деревьях растут. Ну, вообщем, тепло и радостно на душе. \n" +
                "Но, все это на деле не так. Знаете чье приходит время? Нет? Не догадались? Наших старых, мать их, друзей, комаров. Премерзкие, отвратительные, мелкие жужжащие твари. Визгливые кровососы. Знаете почему я не люблю городских комаров? Они почему-то ужасные гурманы. Подвальные любят кусать в руки, комнатные в ноги и в задницу. А уличные предпочитают переднюю часть черепа. Деревенские хотя бы более неразборчивы. Да и убивать их премного легче.\n" +
                "Но думаете, они пришли сами? Конечно же нет! Они привели с собой друзей! Мошек, таракашек, лягушку, мух, а еще какую-то неведомую науке *банину, размером с мизинец и орущую при включении света. А еще премилых червяков и сороконожек. Но ни хотя бы тихие.";
        textArea.setText(TEXT);
    }
    public void buttonCorrectTextAction() {
        String[] arr = textArea.getText().split(" ");
        String result = "";
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].contains("$")) {
                String[] arrLetters = arr[i].split("");
                for (int j = 0; j < arrLetters.length; j++) {
                    if (arrLetters[j].equals("$")) {
                        arrLetters[j] = items.get(count).getWord();
                        count++;
                    }
                }
                arr[i] = "";
                for (int j = 0; j < arrLetters.length; j++) {
                    arr[i] += arrLetters[j];
                }
            }
        }
        for (int j = 0; j < arr.length; j++) {
            result += arr[j] + " ";
        }
        textArea.setText(result);

        for (int i = 0; i < items.size(); i++) {
            items.get(i).setWord("");
        }
        count = 0;
        tableView.refresh();
    }
    public void buttonAddRowAction() {
        items.add(new TextRobot(""));
    }
}