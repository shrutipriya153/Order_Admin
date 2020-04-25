package com.example.deliveryboy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuPage extends AppCompatActivity implements ListView_Adapter.OnItemClickListener {
    FirebaseDatabase mdatabase;

    DatabaseReference mRef;

    int i;
    List<Food_POJO> or,refresh;
    List<item> items;
    RecyclerView recyclerView;
    ImageButton back,menu;

    ValueEventListener valueEventListener;
    ListView_Adapter listView_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
        recyclerView=findViewById(R.id.orderdisplay);
        back=findViewById(R.id.back);
        menu=findViewById(R.id.menubar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        or=new ArrayList<>();
        refresh=new ArrayList<>();
        mdatabase=FirebaseDatabase.getInstance();

        mRef= FirebaseDatabase.getInstance().getReference().child("Request");
        listView_adapter = new ListView_Adapter(MenuPage.this, or);

        recyclerView.setAdapter(listView_adapter);
        listView_adapter.setOnItemClickListener((ListView_Adapter.OnItemClickListener) MenuPage.this);
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        i=0;
        Intent service=new Intent(MenuPage.this,ListenOrder.class);
        startService(service);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(MenuPage.this,menu);
                popupMenu.getMenuInflater().inflate(R.menu.signout,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent=new Intent(MenuPage.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        valueEventListener=mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(int j=0;j<or.size();i++){
                    refresh.add(or.get(j));
                }
                or.clear();
                progressDialog.dismiss();
                if(dataSnapshot.exists()){
                    for(DataSnapshot i:dataSnapshot.getChildren()){
                        if(i.exists()){
                            for(DataSnapshot ds:i.getChildren()){
                                Request_POJO request_pojo = (Request_POJO)ds.getValue(Request_POJO.class);
                                items = request_pojo.getList();
                                Food_POJO food=new Food_POJO(request_pojo.getDate(), items, request_pojo.getName(), request_pojo.getPhone(), request_pojo.getAddress(),1);
                                food.setKey(ds.getKey());
                                or.add(food);
                                refresh.add(food);

                            }
                            listView_adapter.notifyDataSetChanged();
                        }
                    }
                }else{
                    Toast.makeText(MenuPage.this, "No New Orders", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRef.removeEventListener(valueEventListener);
    }

    @Override
    public void onDeleteClick(int position) {
        Food_POJO requestPojo=refresh.get(position);
        final String selected=requestPojo.getKey();

        Log.d("qwertyuuiop",selected);
       mRef=FirebaseDatabase.getInstance().getReference().child("Request");
       mRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(dataSnapshot.exists()){
                    for(DataSnapshot i:dataSnapshot.getChildren()){
                        if(i.exists()){
                            for(DataSnapshot ds:i.getChildren()){
                                if(ds.getKey().equals(selected)){
                                    ds.getRef().removeValue();
                                    listView_adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
        //Toast.makeText(this, "Delete"+ String.valueOf(position), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPickClick(int position) {
        Food_POJO requestPojo=refresh.get(position);
        final String selected=requestPojo.getKey();

        Log.d("qwertyuuiop",selected);
        mRef=FirebaseDatabase.getInstance().getReference().child("Request");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot i:dataSnapshot.getChildren()){
                        if(i.exists()){
                            for(DataSnapshot ds:i.getChildren()){
                                if(ds.getKey().equals(selected)){
                                    /*Request_POJO request_pojo = ds.getValue(Request_POJO.class);
                                    items = request_pojo.getList();
                                    Food_POJO food=new Food_POJO(request_pojo.getDate(), items, request_pojo.getName(), request_pojo.getPhone(), request_pojo.getAddress(),1);
                                    ds.getRef().setValue(food);*/
                                    ds.getRef().child("status").setValue(1);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
