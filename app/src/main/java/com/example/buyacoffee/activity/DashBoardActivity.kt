package com.example.buyacoffee.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.buyacoffee.adapter.CategoryAdapter
import com.example.buyacoffee.adapter.PopularAdapter
import com.example.buyacoffee.databinding.ActivityDashBinding
import com.example.buyacoffee.viewmodel.DashViewModel

class DashBoardActivity : AppCompatActivity() {
    lateinit var binding: ActivityDashBinding
    private val viewModel = DashViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDashBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initBanner()
        initCategory()
        initPopular()

    }

    private fun initBanner() {
        binding.progressBarBanner.visibility = View.VISIBLE
        viewModel.loadBanner().observeForever {
            Glide.with(this@DashBoardActivity)
                .load(it[0].url)
                .into(binding.ivBanner)
            binding.progressBarBanner.visibility = View.GONE
        }
    }

    private fun initCategory(){
        binding.progressbarCategorias.visibility = View.VISIBLE
        viewModel.loadCategory().observeForever {
            binding.recyclerViewCategorias.layoutManager =
                LinearLayoutManager(this@DashBoardActivity, LinearLayoutManager.HORIZONTAL,
                    false)

            binding.recyclerViewCategorias.adapter = CategoryAdapter(it)
            binding.progressbarCategorias.visibility = View.GONE
        }
    }

    private fun initPopular(){
        binding.progressBarPopulares.visibility = View.VISIBLE
        viewModel.loadPopular().observeForever {
            binding.rvPopulares.layoutManager =GridLayoutManager(this,2)
            binding.rvPopulares.adapter = PopularAdapter(it)
            binding.progressBarPopulares.visibility = View.GONE
        }
    }
}