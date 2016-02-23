package service;

/**
 * Created by pc8 on 10.08.15.
 */
public class UrlService {

    private static final String URL = "http://10.251.0.9:8080/rest?bookId=";
   // private static final String URL = "http://localhost:8080/rest?bookId=";

    public String getUrl(String bookId, String page) {
        StringBuilder url = new StringBuilder();
        url.append(URL);
        url.append(bookId);
        url.append("&page=" + page);
        return url.toString();
    }
}
