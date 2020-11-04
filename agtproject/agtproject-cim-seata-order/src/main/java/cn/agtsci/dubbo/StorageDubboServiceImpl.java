package cn.agtsci.dubbo;

import cn.agtsci.service.StorageService;
import io.seata.core.context.RootContext;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class StorageDubboServiceImpl implements StorageDubboService {

    @Autowired
    StorageService storageService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public int decreaseStorage(String storage) {
        return storageService.decreaseStorage("123");
    }
}
