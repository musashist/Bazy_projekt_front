package sample;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import interfaces.*;
import model.Pracownik;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javafx.collections.FXCollections;
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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import model.*;
import sun.java2d.pipe.SpanClipRenderer;

import javax.smartcardio.Card;
import interfaces.PracownikMapper;
import model.Pracownik;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
public class Controller implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    TabPane tableTabPane,updateTabPane,addTabPane;

    @FXML
    TableView<Pracownik> tabela1;//,tableLawsuit,tableJudgement,tableCriminal,tablePerson,tableEvidence,tableWitness;
    @FXML
    TableView<Sprawa> tableLawsuit;
    @FXML
    TableView<Wyrok> tableJudgement;
    @FXML
    TableView<Oskarzony> tableCriminal;
    @FXML
    TableView<Dowod> tableEvidence;
    @FXML
    TableView<Swiadek> tableWitness;

    @FXML
    TableColumn<Pracownik, String> workerName,workerSurname,workerRole;
    @FXML
    TableColumn<Pracownik, Integer> workerCardId;

    @FXML
    TableColumn<Sprawa, Integer> lawsuitId;
    @FXML
    TableColumn<Sprawa, BigDecimal> courtroomId,prokuratorCardId,sedziaCardId;
    @FXML
    TableColumn<Sprawa, Date> lawsuitStartDate,lawsuitEndDate;

    @FXML
    TableColumn<Wyrok,String> judgementContent,judgement,suspence;
    @FXML
    TableColumn<Wyrok,Integer> judgementId;
    @FXML
    TableColumn<Wyrok,BigDecimal> judgementLawsuitId;

    @FXML
    TableColumn<Oskarzony,String> crimeCategory,codexArticle;
    @FXML
    TableColumn<Oskarzony,Integer> criminalId,obroncaCardId;
    @FXML
    TableColumn<Oskarzony,BigDecimal> criminalLawsuitId,criminalPesel;

    @FXML
    TableColumn<Dowod,String> evidenceName,evidenceType;
    @FXML
    TableColumn<Dowod,Integer> evidenceId;
    @FXML
    TableColumn<Dowod,BigDecimal> evLawsuitId;

    @FXML
    TableColumn<Swiadek,String> witnessName,witnessSurname,witnessProfession;
    @FXML
    TableColumn<Swiadek, BigDecimal> witnessLawsuitId;
    @FXML
    TableColumn<Swiadek, Integer> witnessId;

    @FXML
    TextField searchfield;



    @FXML
    Tab tabLawsuit;

    PracownikMapper pracownikMapper=null;

    SwiadekMapper swiadekMapper=null;

    WyrokMapper wyrokMapper=null;

    SprawaMapper sprawaMapper=null;

    OskarzonyMapper oskarzonyMapper=null;

    DowodMapper dowodMapper=null;


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

    //tworzy obiekt z formularza do dodawania lub update'owania
    public ArrayList<String> confirmButtonAction3(ActionEvent event){
        Button btn = (Button) event.getSource();
        Node tabpan = btn.getParent().getParent().getParent().getParent();
        TabPane addTabpan = (TabPane) tabpan;
        int addIndex = addTabpan.getSelectionModel().getSelectedIndex();


        System.out.println(addIndex);
        Stage stage = (Stage) btn.getScene().getWindow();
        ArrayList<String> fromTextFields = new ArrayList<String>();
        for (Node node : btn.getParent().getChildrenUnmodifiable()) {
            if(node instanceof TextField) {
                fromTextFields.add(((TextField) node).getText());
            }

        }
        convert(fromTextFields,addIndex);

        stage.close();
        return fromTextFields;
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
            case 5:
                tableEvidence.getItems().removeAll(tableEvidence.getSelectionModel().getSelectedItem());
                break;
            case 6:
                tableWitness.getItems().removeAll(tableWitness.getSelectionModel().getSelectedItem());
                break;
        }
    }

    //otwiera okno do wartosci update
    //update konczy sie rowniez convertem, wiec tam zarzadzac obiektem w bazie
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
    public void convert(ArrayList<String> listOfInputs, int index){
        switch (index) {
            case 0:
                Pracownik worker1 = new Pracownik();
                worker1.setName(listOfInputs.get(0));
                worker1.setSurname(listOfInputs.get(1));
                worker1.setCardId(Integer.parseInt(listOfInputs.get(2)));
                worker1.setRole(listOfInputs.get(3));
                System.out.println(worker1.getName());
                //tutaj kod dodajacy ziomala do tabeli
                break;
            case 1:
                SimpleDateFormat parse = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
                Sprawa lawsuit1 = new Sprawa();

                lawsuit1.setLawsuitId(3);
                lawsuit1.setCourtroomId(new BigDecimal(listOfInputs.get(0)));
                try {
                    lawsuit1.setLawsuitStartDate(parse.parse(listOfInputs.get(1)));
                    lawsuit1.setLawsuitEndDate(parse.parse(listOfInputs.get(2)));
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
                lawsuit1.setProkuratorCardId(new BigDecimal(listOfInputs.get(3)));
                lawsuit1.setSedziaCardId(new BigDecimal(listOfInputs.get(4)));

                System.out.println(lawsuit1.getCourtroomId());
                break;
            case 2:
                Wyrok judgement1= new Wyrok();
                judgement1.setContent(listOfInputs.get(0));
                judgement1.setJudgement(listOfInputs.get(1));
                judgement1.setSuspence(listOfInputs.get(2));
                judgement1.setLawsuitId(new BigDecimal(listOfInputs.get(3)));
                judgement1.setJudgementId(1);
                System.out.println(judgement1.getContent());
                break;
            case 3:
                Oskarzony criminal1= new Oskarzony();
                criminal1.setCrimeCategory(listOfInputs.get(0));
                criminal1.setPesel(new BigDecimal(listOfInputs.get(1)));
                criminal1.setLawsuitId(new BigDecimal(listOfInputs.get(2)));
                criminal1.setObroncaCardId(Short.parseShort(listOfInputs.get(3)));
                criminal1.setCodexArticle(listOfInputs.get(4));
                criminal1.setCriminalId(1);
                break;
            case 4:
                System.out.println("Tu bylaby osoba gdyby fizycznie wystepowala");
                break;
            case 5:
                Dowod evidence1 = new Dowod();
                evidence1.setName(listOfInputs.get(0));
                evidence1.setType(listOfInputs.get(1));
                evidence1.setLawsuitId(new BigDecimal(listOfInputs.get(2)));
                evidence1.setEvidenceId(1);
                System.out.println(evidence1.getType());
                break;
            case 6:
                Swiadek witness1= new Swiadek();
                witness1.setName(listOfInputs.get(0));
                witness1.setSurname(listOfInputs.get(1));
                witness1.setProfession(listOfInputs.get(2));
                witness1.setLawsuitId(new BigDecimal(listOfInputs.get(3)));
                witness1.setWitnessId(1);
                break;
        }
    }

    //ogolnie sytuacja jest taka, ze potrzeba listy obiektow do wstawienia
    //i jak sie dostarczy tej metodzie, idk czy jakies globalne listy maja byc z obiektami z tego sqla
    // to po odkomentowaniu ostatniej linijki w kazdym case'sie zostaną dodane
    public void fillTable(){
        inicjalizacjaMapperow();
        tableTabPane.getTabs();
        int index = tableTabPane.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        switch (index) {
            case 0:
                workerName.setCellValueFactory(new PropertyValueFactory<>("name"));
                workerSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
                workerCardId.setCellValueFactory(new PropertyValueFactory<>("cardId"));
                workerRole.setCellValueFactory(new PropertyValueFactory<>("role"));

                List<Pracownik> pracownicy=pracownikMapper.selectAll();
                ObservableList<Pracownik> listaPracow = FXCollections.observableArrayList(pracownicy);
                tabela1.getItems().clear();
                tabela1.setItems(listaPracow);
                /*
                tabela1.getItems().clear();
                tabela1.setItems(listaObiektow);
                 */



                break;
            case 1:
                lawsuitId.setCellValueFactory(new PropertyValueFactory<>("lawsuitId"));
                courtroomId.setCellValueFactory(new PropertyValueFactory<>("courtroomId"));
                prokuratorCardId.setCellValueFactory(new PropertyValueFactory<>("prokuratorCardId"));
                sedziaCardId.setCellValueFactory(new PropertyValueFactory<>("sedziaCardId"));
                lawsuitStartDate.setCellValueFactory(new PropertyValueFactory<>("lawsuitStartDate"));
                lawsuitEndDate.setCellValueFactory(new PropertyValueFactory<>("lawsuitEndDate"));

                List<Sprawa> sprawy=sprawaMapper.selectAll();
                ObservableList<Sprawa> listaSpraw = FXCollections.observableArrayList(sprawy);
                tableLawsuit.getItems().clear();
                tableLawsuit.setItems(listaSpraw);
                break;
            case 2:
                judgementContent.setCellValueFactory(new PropertyValueFactory<>("content"));
                judgement.setCellValueFactory(new PropertyValueFactory<>("judgement"));
                suspence.setCellValueFactory(new PropertyValueFactory<>("suspence"));
                judgementId.setCellValueFactory(new PropertyValueFactory<>("judgementId"));
                judgementLawsuitId.setCellValueFactory(new PropertyValueFactory<>("lawsuitId"));
                List<Wyrok> wyroks=wyrokMapper.selectAll();
                ObservableList<Wyrok> listawyroks = FXCollections.observableArrayList(wyroks);
                tableJudgement.getItems().clear();
                tableJudgement.setItems(listawyroks);
                break;
            case 3:
                crimeCategory.setCellValueFactory(new PropertyValueFactory<>("crimeCategory"));
                codexArticle.setCellValueFactory(new PropertyValueFactory<>("codexArticle"));
                criminalId.setCellValueFactory(new PropertyValueFactory<>("criminalId"));
                obroncaCardId.setCellValueFactory(new PropertyValueFactory<>("obroncaCardId"));
                criminalLawsuitId.setCellValueFactory(new PropertyValueFactory<>("lawsuitId"));
                criminalPesel.setCellValueFactory(new PropertyValueFactory<>("pesel"));
                List<Oskarzony> oskarzonys=oskarzonyMapper.selectAll();
                ObservableList<Oskarzony> listaOskarzony = FXCollections.observableArrayList(oskarzonys);
                tableCriminal.getItems().clear();
                tableCriminal.setItems(listaOskarzony);
                break;
            case 5:
                evidenceName.setCellValueFactory(new PropertyValueFactory<>("name"));
                evidenceType.setCellValueFactory(new PropertyValueFactory<>("type"));
                evidenceId.setCellValueFactory(new PropertyValueFactory<>("evidenceId"));
                evLawsuitId.setCellValueFactory(new PropertyValueFactory<>("lawsuitId"));

                List<Dowod> dowods=dowodMapper.selectAll();
                ObservableList<Dowod> listadowod = FXCollections.observableArrayList(dowods);
                tableEvidence.getItems().clear();
                tableEvidence.setItems(listadowod);
                break;
            case 6:
                witnessName.setCellValueFactory(new PropertyValueFactory<>("name"));
                witnessSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
                witnessProfession.setCellValueFactory(new PropertyValueFactory<>("profession"));
                witnessLawsuitId.setCellValueFactory(new PropertyValueFactory<>("lawsuitId"));
                witnessId.setCellValueFactory(new PropertyValueFactory<>("witnessId"));
                List<Swiadek> swiadeks=swiadekMapper.selectAll();
                ObservableList<Swiadek> listaswiadek = FXCollections.observableArrayList(swiadeks);
                tableWitness.getItems().clear();
                tableWitness.setItems(listaswiadek);
                break;
        }
    }
    private void inicjalizacjaMapperow(){
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        pracownikMapper=session.getMapper(PracownikMapper.class);
        swiadekMapper=session.getMapper(SwiadekMapper.class);
        dowodMapper=session.getMapper(DowodMapper.class);
        oskarzonyMapper=session.getMapper(OskarzonyMapper.class);
        wyrokMapper=session.getMapper(WyrokMapper.class);
        sprawaMapper=session.getMapper(SprawaMapper.class);
    }

}


