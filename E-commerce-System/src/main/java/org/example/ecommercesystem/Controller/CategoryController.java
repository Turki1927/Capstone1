package org.example.ecommercesystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommercesystem.Api.ApiResponse;
import org.example.ecommercesystem.Model.Category;
import org.example.ecommercesystem.Service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService  categoryService;
@GetMapping("/get")
    public ResponseEntity getCategory(){
        return  ResponseEntity.status(200).body(categoryService.getAllCateGory());
    }




    @PostMapping("/add-category")
    public ResponseEntity<?> addCategory(@RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return  ResponseEntity.status(400).body(message);
        }
        boolean isAdd= categoryService.addCategory(category);
        if(isAdd){
            return ResponseEntity.status(200).body(new ApiResponse("the category added "));
        }else{
            return ResponseEntity.status(400).body(new ApiResponse("there is category with same id"));
        }
    }

    @PutMapping("/update-category/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isUpdate = categoryService.updateCategory(category,id);
        if(isUpdate){
            return ResponseEntity.status(200).body(new ApiResponse("the category has been updated"));
        }else {
            return ResponseEntity.status(400).body(new ApiResponse("category not found"));
        }
    }

    @DeleteMapping("/delete-category/{id}")
    public  ResponseEntity deleteCategory(@PathVariable String id){
        boolean isDeleted= categoryService.deleteCategory(id);
        if(isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("the category deleted"));
        }else{
            return ResponseEntity.status(400).body(new ApiResponse("category not found"));
        }
    }


}
