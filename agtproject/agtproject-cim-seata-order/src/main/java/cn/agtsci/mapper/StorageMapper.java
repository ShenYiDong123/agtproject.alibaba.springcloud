package cn.agtsci.mapper;

import cn.agtsci.entity.Storage;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageMapper {

    int decreaseStorage();
}
