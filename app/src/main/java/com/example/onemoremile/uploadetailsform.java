package com.example.onemoremile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class uploadetailsform extends AppCompatActivity {

    AppCompatButton upbtn;
    ImageView imgupbtn;
    EditText name,loc,placename,des,number,rating,budget;
    Uri imguri;

    DatabaseReference db = FirebaseDatabase.getInstance().getReference("PLACES");
    StorageReference sb = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_uploadetailsform);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgupbtn = findViewById(R.id.imagebtn);
        upbtn = findViewById(R.id.uploadbtn);
        name = findViewById(R.id.upname);
        loc = findViewById(R.id.uploc);
        placename = findViewById(R.id.upplacename);
        des = findViewById(R.id.updes);
        number = findViewById(R.id.upnumber);
        rating = findViewById(R.id.uprating);
        budget = findViewById(R.id.upbudget);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if(o.getResultCode() == Activity.RESULT_OK){
                            Intent data = o.getData();
                            imguri = data.getData();
                            imgupbtn.setImageURI(imguri);
                        }
                        else {
                            Toast.makeText(uploadetailsform.this, "No image is selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        imgupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photopicker = new Intent();
                photopicker.setAction(Intent.ACTION_GET_CONTENT);
                photopicker.setType("image/*");
                activityResultLauncher.launch(photopicker);
            }
        });

        upbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names,locs,places,nums,dess,rates,budgets;
                names = name.getText().toString();
                locs = loc.getText().toString();
                places = placename.getText().toString();
                nums = number.getText().toString();
                dess = des.getText().toString();
                rates = rating.getText().toString();
                budgets = budget.getText().toString();
                if(names.isEmpty() || locs.isEmpty() || places.isEmpty() || nums.isEmpty() || dess.isEmpty() || rates.isEmpty() || budgets.isEmpty()){
                    Toast.makeText(uploadetailsform.this, "Please Enter all information", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    if(imguri!=null){
                        uploadtofirebase(imguri,names,locs,places,nums,dess,rates,budgets);
                    }
                    else{
                        Toast.makeText(uploadetailsform.this, "Please select image", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void uploadtofirebase(Uri uri,String names,String locs,String places,String nums,String dess,String rates, String budgets){
        StorageReference imgref = sb.child(names+locs+places);
        imgref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imgref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        dataclass dc = new dataclass(uri.toString(),names,locs,places,nums,dess,rates,budgets);
                        db.child(names+locs+places).setValue(dc);
                        Toast.makeText(uploadetailsform.this, "Succesfully uploaded", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(uploadetailsform.this,homepage.class);
                        startActivity(i);
                        finish();

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(uploadetailsform.this, "Uploading failure", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void onBackPressed() {
        // Call finish() to exit the app
        super.onBackPressed();
        //finish();
        finish();
    }

}