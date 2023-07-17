package com.example.appfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appfirebase.Class.ChefClass;
import com.example.appfirebase.Class.MealDetailse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class add_wagba_only extends AppCompatActivity {
    ImageButton imageupload;
    Button post_dish;
    Spinner Dishes;
    TextInputLayout desc,qty,pri;
    String description,quntitiy,price,dishes;
    Uri imageUri;
    private Uri mcropimageuri;
    private Uri mCropimageuri;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference dataa;
    FirebaseAuth FAuth;
    StorageReference ref;
    String ChefId;
    String RandomUId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wagba_only);
        Log.d("noooooooooooooooo","nnnnnnnnooooooooooooooo");

        storage = FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        Dishes = (Spinner) findViewById(R.id.spinner);
        desc=(TextInputLayout)findViewById(R.id.Description);
        qty=(TextInputLayout)findViewById(R.id.Quantity);
        pri=(TextInputLayout)findViewById(R.id.price);
        post_dish=(Button)findViewById(R.id.vost);
        FAuth=FirebaseAuth.getInstance();

        databaseReference=firebaseDatabase.getInstance().getReference("FoodSupplyDetails");

        try {
            String userid =FirebaseAuth.getInstance().getCurrentUser().getUid();

            dataa=firebaseDatabase.getInstance().getReference("Chef").child(userid);
            dataa.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshots) {
                    ChefClass chefc = dataSnapshots.getValue(ChefClass.class);


                    imageupload=(ImageButton)findViewById(R.id.imageIv);
                    imageupload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            choessPicture();
                        }
                    });
                    post_dish.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dishes =Dishes.getSelectedItem().toString().trim();
                            description=desc.getEditText().getText().toString().trim();
                            quntitiy=qty.getEditText().getText().toString().trim();
                            price=pri.getEditText().getText().toString().trim();
                            Log.e("npppppppppppppppppppppppppppppppppppppp","dpooooooooooooooooooooooooooooooooooooooooooooooooo");
                            if (isValid()){
                                uploadImage(imageUri);

                            }

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(),"hhhhhhhhhh",Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();

        }





    }
    private void choessPicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null){
            imageUri=data.getData();
            imageupload.setImageURI(imageUri);
        }
    }
    private boolean isValid(){
        desc.setErrorEnabled(false);
        desc.setError("");
        qty.setErrorEnabled(false);
        qty.setError("");
        pri.setErrorEnabled(false);
        pri.setError("");
        boolean isValiddescription = false,isValidprice = false,isValidqty =false,isvalid=false;
        if (TextUtils.isEmpty(description)){
            desc.setErrorEnabled(true);
            desc.setError("Description is Reqauried");
        }else {
            desc.setError(null);
            isValiddescription=true;
        }
        if (TextUtils.isEmpty(quntitiy)){
            qty.setErrorEnabled(true);
            qty.setError("quntitiy is Reqauried");
        }else {
            qty.setError(null);
            isValidqty=true;
        }
        if (TextUtils.isEmpty(price)){
            pri.setErrorEnabled(true);
            pri.setError("price is Reqauried");
        }else {
            isValidprice=true;
        }
        isvalid= (isValiddescription && isValidqty && isValidprice);
        return isvalid;
    }
    private void uploadImage(Uri imageUri1){
        if (imageUri1 != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploding...");
            progressDialog.show();
            RandomUId = UUID.randomUUID().toString();
            ref=storageReference.child(RandomUId);
            ChefId=FirebaseAuth.getInstance().getCurrentUser().getUid();
            ref.putFile(imageUri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(@NonNull  UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(@NonNull  Uri uri) {
                            MealDetailse info = new MealDetailse(dishes,quntitiy,price,description,String.valueOf(uri),RandomUId,ChefId);
                            firebaseDatabase.getInstance().getReference("FoodSupplyDetails")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUId)
                                    .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"Doneeeeeeeeeeeee",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull  Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"NOOOOOOOOOOOOOODoneeeeeeeeeeeee3",Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressDialog.setMessage("Uplpded " + (int) progress + "%");
                    progressDialog.setCanceledOnTouchOutside(false);
                }
            });
        }

    }
}