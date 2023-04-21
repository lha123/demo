//package com.example.demo.conf;
//
//import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.*;
//import springfox.documentation.oas.annotations.EnableOpenApi;
//import springfox.documentation.schema.ScalarType;
//import springfox.documentation.service.*;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@EnableKnife4j
//@EnableOpenApi
//@Configuration
//public class SwaggerConfig {
//
//    @Bean
//    public Docket storeAdminApi() {
//        return new Docket(DocumentationType.OAS_30)
//                .apiInfo(apiInfo())
//                .enable(true)
//                .select()
//                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
//                .paths(PathSelectors.any())
//                .build()
//                .groupName("demo");
//        //.pathMapping("/sms");
//    }
//
//    private ApiInfo apiInfo() {
//        Contact contact = new Contact("liu", "", "liuhonger@gmail.com");
//        return new ApiInfoBuilder()
//                .title("demo")
//                .contact(contact)
//                .version("3.0")
//                .description("demo")
//                .version("1.0.0")
//                .build();
//    }
//
//
//    //生成全局通用参数
//    private List<RequestParameter> getGlobalRequestParameters() {
//        List<RequestParameter> parameters = new ArrayList<>();
//        parameters.add(new RequestParameterBuilder()
//                .name("appid")
//                .description("应用id")
//                .required(true)
//                .in(ParameterType.QUERY)
//                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
//                .required(false)
//                .build());
//        parameters.add(new RequestParameterBuilder()
//                .name("token")
//                .description("token")
//                .required(true)
//                .in(ParameterType.QUERY)
//                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
//                .required(false)
//                .build());
//        return parameters;
//    }
//
//    //生成通用响应信息
//    private List<Response> getGlobalResonseMessage() {
//        List<Response> responseList = new ArrayList<>();
//        responseList.add(new ResponseBuilder().code("404").description("找不到资源").build());
//        return responseList;
//    }
//
//
//
//}
