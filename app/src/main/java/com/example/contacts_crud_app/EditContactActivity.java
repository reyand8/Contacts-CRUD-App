package com.example.contacts_crud_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.contacts_crud_app.models.Contacts;
import com.example.contacts_crud_app.models.ContactsViewModel;
import com.squareup.picasso.Picasso;


public class EditContactActivity extends AppCompatActivity {

    private EditText firstNameEditText,lastNameEditText,
            emailEditText;
    private ImageView imageEdit;
    private Button saveButton, cancelButton;

    private ContactsViewModel contactsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.editEmail);
        imageEdit = findViewById(R.id.imageEdit);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        contactsViewModel =new ViewModelProvider(this).get(ContactsViewModel.class);

        Intent intent = getIntent();
        String contactId = intent.getStringExtra("contact_id");
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");
        String email = intent.getStringExtra("email");
        String image = intent.getStringExtra("imagePath");
        firstNameEditText.setText(firstName);
        lastNameEditText.setText(lastName);
        emailEditText.setText(email);
        Picasso.get()
                .load(image)
                .into(imageEdit);

        saveButton.setOnClickListener(v -> {
            String updatedFirstName = firstNameEditText.getText().toString();
            String updatedLastName = lastNameEditText.getText().toString();
            String updatedEmail = emailEditText.getText().toString();

            Contacts updatedContact = new Contacts();
            updatedContact.setFirstName(updatedFirstName);
            updatedContact.setLastName(updatedLastName);
            updatedContact.setEmail(updatedEmail);
            updatedContact.setAvatar(image);

            contactsViewModel.updateContact(contactId, updatedContact);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("isUpdated", true);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });

        cancelButton.setOnClickListener(v -> finish());
    }
}