package com.example.myapplication.userlist.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemUserListBinding
import com.example.myapplication.userlist.ItemClickListener
import com.example.myapplication.userlist.model.Data
import com.example.myapplication.userlist.viewmodel.UserListViewModel

class UserListAdapter(
    var users: ArrayList<Data>,
    var context: Context,
    var viewModel: UserListViewModel
    ) : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    lateinit var binding: ItemUserListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        binding = ItemUserListBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int  = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int)  = holder.bind(viewModel )

    @SuppressLint("NotifyDataSetChanged")
    fun refreshAdapter(newUsers:List<Data>){
        users.clear()
        users.addAll(newUsers)
        notifyDataSetChanged()
    }

    inner  class UserViewHolder(view: ItemUserListBinding) : RecyclerView.ViewHolder(view.root){
        fun bind(viewModel: UserListViewModel){
            binding.setVariable(BR.viewModel, viewModel)
            binding.setVariable(BR.position, position)
            binding.executePendingBindings()
        }
    }
}