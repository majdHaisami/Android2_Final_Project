package com.example.appfirebase;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
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

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Update_Delet_Dish extends AppCompatActivity {
TextInputLayout desc,qty,pri;
TextView Dishname;
ImageButton imageButton;
Uri imageuri;
String dburi;
private  Uri mCropimageuri;
Button Updatedish;
String desciption,quantity,price,dishes,ChefId;
String RandomUId;
StorageReference ref;
FirebaseStorage storage;
StorageReference storageReference;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
Button Deletdish;
FirebaseAuth FAuth;
String ID;
private ProgressDialog progressDialog;
DatabaseReference dataa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delet_dish);
        desc=(TextInputLayout)findViewById(R.id.Description123);
        qty=(TextInputLayout)findViewById(R.id.Quantity123);
        pri=(TextInputLayout)findViewById(R.id.price123);
        Dishname = (TextView)findViewById(R.id.dishname123);
        imageButton=(ImageButton)findViewById(R.id.imageIv123);
        Updatedish=(Button)findViewById(R.id.update);
        Deletdish=(Button)findViewById(R.id.del);
        ID=getIntent().getStringExtra("updatedeleteddish");
        String userid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        dataa=firebaseDatabase.getInstance().getReference("Chef").child(userid);
        dataa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                ChefClass chefc = snapshot.getValue(ChefClass.class);

                String userid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                progressDialog=new ProgressDialog(Update_Delet_Dish.this);
                databaseReference=FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(userid).child(ID);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                        MealDetailse updateDishModel = snapshot.getValue(MealDetailse.class);
                        desc.getEditText().setText(updateDishModel.getDescription());
                        qty.getEditText().setText(updateDishModel.getQuantity());
                        Dishname.setText("Dish name : " + updateDishModel.getDishes());
                        dishes = updateDishModel.getDishes();
                        pri.getEditText().setText(updateDishModel.getPrice());
                        Glide.with(Update_Delet_Dish.this).load(updateDishModel.getImageURL()).into(imageButton);
                        dburi = updateDishModel.getImageURL();


                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
                FAuth =FirebaseAuth.getInstance();
                databaseReference=firebaseDatabase.getInstance().getReference(userid);
                storage=FirebaseStorage.getInstance();
                storageReference=storage.getReference();
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        choessPicture();
                    }
                });
                Deletdish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Update_Delet_Dish.this,"hhhhhhhhhhhhhhhh",Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(Update_Delet_Dish.this);
                        builder.setMessage("Are you sure ypu want to delete Dish?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                firebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ID).removeValue();
                                AlertDialog.Builder food = new AlertDialog.Builder(Update_Delet_Dish.this);
                                food.setMessage("Your Dish has been Deleted");
                                food.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(Update_Delet_Dish.this,ChefFoodPanel_BottomNavigation.class));
                                    }
                                });
                                AlertDialog alertt = food.create();
                                alertt.show();

                            }
                        });

                    }
                });
Updatedish.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        desciption = desc.getEditText().getText().toString().trim();
        quantity=qty.getEditText().getText().toString().trim();
        price=pri.getEditText().getText().toString().trim();
        if (isValid()){
            if (imageuri != null)
            {
                uploadImage();
            }else {
                updatedesc(dburi);
            }


        }




    }
});


            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

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
            imageuri=data.getData();
            imageButton.setImageURI(imageuri);
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
        if (TextUtils.isEmpty(desciption)){
            desc.setErrorEnabled(true);
            desc.setError("Description is Reqauried");
        }else {
            desc.setError(null);
            isValiddescription=true;
        }
        if (TextUtils.isEmpty(quantity)){
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
    private void updatedesc(String uri){
        ChefId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        MealDetailse info = new MealDetailse(dishes,quantity,price,desciption,String.valueOf(uri),ID,ChefId);
        firebaseDatabase.getInstance().getReference("FoodSupplyDetails")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ID)
                .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull  Task<Void> task) {
                progressDialog.dismiss();
                Toast.makeText(Update_Delet_Dish.this,"Dish Updated Sucssefully",Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void uploadImage(){
        if (imageuri != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploding...");
            progressDialog.show();
            RandomUId = UUID.randomUUID().toString();
            ref=storageReference.child(RandomUId);
            ChefId=FirebaseAuth.getInstance().getCurrentUser().getUid();
            ref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(@NonNull  UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(@NonNull  Uri uri) {
                            MealDetailse info = new MealDetailse(dishes,quantity,price,desciption,String.valueOf(uri),ID,ChefId);
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