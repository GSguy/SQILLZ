package com.example.sqillz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String email, password;
    private EditText emailET, passwordET;
    private Button loginBTN, signupBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        loginBTN = findViewById(R.id.loginBTN);
        signupBTN = findViewById(R.id.signupBTN);

        mAuth = FirebaseAuth.getInstance();

        signupBTN.setOnClickListener(v -> regFunc(v));
        loginBTN.setOnClickListener(v -> loginFunc(v));
    }

    public void loginFunc(View view) {
        if (emailET.getText().toString().isEmpty() || passwordET.getText().toString().isEmpty())
            Toast.makeText(LoginActivity.this, "Please fill the lines.",
                    Toast.LENGTH_SHORT).show();
        else
            mAuth.signInWithEmailAndPassword(emailET.getText().toString().trim(), passwordET.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "login ok.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, GameActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "login failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void regFunc(View view){
        if (emailET.getText().toString().isEmpty() || passwordET.getText().toString().isEmpty())
            Toast.makeText( LoginActivity.this,"Please fill the lines.",
                    Toast.LENGTH_SHORT).show();

        else
            mAuth.createUserWithEmailAndPassword(emailET.getText().toString().trim(),passwordET.getText().toString().trim())
                    .addOnCompleteListener(this, task -> {
                        if(task.isSuccessful()){
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText( LoginActivity.this,"REG ok.",
                                    Toast.LENGTH_SHORT).show();
                        //    Intent intent = new Intent( LoginActivity.this, ProfileView.class );
                        //    startActivity( intent );
                        }else{
                            // If sign in fails, display a message to the user.
                            Toast.makeText( LoginActivity.this,"REG failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
    }

}