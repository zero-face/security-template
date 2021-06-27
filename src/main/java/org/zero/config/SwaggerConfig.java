package org.zero.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.service.Parameter;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Zero
 * @Description
 * @Date 2021/6/24 15:15
 * @Since 1.8
 **/
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {
    @Bean
    public Docket createResApi() {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> params = new ArrayList<>();
        parameterBuilder.name("token").description("请求令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        params.add(parameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("Default")
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.zero.controller"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .globalOperationParameters(params); //全局参数配置
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("spring-security-template文档")
                .description("用户管理")
                .contact(new Contact("zero", null, "1444171773@qq.com"))
                .version("0.0.1")
                .build();
    }

}
