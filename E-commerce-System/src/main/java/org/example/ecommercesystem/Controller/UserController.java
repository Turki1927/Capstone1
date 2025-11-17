package org.example.ecommercesystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommercesystem.Api.ApiResponse;
import org.example.ecommercesystem.Model.Product;
import org.example.ecommercesystem.Model.User;
import org.example.ecommercesystem.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/get")
    public ResponseEntity getAllUsers() {
        ArrayList<User> users = userService.getUser();
        return ResponseEntity.status(200).body(users);
    }


    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean isAdded = userService.addUser(user);

        if (isAdded) {
            return ResponseEntity.status(200).body(new ApiResponse("User added successfully!"));
        } else {
            return ResponseEntity.status(400).body(new ApiResponse("User with this ID already exists!"));
        }
    }


    @PutMapping("/update/{ID}")
    public ResponseEntity updateUser(@PathVariable String ID, @RequestBody @Valid User updatedUser, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean isUpdate = userService.updateUser(ID, updatedUser);

        if (isUpdate) {
            return ResponseEntity.status(200).body(new ApiResponse("User updated successfully!"));
        } else {
            return ResponseEntity.status(400).body(new ApiResponse("User with this ID does not exist!"));
        }
    }


    @DeleteMapping("/delete/{ID}")
    public ResponseEntity deleteUser(@PathVariable String ID) {
        boolean isDelete = userService.deleteUser(ID);

        if (isDelete) {
            return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully!"));
        } else {
            return ResponseEntity.status(400).body(new ApiResponse("User with this ID does not exist!"));
        }
    }



    @PostMapping("/buy/{userId}/{productId}/{merchantId}")
    public ResponseEntity<?> buyProduct(@PathVariable String userId ,@PathVariable String productId ,@PathVariable String merchantId ){
        int result = userService.buyProduct(userId ,productId ,merchantId );
        switch(result) {
            case 0:
                return ResponseEntity.status(400).body(new ApiResponse("User not found"));
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
            case 2:
                return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
            case 3:
                return ResponseEntity.status(400).body(new ApiResponse("Product not available in merchant stock"));
            case 4:
                return ResponseEntity.status(400).body(new ApiResponse("Not enough balance"));
            case 5:
                return ResponseEntity.status(200).body(new ApiResponse("Purchase successful"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("Unknown error"));
        }


    }

//=========First Endpoint =================
    @GetMapping("affordable-products/{userId}")
    public ResponseEntity getAffordableProducts(@PathVariable String userId) {

        List<Product> products = userService.getAffordableProducts(userId);
        if(products==null){
            return  ResponseEntity.status(400).body(new ApiResponse(" User not found or there is no affordable products"));
        }
        else {
            return ResponseEntity.status(200).body(products);
        }
    }




//===========Second Endpoint=========
@GetMapping("/get-range/{categoryId}/{minPrice}/{maxPrice}")
public  ResponseEntity <?> getProductsByCategoryAndPriceRange (@PathVariable String categoryId , @PathVariable int minPrice , @PathVariable int maxPrice ){
    if(userService.getProductsByCategoryAndPriceRange(categoryId ,minPrice ,maxPrice) == null){
        return ResponseEntity.status(400).body( new ApiResponse( "Failed to get rang price category"));
    }
    return ResponseEntity.status(200).body(userService.getProductsByCategoryAndPriceRange(categoryId ,minPrice ,maxPrice));
}



//============Third Endpoint=============
    @PutMapping("/recharge-balance/{userID}/{amount}")
    public ResponseEntity<ApiResponse> rechargeBalance(@PathVariable String userID, @PathVariable double amount) {
        int responseNumber = userService.topUpBalance(userID, amount);

        if (responseNumber == 1) {
            return ResponseEntity.status(400).body(new ApiResponse("User cannot be found!"));
        } else if (responseNumber == 2) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse("Amount cannot be negative or zero!"));
        } else if (responseNumber == 3) {
            User user = userService.getUserById(userID);
            return ResponseEntity.status(200)
                    .body(new ApiResponse("Recharge successfully New balance: " + user.getBalance()));
        } else {
            return ResponseEntity.status(400)
                    .body(new ApiResponse("Something went wrong"));
        }
    }


    @GetMapping("/purchases/{userId}/")
    public ResponseEntity<?> getUserPurchases(@PathVariable String userId) {
        ArrayList<Product> orders = userService.getUserPurchases(userId);

        if (orders == null || orders.isEmpty()) {
            return  ResponseEntity.status(400).body(new ApiResponse("User not found or has no purchases"));
        }

        return ResponseEntity.status(200).body(orders);
    }



    @GetMapping("/get-user-order-history/{userID}")
    public ResponseEntity getUserUserPurchased(@PathVariable String userID) {
        ArrayList<Product> orderHistory = userService.getUserPurchased(userID);

        if (orderHistory.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no user order history"));
        }
        return ResponseEntity.status(200).body(orderHistory);
    }


    @GetMapping("/favorite-category-products/{id}")
    public ResponseEntity getFavoriteCategoryProducts(@PathVariable String id) {

        List<Product> favResponse = userService.getFavoriteCategoryProducts(id);

        //user not found
        if (favResponse == null) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse("User not found"));
        }

        //no purchase history
        if (userService.getUserById(id).getOrder()==null) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse("User has no purchase history"));
        }



        // success
        return ResponseEntity.status(200).body(favResponse);
    }







    @PostMapping("/refund/{userId}/{productId}/{merchantId}")
    public ResponseEntity refundProduct(@PathVariable String userId, @PathVariable String productId, @PathVariable String merchantId) {

        int result = userService.refundProduct(userId, productId, merchantId);

        if (result == 0) {
            return ResponseEntity.status(400).body(new ApiResponse("User not found"));
        }

        if (result == 1) {
            return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
        }

        if (result == 2) {
            return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
        }

        if (result == 3) {
            return ResponseEntity.status(400).body(new ApiResponse("User did not purchase this product"));
        }

        return ResponseEntity.ok(new ApiResponse("Refund processed successfully"));
    }





}
