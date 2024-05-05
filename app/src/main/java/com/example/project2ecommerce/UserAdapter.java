package com.example.project2ecommerce;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2ecommerce.database.entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Miguel Santiago
 * Adapter Class for the Recycler view used in the admin delete user activity
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<String> data = new ArrayList<>();
    private int selectedPosition = RecyclerView.NO_POSITION;


    public UserAdapter (List<String> data){
        this.data = data;

    }


    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_delete_user_list_item_extension, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder( UserAdapter.ViewHolder holder, int position) {
        String item = data.get(position);
        holder.textView.setText(item);
        holder.textView.setSelected(selectedPosition == position);

    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

//    public void setSelectedPosition(int position) {
//        selectedPosition = position;
//        notifyDataSetChanged(); // Notify adapter to redraw views
//    }

    public void setData(List<String> newData) {
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;


        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.textView = view.findViewById(R.id.adminDeleteUserExtensionTextView);

        }

        @Override
        public void onClick(View view) {



        }
    }


}
