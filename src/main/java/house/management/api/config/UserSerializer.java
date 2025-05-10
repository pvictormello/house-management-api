package house.management.api.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import house.management.api.model.User;

@Component
public class UserSerializer extends JsonSerializer<User> {

    private String bucketUrl;

    public UserSerializer(@Value("${aws.s3.bucket-url}") String bucketUrl) {
        this.bucketUrl = bucketUrl;
    }

    @Override
    public void serialize(User user, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id", user.getId().toString());
        gen.writeStringField("username", user.getUsername());
        gen.writeStringField("email", user.getEmail());
        gen.writeStringField("name", user.getName());

        if (user.getProfileImageUrl() != null && !user.getProfileImageUrl().isEmpty()) {
            if (user.getProfileImageUrl().startsWith("http")) {
                gen.writeStringField("profileImageUrl", user.getProfileImageUrl());
            } else {
                if (bucketUrl != null) {
                    gen.writeStringField("profileImageUrl", bucketUrl + "/" + user.getProfileImageUrl());
                } else {
                    gen.writeStringField("profileImageUrl", user.getProfileImageUrl());
                }
            }
        } else {
            gen.writeNullField("profileImageUrl");
        }

        gen.writeObjectField("createdAt", user.getCreatedAt());
        gen.writeEndObject();
    }
}