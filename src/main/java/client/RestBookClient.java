package client;


import controller.ImageFieldController;
import entity.Book;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import service.UrlService;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pavel on 04.06.15.
 */
public class RestBookClient {


    private static String bookId;
    private int page = 1;
    private int pageCount;
    private InputStream inputStreamImg;
    private ImageFieldController imageFieldController;
    private static final Logger logger = Logger.getLogger(RestBookClient.class);
    private static final String URL = "http://10.251.0.9:8080/listBook";
  //  private static final String URL = "http://localhost:8080/listBook";

    public void setImageFieldController(ImageFieldController imageFieldController) {
        this.imageFieldController = imageFieldController;
    }

    public boolean incrementPage() {

        if (page == pageCount) {
            return false;
        }
        this.page++;
        return true;

    }

    public boolean decrementPage() {
        if (page == 1) {
            return false;
        }
        this.page--;
        return true;

    }

    public int getPage() {
        return page;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void resetPage() {
        this.page = 1;
    }

    public void setInputStreamImg() {

        try {
            this.inputStreamImg = getInputImg(bookId, String.valueOf(page));
        } catch (IOException | ParseException e) {
            logger.error(e);
        }
    }

    public void setImageField() throws IOException {
        imageFieldController.setImgToImv(inputStreamImg);
    }

    public static void setBookId(String bookId) {
        RestBookClient.bookId = bookId;
    }

    private InputStream getInputImg(String bookId, String page) throws IOException, ParseException {
        UrlService urlService = new UrlService();
        String url = urlService.getUrl(bookId, page);
        HttpResponse response = executeGetRequest(url);
        String resultStr = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(resultStr.toString());
        String imageBase64 = (String) jsonObject.get("imageBase64");
        pageCount = ((Number) jsonObject.get("pageCount")).intValue();
        byte[] backToBytes = Base64.decodeBase64(imageBase64);
        return new ByteArrayInputStream(backToBytes);
    }

    public List<Book> getListBook() throws IOException, ParseException {
        List<Book> resultList = new ArrayList<>();
        HttpResponse response = executeGetRequest(URL);
        String resultStr = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(resultStr.toString());
        JSONArray jsonArray = (JSONArray) jsonObject.get("listBook");

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = (JSONObject) jsonArray.get(i);
            Book book = new Book(String.valueOf(obj.get("id")), String.valueOf(obj.get("name")), String.valueOf(obj.get("author")), String.valueOf(obj.get("year")), String.valueOf(obj.get("pageCount")));
            resultList.add(book);
        }

        return resultList;

    }

    private HttpResponse executeGetRequest(String url) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        return response;
    }


}
