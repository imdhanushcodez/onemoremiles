package com.example.onemoremile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

public class loginpage extends AppCompatActivity {
    TextView tv;

    FirebaseAuth auth;

    EditText email,pass;
    AppCompatButton btn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loginpage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email = findViewById(R.id.loginemail);
        pass = findViewById(R.id.loginpass);
        btn = findViewById(R.id.logintohome);

        auth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emails = "";
                String passs = "";

                if (email != null && pass!=null) {
                    emails = email.getText().toString();
                    passs = pass.getText().toString();
                } else {
                    Toast.makeText(loginpage.this,  " error", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!emails.contains("@") && !emails.contains(".")){
                    email.setError("only valid emails");
                    Toast.makeText(loginpage.this,  " only valid emails", Toast.LENGTH_LONG).show();
                    return;

                }
                if(passs.length() < 6){
                    pass.setError("password should be more than 6 characters");
                    Toast.makeText(loginpage.this,  " password should be more than 6 characters", Toast.LENGTH_LONG).show();
                    return;

                }

                try{

                    auth.signInWithEmailAndPassword(emails,passs).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(loginpage.this,"succesfully logined",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(loginpage.this, homepage.class);
                                startActivity(i);
                            }
                            else Toast.makeText(loginpage.this,"Incorrect email or password",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                catch (Exception e){
                    Toast.makeText(loginpage.this,"please contact developer",Toast.LENGTH_LONG).show();
                }




            }
        });


        tv = findViewById(R.id.regtobtn);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(loginpage.this, regpage.class);
                startActivity(i);
            }
        });
    }
}