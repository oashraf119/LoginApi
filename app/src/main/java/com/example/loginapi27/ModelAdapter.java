package com.example.loginapi27;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginapi27.databinding.ListItemBinding;

import java.util.ArrayList;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ViewHolder>{
    ArrayList<User> users = new ArrayList<>();

    public void setUsers(ArrayList<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ModelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemBinding binding = ListItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ModelAdapter.ViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users == null ?0 : users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ListItemBinding binding;
        public ViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
        public void bind(User user){
            binding.textEmail.setText(user.getEmail());
            binding.textName.setText(user.getName());
            binding.textPhone.setText(user.getPhone());
            binding.btnrec.setText(user.getId());
        }
    }
}
