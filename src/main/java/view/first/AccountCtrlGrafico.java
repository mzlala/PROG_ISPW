package view.first;

import controller.applicativo.AccountCtrlApplicativo;
import engineering.bean.*;
import engineering.others.Printer;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import view.first.utils.*;

import java.net.URL;
import java.util.*;

public class AccountCtrlGrafico<T extends ClientBean> implements Initializable {

    @FXML
    public Button saveButton;
    @FXML
    private Label usernameText;
    @FXML
    private Label emailText;

    @FXML
    private TableView<PlaylistBean> playlistTable;
    @FXML
    private TableColumn<PlaylistBean, String> playlistNameColumn;
    @FXML
    private TableColumn<PlaylistBean, List<String>> genreColumn;
    @FXML
    private TableColumn<PlaylistBean, Boolean> approveColumn;
    @FXML
    private TableColumn<PlaylistBean, String> linkColumn;

    @FXML
    private CheckBox pop;
    @FXML
    private CheckBox jazz;
    @FXML
    private CheckBox acoustic;
    @FXML
    private CheckBox indie;
    @FXML
    private CheckBox classic;
    @FXML
    private CheckBox house;
    @FXML
    private CheckBox hipHop;
    @FXML
    private CheckBox rock;
    @FXML
    private CheckBox electronic;
    @FXML
    private CheckBox reb;
    @FXML
    private CheckBox country;
    @FXML
    private CheckBox alternative;

    private T clientBean;
    private List<CheckBox> checkBoxList;
    private ObservableList<PlaylistBean> observableList;
    private SceneController sceneController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkBoxList = Arrays.asList(pop, indie, classic, rock, electronic, house, hipHop, jazz,
                acoustic, reb, country, alternative);
    }
    public void showUserInfo(){
        usernameText.setText(clientBean.getUsername());
        emailText.setText(clientBean.getEmail());
        List<String> preferences = clientBean.getPreferences();

        // Imposta le CheckBox in base alle preferenze del client
        GenreManager.setCheckList(preferences,checkBoxList);
    }

    public void setAttributes(T clientBean, SceneController sceneController) { // T è una classe che estende ClientBean -> UserBean o SupervisorBean
        this.clientBean = clientBean;
        this.sceneController = sceneController;

        Printer.logPrint(String.format("GUI Account setAttributes: %s", clientBean));

        // Inizializza i dati nella GUI
        showUserInfo();
        // Recupera e visualizza le playlist dell'utente
        retrivePlaylist();
    }

    /** Recupera tutte le playlist dell'utente */
    public void retrivePlaylist() {
        AccountCtrlApplicativo accountCtrlApplicativo = new AccountCtrlApplicativo();

        // Recupera le playlist dell'utente
        List<PlaylistBean> userPlaylists = accountCtrlApplicativo.retrievePlaylists(clientBean);

        // Imposto la struttura delle colonne della Table View
        List<TableColumn<PlaylistBean, ?>> columns = Arrays.asList(playlistNameColumn, linkColumn, approveColumn, genreColumn);
        List<String> nameColumns = Arrays.asList("playlistName", "link", "approved", "playlistGenre");
        TableManager.setColumnsTableView(columns, nameColumns);
        linkColumn.setCellFactory(button -> new SingleButtonTableCell());
        approveColumn.setCellFactory(button -> new ImageButtonTableCell());

        observableList = FXCollections.observableArrayList(userPlaylists);
        playlistTable.setItems(observableList);
    }

    /** Gestisce il click sul pulsante Salva */
    @FXML
    public void onSaveClick(ActionEvent event) {
        // Recupera le preferenze aggiornate dalle CheckBox
        List<String> preferences = GenreManager.retrieveCheckList(checkBoxList);

        // Aggiorna le preferenze nel bean del cliente
        clientBean.setPreferences(preferences);

        AccountCtrlApplicativo accountCtrlApplicativo = new AccountCtrlApplicativo();
        // Aggiorna le preferenze nel backend
        accountCtrlApplicativo.updateGenreUser(clientBean);

        // Mostra una notifica pop-up
        sceneController.textPopUp(event, MessageString.UPDATED_PREFERNCES, false);
    }

    /** Gestisce il click sul pulsante Indietro */
    @FXML
    public void onBackClick(ActionEvent event) {
        // Torna alla schermata precedente
        sceneController.goBack(event);
    }

    /** Gestisce il click sul pulsante Aggiungi Playlist */
    @FXML
    public void addPlaylistClick(ActionEvent event) {
        // Passa alla schermata di caricamento della playlist, passando il bean del client
        sceneController.goToScene(event, FxmlFileName.UPLOAD_PLAYLIST_FXML, clientBean, observableList);
    }
}
