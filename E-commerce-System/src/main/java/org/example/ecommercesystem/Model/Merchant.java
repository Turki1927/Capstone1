package org.example.ecommercesystem.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {

    @NotEmpty(message = "the id of Merchant cannot be empty")
    private String id;
    @NotEmpty(message = "the name of Merchant cannot be empty")
    @Size(min = 4, message = "the name of Merchant must be grater than 3")
    private String name;



}
