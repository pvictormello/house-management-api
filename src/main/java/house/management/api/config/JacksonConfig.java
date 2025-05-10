package house.management.api.config;

import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import house.management.api.model.User;

@Configuration
public class JacksonConfig {

    private final UserSerializer userSerializer;

    public JacksonConfig(UserSerializer userSerializer) {
        this.userSerializer = userSerializer;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        SimpleModule userModule = new SimpleModule();
        userModule.addSerializer(User.class, userSerializer);
        objectMapper.registerModule(userModule);

        return objectMapper;
    }
}
