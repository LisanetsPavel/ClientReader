package entity;

/**
 * Created by pavel on 01.07.15.
 */

public class Book {
    private String id;
    private String name;
    private String author;
    private String year;
    private String pageCount;
    public Book() {
    }

    public Book(String id, String name, String author, String year, String pageCount) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
        this.pageCount = pageCount;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", year='" + year + '\'' +
                ", pageCount='" + pageCount + '\'' +
                '}';
    }
}
