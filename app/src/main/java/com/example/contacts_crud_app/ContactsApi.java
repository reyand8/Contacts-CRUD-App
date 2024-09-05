package com.example.contacts_crud_app;

import com.example.contacts_crud_app.models.Contacts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface ContactsApi {
    @GET("/users/")
    Call<List<Contacts>> getAllContacts();

    @GET("/users/{id}")
    Call<Contacts> getContactById(@Path("id") String userId);

    @POST("users")
    Call<Contacts> createContact(@Body Contacts contact);

    @PUT("/users/{id}")
    Call<Contacts> updateUser(@Path("id") String userId, @Body Contacts contact);

    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") String userId);
}