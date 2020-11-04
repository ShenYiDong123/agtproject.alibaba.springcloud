package cn.agtsci.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class MyAutoGenerator {
    public static void main(String[] args) {
        AutoGenerator generator = new AutoGenerator();
        // set freemarker engine
        generator.setTemplateEngine(new FreemarkerTemplateEngine());

        GlobalConfig globalConfig = new GlobalConfig();
        String itemPath = System.getProperty("user.dir");
        globalConfig.setOutputDir(itemPath + "/agtproject-cim-base/src/main/java");
        globalConfig.setAuthor("AgtAuto");
        globalConfig.setOpen(false);
        globalConfig.setSwagger2(true);
        globalConfig.setFileOverride(false);

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://192.168.1.16:3306/gi__AgtProject?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("yongdao123456");

        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("cn.agtsci.common");

        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setEntityColumnConstant(true);
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setTablePrefix("_agt_project");
        strategyConfig.setInclude("_agt_project_user_account");

        generator.setGlobalConfig(globalConfig);
        generator.setDataSource(dataSourceConfig);
        generator.setPackageInfo(packageConfig);
        generator.setStrategy(strategyConfig);

        generator.execute();

    }
}