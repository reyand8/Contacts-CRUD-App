package com.example.contacts_crud_app.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contacts_crud_app.EditContactActivity;
import com.example.contacts_crud_app.R;
import com.example.contacts_crud_app.models.Contacts;
import com.example.contacts_crud_app.models.ContactsViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ContactsAdapter extends RecyclerView
        .Adapter<ContactsAdapter.ViewHolder> {

    private ContactsViewModel contactsViewModel;
    private List<Contacts> contactsList;
    private Context context;
    private static final int REQUEST_CODE_EDIT = 100;

    public ContactsAdapter(Context context, List<Contacts> contactsList,
                           ContactsViewModel contactsViewModel) {
        this.contactsList = contactsList;
        this.context = context;
        this.contactsViewModel = contactsViewModel;
    }

    public void setContactsList(List<Contacts> contactsList) {
        this.contactsList = contactsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder holder, int position) {
        Contacts contact = contactsList.get(position);
        holder.firstNameTextView.setText(contact.getFirstName());
        holder.lastNameTextView.setText(contact.getLastName());
        holder.emailTextView.setText(contact.getEmail());
        Picasso.get().load(contact.getAvatar())
                .into(holder.avatarImageView);

        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditContactActivity.class);
            intent.putExtra("contact_id", contact.getId());
            intent.putExtra("firstName", contact.getFirstName());
            intent.putExtra("lastName", contact.getLastName());
            intent.putExtra("email", contact.getEmail());
            intent.putExtra("imagePath", contact.getAvatar());
            ((Activity) context).startActivityForResult(intent, REQUEST_CODE_EDIT);
        });

        holder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete contact")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        contactsViewModel.deleteContact(contact.getId());
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImageView;
        TextView firstNameTextView;
        TextView lastNameTextView;
        TextView emailTextView;
        Button editButton;
        Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.contactImg);
            firstNameTextView = itemView.findViewById(R.id.contactFirstName);
            lastNameTextView = itemView.findViewById(R.id.contactLastName);

            emailTextView = itemView.findViewById(R.id.email);
            editButton = itemView.findViewById(R.id.editBtn);
            deleteButton = itemView.findViewById(R.id.deleteBtn);
        }
    }
}