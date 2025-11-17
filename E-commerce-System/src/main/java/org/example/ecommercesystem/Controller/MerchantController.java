package org.example.ecommercesystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommercesystem.Api.ApiResponse;
import org.example.ecommercesystem.Model.Merchant;
import org.example.ecommercesystem.Service.MerchantService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor

public class MerchantController {
    private final MerchantService merchantService;
@GetMapping("/get-merchant")
public ResponseEntity getMerchants (){
    return  ResponseEntity.status(200).body(merchantService.getAllMerchant());
}

@PostMapping("/add-merchant")
public  ResponseEntity addMerchant(@RequestBody @Valid Merchant merchant, Errors errors){
    if(errors.hasErrors()){
        String message = errors.getFieldError().getDefaultMessage();
        return  ResponseEntity.status(400).body(message);
    }

    boolean isAdd= merchantService.addMerchant(merchant);
    if(isAdd){
        return ResponseEntity.status(200).body(new ApiResponse("Merchant is added"));
    }else{
        return  ResponseEntity.status(400).body(new ApiResponse("there is Merchant has the same id"));
    }

}

@PutMapping("/update-merchant/{id}")
public  ResponseEntity updateMerchant (@PathVariable String id , @RequestBody @Valid Merchant merchant , Errors errors ){
    if (errors.hasErrors()){
        String message = errors.getFieldError().getDefaultMessage();
        return  ResponseEntity.status(400).body(message);
    }

    boolean isUpdate= merchantService.updateMerchant(merchant, id);
    if(isUpdate){
        return  ResponseEntity.status(200).body(new ApiResponse("Merchant is updated"));
    }else{
        return  ResponseEntity.status(400).body(new ApiResponse("Merchant Not found"));
    }

}
@DeleteMapping("/delete-Merchant/{id}")
public  ResponseEntity deletedMerchant (@PathVariable String id){
    boolean isDeleted= merchantService.deletedMerchant(id);
    if(isDeleted){
        return  ResponseEntity.status(200).body(new ApiResponse("Merchant has Deleted"));
    }else {
        return  ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
    }
}





}
