package com.hmai.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddSinhvien extends AppCompatActivity {
    private EditText edmasv, edhoten,edlopsh, edavata;
    private Button btnthem, btnhuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sinhvien);
        
        Anhxa();

        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            finish();
            }
        });
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String masv = edmasv.getText().toString();
                String hoten = edhoten.getText().toString();
                String lopsh = edlopsh.getText().toString();
                String avt= edavata.getText().toString();
                Sinhvien sinhvien = new Sinhvien(masv,hoten,lopsh,avt);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("DBsinhvien");
                String id = myRef.push().getKey();
                myRef.child(id).setValue(sinhvien).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // if success
                        Toast.makeText(getApplicationContext(),"Đã thêm thành công!",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Thêm thất bại!"+e.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void Anhxa() {
        edmasv = (EditText) findViewById(R.id.edmasv);
        edhoten = (EditText)findViewById(R.id.tvhoten);
        edlopsh = (EditText)findViewById(R.id.edlopsh);

        btnthem = (Button) findViewById(R.id.btnthem);
        btnhuy = (Button) findViewById(R.id.btnthoat);
        edavata =(EditText) findViewById(R.id.edavata);
    }

}
