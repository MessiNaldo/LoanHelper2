package com.jmy.loanhelper

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jmy.loanhelper.databinding.FragmentEBinding
import kotlin.math.*


class EFragment : Fragment() {
    lateinit var binding:FragmentEBinding
    var mainActivity:MainActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEBinding.inflate(inflater, container, false)

        mainActivity?.bundle?.let{
            var rtiResult = it.getLong("yearlyRent").toDouble().div(it.getLong("yearlySanghwan"))
            rtiResult = floor(rtiResult.times(100))/100.0
            binding.textRtiResult.setText("${rtiResult}")

            if(it.getInt("house") == R.id.radio_Not_House){
                if (rtiResult >= 1.5){
                    binding.textJunkyeol.setText("영업점 전결")
                } else if (rtiResult >= 1.2){
                    binding.textJunkyeol.setText("여신심사부 승인 신청 가능")
                } else {
                    binding.textJunkyeol.setText("대출 취급 불가")
                }
            } else{
                if (it.getInt("tooki") == R.id.radio_Tooki){
                    if (rtiResult >= 1.5){
                        binding.textJunkyeol.setText("영업점 전결")
                    }else{
                        binding.textJunkyeol.setText("대출 취급 불가")
                    }
                }else{
                    if (rtiResult >= 1.25){
                        binding.textJunkyeol.setText("영업점 전결")
                    }else if (rtiResult >= 1.0){
                        binding.textJunkyeol.setText("여신심사부 승인 신청 가능")
                    }else{
                        binding.textJunkyeol.setText("대출 취급 불가")
                    }
                }
            }
        }

        binding.btnPreviousE.setOnClickListener{
            mainActivity?.bundle?.putInt("gonext", 4)
            mainActivity?.setPreviousFragment(4)
        }

        binding.btnToBFragment.setOnClickListener{
            mainActivity?.supportFragmentManager?.beginTransaction()?.replace(R.id.frameLayout, BFragment())?.commit()
        }

        binding.btnClear.setOnClickListener {
            mainActivity?.bundleReset()
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is MainActivity) mainActivity = context
    }
}