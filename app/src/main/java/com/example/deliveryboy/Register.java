package com.example.deliveryboy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText et1,et2,et3,et4,et5;
    ImageButton b1;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    FirebaseAuth mFirebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et1=findViewById(R.id.name);
        et2=findViewById(R.id.email1);
        et3=findViewById(R.id.phone);
        et4=findViewById(R.id.password);
        et5=findViewById(R.id.conpassword);
        b1=findViewById(R.id.register);
        mFirebase=FirebaseAuth.getInstance();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registration();
            }
        });
    }

    private void registration() {
        final String pass, email, phone,conpass,name;
        name=et1.getText().toString();
        email=et2.getText().toString().trim();
        phone=et3.getText().toString().trim();
        pass=et4.getText().toString().trim();
        conpass=et5.getText().toString().trim();
        if(name.isEmpty()){
            et1.setError("Please enter your name");
            et1.requestFocus();
            return;
        }
        if(email.isEmpty()){
            et2.setError("Please enter your email");
            et2.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            et4.setError("Enter password");
            et4.requestFocus();
            return;
        }
        if(phone.isEmpty()){
            et3.setError("Enter Phone.No");
            et3.requestFocus();
            return;
        }
        if(!(phone.length()==10)){
            et3.setError("10 digits requried");
            et3.requestFocus();
            return;
        }
        if(conpass.isEmpty()){
            et5.setError("Please Confirm");
            et5.requestFocus();
        }
        if(!conpass.equals(pass)){
            et4.setError("Password Mismatch");
            et4.requestFocus();
            return;
        }
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        mFirebase.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    user u=new user(name,email,phone,true);
                    FirebaseDatabase.getInstance().getReference("User")
                            .child(FirebaseAuth.getInstance().getUid())
                            .setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(Register.this, "Registration success", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Register.this,MenuPage.class);

                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                //intent.putExtra("email",name);
                                startActivity(intent);
                                Register.this.finish();
                                // Toast.makeText(Register.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(Register.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(Register.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
