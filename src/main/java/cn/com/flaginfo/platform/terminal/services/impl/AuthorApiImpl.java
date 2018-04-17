package cn.com.flaginfo.platform.terminal.services.impl;

import cn.com.flaginfo.platform.terminal.mongo.models.Author;
import cn.com.flaginfo.platform.terminal.mongo.repo.AuthorRepo;
import cn.com.flaginfo.platform.terminal.services.AuthorApi;
import cn.com.flaginfo.platform.terminal.utils.HtmlUtils;
import cn.com.flaginfo.platform.terminal.utils.JSONHelper;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AuthorApiImpl implements AuthorApi {

    private static final Logger log = LoggerFactory.getLogger(AuthorApiImpl.class);

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;


    @Override
    public void generateAuthor() {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Author> authorList = new ArrayList<>(100);
                    for(int i=1;i<10;i++) {
                        String strUrl =String.format("http://www.kanunu8.com/author%s.html",i);
                        log.info("url is[{}]",strUrl);
                        String content = JSONHelper.loadJson(strUrl);
                        List<Map<String, String>> list = HtmlUtils.getAuthList(content);
                        log.info(JSON.toJSONString(list));
                        for (Map<String, String> item : list) {
                            String name = item.get("name");
                            String url = item.get("url");
                            Author author = new Author(name, url);
                            authorList.add(author);
                        }
                    }
                    authorRepo.saveBatch(authorList);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


    @Autowired
    private AuthorRepo authorRepo;

}
