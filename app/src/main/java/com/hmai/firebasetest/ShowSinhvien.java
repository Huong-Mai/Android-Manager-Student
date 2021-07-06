package com.hmai.firebasetest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ShowSinhvien extends AppCompatActivity {
    private TextView tvmasv, tvhoten, tvlopsh;
    private ImageView avata;
    private Button btnsua, btnthoat,btnxoa;
    private Sinhvien sinhvien;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("DBsinhvien");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sinhvien);
        Anhxa();

        Intent intent = getIntent();
        sinhvien = (Sinhvien) intent.getSerializableExtra("SV");
        if (sinhvien!=null){
            tvmasv.setText(sinhvien.getMasv());
            tvhoten.setText(sinhvien.getHoten());
            tvlopsh.setText(sinhvien.getLopSH());
            Log.d("tag",sinhvien.getAvata());
            Picasso.with(this).load(sinhvien.getAvata())
                    .into(avata);

        }
        else {
            Toast.makeText(getApplicationContext(), "Lỗi tải dữ liệu!",Toast.LENGTH_SHORT).show();
        }

        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowSinhvien.this,UpdateSinhvien.class);
                intent.putExtra("SV",sinhvien);
                ShowSinhvien.this.startActivity(intent);
            }
        });

        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowSinhvien.this,MainActivity.class);
                ShowSinhvien.this.startActivity(intent);
            }
        });
        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             DeleteData(sinhvien.getId());

            }
        });




    }

    private void Anhxa() {
        tvmasv = (TextView) findViewById(R.id.edmasv);
        tvhoten = (TextView)findViewById(R.id.tvhoten);
        tvlopsh = (TextView)findViewById(R.id.edlopsh);
        avata = (ImageView) findViewById(R.id.avata);

        btnsua = (Button) findViewById(R.id.btnsua);
        btnthoat = (Button) findViewById(R.id.btnthoat);
        btnxoa = (Button)findViewById(R.id.btnxoa);

    }
    public void DeleteData(final String Id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Xóa Sinh viên?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myRef.child(Id).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        Toast.makeText(getApplicationContext(),"Đã xóa",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ShowSinhvien.this,MainActivity.class);
                        ShowSinhvien.this.startActivity(intent);
                    }
                });
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }

}
