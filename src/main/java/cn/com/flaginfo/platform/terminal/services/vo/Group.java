package cn.com.flaginfo.platform.terminal.services.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Group implements Serializable{

    private String id;

    private String name;

    //contactId  11
    //mobile   12
    //version   14
    //name     15
    private List<Map<String,String>> contactsMembers;


    //mobile
    //name  zhangsan
    //content  haole
    //publishTime  2018
    private List<Map<String,String>> contents;


    //版本号自赠1
    private Long version=0L;

    private String creator;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Map<String, String>> getContactsMembers() {
        return contactsMembers;
    }

    public void setContactsMembers(List<Map<String, String>> contactsMembers) {
        this.contactsMembers = contactsMembers;
    }

    public List<Map<String, String>> getContents() {
        return contents;
    }

    public void setContents(List<Map<String, String>> contents) {
        this.contents = contents;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
