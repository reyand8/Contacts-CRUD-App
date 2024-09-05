package com.example.contacts_crud_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.contacts_crud_app.models.Contacts;
import com.example.contacts_crud_app.models.ContactsViewModel;

public class AddContactActivity extends AppCompatActivity {

    private EditText firstNameAdd,lastNameAdd,emailAdd,imageAdd;
    private ContactsViewModel contactsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_contact);

        Button btnSave = findViewById(R.id.btnSave);
        firstNameAdd = findViewById(R.id.addFirstName);
        lastNameAdd = findViewById(R.id.addLastName);
        emailAdd = findViewById(R.id.addEmail);
        imageAdd = findViewById(R.id.addImage);

        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);

        btnSave.setOnClickListener(v -> {
            String firstName = firstNameAdd.getText().toString();
            String lastName = lastNameAdd.getText().toString();
            String email = emailAdd.getText().toString();
            String image = imageAdd.getText().toString();

            if (firstName.isEmpty() || lastName.isEmpty()
                    || email.isEmpty() || image.isEmpty()) {
                Toast.makeText(this, "Your field is empty",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            Contacts newContact = new Contacts();
            newContact.setFirstName(firstName);
            newContact.setLastName(lastName);
            newContact.setEmail(email);
            newContact.setAvatar(image);

            contactsViewModel.addContact(newContact);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("isAdded", true);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
    }
}