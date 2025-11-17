package org.example.ecommercesystem.Service;


import lombok.RequiredArgsConstructor;
import org.example.ecommercesystem.Model.Merchant;
import org.example.ecommercesystem.Model.MerchantStock;
import org.example.ecommercesystem.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {
    ArrayList<MerchantStock> merchantStockList = new ArrayList<>();

   private final MerchantService merchantService;
    private final ProductService productService;

    public  ArrayList<MerchantStock> getMerchantStock(){
        return  merchantStockList;
    }

    public int addMerchantStock(MerchantStock merchantStock) {

        for (Merchant merchant : merchantService.getAllMerchant()){
            if (merchant.getId().equalsIgnoreCase(merchantStock.getMerchantId())){

                for (Product product : productService.getProduct()) {
                    if (product.getId().equalsIgnoreCase(merchantStock.getProductid())) {

                        for (MerchantStock merchantStock1 : merchantStockList) {
                            if (merchantStock1.getId().equalsIgnoreCase(merchantStock.getId())) {

                                // 1: Stock already exists
                                return 1;
                            }
                        }

                        merchantStockList.add(merchantStock);

                        // 2: Stock added
                        return 2;
                    }
                }

                // 3: Product not found for this merchant
                return 3;
            }
        }

        // 4: Merchant not found
        return 4;
    }


    public int updateMerchantStock(String stockID, MerchantStock updatedMerchantStock) {

        // Check if product exists
        for (Product product : productService.getProduct()) {

            if (product.getId().equalsIgnoreCase(updatedMerchantStock.getProductid())) {

                // Check if stock exists
                for (MerchantStock merchantStockU : merchantStockList) {

                    if (merchantStockU.getId().equalsIgnoreCase(stockID)) {

                        merchantStockList.set(
                                merchantStockList.indexOf(merchantStockU), updatedMerchantStock);

                        // 1: Stock updated
                        return 1;
                    }
                }

                // 2: stock ID not found
                return 2;
            }
        }

        // 3: product does not exist
        return 3;
    }


    public boolean deleteMerchantStock(String id){
        for (MerchantStock merchant1 : merchantStockList){
            if (merchant1.getId().equals(id)){
                merchantStockList.remove(merchant1);
                return true;
            }
        }
        return false;
    }


    public MerchantStock checkStock(String productId, String merchantId) {
        for (MerchantStock stock1 : merchantStockList) {
            if (stock1.getProductid().equals(productId) && stock1.getMerchantId().equals(merchantId)) {
                return stock1;
            }
        }
        return null;
    }



    //==========EndPoint===========
    public int addStockToMerchant(String productId, String merchantId, int amount) {

        if(amount<0){
            return 0;
        }
        // Check if product exists
        Product product = productService.getProductById(productId);
        if (product == null) {
            return 1; // Product not found
        }

        // Check if merchant exists
        Merchant merchant = merchantService.getMerchantById(merchantId);
        if (merchant == null) {
            return 2; // Merchant not found
        }

        // Check if merchant already has the product in stock
        for (MerchantStock stock1 : merchantStockList) {
            if (stock1.getProductid().equals(productId) && stock1.getMerchantId().equals(merchantId)) {
                stock1.setStock(stock1.getStock() + amount);
                return 4; // Stock updated
            }
        }

        // Merchant stock with this ID does not found
        return 3;
    }



}
