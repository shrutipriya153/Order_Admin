package com.example.deliveryboy;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ListView_Adapter extends RecyclerView.Adapter<ListView_Adapter.ViewHolder> {
    Activity activity;
    List<Food_POJO> items;
    DatabaseReference ref;
     OnItemClickListener mListener;

    public ListView_Adapter(Activity activity, List<Food_POJO> items) {
        this.activity = activity;
        this.items = items;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.order_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ref = FirebaseDatabase.getInstance().getReference();
        Food_POJO l = items.get(position);
        holder.date.setText(l.getDate());
        List<item> menu = l.getOrder();
        holder.list.setHasFixedSize(true);
        holder.list.setLayoutManager(new LinearLayoutManager(activity));
        final ListAdapter adapter = new ListAdapter(activity, menu);
        holder.list.setAdapter(adapter);
        holder.ordee_name.setText(l.getName());
        holder.add.setText(l.getAddress());
        holder.phone.setText(l.getPhone());
        Log.d("fjiijw", ref.toString());
        holder.b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    //int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION)
                        mListener.onDeleteClick(position);
                        items.remove(position);
                }
                return;
            }

        });
        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    //int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION)
                        mListener.onPickClick(position);
                    items.remove(position);
                }
                return;
            }
        });

        }



    Food_POJO getView(int pos) {
        return items.get(pos);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView date, ordee_name, phone, add;
        Button b1, b2;
        RecyclerView list;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            list = itemView.findViewById(R.id.list);
            date = itemView.findViewById(R.id.date);
            ordee_name = itemView.findViewById(R.id.orderee_name);
            phone = itemView.findViewById(R.id.orderee_phone);
            add = itemView.findViewById(R.id.orderee_add);
            b1 = itemView.findViewById(R.id.pick);
            b2 = itemView.findViewById(R.id.deilvered);
            b2.setOnClickListener(this);
            b1.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION)
                    mListener.onDeleteClick(position);
            }
        }


    }
    public  interface OnItemClickListener{
        void  onDeleteClick(int position);
        void onPickClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

}
