package application;

import client.RestBookClient;
import controller.BookListController;
import controller.ImageFieldController;
import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.controlsfx.dialog.Dialogs;
import service.ScreenService;

import java.io.IOException;

/**
 * Created by pavel on 04.06.15.
 */
public class ClientApp extends Application implements Runnable {


    private Stage primaryStage;
    private static RestBookClient restBookClient;
    private static final Logger logger = Logger.getLogger(ClientApp.class);
    private ScreenService screenService;

    @Override
    public void start(Stage primaryStage) {
        try {
            this.primaryStage = primaryStage;
            screenService = new ScreenService();
            showBookListView();
          //   showLoginView();
        } catch (Exception e) {
            logger.error(e);
            Dialogs.create().title("Помилка").message("Сталась невідома помилка: " + e.getMessage()).showWarning();
        }
    }

    public void setClient(RestBookClient restBookClient) {
        this.restBookClient = restBookClient;
    }


    public void showImageView() throws IOException {

        final String fxmlFile = "/fxml/imageField.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
        ImageFieldController fieldController = loader.getController();
        fieldController.setRestBookClient(restBookClient);
        fieldController.setClientApp(this);
        restBookClient.setImageFieldController(fieldController);
        primaryStage.setTitle("ReaderCIT");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/fxml/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setHeight(screenService.getHeightForImageField());
        primaryStage.setWidth(screenService.getWidthForImageField());
        primaryStage.show();

    }

    public void showBookListView() throws IOException {
        final String fxmlFile = "/fxml/bookList.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
        BookListController bookListController = loader.getController();
        bookListController.setRestBookClient(restBookClient);
        bookListController.setClientApp(this);
        bookListController.init();
        primaryStage.setTitle("BookList");
        primaryStage.setScene(new Scene(root));
        primaryStage.setHeight(screenService.getHeightForBookList());
        primaryStage.setWidth(screenService.getWidthForBookList());
        primaryStage.show();
    }

    public void showLoginView() throws IOException {
        final String fxmlFile = "/fxml/login.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
        LoginController loginController = loader.getController();
        loginController.setRestBookClient(restBookClient);
        loginController.setClientApp(this);
        loginController.init();
        primaryStage.setTitle("Authorization");
        primaryStage.setScene(new Scene(root));
        primaryStage.setHeight(screenService.getHeightForloginView());
        primaryStage.setWidth(screenService.getWidthForloginView());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void run() {
        launch();
    }
}
