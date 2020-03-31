package com.example.emailverify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText editText1,editText2;
    TextView signup_new;
    Button login_button;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText1 = findViewById(R.id.login_email_id);
        editText2 = findViewById(R.id.login_password_id);
        signup_new = findViewById(  R.id.account_create_id );
        login_button = findViewById(R.id.button_login_id);
        mAuth = FirebaseAuth.getInstance();

        final ProgressDialog progressDialog = new ProgressDialog( this );
        progressDialog.setMessage( "Please wait...." );

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String email = editText1.getText().toString().trim();
               String password = editText2.getText().toString();
               progressDialog.show();
               mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                          if (mAuth.getCurrentUser().isEmailVerified()){
                              progressDialog.dismiss();
                              Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                              startActivity(intent);
                              Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                          }else {
                              progressDialog.dismiss();
                              Toast.makeText(LoginActivity.this, "Verify your email first", Toast.LENGTH_SHORT).show();
                          }
                       }else {
                           progressDialog.dismiss();
                           Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                       }
                   }
               });
            }
        });

        signup_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}
