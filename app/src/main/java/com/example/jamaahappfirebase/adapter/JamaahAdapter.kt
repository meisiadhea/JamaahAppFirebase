package com.example.jamaahappfirebase.adapter

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.jamaahappfirebase.R
import com.example.jamaahappfirebase.databinding.ActivityMainBinding
import com.example.jamaahappfirebase.databinding.ItemJamaahBinding
import com.example.jamaahappfirebase.entity.Jamaah
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class JamaahAdapter (context: Context, private var listJamaah: ArrayList<Jamaah>?) :
RecyclerView.Adapter<JamaahAdapter.JamaahViewHolder>() {

    private val listener: FireBaseDataListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JamaahViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_jamaah, parent, false)
        return JamaahViewHolder(view)
    }

    override fun onBindViewHolder(holder: JamaahViewHolder, position: Int) {
        holder.bind(listJamaah?.get(position))
    }

    override fun getItemCount(): Int = listJamaah?.size !!

    inner class JamaahViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemJamaahBinding.bind(itemView)
        fun bind(jamaah: Jamaah?) {
            val age = itemView.context.getString(R.string.umur, getAge(jamaah?.tglLahir))
            binding.tvNamaJamaah.text = jamaah?.nama
            binding.tvUmur.text = age
            binding.tvAlamatJamaah.text = jamaah?.alamat
            binding.tvTglInput.text = jamaah?.tglInput
            binding.cvJamaah.setOnClickListener {
                listener.onDataClick(jamaah, adapterPosition)
            }
            var  color = R.color.blue
            if (jamaah?.jenisKelamin == "Perempuan") color = R.color.pink
            binding.cvJamaah.strokeColor = itemView.context.getColor(color)
        }
    }

    fun setSearchedList(listJamaah: ArrayList<Jamaah>) {
        this.listJamaah = listJamaah
        notifyDataSetChanged()
    }

    //Metode untuk menghitung Umur dari sejak lahir sampai tahun sekarang
    fun getAge(tglLahir: String?): String {
        val tglArray = tglLahir?.split("-")?.toTypedArray()
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val cYear = Integer.parseInt(tglArray?.get(0) ?: "0")
        val age = currentYear - cYear
        return age.toString()
    }

    interface FireBaseDataListener {

        fun onDataClick(jamaah: Jamaah?, position: Int)
    }

    init {
        listener = context as FireBaseDataListener
    }
}

