package com.hmai.firebasetest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    ArrayList<Sinhvien> arrayList;
   // AdapterSinhvien adapter;
   Adapter adapter;
    ArrayList<Integer> array = new ArrayList<>();
    ListView listView;
    FirebaseDatabase database;
    List<Sinhvien> work_select = new ArrayList<Sinhvien>();
    DatabaseReference myRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("DBsinhvien");
        //registerForContextMenu(listView);
        SelectData();
        showdata();

    }
//Context menu
    /*public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        menu.setHeaderTitle("Action");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Sinhvien sinhvien = arrayList.get(info.position);
        switch (item.getItemId()){
            case R.id.show:
                //UpdateData(sinhvien.getId(),sinhvien.getMasv(),sinhvien.getHoten(),sinhvien.getLopSH());
                Intent intent2 = new Intent(MainActivity.this,ShowSinhvien.class);
                intent2.putExtra("SV",sinhvien);
                MainActivity.this.startActivity(intent2);

                return true;
            case R.id.add:
//                InsertData();
                Intent intent = new Intent(MainActivity.this,AddSinhvien.class);
                MainActivity.this.startActivity(intent);
                return true;
            case R.id.update:
               // UpdateData(sinhvien.getId(),sinhvien.getMasv(),sinhvien.getHoten(),sinhvien.getLopSH());

                Intent intent1 = new Intent(MainActivity.this,UpdateSinhvien.class);
                intent1.putExtra("SV",sinhvien);
                MainActivity.this.startActivity(intent1);

                return true;
            case R.id.delete:
                DeleteData(sinhvien.getId());
                return true;
            default:     return false;
        }

    }*/
// Menu contextual bar
    AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

        if  (work_select.contains(arrayList.get(position))){
            work_select.remove(arrayList.get(position));
        } else {
            work_select.add(arrayList.get(position));
        }
        mode.setTitle(work_select.size()+"item select...");

    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater menuInflater = mode.getMenuInflater();
        menuInflater.inflate(R.menu.menu_context_bar,menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item)
    {

        switch (item.getItemId()){
            case R.id.action_delete:
                for (int i=0;i<work_select.size();i++) {
                    Sinhvien sinhvien = work_select.get(i);
                    DeleteData(sinhvien.getId());
                }
                SelectData();
                work_select.clear();
                mode.finish();
                //Toast.makeText(this,Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_share:
                Toast.makeText(MainActivity.this,"Share",Toast.LENGTH_SHORT).show();
                mode.finish();
                return true;
            default:return false;

        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
};
    //popup add
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add){
            Intent intent = new Intent(MainActivity.this,AddSinhvien.class);
            MainActivity.this.startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }


    public void SelectData(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("DBsinhvien");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList = new ArrayList<>();
                arrayList.clear();
                array = new ArrayList<>();
                array.clear();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    Sinhvien sinhvien = data.getValue(Sinhvien.class);

                    sinhvien.setId(data.getKey());
                    arrayList.add(sinhvien);
                    array.add(arrayList.size());
                    Log.d("id","onDataChange: "+sinhvien.getId());
                }

                adapter= new Adapter(MainActivity.this,R.layout.line,arrayList,array);
                listView.setAdapter( adapter);
                listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
                listView.setMultiChoiceModeListener(modeListener);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
//    public void Select(){
//        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference("DBsinhvien");
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                arrayList.clear();
//                for(DataSnapshot data: dataSnapshot.getChildren()){
//                    Sinhvien sinhvien = data.getValue(Sinhvien.class);
//
//                    sinhvien.setId(data.getKey());
//                    arrayList.add(sinhvien);
//                    listView.setAdapter(adapter);
//                   adapter = new AdapterSinhvien(MainActivity.this,android.R.layout.simple_list_item_checked,arrayList);
//
//                    Log.d("id","onDataChange: "+sinhvien.getId());
//                }
//
//                adapter= new AdapterSinhvien(MainActivity.this,R.layout.line_item,arrayList);
//                listView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }
//    public  void InsertData(){
//
//        final Dialog add = new Dialog(this);
//        add.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        add.setContentView(R.layout.activity_add);
//
//        final EditText edmasv, edhoten,edlopsh;
//        final Button btnthem, btnhuy;
//
//        edmasv = (EditText) add.findViewById(R.id.tvmasv);
//        edhoten = (EditText)add.findViewById(R.id.tvhoten);
//        edlopsh = (EditText) add.findViewById(R.id.tvlopsh);
//
//        btnthem = (Button) add.findViewById(R.id.btnthem);
//        btnhuy= (Button) add.findViewById(R.id.btnthoat);
//
//        btnhuy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                add.dismiss();
//            }
//        });
//        btnthem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String masv = edmasv.getText().toString();
//                String hoten = edhoten.getText().toString();
//                String lopsh = edlopsh.getText().toString();
////                String avt =
//                String id = myRef.push().getKey();
//                Sinhvien sinhvien = new Sinhvien(masv,hoten,lopsh);
//
//                myRef.child(id).setValue(sinhvien).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        // if success
//                        Toast.makeText(getApplicationContext(),"Thêm thành công!",Toast.LENGTH_SHORT).show();
//                    }
//                });
//                add.dismiss();
//            }
//        });
//        add.show();
//    }
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
    public void UpdateData(final String id, String masv, String hoten, String lopsh){

        final Dialog update = new Dialog(this);
        update.requestWindowFeature(Window.FEATURE_NO_TITLE);
        update.setContentView(R.layout.activity_update);


        final EditText edmasv, edhoten,edlopsh;
        final Button btnluu, btnthoat;

        edmasv = (EditText) update.findViewById(R.id.edmasv);
        edhoten = (EditText)update.findViewById(R.id.tvhoten);
        edlopsh = (EditText) update.findViewById(R.id.edlopsh);

        btnluu = (Button) update.findViewById(R.id.btnluu);
        btnthoat= (Button) update.findViewById(R.id.btnthoat);

        edmasv.setText(masv);
        edhoten.setText(hoten);
        edlopsh.setText(lopsh);

        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String masv = edmasv.getText().toString().trim();
                String hoten = edhoten.getText().toString().trim();
                String lopsh = edlopsh.getText().toString().trim();

                Sinhvien sv = new Sinhvien(masv,hoten,lopsh,null);
                myRef.child(id).setValue(sv);
                update.dismiss();
                Intent intent2 = new Intent(MainActivity.this,ShowSinhvien.class);
                intent2.putExtra("SV",sv);
                MainActivity.this.startActivity(intent2);
            }
        });
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update.dismiss();
            }
        });
    update.show();
    }
    private void showdata(){


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Sinhvien sinhvien = arrayList.get(position);
//                switch(id)

                Intent intent = new Intent(MainActivity.this,ShowSinhvien.class);
                intent.putExtra("SV",sinhvien);
                MainActivity.this.startActivity(intent);
            }
        });
    }

}
