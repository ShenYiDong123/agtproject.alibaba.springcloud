package cn.agtsci.service.impl;


import cn.agtsci.entity.Storage;
import cn.agtsci.mapper.StorageMapper;
import cn.agtsci.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    StorageMapper storageMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public int decreaseStorage(String storageDTO) {

        logger.info("开始扣减库存。商品编号:{}",storageDTO);

        Storage storage = new Storage();

        return storageMapper.decreaseStorage();
    }
}
