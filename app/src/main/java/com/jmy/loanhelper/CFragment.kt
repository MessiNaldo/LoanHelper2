package com.jmy.loanhelper

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jmy.loanhelper.databinding.FragmentCBinding


class CFragment : Fragment() {

    lateinit var binding: FragmentCBinding
    var mainActivity: MainActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCBinding.inflate(inflater,container,false)

        mainActivity?.bundle?.putInt("house", R.id.radio_Not_House)
        mainActivity?.bundle?.putInt("gongsil", R.id.radio_Gongsil)
        mainActivity?.bundle?.putInt("tooki", R.id.radio_Not_Tooki)
        mainActivity?.bundle?.putInt("gonext", 1)

        binding.radioGroupHouse.setOnCheckedChangeListener { radioGroup, checkedId ->
            mainActivity?.bundle?.putInt("house", checkedId)
        }

        binding.radioGroupTooki.setOnCheckedChangeListener{ radioGroup, checkedId ->
            mainActivity?.bundle?.putInt("tooki", checkedId)
        }

        binding.radioGroupGongsil.setOnCheckedChangeListener { radioGroup, checkedId ->
            mainActivity?.bundle?.putInt("gongsil", checkedId)
        }

        binding.btnPreviousC.setOnClickListener{
            mainActivity?.bundle?.putInt("gonext", 3)
            mainActivity?.setPreviousFragment(2)
        }

        binding.btnNextC.setOnClickListener{
            mainActivity?.bundle?.putInt("gonext", 0)
            mainActivity?.setNextFragment(2)
        }

       return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is MainActivity) mainActivity = context
    }
}