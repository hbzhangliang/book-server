package cn.com.flaginfo.platform.terminal.services.impl;

import cn.com.flaginfo.platform.terminal.mongo.models.Author;
import cn.com.flaginfo.platform.terminal.mongo.models.Book;
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

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

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
                    Future<Set<Author>> list = taskExecutor.submit(new Callable<Set<Author>>() {
                        @Override
                        public Set<Author> call() throws Exception {
                            Set<Author> result = new HashSet<>(100);
                            for(int i=1;i<10;i++) {
                                String strUrl = String.format("http://www.kanunu8.com/author%s.html", i);
                                log.info("url is[{}]", strUrl);
                                String content = JSONHelper.loadJson(strUrl);
                                List<Map<String, String>> list = i!=3? HtmlUtils.getAuthList(content):HtmlUtils.getAuthListADD(content);
                                log.info("when i is [{}]result[{}]",i,JSON.toJSONString(list));
                                for (Map<String, String> item : list) {
                                    String name = item.get("name");
                                    String url = item.get("url");
                                    Author author = new Author(name, url);
                                    result.add(author);
                                }
                            }
                            return result;
                        }
                    });


                    try {
                        Boolean flag = true;
                        while (flag) {
                            //完成且没有取消
                            if (list.isDone() && !list.isCancelled()) {
                                flag = false;
                                Set<Author> result = list.get();
                                //清除重复数据
                                Set<Author> AuthorList=new HashSet<>(1000);
                                for(Author item:result){
                                    Boolean tmpFlag=false;
                                    if(AuthorList!=null){
                                        for(Author pp:AuthorList){
                                            if(item.getName().equals(pp.getName())){
                                                tmpFlag=true;
                                                break;
                                            }
                                        }
                                    }
                                    if(!tmpFlag){ //不存在才加入
                                        AuthorList.add(item);
                                    }
                                }
                                authorRepo.save(AuthorList);
                            }
                            Thread.sleep(1000);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });
    }


    /**
     * 总计962 条
     * 三类
     * /files/    953条
     * /author/    4条
     * /zj/    4条
     * /hmtl/   唐家三少    1条
     */
    @Override
    public void filterAuthor() {
        List<Author> list=authorRepo.list();
        List<Author> filesList=new ArrayList<>(1000);
        List<Author>  authorList=new ArrayList<>(10);
        List<Author> zjList=new ArrayList<>(10);
        List<Author> otherList=new ArrayList<>(10);
        for(Author item:list){
            String url=item.getUrl().toLowerCase();
            if(url.indexOf("files")>-1){
                filesList.add(item);
            }
            else if(url.indexOf("author")>-1){
                authorList.add(item);
            }
            else if(url.indexOf("zj")>-1){
                zjList.add(item);
            }else {
                otherList.add(item);
            }
        }

        log.info("contains auth [{}]",JSON.toJSONString(authorList));

        log.info("contains zj [{}]",JSON.toJSONString(zjList));

        log.info("contains others  [{}]",JSON.toJSONString(otherList));
    }

    @Autowired
    private AuthorRepo authorRepo;

}
