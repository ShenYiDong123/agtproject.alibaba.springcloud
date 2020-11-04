package cn.agtsci.generator;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author chengl
 * @date 2019-10-31
 *
 * @auth caiyt
 * @date 2019-11-05
 *
 * **/
public class MyAutoGenerator {

    private static Properties getProperties(){
        Properties properties = new Properties();
        InputStreamReader in = null;
        try{
            in = new InputStreamReader(MyAutoGenerator.class.getClassLoader().getResourceAsStream("properties/mybatisPlusGenerator.properties"), "UTF-8");
            properties.load(in);
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

    public static void main(String[] args) {
        Properties properties = getProperties();

        Scanner sc = new Scanner(System.in);
        System.out.println("请输入模块名：");
        String moduleName = sc.nextLine();
        System.out.println("请输入表名：");
        String tableName = sc.nextLine();
        if(StringUtils.isEmpty(tableName) || StringUtils.isEmpty(moduleName)){
            System.out.println("模块名和表名不能为空");
            return;
        }
        AutoGenerator generator = new AutoGenerator();
        // set freemarker engine
        generator.setTemplateEngine(new FreemarkerTemplateEngine());

        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        globalConfig.setOutputDir(projectPath + "/agtproject-cim-mpic/src/main/java");
        globalConfig.setAuthor(properties.getProperty("mybatis.plus.global.author"));
        globalConfig.setOpen(false);
        globalConfig.setSwagger2(true);
        globalConfig.setFileOverride(false);
        globalConfig.setDateType(DateType.ONLY_DATE);

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(properties.getProperty("mybatis.plus.dataSource.url"));
        dataSourceConfig.setDriverName(properties.getProperty("mybatis.plus.dataSource.driverName"));
        dataSourceConfig.setUsername(properties.getProperty("mybatis.plus.dataSource.username"));
        dataSourceConfig.setPassword(properties.getProperty("mybatis.plus.dataSource.password"));

        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setModuleName(moduleName);
        packageConfig.setParent("cn.agtsci.modules");

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/generator/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/agtproject-cim-mpic/src/main/resources/mapper/"
                        + packageConfig.getModuleName() + "/" + tableInfo.getEntityName() + "Mapper"
                        + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        generator.setCfg(cfg);
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setController("/templates/generator/controller.java");
        templateConfig.setEntity("/templates/generator/entity.java");
        templateConfig.setMapper("/templates/generator/mapper.java");
        templateConfig.setService("/templates/generator/service.java");
        templateConfig.setServiceImpl("templates/generator/serviceImpl.java");
        templateConfig.setXml(null);
        generator.setTemplate(templateConfig);

        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setEntityColumnConstant(true);
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setTablePrefix("_agt_project");
        strategyConfig.setInclude(tableName);
        strategyConfig.setControllerMappingHyphenStyle(true);
        strategyConfig.setRestControllerStyle(true);

        generator.setGlobalConfig(globalConfig);
        generator.setDataSource(dataSourceConfig);
        generator.setPackageInfo(packageConfig);
        generator.setStrategy(strategyConfig);

        generator.execute();

    }
}