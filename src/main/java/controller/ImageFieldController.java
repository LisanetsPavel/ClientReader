package controller;

import application.ClientApp;
import client.RestBookClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.log4j.Logger;
import org.controlsfx.dialog.Dialogs;
import service.ScreenService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by pavel on 08.06.15.
 */
public class ImageFieldController implements Initializable {


    @FXML
    private ImageView imv = new ImageView();

    @FXML
    private Button buttonPrev;

    @FXML
    private Button buttonNext;

    @FXML
    private Button buttonMenu;

    private RestBookClient restBookClient;
    private ClientApp clientApp;
    private ScreenService screenService = new ScreenService();
    private static final Logger logger = Logger.getLogger(ImageFieldController.class);

    public void setClientApp(ClientApp clientApp) {
        this.clientApp = clientApp;
    }


    public void setRestBookClient(RestBookClient restBookClient) {
        this.restBookClient = restBookClient;
    }

    public ImageFieldController() {
    }


    public void setImgToImv(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new NullPointerException();
        }

        BufferedImage bufferedImage = ImageIO.read(inputStream);
        double width = (double) bufferedImage.getWidth();
        double height = (double) bufferedImage.getHeight();
        inputStream.reset();
        Image page = new Image(inputStream, width, height, false, true);
        double divider = getDivider(height);
        imv.setFitHeight(height / divider);
        imv.setFitWidth(width / divider);
        imv.setImage(page);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonPrev.setFocusTraversable(false);
        buttonNext.setFocusTraversable(false);
        buttonMenu.setFocusTraversable(false);
        buttonPrev.setVisible(false);

    }


    @FXML
    public void handleNext() {

        if (!restBookClient.incrementPage()) {
            showError();
            return;
        }

        if (restBookClient.getPage() != 1) {
            buttonPrev.setVisible(true);
        }

        if (restBookClient.getPage() == restBookClient.getPageCount()) {
            buttonNext.setVisible(false);
        }

        try {
            restBookClient.setInputStreamImg();
            restBookClient.setImageField();
        } catch (IOException e) {
            logger.error(e);
            showError();
        }

    }

    @FXML
    public void handlePrev() {


        if (!restBookClient.decrementPage()) {
            showError();
            return;
        }
        if (restBookClient.getPage() == 1) {
            buttonPrev.setVisible(false);
        }

        if (restBookClient.getPage() != restBookClient.getPageCount()) {
            buttonNext.setVisible(true);
        }

        try {
            restBookClient.setInputStreamImg();
            restBookClient.setImageField();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }

    }

    @FXML
    public void handleMainMenu() {
        try {
            restBookClient.resetPage();
            clientApp.showBookListView();
        } catch (Exception e) {
            logger.error(e);
            showError();
        }
    }

    private double getDivider(double height) {
        return height / screenService.getHeightForImageView();
    }

    private void showError() {
        Dialogs.create().title("Помилка").message("Сталась невідома помилка").showWarning();
    }

}



