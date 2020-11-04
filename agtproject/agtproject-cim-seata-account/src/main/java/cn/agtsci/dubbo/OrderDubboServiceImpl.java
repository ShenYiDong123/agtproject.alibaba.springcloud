package cn.agtsci.dubbo;


import cn.agtsci.service.OrderService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class OrderDubboServiceImpl implements OrderDubboService {


    @Autowired
    OrderService orderService;

    @Override
    public void createOrder(String order) {
        orderService.createOrder(order);
    }
}
