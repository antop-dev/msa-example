package com.skcomms.dev.api.order.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Data
@JsonDeserialize(builder = User.UserBuilder.class)
@Builder(toBuilder = true)
public class User {
    private String userId;
    private String name;
    private String email;

    @JsonPOJOBuilder(withPrefix = "")
    public static class UserBuilder {
    }
}
