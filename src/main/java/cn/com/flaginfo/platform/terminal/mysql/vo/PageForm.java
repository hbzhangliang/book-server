package cn.com.flaginfo.platform.terminal.mysql.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liang_zhang on 2017/11/1.
 */
public class PageForm implements Serializable {
    //页码
    private Integer page=1;
    //每页记录数
    private Integer pageSize=10;

    //查询参数
    private Map<String,Object> params = new HashMap<>(10);

    //临时传递参数
    private Map<String, String> tmpParams = new HashMap<String, String>(10);

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

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, String> getTmpParams() {
        return tmpParams;
    }

    public void setTmpParams(Map<String, String> tmpParams) {
        this.tmpParams = tmpParams;
    }
}
