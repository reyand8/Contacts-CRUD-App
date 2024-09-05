package com.example.contacts_crud_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contacts_crud_app.adapters.ContactsAdapter;
import com.example.contacts_crud_app.models.ContactsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_EDIT = 100;
    private RecyclerView recyclerView;
    private ContactsAdapter contactsAdapter;
    private ContactsViewModel contactsViewModel;
    private FloatingActionButton fabAddContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);

        contactsAdapter = new ContactsAdapter(this, new ArrayList<>(), contactsViewModel);
        recyclerView.setAdapter(contactsAdapter);

        fabAddContact = findViewById(R.id.fabAddContact);
        fabAddContact.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
            startActivityForResult(intent, REQUEST_CODE_EDIT);
        });

        contactsViewModel.fetchContacts();

        contactsViewModel.getContacts().observe(this, contacts -> {
            if (contacts != null) {
                contactsAdapter.setContactsList(contacts);
                contactsAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("One!", String.valueOf(requestCode));
        Log.d("One!", String.valueOf(REQUEST_CODE_EDIT));
        Log.d("One!", String.valueOf(resultCode));
        Log.d("One!", String.valueOf(Activity.RESULT_OK));
        Log.d("One!", String.valueOf(data));
        if (requestCode == REQUEST_CODE_EDIT && resultCode == Activity.RESULT_OK && data != null) {
            boolean isUpdated = data.getBooleanExtra("isUpdated", false);
            boolean isAdded = data.getBooleanExtra("isAdded", false);

            if (isUpdated || isAdded) {
                contactsViewModel.fetchContacts();
            }
        }
    }
}
