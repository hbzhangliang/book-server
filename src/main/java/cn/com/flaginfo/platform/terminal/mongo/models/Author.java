package cn.com.flaginfo.platform.terminal.mongo.models;


import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Author")
public class Author extends BaseMongoDbModel{

    @Indexed
    private String name;
    //介绍
    private String introduction;
    //文章路径
    private String url;

    private List<BookEmt> bookList;


    public Author(){

    }

    public Author(String name,String url){
        this.name=name;
        this.url=url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<BookEmt> getBookList() {
        return bookList;
    }

    public void setBookList(List<BookEmt> bookList) {
        this.bookList = bookList;
    }
}
