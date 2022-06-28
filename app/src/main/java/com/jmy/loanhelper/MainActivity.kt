package com.jmy.loanhelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.jmy.loanhelper.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Thread.sleep
import kotlin.concurrent.thread

//test
class MainActivity : AppCompatActivity() {
    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    var bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.Main).launch {
            launch {
                supportFragmentManager.beginTransaction().add(R.id.frameLayout, AFragment()).commit()
                delay(500)
            }.join()

            launch {
                setNextFragment(0)
            }
        }
    }

    fun setNextFragment(int: Int){
        when(int){
            0 -> {
                supportFragmentManager.beginTransaction().add(R.id.frameLayout, BFragment())
                    .commit()
                supportFragmentManager.beginTransaction().show(BFragment()).commit()
                supportFragmentManager.beginTransaction().hide(CFragment()).commit()
                supportFragmentManager.beginTransaction().hide(DFragment()).commit()
                supportFragmentManager.beginTransaction().hide(EFragment()).commit()

            }
            1 -> {
                supportFragmentManager.beginTransaction().add(R.id.frameLayout, CFragment())
                    .commit()
                supportFragmentManager.beginTransaction().hide(BFragment()).commit()
                supportFragmentManager.beginTransaction().show(CFragment()).commit()
                supportFragmentManager.beginTransaction().hide(DFragment()).commit()
                supportFragmentManager.beginTransaction().hide(EFragment()).commit()
            }
            2 -> {
                supportFragmentManager.beginTransaction().add(R.id.frameLayout, DFragment())
                    .commit()
                supportFragmentManager.beginTransaction().hide(BFragment()).commit()
                supportFragmentManager.beginTransaction().hide(CFragment()).commit()
                supportFragmentManager.beginTransaction().show(DFragment()).commit()
                supportFragmentManager.beginTransaction().hide(EFragment()).commit()
            }
            3 -> {
                supportFragmentManager.beginTransaction().add(R.id.frameLayout, EFragment())
                    .commit()
                supportFragmentManager.beginTransaction().hide(BFragment()).commit()
                supportFragmentManager.beginTransaction().hide(CFragment()).commit()
                supportFragmentManager.beginTransaction().hide(DFragment()).commit()
                supportFragmentManager.beginTransaction().show(EFragment()).commit()
            }
        }
    }

    fun setPreviousFragment(int: Int){
        when(int){
            2 -> {
                supportFragmentManager.beginTransaction().add(R.id.frameLayout, BFragment())
                    .commit()
                supportFragmentManager.beginTransaction().show(BFragment()).commit()
            }
            3 -> {
                supportFragmentManager.beginTransaction().add(R.id.frameLayout, CFragment())
                    .commit()
                supportFragmentManager.beginTransaction().show(CFragment()).commit()
            }
            4 -> {
                supportFragmentManager.beginTransaction().add(R.id.frameLayout, DFragment())
                    .commit()
                supportFragmentManager.beginTransaction().show(DFragment()).commit()
            }
        }
    }

    fun bundleReset(){
        bundle.clear()
    }
}