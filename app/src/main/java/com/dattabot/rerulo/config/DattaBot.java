package com.dattabot.rerulo.config;

import com.dattabot.rerulo.model.RestModel.CategoryModel;
import com.dattabot.rerulo.model.RestModel.ProductModel;
import com.dattabot.rerulo.model.RestModel.StoreModel;
import com.dattabot.rerulo.model.RestModel.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by alhamwa on 10/30/17.
 */

public interface DattaBot {
    @GET("products_cat.php")
    Call<List<ProductModel>> getProductCategory(@Query("w_id") String idStore, @Query("cat_id") String catId);

    @GET("wholesale_cat.php")
    Call<List<CategoryModel>> getStoreCategory(@Query("w") String wholeSaleCode);

    @GET("user.php")
    Call<List<UserModel>> getCurrentUser(@Query("u") String userPhone, @Query("p") String pass);

    @GET("wholesale.php")
    Call<List<StoreModel>> getStore(@Query("w_province") String province, @Query("w_district") String district, @Query("search") String keyword);
}
