package cn.com.flaginfo.platform.terminal.mongo.models;


import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Book")
public class Book extends BaseMongoDbModel{

    private String name;

    private String introduction;

    private List<CapterEmt> capterEmtList;

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

    public List<CapterEmt> getCapterEmtList() {
        return capterEmtList;
    }

    public void setCapterEmtList(List<CapterEmt> capterEmtList) {
        this.capterEmtList = capterEmtList;
    }
}
