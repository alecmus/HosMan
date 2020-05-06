package hosman;

import hosman.dashboard.Dashboard;
import hosman.login.Login;
import hosman.newuser.NewUser;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private static String databaseName = new String("database.dbs");
    private static String databasePassword = new String("");

    private Database db = new Database();
    private NewUser new_user = new NewUser();
    private Login login = new Login();
    private Dashboard dash = new Dashboard();

    public static void main(String[] args) { launch(args); }

    @Override // override the init method in the Application class
    public void init() {
        try {
            db.connect(databaseName, databasePassword);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override // override the start method in the Application class
    public void start(Stage primaryStage) {
        if (!db.hasUsers())
            new_user.run(db, primaryStage, dash, login);
        else
            login.run(db, primaryStage, dash);
    }

    @Override
    public void stop() { db.close(); }
}
