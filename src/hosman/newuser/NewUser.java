package hosman.newuser;

import hosman.dashboard.Dashboard;
import hosman.Database;
import hosman.login.Login;
import javafx.fxml.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class NewUser {
    private static final String title = new String("HosMan New User");
    private Database db;
    private Stage stage = new Stage();
    private Stage primaryStage;
    private Dashboard dash;
    private Login login;

    @FXML private Button btnSave;
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmPassword;
    @FXML private Label lblPasswordStatus;

    public void alert(Alert.AlertType type, String header, String contextText) {
        // display message box
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

    public void run(Database db, Stage primaryStage, Dashboard dash, Login login) {
        this.db = db;
        this.primaryStage = primaryStage;
        this.dash = dash;
        this.login = login;

        try {
            // load UI from fxml file and create a scene from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewUser.fxml"));
            loader.setController(this);
            Scene scene = new Scene(loader.load());

            // initialize controls
            lblPasswordStatus.setText("");
            btnSave.setDisable(true);

            // configure the stage and show it
            stage.setTitle(title);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML private void onPasswordType() {
        final String password = txtPassword.getText();
        final String confirmPassword = txtConfirmPassword.getText();

        if (!password.isEmpty() || !confirmPassword.isEmpty()) {
            if (password.equals(confirmPassword)) {
                lblPasswordStatus.setText("Passwords match");
                btnSave.setDisable(false);
            }
            else {
                lblPasswordStatus.setText("Passwords DO NOT match");
                btnSave.setDisable(true);
            }
        }
        else {
            lblPasswordStatus.setText("");
            btnSave.setDisable(true);
        }
    }

    @FXML private void onSave() {
        final String username = txtUsername.getText();
        final String password = txtPassword.getText();
        final String confirmPassword = txtConfirmPassword.getText();

        if (username.isEmpty() || password.isEmpty() || !password.equals(confirmPassword)) return;

        // save user details to database
        db.newUser(username, password);
        stage.close();
        login.run(db, primaryStage, dash);
    }
}
