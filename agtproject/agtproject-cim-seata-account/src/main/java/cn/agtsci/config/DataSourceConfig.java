//package cn.agtsci.config;
//
//import io.seata.rm.datasource.DataSourceProxy;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
///**
// * Seata 是通过代理数据源实现事务分支，所以需要先配置一个数据源的代理，否则事务不会回滚。
// */
//@Configuration
//public class DataSourceConfig {
//
//    @Bean
//    public DataSourceProxy dataSourceProxy(DataSource dataSource) {
//        return new DataSourceProxy(dataSource);
//    }
//}
