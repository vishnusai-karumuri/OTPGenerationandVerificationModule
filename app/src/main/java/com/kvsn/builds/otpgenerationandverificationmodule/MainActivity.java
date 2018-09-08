package com.kvsn.builds.otpgenerationandverificationmodule;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
{

    FirebaseAuth auth;
    EditText e1 , e2;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    String otp;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1 = findViewById(R.id.mnum);
        e2 = findViewById(R.id.otpfield);

        auth = FirebaseAuth.getInstance();

        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
        {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential)
            {

            }

            @Override
            public void onVerificationFailed(FirebaseException e)
            {

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken)
            {
                super.onCodeSent(s, forceResendingToken);
                otp = s;
                Toast.makeText(getApplicationContext(), "OTP sent",Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void otpsend(View view)
    {
        String num = e1.getText().toString();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(num,60,TimeUnit.SECONDS,this,mCallback);
    }

    public void signinmobile(PhoneAuthCredential credential)
    {

        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext() , "SUCCESSS" , Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "FAILED!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }

    public void verifyotp(View v)
    {
        String input_code = e2.getText().toString();

         verifyphonenumber(otp,input_code);
    }

    public void verifyphonenumber(String verifyCode ,String input_code )
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifyCode , input_code);
        signinmobile(credential);
    }
}