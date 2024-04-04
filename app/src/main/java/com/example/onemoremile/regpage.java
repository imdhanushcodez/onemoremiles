package com.example.onemoremile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class regpage extends AppCompatActivity {

    FirebaseAuth auth;
    EditText email,pass;
    AppCompatButton btn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_regpage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.regemail);
        pass = findViewById(R.id.regpass);


        btn = findViewById(R.id.regbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emails = "";
                String passs = "";

                if (email != null && pass!=null) {
                    emails = email.getText().toString();
                    passs = pass.getText().toString();
                } else {
                    Toast.makeText(regpage.this,  " error", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!emails.contains("@") && !emails.contains(".")){
                    email.setError("only valid emails");
                    Toast.makeText(regpage.this,  " only valid emails", Toast.LENGTH_LONG).show();
                    return;

                }
                if(passs.length() < 6){
                    pass.setError("password should be more than 6 characters");
                    Toast.makeText(regpage.this,  " password should be more than 6 characters", Toast.LENGTH_LONG).show();
                    return;

                }





                try {

                    if (emails.length() != 0 && passs.length() != 0) {
                    auth.createUserWithEmailAndPassword(emails,passs).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()) Toast.makeText(regpage.this,"Successfully registered",Toast.LENGTH_LONG).show();
                            else{
                                Toast.makeText(regpage.this,"Already registered or may be some issues",Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                    } else {
                        Toast.makeText(regpage.this, "Can't register", Toast.LENGTH_LONG).show();

                    }
                }
                catch (Exception e){
                    Toast.makeText(regpage.this, "please contact the developer", Toast.LENGTH_LONG).show();

                }

                










            }
        });
    }
}