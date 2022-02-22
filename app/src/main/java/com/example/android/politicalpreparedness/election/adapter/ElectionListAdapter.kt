package com.example.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.ViewholderElectionBinding
import com.example.android.politicalpreparedness.network.models.Election

class ElectionListAdapter(private val clickListener: ElectionListener): ListAdapter<Election, ElectionViewHolder>(ElectionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder.from(parent)
    }

    //TODO: Bind ViewHolder

    //TODO: Add companion object to inflate ViewHolder (from)

    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        //TODO("Not yet implemented")
        val election = getItem(position)
        holder.bind(election, clickListener)
    }
}

//TODO: Create ElectionViewHolder
class ElectionViewHolder(val binding: ViewholderElectionBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(election: Election, listener: ElectionListener) {
        binding.election = election
        binding.clickListener = listener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ElectionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ViewholderElectionBinding.inflate(layoutInflater, parent, false)
            return ElectionViewHolder(binding)
        }
    }
}

//TODO: Create ElectionDiffCallback
class ElectionDiffCallback : DiffUtil.ItemCallback<Election>() {
    override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem == newItem
    }
}

//TODO: Create ElectionListener
class ElectionListener(val clickListener: (election: Election) -> Unit) {
    fun onClick(election: Election) = clickListener(election)
}