package org.example.ecommercesystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.ecommercesystem.Model.Category;
import org.example.ecommercesystem.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {
    ArrayList <Product> products = new ArrayList<>();
    private  final  CategoryService categoryService;

    public  ArrayList<Product>  getProduct (){
        return products;
    }

    public int addProduct(Product product) {

        for (Category category : categoryService.getAllCateGory()) {
            if (category.getId().equalsIgnoreCase(product.getCategoryId())) {

                for (Product product1 : products) {
                    if (product1.getId().equalsIgnoreCase(product.getId())) {
                        return 1;
                    }
                }
                products.add(product);
                return 2;
            }
        }
        return 3;
    }


public int updateProduct(Product updateProduct, String productId){
        for(Product product:products ){
            if(product.getId().equalsIgnoreCase(productId)){
                for (Category category: categoryService.getAllCateGory()){
                    if(category.getId().equalsIgnoreCase(updateProduct.getCategoryId())){
                        products.set(products.indexOf(product),updateProduct);
                        return 1;
                    }
                }
                return 2;
            }
        }
        return 3;

}


public  boolean deleteProduct (String productId){
        for (Product product : products){
            if (product.getId().equalsIgnoreCase(productId)) {
                products.remove(product);
                return true;
            }
        }
        return  false;

}


    public Product getProductById(String id) {
        for (Product product : products) {
            if (product.getId().equalsIgnoreCase(id)) {
                return product;
            }
        }
        return null;
    }






}
