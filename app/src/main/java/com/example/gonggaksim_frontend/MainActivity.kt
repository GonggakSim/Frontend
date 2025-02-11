package com.example.gonggaksim_frontend

import android.os.Bundle
import android.os.Debug
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.emptyLongSet
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.gonggaksim_frontend.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ActivityCompat.finishAffinity(ActiveActivity())

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            view.setPadding(0, 0, 0, insets.systemWindowInsetBottom)
            insets.consumeSystemWindowInsets()
        }

        replaceFragment(TestFragment())

        binding.btmNav.setOnItemSelectedListener{ item ->
            when(item.itemId){
                R.id.btm_test -> {
                    replaceFragment(TestFragment())
                    true
                }
                R.id.btm_cal -> {
                    replaceFragment(CalenderFragment())
                    true
                }
                R.id.btm_noti -> {
                    replaceFragment(NotificationFragment())
                    true
                }
                R.id.btm_my -> {
                    replaceFragment(MypageFragment())
                    true
                }

                else -> true
            }


        }

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
    }

}