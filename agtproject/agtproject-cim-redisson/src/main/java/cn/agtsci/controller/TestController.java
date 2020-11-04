package cn.agtsci.controller;

import org.redisson.Redisson;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/redisson")
public class TestController {

    @Value("${spring.redis.host}")
    private String host; // 地址

    @Value("${spring.redis.port}")
    private String port; // 端口

    @GetMapping(value = "/redisson/{number}")
    public String testRedisson(@PathVariable("number") String number) throws IOException {
        //1.创建redis对象
        Config config = new Config();
        //config = Config.fromYAML(new File("config-file.yaml"));
        config.useClusterServers().addNodeAddress("redis://192.168.17.128:16379");
        //2.创建Redisson对象(根据config)
        RedissonClient redisson = Redisson.create(config);

        //@TODO 2020-05-20 11:33:11.915  WARN 7564 --- [nio-8003-exec-1] o.r.cluster.ClusterConnectionManager     : ERR This instance has cluster support disabled. channel: [id: 0xa4a77f91, L:/192.168.17.1:55890 - R:/192.168.17.128:16379] command: (CLUSTER NODES), params: []
        //解决方案:redis.conf将# cluster-enabled yes 注释放开


        //3.获取分布式锁
        RLock rLock = redisson.getLock("lockName");

        //4.读写锁（2个方法，一个在读，一个在写，如果写方法获取到写锁，那么读方法会等待写锁释放才会执行下去）
        //写锁是一个排他锁（互斥锁），读锁是一个共享锁
        RReadWriteLock readWriteLock = redisson.getReadWriteLock("rw-lock");
        RLock readLock = readWriteLock.readLock();
        RLock writeLock = readWriteLock.writeLock();


        //如果获取锁成功
        if (rLock.tryLock()) {
            try {
                System.out.println("获取锁成功,执行业务逻辑");
            } finally {
                // 解锁
                rLock.unlock();
                System.out.println("解锁成功");
            }
        }
        return "获取分布式锁成功";
    }

    /**
     * 闭锁（模拟学校锁门）
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/lockDoor/{number}")
    public String lockDoor() throws IOException, InterruptedException {
        Config config = new Config();
        config.useClusterServers().addNodeAddress("redis://192.168.17.128:16379");
        RedissonClient redisson = Redisson.create(config);

        RCountDownLatch door = redisson.getCountDownLatch("door");
        door.trySetCount(5); //5个班级
        door.wait(); //等待闭锁完成，只有等待door计数为0时，就会往下走

        return "放假了";
    }

    @GetMapping(value = "/go/{number}")
    public String go() throws IOException, InterruptedException {
        Config config = new Config();
        config.useClusterServers().addNodeAddress("redis://192.168.17.128:16379");
        RedissonClient redisson = Redisson.create(config);

        RCountDownLatch door = redisson.getCountDownLatch("door");
        door.countDown(); //计数减一

        return "锁门完毕";
    }

}
