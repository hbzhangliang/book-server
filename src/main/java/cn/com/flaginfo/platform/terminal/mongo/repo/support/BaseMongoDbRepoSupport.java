package cn.com.flaginfo.platform.terminal.mongo.repo.support;


import cn.com.flaginfo.platform.terminal.mongo.models.BaseMongoDbModel;
import cn.com.flaginfo.platform.terminal.mongo.repo.BaseMongoDbRepo;
import com.mongodb.WriteResult;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by liang_zhang on 2017/9/19.
 */
public abstract class BaseMongoDbRepoSupport<T extends BaseMongoDbModel> implements BaseMongoDbRepo<T> {

//    private static final Logger log =
//            LoggerFactory.getLogger(BaseMongoDbRepoSupport.class);
    @Autowired
    protected MongoTemplate mongoTpl;
    protected Class<T> type;

    @SuppressWarnings("unchecked")
    public BaseMongoDbRepoSupport() {
        type = (Class<T>) GenericTypeResolver.resolveTypeArgument(
                getClass(), BaseMongoDbRepoSupport.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T find(Query query) {
        return mongoTpl.findOne(query, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T find(String id) {
        return mongoTpl.findById(new ObjectId(id), type);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> list(Query query) {
        return mongoTpl.find(query, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> list(Collection<?> keys) {
        return list(new Query(Criteria.where("id").in(keys)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> list() {
        return mongoTpl.findAll(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count(Query query) {
        return mongoTpl.count(query, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public void insert(T ... models) {
        for (T model : models) {
            if (model.getDateCreated() == null) {
                model.setDateCreated(new Date());
            }
            mongoTpl.insert(model);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(Collection<T> models) {
        if (models != null && models.isEmpty()) {
            for (T model : models) {
                if (model.getDateCreated() == null) {
                    model.setDateCreated(new Date());
                }
                mongoTpl.insert(model);
            }
        }
        else {
//            log.warn("No Models Insert.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public void save(T ... models) {
        for (T model : models) {
            if (StringUtils.isBlank(model.getId())) {
                if (model.getDateCreated() == null) {
                    model.setDateCreated(new Date());
                }
            }
            mongoTpl.save(model);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Collection<T> models) {
        if (models != null && !models.isEmpty()) {
            for (T model : models) {
                if (StringUtils.isBlank(model.getId())) {
                    if (model.getDateCreated() == null) {
                        model.setDateCreated(new Date());
                    }
                }
                mongoTpl.save(model);
            }
        }
        else {
//            log.warn("No Model To Save.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T update(Query query, Update update) {
        return mongoTpl.findAndModify(query, update, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(Query query) {
        mongoTpl.remove(query, type);
    }


    @Override
    public void remove(String id){
        mongoTpl.remove(this.find(id));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public WriteResult updateOrInsert(Query query, Update update) {
        return mongoTpl.upsert(query, update, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WriteResult updateFirst(Query query, Update update) {
        return mongoTpl.updateFirst(query, update, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WriteResult updateMulti(Query query, Update update) {
        return mongoTpl.updateMulti(query, update, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T remove(T model) {
        mongoTpl.remove(model);
        return model;
    }

    protected String table() {
        String table = null;
        Document doc = type.getAnnotation(Document.class);
        if (doc != null) {
            table = doc.collection();
        }
        else {
            table = StringUtils.uncapitalize(
                    type.getSimpleName());
        }
        return table;
    }


}
