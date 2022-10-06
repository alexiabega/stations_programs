package com.example.stationprogram

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.stationprogram.databinding.ProgramCellBinding

class ProgramsAdapter( private val list: List<Program>
): RecyclerView.Adapter<ProgramsAdapter.ViewHolder>() {

    var onMoreClickedListener: ((Int) -> Unit)? = null


    override fun getItemCount(): Int = list.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProgramCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding).apply {
            onClick = {
                onMoreClickedListener?.invoke(it)
            }
        }
    }

    inner class ViewHolder(binding: ProgramCellBinding): RecyclerView.ViewHolder(binding.root) {
        val time = binding.time
        val name = binding.programsName

        var onClick: ((Int) -> Unit)? = null

        init {
            binding.root.setOnClickListener { onClick?.invoke(adapterPosition) }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(data: Program) {
            time.text = "${data.startingTime} - " + "${data.endingTime} \n"
            name.text = data.name
        }
    }
}