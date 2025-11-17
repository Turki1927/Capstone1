package org.example.ecommercesystem.Controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommercesystem.Api.ApiResponse;
import org.example.ecommercesystem.Model.MerchantStock;
import org.example.ecommercesystem.Service.MerchantStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchantStock")
@RequiredArgsConstructor
public class MerchantStockController {
   private final MerchantStockService merchantStockService;
@GetMapping("/get")
    public ResponseEntity getMerchantStock (){
        return  ResponseEntity.status(200).body(merchantStockService.getMerchantStock());
    }
@PostMapping("/add")
    public  ResponseEntity addMerchantStock (@RequestBody MerchantStock merchantStock, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return  ResponseEntity.status(400).body(message);
        }

        int addResponse= merchantStockService.addMerchantStock(merchantStock);
        if(addResponse ==1){
            return ResponseEntity.status(400).body(new ApiResponse("Stock already exists"));
        } else if (addResponse==2) {
            return  ResponseEntity.status(200).body(new ApiResponse("Stock added"));
        } else if (addResponse==3) {
            return ResponseEntity.status(400).body(new ApiResponse("Product not found for this merchant"));
        }else{
            return ResponseEntity.status(400).body( new ApiResponse("Merchant not found"));
        }

    }

    @PutMapping("/update/{stockID}")
    public ResponseEntity updateMerchantStock(@PathVariable String stockID, @RequestBody @Valid MerchantStock updatedMerchantStock, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        int updateResponse = merchantStockService.updateMerchantStock(stockID, updatedMerchantStock);

        if (updateResponse == 1) {
            return ResponseEntity.status(200).body(new ApiResponse("Stock updated"));
        } else if (updateResponse == 2) {
            return ResponseEntity.status(400).body(new ApiResponse("Stock ID not found"));
        } else {
            return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
        }
    }
@DeleteMapping("delete/{stockId}")
public  ResponseEntity deleteMerchantStock(@PathVariable String stockId){
    boolean isDeleted= merchantStockService.deleteMerchantStock(stockId);
    if(isDeleted){
        return  ResponseEntity.status(200).body(new ApiResponse("Merchant stock deleted"));
    }else{
        return  ResponseEntity.status(400).body(new ApiResponse("Merchant stock ID not found"));

    }
}







//=============end point==================
    @PutMapping("/add-stock/{merchantId}/{productId}/{amount}")
    public ResponseEntity addStockToMerchant(@PathVariable String merchantId, @PathVariable String productId, @PathVariable int amount) {

        int responseAddStock = merchantStockService.addStockToMerchant(productId, merchantId, amount);

        switch(responseAddStock) {
            case 0:
                return ResponseEntity.status(400).body(new ApiResponse("the amount must be greater than 0"));
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
            case 2:
                return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
            case 3:
                return ResponseEntity.status(400).body(new ApiResponse("Merchant stock with this ID does not found"));
            case 4:
                return ResponseEntity.status(200).body(new ApiResponse("Stock updated "));
            default:
                return ResponseEntity.status(500).body(new ApiResponse("Unknown error"));
        }
    }












}
