package com.skcomms.dev.api.order.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Data
@JsonDeserialize(builder = Product.ProductBuilder.class)
@Builder(toBuilder = true)
public class Product {
    private String id;
    private String name;
    private int price;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ProductBuilder {
    }

}
