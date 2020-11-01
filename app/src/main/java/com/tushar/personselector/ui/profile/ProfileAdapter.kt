package com.tushar.personselector.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tushar.personselector.databinding.RowProfileBinding
import com.tushar.personselector.model.user.PersonDetail
import com.tushar.personselector.utils.hide
import com.tushar.personselector.utils.show

/**
 * @param items : List of Persons
 * @param callBack : Callback when accept/reject is clicked
 */
class ProfileAdapter(private val items: MutableList<PersonDetail>, private val callBack: (PersonDetail) -> Unit) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowProfileBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        binding.vm = ProfileRowViewModel()
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: RowProfileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PersonDetail) {
            binding.vm?.item?.set(item)
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        val selectedPerson = items[position]

        holder.binding.accept.setOnClickListener {
            updatePersons(selectedPerson, true, position)
        }
        holder.binding.decline.setOnClickListener {
            updatePersons(selectedPerson, false, position)
        }
        selectedPerson.isAccepted?.let {
            holder.binding.status.show()
            if (it) {
                holder.binding.ivAccepted.show()
                holder.binding.ivRejected.hide()
            } else {
                holder.binding.ivAccepted.hide()
                holder.binding.ivRejected.show()
            }
        } ?: kotlin.run {
            holder.binding.status.hide()
        }
    }

    /**
     * @param selectedPerson person to update
     * @param isAccepted true for accepted , false otherwise
     * @param position view holder item position
     */
    private fun updatePersons(selectedPerson: PersonDetail, isAccepted: Boolean, position: Int) {
        selectedPerson.isAccepted = isAccepted
        callBack.invoke(selectedPerson)
        notifyItemChanged(position)
    }

    /**
     * Add list of persons and notify data set change
     */
    fun addPeople(personList: List<PersonDetail>) {
        items.clear()
        items.addAll(personList)
        notifyDataSetChanged()
    }
}