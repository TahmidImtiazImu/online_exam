 package com.example.online_exam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.jar.Attributes;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    private Button saveBtn, changePassBtn;
    private ImageView chooseImg;
    private CircleImageView showImg;
    private Uri filePath;
    private EditText fullNameEdit, emailEdit, userEdit, insEdit, genderEdit, passEdit, roleEdit;
    private final int PICK_IMAGE_REQUEST = 22;

    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    DatabaseReference new_ref;
    FirebaseAuth mAuth;
    FirebaseUser user;

    SharedPreferences sp;

    String _NAME, _EMAIL, _PASSWORD, _FULLNAME, _INSTITUTION, _GENDER, _ROLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().hide();

        saveBtn = findViewById(R.id.btnSave);
        showImg = findViewById(R.id.imgDP);
        chooseImg = findViewById(R.id.imgChooseDP);
        fullNameEdit = findViewById(R.id.editFullName);
        insEdit = findViewById(R.id.editIns);
        emailEdit = findViewById(R.id.editEmail);
        emailEdit.setEnabled(false);
        userEdit = findViewById(R.id.editUser);
        userEdit.setEnabled(false);
        genderEdit = findViewById(R.id.editGender);
        genderEdit.setEnabled(false);
        //passEdit = findViewById(R.id.editPass);
        roleEdit = findViewById(R.id.editRole);
        roleEdit.setEnabled(false);
        changePassBtn = findViewById(R.id.btnChangePass);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        sp = getApplicationContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        _NAME = sp.getString("UserName", "");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(_NAME);

        showAllUserData();

        chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Imageview clickable working");
                selectImage();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
                update();
            }
        });


        changePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetPass = new EditText(v.getContext());

                final AlertDialog.Builder passResetDialog = new AlertDialog.Builder(v.getContext());
                passResetDialog.setTitle("Update Password?");
                passResetDialog.setMessage("Enter New Password here");
                passResetDialog.setView(resetPass);

                passResetDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String newPassword = resetPass.getText().toString();
                        user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditProfile.this, "Password Reset Successfully.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditProfile.this, "Password Reset Failed.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passResetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close
                    }
                });

                passResetDialog.create().show();
            }
        });

    }

    private void showAllUserData() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                 _FULLNAME = snapshot.child("Enter_name").getValue(String.class);
                 _EMAIL = snapshot.child("Email").getValue(String.class);
                 _GENDER = snapshot.child("Gender").getValue(String.class);
                 _INSTITUTION = snapshot.child("Institution").getValue(String.class);
                 _ROLE = snapshot.child("Role").getValue(String.class);
                 String loadURL = snapshot.child("Picture URL").getValue(String.class);

                fullNameEdit.setText(_FULLNAME);
                emailEdit.setText(_EMAIL);
                userEdit.setText(_NAME);
                genderEdit.setText(_GENDER);
                insEdit.setText(_INSTITUTION);
                roleEdit.setText(_ROLE);
                if(loadURL.length() > 1)
                    Picasso.get().load(loadURL).into(showImg);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        System.out.println(_NAME);
    }

    //select Image Method
    private void selectImage(){

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);

    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                showImg.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }


    // UploadImage method
    private void uploadImage()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            // Progress Listener for loading
            // percentage on the dialog box
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            taskSnapshot -> {

                                // Image uploaded successfully
                                // Dismiss dialog
                                progressDialog.dismiss();
                                Toast
                                        .makeText(EditProfile.this,
                                                "Image Uploaded!!",
                                                Toast.LENGTH_SHORT)
                                        .show();


                                //realtime database URL
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while(!uriTask.isComplete()) ;
                                Uri uri = uriTask.getResult() ;

                                String imgName = Objects.requireNonNull(uri).toString() ;

                                databaseReference.child("Picture URL").setValue(imgName);

                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(EditProfile.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            taskSnapshot -> {
                                double progress
                                        = (100.0
                                        * taskSnapshot.getBytesTransferred()
                                        / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage(
                                        "Uploaded "
                                                + (int)progress + "%");
                            });
        }
    }

    private void update(){
        //String fullName = fullNameEdit.getText().toString();
        //databaseReference.child("Enter_name").setValue(fullName); //change korsi
        isNameChanged();
        isInsChanged();
        if (isNameChanged() || isInsChanged() ){
            Toast.makeText(EditProfile.this, "Data has been updated", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(EditProfile.this, "Data is same", Toast.LENGTH_LONG).show();
    }


    private boolean isInsChanged() {
        if(!_INSTITUTION.equals(insEdit.getText().toString())){
            databaseReference.child("Institution").setValue(insEdit.getText().toString());
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isNameChanged() {
        if(!_NAME.equals(fullNameEdit.getText().toString())){
            databaseReference.child("Enter_name").setValue(fullNameEdit.getText().toString());

            new_ref = FirebaseDatabase.getInstance().getReference("courses");

            new_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        String username = dataSnapshot.child("currentUser").getValue(String.class);

                        if(_NAME.equals(username)) {

                            dataSnapshot.child("teacherEnter_name").getRef().setValue(fullNameEdit.getText().toString());
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });


            return true;
        }
        else{
            return false;
        }
    }

}