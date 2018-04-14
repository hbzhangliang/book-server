package cn.com.flaginfo.platform.terminal.mongo.repo.support;

import cn.com.flaginfo.platform.terminal.mongo.models.Tmp;
import cn.com.flaginfo.platform.terminal.mongo.repo.TmpRepo;
import org.springframework.stereotype.Service;


@Service
public class TmpRepoImpl extends BaseMongoDbRepoSupport<Tmp> implements TmpRepo {
}
