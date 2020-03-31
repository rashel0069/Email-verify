package com.example.emailverify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    EditText user_email,user_pass;
    Button singup_button;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        user_email = findViewById(R.id.singup_email_id);
        user_pass = findViewById(R.id.signup_pass_id);
        singup_button  =findViewById(R.id.signup_button_id);
        
        mAuth = FirebaseAuth.getInstance();

        final ProgressDialog progressDialog = new ProgressDialog( this );
        progressDialog.setMessage( "Please Wait...." );

        singup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = user_email.getText().toString().trim();
                String password = user_pass.getText().toString();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                  if (task.isSuccessful()){
                                      mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                          @Override
                                          public void onComplete(@NonNull Task<Void> task) {
                                              progressDialog.dismiss();
                                              Toast.makeText(SignInActivity.this, "Check your Email to continue", Toast.LENGTH_SHORT).show();
                                          }
                                      });
                                  }else {
                                      progressDialog.dismiss();
                                      Toast.makeText(SignInActivity.this, "Failed sign up", Toast.LENGTH_SHORT).show();
                                  }
                                }
                            });
                }else {
                    Toast.makeText(SignInActivity.this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
//    public void buildActionCodeSettings() {
//        // [START auth_build_action_code_settings]
//        ActionCodeSettings actionCodeSettings =
//                ActionCodeSettings.newBuilder()
//                        // URL you want to redirect back to. The domain (www.example.com) for this
//                        // URL must be whitelisted in the Firebase Console.
//                        .setUrl("https://www.example.com/finishSignUp?cartId=1234")
//                        // This must be true
//                        .setHandleCodeInApp(true)
//                        .setIOSBundleId("com.example.ios")
//                        .setAndroidPackageName(
//                                "com.example.android",
//                                true, /* installIfNotAvailable */
//                                "12"    /* minimumVersion */)
//                        .build();
//        // [END auth_build_action_code_settings]
//    }

}
