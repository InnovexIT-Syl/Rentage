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
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class ProfileActivity extends AppCompatActivity {
    Uri image_uri = null;
    String cameraPermission[];
    String storagePermission[];
    String storagePath = "user_profile_image";
    private static final String TAG = "";
    private Toolbar toolbarLogSign;

    private ImageView profileImage, previousProfileImage;
    private TextView chooseProfileImage, haveProfileImage;
    private EditText update_name;
    private Button updateButton;

    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;

    private String uid = null;

    // firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    // storage
    private StorageReference storageReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
    }

    private void init() {
        toolbarLogSign = findViewById(R.id.settings_in_toolbar);

        setSupportActionBar(toolbarLogSign);
        toolbarLogSign.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbarLogSign.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().setTitle("Update profile");
        profileImage = findViewById(R.id.profile_image);
        update_name = findViewById(R.id.update_name);
        previousProfileImage = findViewById(R.id.previous_profile_image);
        chooseProfileImage = findViewById(R.id.chooseProfilePicture);
        updateButton = findViewById(R.id.update);
        haveProfileImage = findViewById(R.id.haveProfileImage);

        progressDialog = new ProgressDialog(this);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        // init firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        storageReference = FirebaseStorage.getInstance().getReference(); // firebase storage reference

        //camera permission
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        //storage permission
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        chooseProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageImportDialog();
            }
        });

        checkUserStatus();
        if (uid != null) {

            // databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

            Query query = databaseReference.orderByChild("email").equalTo(firebaseUser.getEmail());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // checks until required data got
                    for (DataSnapshot data_snapshot : dataSnapshot.getChildren()) {

                        // get data
                        String name = data_snapshot.child("name").getValue().toString();
                        String email = data_snapshot.child("email").getValue().toString();
                        String profileImage = data_snapshot.child("profile_image").getValue().toString();

                        // set data
                        update_name.setText(name);

                        // for profile photo
                        try {
                            // if image is received then set
                            Picasso.get().load(profileImage).into(previousProfileImage);
                        } catch (Exception e) {
                            // if there is any exception while setting image then set default
//                            Picasso.get().load(R.drawable.index).into(previousProfileImage);
                            haveProfileImage.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void showImageImportDialog() {
        //items to display in delay
        String[] items = {"Camera", "Gallery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        //set title
        dialog.setTitle("Select Image");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    //camera option clicked
                    if (!checkCameraPermission()) {
                        //camera permission not allowed
                        requestCameraPermission();
                    } else {
                        //permission allowed,take picture
                        pickCamera();
                    }
                }
                if (which == 1) {
                    //gallery option clicked
                    if (!checkStoragePermission()) {
                        //Storage permission not allowed
                        requestStoragePermission();
                    } else {
                        //permission allowed,take picture
                        pickGallery();
                    }
                }
            }
        });
        dialog.create().show(); //show dialog
    }

    private void pickCamera() {

        //intent to take image from camera,it will also be save to storage to get high image
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New picture");//title of the picture
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image demo description");

        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void pickGallery() {
        //intent to pick image from gallery
        Intent intent = new Intent(Intent.ACTION_PICK);

        //set intent type to image
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        // Check camera permission and return the result
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);

        boolean result_1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result_1;
    }

    //Handle Permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && writeStorageAccepted) {
                        pickCamera();
                    } else {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case STORAGE_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted) {
                        pickGallery();
                    } else {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    //Handle Image result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                image_uri = data.getData();
                profileImage.setImageURI(image_uri);
                profileImage.invalidate();
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                image_uri = data.getData();
                profileImage.setImageURI(image_uri);
                profileImage.invalidate();
            }
        }
    }

    //get image extension
    public String getFileExtension(Uri imageUrl) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUrl));
    }

    private void saveData() {
        progressDialog.setMessage("Updating profile..");
        progressDialog.show();
        StorageReference reference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(image_uri));

        reference.putFile(image_uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        Uri downloadUri = uriTask.getResult();

                        //getting image url
                        progressDialog.dismiss();
                        Toast.makeText(ProfileActivity.this, "Profile updated successfully..", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(ProfileActivity.this, "" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkUserStatus() {
        if (firebaseUser != null) {
            uid = firebaseUser.getUid();
        }
    }
}
