package org.example.ecommercesystem.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class User {
    @NotEmpty(message = "the id of user cannot be empty")
    private String id;
    @NotEmpty(message = "the username cannot be empty")
    @Size(min = 6, message = "the username must be greater than 5")
    private String username;

    @NotEmpty(message = "Password Cannot Be Empty")
    @Size(min = 7, message = "Password Cannot Be Less Than 6")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$", message = "Password Must Contains Characters and Digits")
    private String password;


    @NotEmpty(message = "the email cannot be empty")
    @Email
    private String email;
    @NotEmpty(message =  "the role cannot be empty")
    @Pattern(regexp = "^(Admin|Customer)$", message = "the role must be Admin or Customer")
    private String role;
    @NotNull(message = "the balance cannot be empty")
    @Positive(message = "the balance must be positive")
    private double balance;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ArrayList<Product> order = new ArrayList<>();

}
