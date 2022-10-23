package com.training.conferences.configuration;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfiguration {

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {

    return builder -> {
      DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
      builder.deserializers(new LocalDateDeserializer(dateFormatter));
      builder.serializers(new LocalDateSerializer(dateFormatter));
    };
  }
}
