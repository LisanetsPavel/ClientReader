package controller;

import application.ClientApp;
import client.RestBookClient;
import entity.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.log4j.Logger;
import org.controlsfx.dialog.Dialogs;
import org.json.simple.parser.ParseException;
import service.TableComparator;

import java.io.IOException;
import java.util.List;


/**
 * Created by pavel on 01.07.15.
 */
public class BookListController {

    private ObservableList<Book> bookList = FXCollections.observableArrayList();
    private RestBookClient restBookClient;
    private ClientApp clientApp;
    private String bookId;
    private List<Book> listBook;
    private static final Logger logger = Logger.getLogger(BookListController.class);

    @FXML
    private TableView<Book> tableBook;

    @FXML
    private TableColumn<Book, String> author;

    @FXML
    private TableColumn<Book, String> name;

    @FXML
    private TableColumn<Book, String> year;

    @FXML
    private TableColumn<Book, String> pageCount;

    @FXML
    private Label authorLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label yearLabel;

    @FXML
    private ComboBox box;

    @FXML
    private TextField findString;

    @FXML
    private Button buttonOpen;

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void init() {

        year.setComparator(new TableComparator());
        pageCount.setComparator(new TableComparator());
        buttonOpen.setVisible(false);
        initData();

        author.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        name.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));
        year.setCellValueFactory(new PropertyValueFactory<Book, String>("year"));
        pageCount.setCellValueFactory(new PropertyValueFactory<Book, String>("pageCount"));
        tableBook.getSelectionModel();
        tableBook.setItems(bookList);

        tableBook.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    buttonOpen.setVisible(true);
                    if (newValue != null) {
                        setBookId(newValue.getId());
                        authorLabel.setText(newValue.getAuthor());
                        nameLabel.setText(newValue.getName());
                        yearLabel.setText(newValue.getYear());
                    }
                }
        );
    }


    private void showImageView(String bookId) {

        restBookClient.setBookId(bookId);
        try {
            restBookClient.setInputStreamImg();
            clientApp.showImageView();
            restBookClient.setImageField();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
            Dialogs.create().title("Помилка").masthead("Сталась невідома помилка").showWarning();
        }

    }

    private void initData() {

        try {
            listBook = restBookClient.getListBook();
        } catch (IOException | ParseException e) {
            logger.error(e);
            Dialogs.create().title("Помилка").masthead("Сталась невідома помилка").showWarning();
        }
        for (Book book : listBook) {
            bookList.add(book);
        }
    }


    public void setRestBookClient(RestBookClient restBookClient) {
        this.restBookClient = restBookClient;
    }

    public void setClientApp(ClientApp clientApp) {
        this.clientApp = clientApp;
    }

    @FXML
    private void handleOpen() {
        showImageView(bookId);
    }


    @FXML
    private void handleFind() {
        bookList.clear();
        for (Book book : listBook) {
            if (box.getSelectionModel().getSelectedIndex() <= 0) {
                if (book.getName().contains(findString.getText())) {
                    bookList.add(book);
                }
            } else if (box.getSelectionModel().getSelectedIndex() == 1) {
                if (book.getAuthor().contains(findString.getText())) {
                    bookList.add(book);
                }
            } else {
                if (book.getYear().contains(findString.getText())) {
                    bookList.add(book);
                }

            }
        }
    }
}
