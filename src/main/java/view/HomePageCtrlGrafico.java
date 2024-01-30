package view;

import controllerApplicativo.HomePageCtrlApplicativo;
import engineering.bean.UserBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import start.MainApplication;

import java.io.IOException;

public class HomePageCtrlGrafico {

    @FXML
    private Button account, addButton;

    private UserBean userBean;

/*quando utilizzi fxmlLoader.load(), viene creato un nuovo controller associato al file FXML, e questo nuovo controller è quello che viene ottenuto con fxmlLoader.getController().

Prima della tua modifica, il codice sembrava cercare di impostare userBean direttamente nel controller corrente (quello che stai utilizzando nel metodo start). Tuttavia, questo non stava influenzando il nuovo controller associato alla vista appena creata, e quindi userBean rimaneva null nel nuovo controller.

Dopo la modifica, hai spostato la logica di impostazione di userBean direttamente nel nuovo controller associato alla vista. */
    public void start(Stage stage, UserBean user) throws IOException {
        if(user == null) {
            System.out.println("Guest mode, dovremmo rendere invisibili dei tasti");
            account.setVisible(false);
            addButton.setVisible(false);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/view/homePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Imposta il valore di userBean nel controller
        HomePageCtrlGrafico controller = fxmlLoader.getController();
        controller.setUserBean(user);

        stage.setResizable(false);
        stage.setTitle("Home Page");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    protected void onAccountClick() throws IOException, InterruptedException {
        System.out.println("HCG on Account Click: Bean: " + userBean);

        Stage stage = (Stage) account.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/view/account.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Ottieni l'istanza corrente del controller AddPlaylistCtrlGrafico
        AccountCtrlGrafico accountCtrlGrafico = fxmlLoader.getController();

        // Chiamare il metodo start del controller AddPlaylistCtrlGrafico
        accountCtrlGrafico.start(stage, userBean);
    }

    public void setUserBean(UserBean user) {
        this.userBean = user;
    }

    @FXML
    public void addPlaylistClick() throws IOException {
        Stage stage = (Stage) addButton.getScene().getWindow();
        AddPlaylistCtrlGrafico addPlaylist = new AddPlaylistCtrlGrafico();
        addPlaylist.start(stage, userBean);
    }
}
