package org.example.ecommercesystem.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {
@NotEmpty(message = "the id of category cannot be empty")
    private  String  id;
@NotEmpty(message = "the name of category cannot be empty")
@Size(min = 4, message =  "the length of name must be greater than 3")
private  String  name;



}
