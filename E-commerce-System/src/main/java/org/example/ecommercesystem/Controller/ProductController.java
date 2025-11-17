package org.example.ecommercesystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommercesystem.Api.ApiResponse;
import org.example.ecommercesystem.Model.Product;
import org.example.ecommercesystem.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/get-product")
    public ResponseEntity getAllProduct(){
        return ResponseEntity.status(200).body(productService.getProduct());
    }

    @PostMapping("/add-product")
    public  ResponseEntity addProduct(@RequestBody Product product , Errors errors){
        if(errors.hasErrors()){
            String  message = errors.getFieldError().getDefaultMessage();
            return  ResponseEntity.status(400).body(message);
        }

        int addResponse= productService.addProduct(product);

        if(addResponse==1){
            return ResponseEntity.status(400).body(new ApiResponse("the product with this id already exists"));
        } else if (addResponse==2) {
            return  ResponseEntity.status(200).body(new ApiResponse("the product is added"));

        }else{
            return ResponseEntity.status(400).body(new ApiResponse("category id not found"));
        }

    }

    @PutMapping("/update/{productId}")
    public  ResponseEntity updateProduct(@PathVariable String productId , @RequestBody @Valid Product updateproduct , Errors errors ){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return  ResponseEntity.status(400).body(message);
        }
        int updateResponse= productService.updateProduct(updateproduct,productId);
        if(updateResponse==1){
            return  ResponseEntity.status(200).body(new ApiResponse("Product has been updated"));


        } else if (updateResponse==2) {
            return  ResponseEntity.status(400).body(new ApiResponse("Category Id not found"));
        }else {
            return  ResponseEntity.status(400).body(new ApiResponse("product not found"));
        }

    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity deleteProduct(@PathVariable String productId){
        boolean isDeleted= productService.deleteProduct(productId);
        if(isDeleted){
            return  ResponseEntity.status(200).body(new ApiResponse("product is deleted "));
        }else{
            return  ResponseEntity.status(400).body(new ApiResponse("product not found"));
        }
    }










}
