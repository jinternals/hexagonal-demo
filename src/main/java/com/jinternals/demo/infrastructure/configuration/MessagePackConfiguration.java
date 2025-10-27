package com.jinternals.demo.infrastructure.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MessagePackConfiguration implements WebMvcConfigurer {

    private static final MediaType APP_X_MSGPACK = new MediaType("application", "x-msgpack");
    private static final MediaType APP_MSGPACK   = new MediaType("application", "msgpack");

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // Build an ObjectMapper that inherits Spring’s Jackson config (modules, JavaTime, etc.)
        ObjectMapper msgpackMapper = new Jackson2ObjectMapperBuilder()
                .factory(new MessagePackFactory())
                .build();

        HttpMessageConverter<Object> msgpackConverter =
                new AbstractJackson2HttpMessageConverter(msgpackMapper, APP_X_MSGPACK, APP_MSGPACK) {};

        // Add at the END so JSON stays default
        converters.add(msgpackConverter);
    }
}

