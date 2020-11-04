package cn.agtsci.controller;

import cn.agtsci.dubbo.StorageDubboService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/seate")
public class SeataController {
    @Autowired
    StorageDubboService storageDubboService;


    @GlobalTransactional
    @PostMapping("/dec_storage")
    public int decreaseStorage(@PathVariable String storage){
        return storageDubboService.decreaseStorage(storage);
    }

}
