package com.baszczyk.mercpiggibank3.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baszczyk.mercpiggibank3.database.entities.PiggyBank
import com.baszczyk.mercpiggibank3.databinding.ItemViewBinding

class PiggyBankAdapter(val clickListener: PiggyBankListener):
        ListAdapter<PiggyBank, PiggyBankAdapter.ViewHolder>(PiggyBankDiffCallback()) {

//    override fun getItemCount() = data.size
//
//    var piggyId = 0L
//
//    var data = listOf<PiggyBank>()
//    set(value) {
//        field = value
//        notifyDataSetChanged()
//    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = data[position]
//        piggyId = item.piggyId
//        val res = holder.itemView.context.resources
//        holder.surname.text = item.piggyName
//        holder.actualAmount.text = item.actualAmount.toString()
//        holder.itemView.setOnClickListener { view: View ->
//            view.findNavController().navigate(ListFragmentDirections
//                .actionListFragmentToPiggiBankFragment(piggyId))
//        }

        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemViewBinding): RecyclerView.ViewHolder(binding.root){
//        val surname: TextView = itemView.findViewById(R.id.surname_mercedes)
//        val actualAmount: TextView = itemView.findViewById(R.id.actual_amount)
        fun bind(
    item: PiggyBank,
    clickListener: PiggyBankListener
) {
    binding.piggy = item
    binding.clickListener = clickListener
    binding.executePendingBindings()

}

        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemViewBinding.inflate(layoutInflater, parent, false)
                return  ViewHolder(binding)
            }
        }
    }


}

class PiggyBankListener(val clickListener: (id: Long) -> Unit) {
    fun onClick(piggy: PiggyBank) = clickListener(piggy.piggyId)
}

class PiggyBankDiffCallback : DiffUtil.ItemCallback<PiggyBank>() {
    override fun areItemsTheSame(oldItem: PiggyBank, newItem: PiggyBank): Boolean {
        return oldItem.piggyId == newItem.piggyId
    }

    override fun areContentsTheSame(oldItem: PiggyBank, newItem: PiggyBank): Boolean {
        return oldItem == newItem
    }

}