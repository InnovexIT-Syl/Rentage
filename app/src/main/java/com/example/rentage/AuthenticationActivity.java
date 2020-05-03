package com.example.rentage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class AuthenticationActivity extends AppCompatActivity {
    private static final String TAG = "";
    private Toolbar toolbarLogSign;

    private EditText register_name, register_email, register_password, register_confirmPassword;
    private Button register_button;
    ProgressDialog progressDialog;
    // declare firebase auth
    private FirebaseAuth firebaseAuth;
    // storage
    private StorageReference storageReference;

    private EditText loginEmail,loginPassword;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        initialize();
    }

    private void initialize() {

        toolbarLogSign = findViewById(R.id.login_sign_in_toolbar);

        setSupportActionBar(toolbarLogSign);
        toolbarLogSign.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbarLogSign.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().setTitle("Login / Sign up");

        register_name = findViewById(R.id.register_name);
        register_email = findViewById(R.id.register_email);
        register_password = findViewById(R.id.register_password);
        register_confirmPassword = findViewById(R.id.register_confirm_password);
        register_button = findViewById(R.id.register_button);

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton  = findViewById(R.id.logInButton);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User...");

        firebaseAuth = FirebaseAuth.getInstance();

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get input value
                String name = register_name.getText().toString().trim();
                String email = register_email.getText().toString().trim();
                String password = register_password.getText().toString().trim();
                String confirm_password = register_confirmPassword.getText().toString().trim();

                // validate
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    // set error and focus to email edit text
                    register_email.setError("Invalid Email");
                    register_email.setFocusable(true);
                } else if (password.length() < 6) {
                    register_password.setError("Password length at least 6 characters or password does not match");
                    register_password.setFocusable(true);
                } else if (!password.equals(confirm_password)) {
                    register_confirmPassword.setError("Password does not match");
                    register_confirmPassword.setFocusable(true);
                } else {
                    registerUser(email, password, name);
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();
                loginUser(email,password);
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //  go previous activity
        return super.onSupportNavigateUp();
    }


    private void loginUser(String email, String password) {
        // show progress dialog
        progressDialog.setMessage("Logging in...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // dismiss progress dialog
                            progressDialog.dismiss();

                            // Sign in success, update UI with the signed-in user'AdapterPosts information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(AuthenticationActivity.this, "You are now login..", Toast.LENGTH_SHORT).show();

                        } else {
                            // dismiss progress dialog
                            progressDialog.dismiss();

                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // dismiss progress dialog
                progressDialog.dismiss();
                // error, get and show error message
                Toast.makeText(AuthenticationActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerUser(String email, String password, final String name) {
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            progressDialog.dismiss();
                            // Sign in success, dismiss dialog and start register activity
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            // get user email and uid from auth
                            assert user != null;
                            final String email = user.getEmail();
                            final String uid = user.getUid();

                            HashMap<Object, String> hashMap = new HashMap<>();

                            // put data into hash map
                            hashMap.put("email", email);
                            hashMap.put("uid", uid);
                            hashMap.put("name", name);
                            hashMap.put("profile_image","");

                            // firebase database instance
                            FirebaseDatabase database = FirebaseDatabase.getInstance();

                            // path to store user data named "Users"
                            DatabaseReference databaseReference = database.getReference("Users");

                            // put data within hash map in database
                            databaseReference.child(uid).setValue(hashMap);
                            Toast.makeText(AuthenticationActivity.this, "Registration successfully finished. Now you can login.", Toast.LENGTH_SHORT).show();
                            register_name.setText("");
                            register_email.setText("");
                            register_password.setText("");
                            register_confirmPassword.setText("");

                        } else {
                            progressDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            Toast.makeText(AuthenticationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // error , dismiss progress dialog and get and show the error message.
                progressDialog.dismiss();
                Toast.makeText(AuthenticationActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
