package view;

import controller.applicativo.LoginCtrlApplicativo;
import engineering.bean.*;
import engineering.others.FxmlFileName;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.commons.validator.routines.EmailValidator;
import java.io.IOException;

public class LoginCtrlGrafico {

    @FXML
    private Label errorLabel;
    @FXML
    private PasswordField password;
    @FXML
    private TextField username;

    @FXML
    protected void onLoginClick(ActionEvent event) throws IOException {

        /* ------ Recupero informazioni dalla schermata di login ------ */
        String email = username.getText();
        String pass = password.getText();

        /* ------ Verifica dei parametri inseriti (validità sintattica) ------ */
        if (!checkMailCorrectness(email)){
            errorLabel.isVisible();
            errorLabel.setText("Email non valida");
        } else {
            /* ------ Creo la bean e imposto i parametri ------ */
            LoginBean loginBean = new LoginBean(email,pass);

            /* ------ Creo istanza del Login controller applicativo e utilizzo i metodi di verifica credenziali ------ */
            LoginCtrlApplicativo loginCtrlApp = new LoginCtrlApplicativo(); //Dovrebbe essere static

            if (loginCtrlApp.verificaCredenziali(loginBean)) {
                /* --------------- Credenziali corrette -------------- */
                System.out.println("CREDENZIALI CORRETTE -> Recupero l'istanza di bean ");

                UserBean userBean = loginCtrlApp.loadUser(loginBean);
                //################### Inserire il caricamento da FS ############

                if(userBean != null){
                    //userBean.setRegistered(); // Indica che l'utente con cui sto accedendo è registrato
                    System.out.println("Utente registrato, ho recuperato tutto lo user bean");

                    /* --------------- Mostro la home page -------------- */
                    SceneController.getInstance().<HomePageCtrlGrafico>goToScene(event, FxmlFileName.HOME_PAGE_FXML,userBean);

                }
            } else { /* --------------- Credenziali non valide --------------*/
                errorLabel.isVisible();
                errorLabel.setText("Credenziali errate");
            }
        }
    }
    @FXML
    protected void onRegisterClick(ActionEvent event) throws IOException {
        //Push della scena corrente nello stack delle scene e show() della scena seguente
        SceneController.getInstance().goToScene(event, FxmlFileName.REGISTRATION_FXML);
    }
    @FXML
    protected void onGuestClick(ActionEvent event) throws IOException {
        SceneController.getInstance().<HomePageCtrlGrafico>goToScene(event, FxmlFileName.HOME_PAGE_FXML,null);
    }

    private boolean checkMailCorrectness(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

}