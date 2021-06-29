package com.example.online_exam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.online_exam.Register.TAG;

public class ForgetPassword extends AppCompatActivity {

    EditText forEmail;
    Button forSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        getSupportActionBar().hide();

        forEmail = findViewById(R.id.forEmail);
        forSend = findViewById(R.id.btnResend);

        forSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = forEmail.getText().toString();
                if(email.isEmpty())
                {
                    forEmail.setError("Please provide a valid Email");
                }
                else
                {
                    resendCode(email);
                }
            }
        });
    }

    private void resendCode(String emailAddress) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(getApplicationContext(), "Email sent to reset Password", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ForgetPassword.this, LoginActivity.class));
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Recheck your Email", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}