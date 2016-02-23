import application.ClientApp;
import client.RestBookClient;
import org.apache.log4j.Logger;
import org.controlsfx.dialog.Dialogs;

/**
 * Created by pc8 on 10.08.15.
 */
public class MainClass {
    private static final Logger logger = Logger.getLogger(MainClass.class);

    public static void main(String[] args) {
        logger.trace("Welcome to restBookClient side");
        try {
            RestBookClient restBookClient = new RestBookClient();
            ClientApp clientApp = new ClientApp();
            clientApp.setClient(restBookClient);
            Thread thread = new Thread(clientApp);
            thread.start();
        } catch (Exception e) {
            logger.error(e);
            Dialogs.create().title("Неизвестная ошибка").message("Произошла ошибка: " + e.getMessage() + "Обратитесь к разрабочику");
        }
    }
}
