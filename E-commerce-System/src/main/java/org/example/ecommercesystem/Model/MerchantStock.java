package org.example.ecommercesystem.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {
    @NotEmpty(message = "the id of MerchantStock cannot be empty")
    private String  id;
    @NotEmpty(message = "the product id cannot be empty")
    private  String productid;
    @NotEmpty (message = "the merchant id cannot be empty")
    private String merchantId;
    @NotNull(message = "the stock cannot be empty")
    @Min(value = 11 , message = "the stock must be greater than 10")
    private int stock;

}
