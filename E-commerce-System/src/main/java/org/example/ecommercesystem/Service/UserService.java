package org.example.ecommercesystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.ecommercesystem.Model.Merchant;
import org.example.ecommercesystem.Model.MerchantStock;
import org.example.ecommercesystem.Model.Product;
import org.example.ecommercesystem.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {

     private final ProductService productService;
     private  final  MerchantService merchantService;
     private final  MerchantStockService merchantStockService;
     private final CategoryService categoryService;
ArrayList<User> users = new ArrayList<>();

    ArrayList<Product> purchases  = new ArrayList<>();

public ArrayList<User> getUser (){
    return users;
}


    public boolean addUser(User user) {
        for (User user1 : users) {
            if (user1.getId().equalsIgnoreCase(user.getId())) {
                return false;
            }
        }

        users.add(user);
        return true;
    }


    public boolean updateUser(String ID, User user) {
        for (User userU : users) {
            if (userU.getId().equalsIgnoreCase(ID)) {
                users.set(users.indexOf(userU), user);
                return true;
            }
        }
        return false;
    }


    public boolean deleteUser(String ID) {
        for (User userD : users) {
            if (userD.getId().equalsIgnoreCase(ID)) {
                users.remove(userD);
                return true;
            }
        }
        return false;
    }




    public User getUserById(String userId) {
        for (User user : users) {
            if (user.getId().equalsIgnoreCase(userId)) {
                return user;
            }
        }
        return null;
    }



    //=============EndPoint===============
    public int buyProduct(String userId, String productId, String merchantId) {

        // 0 User not found
        User user = getUserById(userId);
        if (user == null) {
            return 0;
        }

        // 1 Product not found
        Product product = productService.getProductById(productId);
        if (product == null) {
            return 1;
        }

        // 2 Merchant not found
        Merchant merchant = merchantService.getMerchantById(merchantId);
        if (merchant == null) {
            return 2;
        }

        // 3 = Product not available in merchant stock OR stock == 0
        MerchantStock merchantStock = merchantStockService.checkStock(productId, merchantId);
        if (merchantStock == null || merchantStock.getStock() <= 0) {
            return 3;
        }

        double price = product.getPrice();

        // 4 = Not enough balance
        if (user.getBalance() < price) {
            return 4;
        }

        // reduce balance
        user.setBalance(user.getBalance() - price);

        // reduce stock
        merchantStock.setStock(merchantStock.getStock() - 1);

        // add the buy
        addToPurchased(user, product);

       // purchases.add(product);

        // Success
        return 5;
    }



//===============First Endpoint========================
    //get Products that the user can purchase
    public ArrayList<Product> getAffordableProducts(String userId) {
        User user = getUserById(userId);
        if (user == null) return null;

        ArrayList<Product> affordable = new ArrayList<>();

        for (Product p : productService.products) {
            if (p.getPrice() <= user.getBalance()) {
                affordable.add(p);
            }
        }
        return affordable;
    }

//========Second EndPoint=========
public ArrayList<Product> getProductsByCategoryAndPriceRange(String categoryId, int minPrice, int maxPrice) {
    ArrayList<Product> productsWithRang = new ArrayList<>();

    if (minPrice > maxPrice || minPrice < 0 || maxPrice < 0) {
        return null;
    }

    for (Product product : productService.products) {
        if (product.getCategoryId().equals(categoryId) &&
                product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
            productsWithRang.add(product);
        }
    }

    return productsWithRang;
}



    //=========Third Endpoint============
    public int topUpBalance(String userId, double amount) {

        //User not found
        User user = getUserById(userId);
        if (user == null) {
            return 1;
        }

        //Invalid amount (≤ 0)
        if (amount <= 0) {
            return 2;
        }

        // Increase balance
        user.setBalance(user.getBalance() + amount);

        //Success
        return 3;
    }


    public ArrayList<Product> getUserPurchases(String userId) {
        User user = getUserById(userId);
        if (user == null) {
            return null;
        }
        return user.getOrder();
    }






    private void addToPurchased(User user, Product product) {
        if (user.getOrder() == null) {
            user.setOrder(new ArrayList<>());
        }
        user.getOrder().add(product);
    }




//=============Fourth EndPint=========
    public ArrayList<Product> getUserPurchased(String userID) {
        for (User user : users) {
            if (user.getId().equalsIgnoreCase(userID)) {
                return user.getOrder();
            }
        }
        return new ArrayList<>();
    }




//================Fourth End Point===============
    public ArrayList<Product> getFavoriteCategoryProducts(String userId) {

        User user = getUserById(userId);
        if (user == null) {
            return null;
        }
        ArrayList<Product> purchases = user.getOrder();
        if (purchases == null || purchases.isEmpty()) {
            return new ArrayList<>();
        }


        String favCategory = null;
        int maxCount = 0;

        for (Product p : purchases) {// find frequent of category

            int count = 0;
            for (Product x : purchases) {
                if (x.getCategoryId().equalsIgnoreCase(p.getCategoryId())) {
                    count++;
                }
            }

            if (count > maxCount) {
                maxCount = count;
                favCategory = p.getCategoryId();
            }
        }

        if (favCategory == null) {
            return new ArrayList<>();
        }


        ArrayList<Product> favProduct = new ArrayList<>();

        for (Product p : productService.products) {
            if (p.getCategoryId().equalsIgnoreCase(favCategory)) {
                favProduct.add(p);
            }
        }

        return favProduct;
    }





//===================== Fifth End Point=================
    public int refundProduct(String userId, String productId, String merchantId) {

        // 0  User not found
        User user = getUserById(userId);
        if (user == null) {
            return 0;
        }

        // 1 Product not found
        Product product = productService.getProductById(productId);
        if (product == null) {
            return 1;
        }

        // 2  Merchant not found
        Merchant merchant = merchantService.getMerchantById(merchantId);
        if (merchant == null) {
            return 2;
        }

        // check if user actually purchased this product
        ArrayList<Product> purchases = user.getOrder();
        if (purchases == null || !purchases.contains(product)) {
            return 3; // product not purchased
        }

        // remove product from purchase history
        purchases.remove(product);

        // refund user balance
        user.setBalance(user.getBalance() + product.getPrice());

        // increase merchant stock
        MerchantStock stock = merchantStockService.checkStock(productId, merchantId);
        if (stock != null) {
            stock.setStock(stock.getStock() + 1);
        }

        return 4; // success
    }






}
