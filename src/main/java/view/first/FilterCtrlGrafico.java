package view.first;

import engineering.bean.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import view.first.utils.*;

import java.net.URL;
import java.util.*;

public class FilterCtrlGrafico implements Initializable {

    @FXML
    private Slider electronicAcoustic;
    @FXML
    private Slider speakInstrumental;
    @FXML
    private Slider happySad;
    @FXML
    private Slider danceChill;

    @FXML
    private CheckBox pop;
    @FXML
    private CheckBox reb;
    @FXML
    private CheckBox acoustic;
    @FXML
    private CheckBox rock;
    @FXML
    private CheckBox country;
    @FXML
    private CheckBox alternative;
    @FXML
    private CheckBox hipHop;
    @FXML
    private CheckBox indie;
    @FXML
    private CheckBox classic;
    @FXML
    private CheckBox jazz;
    @FXML
    private CheckBox electronic;
    @FXML
    private CheckBox house;
    boolean applyFilter = false;

    private PlaylistBean playlistBean;
    private List<CheckBox> checkBoxList;
    private HomePageCtrlGrafico<?> homeController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkBoxList = Arrays.asList(pop, indie, classic, rock, electronic, house, hipHop, jazz,
                acoustic, reb, country, alternative);
    }

    public void setHomeInstance(HomePageCtrlGrafico<?> homeController){
        this.homeController = homeController;
    }

    /** Viene utilizzata da sceneController per impostare lo userBean e l'istanza di Scene controller da utilizzare */
    public void setAttributes(PlaylistBean playlistBean) {
        // Deve avere un userBean per compilare tutte le informazioni
        this.playlistBean = playlistBean;
        setData();
    }

    @FXML
    private void onApplyClick(ActionEvent event) {
        // Devo modificare i campi del PlaylistBean
        List<String> genre = GenreManager.retrieveCheckList(checkBoxList);

        List<Integer> sliderValues = List.of(
                (int) happySad.getValue(),
                (int) danceChill.getValue(),
                (int) electronicAcoustic.getValue(),
                (int) speakInstrumental.getValue()
        );

        playlistBean.setPlaylistGenre(genre);
        playlistBean.setEmotional(sliderValues);

        applyFilter = !checkEmptyFields();
        homeController.setFilterApplied(applyFilter);
        homeController.onSearchPlaylistClick();

        // Chiudi il popup
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void setData(){
        List<String> preferences = playlistBean.getPlaylistGenre();
        List<Integer> emotional = playlistBean.getEmotional();

        if(preferences != null){
            GenreManager.setCheckList(preferences,checkBoxList);
        }

        if(emotional != null){
            happySad.setValue(playlistBean.getEmotional().get(0));
            danceChill.setValue(playlistBean.getEmotional().get(1));
            electronicAcoustic.setValue(playlistBean.getEmotional().get(2));
            speakInstrumental.setValue(playlistBean.getEmotional().get(3));
        } else {
            happySad.setValue(0.0);
            danceChill.setValue(0.0);
            electronicAcoustic.setValue(0.0);
            speakInstrumental.setValue(0.0);
        }
    }

    private boolean checkEmptyFields() {
        return happySad.getValue() == 0.0 &&
                danceChill.getValue() == 0.0 &&
                electronicAcoustic.getValue() == 0.0 &&
                speakInstrumental.getValue() == 0.0 &&
                checkBoxList.stream().noneMatch(CheckBox::isSelected);
    }

    @FXML
    public void onResetClick() {
        List<String> genre = new ArrayList<>();

        List<Integer> emotional = List.of(0,0,0,0);

        playlistBean.setEmotional(emotional);
        playlistBean.setPlaylistGenre(genre);

        setData();
        applyFilter = false;
    }
}
