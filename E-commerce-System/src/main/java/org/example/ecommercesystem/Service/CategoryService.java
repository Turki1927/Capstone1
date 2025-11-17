package org.example.ecommercesystem.Service;

import org.example.ecommercesystem.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {
ArrayList<Category> categories = new ArrayList<>();


public  ArrayList<Category> getAllCateGory(){
    return categories;
}



    public boolean addCategory(Category category){
        for (Category c: categories){
            if (c.getId().equalsIgnoreCase(category.getId())){
                return false;
            }
        }
        categories.add(category);
        return true;
    }

    public  boolean updateCategory (Category category , String id){
    for(Category c: categories){
        if(c.getId().equalsIgnoreCase(id)){
            categories.set(categories.indexOf(c),category);
            return true;

        }
    }
    return false;

    }

    public boolean deleteCategory(String id){
            for (Category c: categories){
                if(c.getId().equalsIgnoreCase(id)){
                    categories.remove(c);
                    return true;

                }
            }
            return false;
    }


    public String getCategoryNameById(String categoryId){
    for(Category category:categories){
        if(category.getId().equalsIgnoreCase(categoryId)){
            return  category.getName();
        }
    }
    return null;
    }


}
