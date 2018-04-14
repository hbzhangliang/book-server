package cn.com.flaginfo.platform.terminal.mongo.repo;

import com.mongodb.WriteResult;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Collection;
import java.util.List;

/**
 * Created by liang_zhang on 2017/9/19.
 */
public interface BaseMongoDbRepo<T> {

    /**
     * find one by query
     * @param query
     * @return
     */
    T find(Query query);

    /**
     * find one by id
     * @param id
     * @return
     */
    T find(String id);

    /**
     * list by query
     * @param query
     * @return
     */
    List<T> list(Query query);

    /**
     * list in
     * @param members
     * @return
     */
    List<T> list(Collection<?> keys);

    /**
     * list all
     * @return
     */
    List<T> list();

    /**
     * count by query
     * @param query
     * @return
     */
    long count(Query query);

    /**
     * insert models
     * @param model
     */
    @SuppressWarnings("unchecked")
    void insert(T... models);

    /**
     * insert models
     * @param models
     */
    void insert(Collection<T> models);

    /**
     * save models
     * @param model
     */
    @SuppressWarnings("unchecked")
    void save(T... models);

    /**
     * save models
     * @param models
     */
    void save(Collection<T> models);

    /**
     * update model
     * @param query
     * @param update
     * @return
     */
    T update(Query query, Update update);

    /**
     * update or insert model
     * @param model
     */
    WriteResult updateOrInsert(Query query, Update update);

    /**
     * update first
     * @param query
     * @param update
     * @return
     */
    WriteResult updateFirst(Query query, Update update);

    /**
     * update multi
     * @param query
     * @param update
     * @return
     */
    WriteResult updateMulti(Query query, Update update);

    /**
     * remove
     * @param query
     */
    void remove(Query query);


    void remove(String id);
    /**
     * remove model
     * @param model
     * @return
     */
    T remove(T model);
}
