package com.example.ezdictionary

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ezdictionary.databinding.ActivityMainBinding
import com.example.ezdictionary.databinding.RecyclerRowBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var adapter: MeaningAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSearch.setOnClickListener {
            val word = binding.searchInput.text.toString()
            getMeaning(word)

            closeKeyboard(it)

        }
    adapter= MeaningAdapter(emptyList())
        binding.meaningRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.meaningRecyclerView.adapter = adapter

    }




    private fun getMeaning(word: String) {
        setInProgress(true)
        GlobalScope.launch {
            try {
                val response =  RetrofitInstance.dictionaryAPI.getMeaning(word)
                if (response.body()==null){
                    throw (Exception())
                }
                runOnUiThread {
                    setInProgress(false)
                    response.body()?.first()?.let {
                        runUI(it)
                    }
                }

            }catch (e: Exception){
                runOnUiThread{
                    setInProgress(false)
                    Toast.makeText(applicationContext, "Word not available", Toast.LENGTH_SHORT).show()
                }


            }


        }
    }



    private fun runUI(reponse : WordResult){
        binding.wordTextview.text = reponse.word
        binding.phoneticTextview.text = reponse.phonetic
        adapter.updateNewData(reponse.meanings)
    }

    private fun setInProgress(inProgress : Boolean){
        if (inProgress){
            binding.btnSearch.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.btnSearch.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun closeKeyboard(view: View) {
        val imn = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imn.hideSoftInputFromWindow(view.windowToken, 0)
    }


}