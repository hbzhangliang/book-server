package cn.com.flaginfo.platform.terminal.mongo.repo.support;

import cn.com.flaginfo.platform.terminal.mongo.models.Author;
import cn.com.flaginfo.platform.terminal.mongo.repo.AuthorRepo;
import org.springframework.stereotype.Service;

@Service
public class AuthorRepoImpl extends BaseMongoDbRepoSupport<Author> implements AuthorRepo {
}
