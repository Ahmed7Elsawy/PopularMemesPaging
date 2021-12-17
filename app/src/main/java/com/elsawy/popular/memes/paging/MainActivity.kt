package com.elsawy.popular.memes.paging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.elsawy.popular.memes.paging.data.getMemesFromNetwork
import com.elsawy.popular.memes.paging.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

   lateinit var binding: ActivityMainBinding
   private val adapter by lazy { MemesAdapter() }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      binding = ActivityMainBinding.inflate(layoutInflater)
      setContentView(binding.root)

      binding.recyclerView.adapter = adapter
      lifecycleScope.launch {
         getMemesFromNetwork().collectLatest {
            adapter.submitData(it)
         }
      }
   }

}

//https://api.imgflip.com/get_memes