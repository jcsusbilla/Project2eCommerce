package com.example.project2ecommerce;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdminEditUserAdapter extends RecyclerView.Adapter<AdminEditUserAdapter.ViewHolder>{

    private List<String> data = new ArrayList<>();
    private int selectedPosition = RecyclerView.NO_POSITION;

    public AdminEditUserAdapter(List<String> data){
        this.data = data;
    }
    @NonNull
    @Override
    public AdminEditUserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_edit__user_list_item_extension, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = data.get(position);
        holder.textView1.setText(item);
        holder.textView1.setSelected(selectedPosition == position);
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public void setData(List<String> newData) {
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView1;


        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.textView1 = view.findViewById(R.id.adminEditUserExtensionTextView);

        }

        @Override
        public void onClick(View view) {



        }
    }
}
