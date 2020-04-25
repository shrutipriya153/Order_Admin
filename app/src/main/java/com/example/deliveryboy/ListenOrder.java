package com.example.deliveryboy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class ListenOrder extends Service implements ChildEventListener {
    FirebaseDatabase database;
    DatabaseReference mref;
    String uid;
    public ListenOrder() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        database=FirebaseDatabase.getInstance();
        mref=database.getReference().child("Request");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mref.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        if(dataSnapshot.exists()){
            for(DataSnapshot i:dataSnapshot.getChildren()){
                if(i.exists()){
                    for(DataSnapshot ds:i.getChildren()){
                        //Request_POJO request_pojo = (Request_POJO)ds.getValue(Request_POJO.class);

                        Log.d("djnsdjnfs",ds.getKey());
                        if(ds.getKey().equals("status")){
                            long status=ds.getValue(long.class);
                            if(status==0){
                                showNotification(ds.getKey(),status);
                            }
                        }


                    }

                }
            }
        }
    }

    private void showNotification(String key, Long status) {
        Intent intent=new Intent(getBaseContext(),MenuPage.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(getBaseContext(),0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getBaseContext());
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setTicker("New Order!!!")
                .setContentTitle("New Order!!!")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher);
        NotificationManager manager=(NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        int rND= new Random().nextInt(9999-1)+1;
        manager.notify(rND,builder.build());
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
