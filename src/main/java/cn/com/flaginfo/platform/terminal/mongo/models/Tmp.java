package cn.com.flaginfo.platform.terminal.mongo.models;


import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tmp")
public class Tmp extends BaseMongoDbModel{

    private String name;

    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
