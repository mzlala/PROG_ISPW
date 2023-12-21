package start;

import view.LoginCtrlGrafico;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        LoginCtrlGrafico loginCtrlGrafico = new LoginCtrlGrafico();
        loginCtrlGrafico.start(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}