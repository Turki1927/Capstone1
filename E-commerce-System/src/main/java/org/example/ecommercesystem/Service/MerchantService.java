package org.example.ecommercesystem.Service;

import org.example.ecommercesystem.Model.Merchant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {

    ArrayList<Merchant>  merchants= new ArrayList();

    public  ArrayList<Merchant> getAllMerchant(){
        return merchants;
    }


    public  boolean addMerchant(Merchant merchant){
        for (Merchant merchant1: merchants){
            if(merchant1.getId().equalsIgnoreCase(merchant.getId())){
                return false;
            }
        }
        merchants.add(merchant);
        return  true;
    }






    public  boolean updateMerchant (Merchant merchant , String id){
        for (Merchant merchant1 : merchants){
            if(merchant1.getId().equalsIgnoreCase(id)){
                merchants.set(merchants.indexOf(merchant1),merchant);
                return true;
            }

        }
        return  false;
    }


    public  boolean deletedMerchant (String id){
        for (Merchant merchant : merchants){
            if(merchant.getId().equalsIgnoreCase(id)){
                merchants.remove(merchant);
                return true;
            }
        }
        return  false;
    }



    public Merchant getMerchantById(String merchantId) {
        for (Merchant merchant : merchants) {
            if (merchant.getId().equalsIgnoreCase(merchantId)) {
                return merchant;
            }
        }
        return null;
    }



}
