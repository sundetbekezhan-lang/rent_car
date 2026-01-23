import controllers.UserController;
import controllers.interfaces.IUserController;
import data.PostgresDB;
import data.interfaces.IDB;
import repositories.UserRepository;
import repositories.interfaces.IUserRepository;

public class Main {

    public static void main(String[] args) {
        // Environment variables for secure database configuration
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");
        String dbName = System.getenv("DB_NAME");

        // Database connection setup
        MyApplication app = new MyApplication(controller);
        app.start();

        // Cleanup resources
        db.close();
    }
        IDB db = new PostgresDB(url, user, password, dbName);

        // Dependency Injection: Repository depends on DB, Controller depends on Repository
        IUserRepository repo = new UserRepository(db);
        IUserController controller = new UserController(repo);

        // Application entry point
}