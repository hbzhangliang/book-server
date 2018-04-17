package cn.com.flaginfo.platform.terminal.mongo.repo.support;

import cn.com.flaginfo.platform.terminal.mongo.models.Book;
import cn.com.flaginfo.platform.terminal.mongo.repo.BookRepo;
import org.springframework.stereotype.Service;

@Service
public class BookRepoImpl extends BaseMongoDbRepoSupport<Book> implements BookRepo {
}
