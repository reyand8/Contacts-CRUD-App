package com.example.contacts_crud_app.models;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.contacts_crud_app.ContactsApi;
import com.example.contacts_crud_app.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactsViewModel extends ViewModel {

    private MutableLiveData<List<Contacts>> contactsLiveData;
    private ContactsApi contactsApi;

    public ContactsViewModel() {
        contactsLiveData = new MutableLiveData<>();
        contactsApi = RetrofitClient.getRetrofitInstance().create(ContactsApi.class);
        fetchContacts();
    }

    public LiveData<List<Contacts>> getContacts() {
        return contactsLiveData;
    }

    public void fetchContacts() {
        Call<List<Contacts>> call = contactsApi.getAllContacts();
        call.enqueue(new Callback<List<Contacts>>() {
            @Override
            public void onResponse(Call<List<Contacts>> call,
                                   Response<List<Contacts>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    contactsLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Contacts>> call, Throwable t) {
                Log.d("ContactsViewModel", "Error fetching contacts: " + t.getMessage());
            }
        });
    }

    public void addContact(Contacts contact) {
        Call<Contacts> call = contactsApi.createContact(contact);
        call.enqueue(new Callback<Contacts>() {
            @Override
            public void onResponse(Call<Contacts> call, Response<Contacts> response) {
                if (response.isSuccessful()) {
                    fetchContacts();
                }
            }

            @Override
            public void onFailure(Call<Contacts> call, Throwable t) {
                Log.d("ContactsViewModel", "Error adding contact: " + t.getMessage());
            }
        });
    }

    public void updateContact(String contactId, Contacts contact) {
        Call<Contacts> call = contactsApi.updateUser(contactId, contact);
        call.enqueue(new Callback<Contacts>() {
            @Override
            public void onResponse(Call<Contacts> call, Response<Contacts> response) {
                if (response.isSuccessful()) {
                    fetchContacts();
                } else {
                    Log.d("ContactsViewModel", "Error updating contact: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Contacts> call, Throwable t) {
                Log.d("ContactsViewModel", "Error updating contact: " + t.getMessage());
            }
        });
    }

    public void deleteContact(String contactId) {
        Call<Void> call = contactsApi.deleteUser(contactId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    fetchContacts();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ContactsViewModel", "Error deleting contact: " + t.getMessage());
            }
        });
    }
}