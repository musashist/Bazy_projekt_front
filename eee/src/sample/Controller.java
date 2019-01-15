package sample;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.stage.Modality;
import com.sun.rowset.internal.Row;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.Node;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.Event;
import javafx.scene.Parent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.SelectionModel;
import java.util.List;
import javafx.collections.ObservableList;

public class Controller implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    TabPane tableTabPane,updateTabPane;

    @FXML
    TableView<Worker> tabela1,tableLawsuit,tableJudgement,tableCriminal,tablePerson,tableEvidence,tableWitness;
    @FXML
    TableColumn<Worker, String> Imie,Nazwisko;

    @FXML
    TextField searchfield,Namex,surnamex,dupa;
    @FXML
    TableColumn<Worker, String> Cardid;

    @FXML
    Tab tabLawsuit;


    @Override
    public void initialize(URL location, ResourceBundle resources){
    }

    //otwiera okno do dodawania nowej krotki
    public void handle(ActionEvent event) throws Exception{
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample4.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("ABC");
            stage.setScene(new Scene(root1));
            stage.show();
        }catch( Exception e){
            System.out.println("dupaa");
        }
    }

    //zwraca z okien odpowiedzialnych za dodawanie krotek liste z wartosciami z textfieldow
    public ArrayList<String> confirmButtonAction2(ActionEvent event){
        Button btn = (Button) event.getSource();
        Stage stage = (Stage) btn.getScene().getWindow();
        ArrayList<String> fromTextFields = new ArrayList<String>();
        for (Node node : btn.getParent().getChildrenUnmodifiable()) {
            if(node instanceof TextField) {
                fromTextFields.add(((TextField) node).getText());
            }

        }
        System.out.println(fromTextFields);
        //tabPane.getSelectionModel().getSelectedIndex()
        stage.close();
        return fromTextFields;
    }

    //dodaje do 1 tabeli 2 wiersze - do testow
    public void addToTable(ActionEvent event){
        //tabela1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Imie.setCellValueFactory(new PropertyValueFactory<>("Imie"));
        Nazwisko.setCellValueFactory(new PropertyValueFactory<>("Nazwisko"));
        Worker person1 = new Worker("John","Smiths");
        Worker person2 = new Worker("Terry","Fuck");
        tabela1.getItems().add(person1);
        tabela1.getItems().add(person2);
        System.out.println(tabela1.getItems());

    }

    //wybiera pierwszy wiersz z tabeli - tylko do testow
    public void selectFirst(ActionEvent event){
        tabela1.getSelectionModel().selectFirst();
    }

    //usuwa z tabeli wiersz obecnie wybrany
    public void delete(ActionEvent event){
        tableTabPane.getTabs();
        int index = tableTabPane.getSelectionModel().getSelectedIndex();
        switch (index) {
            case 0:
                tabela1.getItems().removeAll(tabela1.getSelectionModel().getSelectedItem());
                break;
            case 1:
                tableLawsuit.getItems().removeAll(tableLawsuit.getSelectionModel().getSelectedItem());
                break;
            case 2:
                tableJudgement.getItems().removeAll(tableJudgement.getSelectionModel().getSelectedItem());
                break;
            case 3:
                tableCriminal.getItems().removeAll(tableCriminal.getSelectionModel().getSelectedItem());
                break;
            case 4:
                tablePerson.getItems().removeAll(tablePerson.getSelectionModel().getSelectedItem());
                break;
            case 5:
                tableEvidence.getItems().removeAll(tableEvidence.getSelectionModel().getSelectedItem());
                break;
            case 6:
                tableWitness.getItems().removeAll(tableWitness.getSelectionModel().getSelectedItem());
                break;
        }
    }

    //otwiera okno do wartosci update
    public void update2(ActionEvent event){
        try{
            tableTabPane.getTabs();
            int index = tableTabPane.getSelectionModel().getSelectedIndex();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample5.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("ABC");
            stage.setScene(new Scene(root1));
            stage.getScene().getWindow();

            Node node1 = root1.getChildrenUnmodifiable().get(0);
            TabPane updateT1 = (TabPane) node1;
            SingleSelectionModel<Tab> selectionModel = updateT1.getSelectionModel();
            selectionModel.select(index);
            updateT1.getTabs().get(index).setDisable(false);
            stage.show();


        }catch( Exception e){
            e.printStackTrace();
        }
    }

    public String search(ActionEvent event){
        System.out.println(searchfield.getText());
        return searchfield.getText();
    }


    //te 2 to poki co syf
    public void down(ActionEvent event){
        tabela1.getSelectionModel().selectPrevious();
    }
    public void up(ActionEvent event){
        tabela1.getSelectionModel().selectNext();
    }

}


