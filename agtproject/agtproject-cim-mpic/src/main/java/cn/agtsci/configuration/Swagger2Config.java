package cn.agtsci.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2配置类
 * @author caiyt
 * @date 2019-11-06
 *
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Value("${swagger2.enable}")
    private boolean enable;
    @Value("${swagger2.host}")
    private String host;
    @Value("${swagger2.basePackage}")
    private String basePackage;
    @Value("${swagger2.apiInfo.title}")
    private String title;
    @Value("${swagger2.apiInfo.description}")
    private String description;
    @Value("${swagger2.apiInfo.termsOfServiceUrl}")
    private String termsOfServiceUrl;
    @Value("${swagger2.apiInfo.version}")
    private String version;
    @Value("${swagger2.apiInfo.license}")
    private String license;
    @Value("${swagger2.apiInfo.licenseUrl}")
    private String licenseUrl;
    @Value("${swagger2.apiInfo.contact.url}")
    private String contactUrl;
    @Value("${swagger2.apiInfo.contact.name}")
    private String contactName;
    @Value("${swagger2.apiInfo.contact.email}")
    private String contactEmail;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .host(host)
                .enable(enable)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .contact(new Contact(contactName,
                        contactUrl,
                        contactEmail))
                .version(version)
                .build();
    }
}
