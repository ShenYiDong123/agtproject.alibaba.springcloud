package cn.agtsci.service.impl;

import cn.agtsci.mapper.OrderMapper;
import cn.agtsci.service.OrderService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.agtsci.dubbo.*;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Reference
    StorageDubboService storageDubboService;

    AtomicInteger order_id = new AtomicInteger(0);

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @GlobalTransactional
    public void createOrder(String orderDTO) {


        logger.info("开始全局事务。XID:{}",RootContext.getXID());

        //1、扣减库存
        storageDubboService.decreaseStorage("123");

        //2、创建订单
        orderMapper.createOrder();

        logger.info("订单已创建：{}");
    }
}
