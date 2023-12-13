package com.mianasad.chatsapp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mianasad.chatsapp.R;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    TextView viewusername;
    ImageView viewimage;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;

    private String ImageURIacessToken;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        viewusername = findViewById(R.id.viewusername);
        viewimage = findViewById(R.id.viewuserphoto);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(USER_SERVICE);
        firebaseStorage = FirebaseStorage.getInstance();


        storageReference =firebaseStorage.getReference();
        storageReference.child("Profiles").
                child(firebaseAuth.getUid()).
                child("Profile Pic").
                getDownloadUrl().
                addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                ImageURIacessToken= uri.toString();
                Picasso.get().load(uri).into(viewimage);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser!=null){
            viewusername.setText(firebaseUser.getPhoneNumber());
        }else{
            startActivity(new Intent(this, PhoneNumberActivity.class));
        }
    }

    public void logout(View view) {
        firebaseAuth.signOut();
        startActivity(new Intent(this, PhoneNumberActivity.class));
    }
}

