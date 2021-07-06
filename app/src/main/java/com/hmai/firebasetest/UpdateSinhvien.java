package com.hmai.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateSinhvien extends AppCompatActivity {
    private EditText edmasv, edhoten,edlopsh, edavata;
    private Button btnluu, btnthoat;
    private Sinhvien sinhvien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sinhvien);
        Anhxa();

        Intent intent = getIntent();
        sinhvien = (Sinhvien) intent.getSerializableExtra("SV");
        if (sinhvien!=null){
            edmasv.setText(sinhvien.getMasv());
            edhoten.setText(sinhvien.getHoten());
            edlopsh.setText(sinhvien.getLopSH());
            edavata.setText(sinhvien.getAvata());
        }
        else {
            Toast.makeText(this, "Lỗi tải dữ liệu!",Toast.LENGTH_SHORT).show();
        }
        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String masv = edmasv.getText().toString().trim();
                String hoten = edhoten.getText().toString().trim();
                String lopsh = edlopsh.getText().toString().trim();
                String avata = edavata.getText().toString();
                String id = sinhvien.getId();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("DBsinhvien");

                final Sinhvien sv = new Sinhvien(masv,hoten,lopsh,avata);
                myRef.child(id).setValue(sv).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // if success
                        Toast.makeText(getApplicationContext(),"Đã sửa thành công!",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateSinhvien.this,ShowSinhvien.class);
                        intent.putExtra("SV",sv);
                        UpdateSinhvien.this.startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Sửa thất bại!"+e.toString(),Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void Anhxa() {
        edmasv = (EditText) findViewById(R.id.edmasv);
        edhoten = (EditText)findViewById(R.id.tvhoten);
        edlopsh = (EditText)findViewById(R.id.edlopsh);
        edavata = (EditText) findViewById(R.id.edavata);

        btnluu = (Button) findViewById(R.id.btnluu);
        btnthoat = (Button) findViewById(R.id.btnthoat);


    }
}
