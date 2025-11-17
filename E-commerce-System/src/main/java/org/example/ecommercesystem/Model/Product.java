package org.example.ecommercesystem.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    @NotEmpty (message = "the id cannot be empty")
    private  String id;
    @NotEmpty(message = "the name cannot be empty")
    @Size(min = 4 , message = "the length of name must be greater than 3")
    private String name;
    @NotNull(message = "the price cannot be empty")
    @Positive(message = "the price must be positive number")
    private  double price;
    @NotEmpty(message = "the id of category cannot be empty")
    private   String categoryId;



}
