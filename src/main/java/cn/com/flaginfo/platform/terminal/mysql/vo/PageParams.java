package cn.com.flaginfo.platform.terminal.mysql.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liang_zhang on 2017/11/1.
 */
public class PageParams<T> implements Serializable {

    //页码
    private Integer page=1;
    //每页记录数
    private Integer pageSize=10;
    //记录总数
    private Integer tatalRows=0;
    //总页数
    private Integer totalPage=1;

    private List<T> data;

    private T bean;

    //临时参数
    private Map<String,Object> params=new HashMap<>(10);


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTatalRows() {
        return tatalRows;
    }

    public void setTatalRows(Integer tatalRows) {
        this.tatalRows = tatalRows;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public T getBean() {
        return bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
