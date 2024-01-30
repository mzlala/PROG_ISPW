package view;

import engineering.bean.UserBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import start.MainApplication;

import java.io.IOException;
import java.util.ArrayList;

public class AccountCtrlGrafico {
    public Button saveButton;
    @FXML
    private Label usernameText, supervisorText, emailText;
    @FXML
    private Text username;
    @FXML
    private Button back, addButton;
    @FXML private CheckBox pop, indie, classic, rock, electronic, house, hipHop, jazz,
            acoustic, reb, country, alternative;


    private UserBean userBean;

    public void setUserBean(UserBean user) {
        // Deve avere un userBean per compilare tutte le informazioni
        this.userBean = user;
        System.out.println("ACG setUserBean: " + this.userBean);
    }


    public void start(Stage stage, UserBean user) throws IOException {
        System.out.println("ACG ho ricevuto " + user + " lo imposto");

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/view/account.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Imposta il valore di userBean nel controller
        AccountCtrlGrafico controller = fxmlLoader.getController(); //Returns the controller associated with the root object.
        controller.setUserBean(user);

        stage.setResizable(false);
        stage.setTitle("My Account");
        stage.setScene(scene);
        stage.show();
    }

    public void inizializeData(){
        usernameText.setText(userBean.getUsername());
        supervisorText.setText("FALSE");
        emailText.setText(userBean.getEmail());

        ArrayList<String> preferences = userBean.getPreferences();
        pop.setSelected(preferences.contains("Pop"));
        indie.setSelected(preferences.contains("Indie"));
        classic.setSelected(preferences.contains("Classic"));
        rock.setSelected(preferences.contains("Rock"));
        electronic.setSelected(preferences.contains("Electronic"));
        house.setSelected(preferences.contains("House"));
        hipHop.setSelected(preferences.contains("Hip Hop"));
        jazz.setSelected(preferences.contains("Jazz"));
        acoustic.setSelected(preferences.contains("Acoustic"));
        reb.setSelected(preferences.contains("REB"));
        country.setSelected(preferences.contains("Country"));
        alternative.setSelected(preferences.contains("Alternative"));
    }


    @FXML
    public void onSaveClick() {
    }

    @FXML
    public void onBackClick() throws IOException {
        System.out.println("AGC on Back Click: Bean: " + userBean);

        Stage stage = (Stage) back.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/view/homePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Ottieni l'istanza corrente del controller HomePageCtrlGrafico
        HomePageCtrlGrafico homeController = fxmlLoader.getController();

        // Chiamare il metodo start del controller HomePageCtrlGrafico
        homeController.start(stage, userBean);
    }


    @FXML
    public void addPlaylistClick() throws IOException {
        Stage stage = (Stage) addButton.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/view/addPlaylist.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Ottieni l'istanza corrente del controller AddPlaylistCtrlGrafico
        AddPlaylistCtrlGrafico addPlaylistController = fxmlLoader.getController();

        // Chiamare il metodo start del controller AddPlaylistCtrlGrafico
        addPlaylistController.start(stage, userBean);
    }
}
