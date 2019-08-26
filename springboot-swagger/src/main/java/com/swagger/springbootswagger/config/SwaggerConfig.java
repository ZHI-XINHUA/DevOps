package com.swagger.springbootswagger.config;

import com.swagger.springbootswagger.controller.IndexController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * swagger 配置。
 * 参考文章：https://blog.csdn.net/qq122516902/article/details/89417964
 */
@Component
@EnableSwagger2 // 开启Swagger2的自动配置
public class SwaggerConfig {

    @Autowired
    private Environment environment;


    /**
     * api 信息配置
     * @return
     */
    public ApiInfo apiInfo(){
        Contact contact = new Contact("zhixinhua","https://github.com/ZHI-XINHUA","1960881192@qq.com");
        return new ApiInfo("Swagger学习", //标题
                "学习Swagger配置", //描述
                "V1.0", //版本
                "http://terms.service.url/组织链接", //组织链接
                contact,//联系人
                "Apach 2.0 许可", // 许可
                "许可链接", // 许可连接
                new ArrayList<>()); // 扩展
    }

    private boolean getEnableSwagger(){
        // 设置要显示swagger的环境
        Profiles profile = Profiles.of("dev","test");
        // 判断当前是处于该环境，通过 enable() 接收此参数判断是否要显示
        return environment.acceptsProfiles(profile);
    }


    /**
     * user组
     * @return
     */
    @Bean
    public Docket docket1(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                // 配置是否启用Swagger，如果是false，在浏览器将无法访问
                .enable(getEnableSwagger())
                // 配置分组
                .groupName("user")
                // 配置要忽略的参数 request
                .ignoredParameterTypes(HttpServletRequest.class)
                // 通过.select()方法，去配置扫描接口
                .select()
                // RequestHandlerSelectors配置如何扫描接口
                //扫描所有，项目中的所有接口都会被扫描到
                //.apis(RequestHandlerSelectors.any())
                // 不扫描接口
                //.apis(RequestHandlerSelectors.none())
                //通过方法上的注解扫描，如withMethodAnnotation(GetMapping.class)只扫描get请求
                //.apis(RequestHandlerSelectors.withMethodAnnotation(GetMapping.class))
                // 通过类上的注解扫描，如.withClassAnnotation(Controller.class)只扫描有controller注解的类中的接口
                //.apis(RequestHandlerSelectors.withClassAnnotation(Controller.class))
                // 根据包路径扫描接口
                .apis(RequestHandlerSelectors.basePackage("com.swagger.springbootswagger.controller"))
                // 配置如何通过 path过滤 即这里只扫描 请求以 /user开头的接口
                .paths(PathSelectors.ant("/user/**"))
                .build();
    }

    /**
     * index 组
     * @return
     */
    @Bean
    public Docket docket2(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                // 配置是否启用Swagger，如果是false，在浏览器将无法访问
                .enable(getEnableSwagger())
                // 配置分组
                .groupName("index")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.swagger.springbootswagger.controller"))
                // 配置如何通过 path过滤 即这里只扫描 请求以 /user开头的接口
                .paths(PathSelectors.ant("/index/**"))
                .build();
    }




}
