package hosman.login;

import hosman.dashboard.Dashboard;
import hosman.Database;
import hosman.Hospital;
import javafx.fxml.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class Login {
    private static final String title = new String("HosMan Login");
    private Dashboard dash;
    private Stage primaryStage;
    private Stage stage = new Stage();
    private Database db;
    private Hospital hospital;

    @FXML private Button btnLogin;
    @FXML private TextField txtUsername;
    @FXML private TextField txtPassword;

    public void alert(String message) {
        // display message box
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public void run(Database db, Stage primaryStage, Dashboard dash) {
        this.db = db;
        this.hospital = new Hospital(db);
        this.primaryStage = primaryStage;
        this.dash = dash;

        try {
            // load UI from fxml file and create a scene from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            loader.setController(this);
            Scene scene = new Scene(loader.load());

            // initialize some controls
            btnLogin.setDisable(true);

            // configure stage and show it
            stage.setTitle(title);
            stage.setResizable(true);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML private void onCredentialsType() {
        final String username = txtUsername.getText();
        final String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty())
            btnLogin.setDisable(true);
        else
            btnLogin.setDisable(false);
    }

    @FXML private void onLogin() {
        final String username = txtUsername.getText();
        final String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) return;

        try {
            if (db.checkCredentials(username, password)) {
                this.hospital.setCurrentUser(username);
                // login successful
                stage.close();
                dash.run(hospital, primaryStage);
            }
            else
                alert("Incorrect Username or Password");
        }
        catch (Exception e) { alert(e.getMessage()); /* this is a comment */}
    }
}
