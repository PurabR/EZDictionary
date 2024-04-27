package com.example.ezdictionary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ezdictionary.databinding.RecyclerRowBinding

class MeaningAdapter(private var meaningList : List<Meaning>) :
    RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder>() {


    class MeaningViewHolder(private val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(meaning: Meaning){

            binding.partOfSpeechTextView.text = meaning.partOfSpeech
            binding.definationsTextView.text = meaning.definitions.joinToString("\n\n") {
                it.definition.toString()
            }

            if(meaning.synonyms.isEmpty()){
                binding.synonymTitleView.visibility = View.GONE
                binding.synonymsTextView.visibility = View.GONE
            } else{
                binding.synonymTitleView.visibility = View.VISIBLE
                binding.synonymsTextView.visibility = View.VISIBLE
                binding.synonymsTextView.text = meaning.synonyms.joinToString( ", ")
            }
            if(meaning.antonyms.isEmpty()){
                binding.antonymsTitleView.visibility = View.GONE
                binding.antonymsTextView.visibility = View.GONE
            } else{
                binding.antonymsTitleView.visibility = View.VISIBLE
                binding.antonymsTextView.visibility = View.VISIBLE
                binding.antonymsTextView.text = meaning.antonyms.joinToString( ", ")
            }

        }

    }

     fun  updateNewData(newMeaningList: List<Meaning>){
        meaningList = newMeaningList
         notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeaningViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return meaningList.size
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
       holder.bind(meaningList[position])
    }
}