package com.fzdkx.config;

import com.fzdkx.interceptor.ThreadLocalInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author 发着呆看星
 * @create 2023/8/9 13:32
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    /**
     * 通过knife4j生成接口文档
     * @return
     */
    @Bean
    public Docket docket() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("苍穹外卖项目接口文档")  // 标题
                .version("2.0")   // 版本
                .description("苍穹外卖项目接口文档")  // 描述
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                 // 扫描哪些包下的接口
                .apis(RequestHandlerSelectors.basePackage("com.fzdkx.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    /**
     * 设置静态资源映射
     * @param registry
     */
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    // ThreadLocal拦截器，对ThreadLocal进行 remove
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ThreadLocalInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/admin/employee/login","/doc.html");
    }
}
