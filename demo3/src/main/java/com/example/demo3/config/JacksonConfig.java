package com.example.demo3.config;


import com.example.demo3.intercepter.TestInterceptor;
import com.example.demo3.serialize.Date2LongSerialize;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.elasticsearch.search.aggregations.pipeline.SimpleModel;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Auther: 冯广
 * @Date: 2021/5/3 19:27
 * @Description:
 */
@Configuration
public class JacksonConfig extends WebMvcConfigurationSupport {

    //    private final ObjectMapper objectMapper;
//
//    public JacksonConfig() {
//        objectMapper = JacksonConfig();
//    }
//
//    public static ObjectMapper JacksonConfig() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        SimpleModule moduleDate = new SimpleModule("Date");
//        moduleDate.addSerializer(new Date2LongSerialize());
//        objectMapper.registerModule(moduleDate);
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        objectMapper.setDateFormat(dateFormat);
//        //排序
//        objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
//        //设置null value处理策略
//        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
//            @Override
//            public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
//                jgen.writeString("-");
//            }
//        });
//        return objectMapper;
//    }
//
//    @Override
//    public ObjectMapper getContext(Class<?> arg0) {
//        return objectMapper;
//    }
//    @Bean
//    @Primary
//    @ConditionalOnMissingBean(ObjectMapper.class)
//    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
//        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
//        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
//            @Override
//            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//                jsonGenerator.writeString("-");
//            }
//        });
//        return objectMapper;
//    }
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(new Date2LongSerialize());
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString("-");
            }
        });
        objectMapper.registerModule(module);
        converter.setObjectMapper(objectMapper);

        //这里是fastJSON的配置方式，更多的内容可以查看SerializerFeature
//        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//        converter.setFeatures(SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero,
//                SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.WriteNullListAsEmpty);
        converters.add(converter);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //使用拦截器注册表进行添加 自定义拦截器
        registry.addInterceptor(new TestInterceptor())
                //添加拦截路径
                .addPathPatterns("/getUserList/**")
                //设置放行路径
                .excludePathPatterns("/outer/**");
    }

}
