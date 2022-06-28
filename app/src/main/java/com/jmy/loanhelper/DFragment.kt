package com.jmy.loanhelper

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jmy.loanhelper.databinding.FragmentDBinding
import java.text.DecimalFormat


class DFragment : Fragment() {
    lateinit var binding: FragmentDBinding
    var mainActivity: MainActivity? = null
    var injungMonthlyRent = 0L
    var yearlyRent = 0L
    var yearlySanghwan = 0L
    var ganjooRent = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDBinding.inflate(inflater, container, false)

        binding.textLoanAmount.setText("${mainActivity?.bundle?.getString("amountComma")}")
        if (mainActivity?.bundle?.getInt("gonext") != 4){
            binding.etLoanRate.setText(mainActivity?.bundle?.getString("loanRate"))
            binding.etDeposit.setText("${mainActivity?.bundle?.getLong("deposit")}")
            binding.etMonthlyRent.setText("${mainActivity?.bundle?.getLong("monthlyRent")}")
            mainActivity?.bundle?.putInt("gonext", 0)
        }

        binding.btnPreviousD.setOnClickListener{
            mainActivity?.setPreviousFragment(3)
        }

        binding.btnNextD.setOnClickListener{

//            if((binding.etLoanRate.text != null) && (binding.etDeposit.text != null) && binding.etMonthlyRent.text != null){
//                enterD()
//                mainActivity?.bundle?.putInt("gonext", 1)
//            } else {
//                mainActivity?.bundle?.putInt("gonext", 0)
//            }

            if(binding.etLoanRate.text.toString().toDouble() > 0 && binding.etDeposit.text.toString().toLong() > 0 && binding.etMonthlyRent.text.toString().toLong() > 0){
                Log.d("loanhelper", "왜 안 되냐???")
                enterD()
                mainActivity?.bundle?.putInt("gonext", 1)
            }else{
                mainActivity?.bundle?.putInt("gonext", 0)
                Log.d("loanhelper", "이거냐???")
            }

            if (mainActivity?.bundle?.getInt("gonext") == 4){
                calD()
            } else if (mainActivity?.bundle?.getInt("gonext") == 1){
                mainActivity?.bundle?.putInt("gonext", 0)
                mainActivity?.setNextFragment(3)
            } else {
                Toast.makeText(mainActivity!!.baseContext, "빈칸을 전부 입력해 주세요", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnConfirmD.setOnClickListener {
            mainActivity?.bundle?.let {
               if (it.getString("loanRate") == null){
                    Toast.makeText(mainActivity!!.baseContext, "대출금리를 입력해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    it.putString("loanRate", binding.etLoanRate.loanRateText)
                }

                if (it.getLong("deposit") == null){
                    Toast.makeText(mainActivity!!.baseContext, "보증금을 입력해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    it.putLong("deposit", binding.etDeposit.amountNumber)
                }

                if (it.getLong("monthlyRent") == null){
                    Toast.makeText(mainActivity!!.baseContext, "월 임대료를 입력해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    it.putLong("monthlyRent", binding.etMonthlyRent.amountNumber)
                }
            }
//            enterD()
            if(binding.etLoanRate.text.toString().toDouble() > 0.0 && binding.etDeposit.text.toString().toLong() > 0L && binding.etMonthlyRent.text.toString().toLong() > 0L){
                mainActivity?.bundle?.putInt("gonext", 1)
            }else{
                mainActivity?.bundle?.putInt("gonext", 0)
            }
            calD()
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is MainActivity) mainActivity = context
    }

    private fun enterD(){
        mainActivity?.bundle?.let {
            if(binding.etLoanRate.text.toString().toDouble() > 0.0){
                it.putString("loanRate", binding.etLoanRate.loanRateText)
            }else{
                Toast.makeText(mainActivity!!.baseContext, "대출금리를 입력해주세요", Toast.LENGTH_SHORT).show()
            }

            if(binding.etDeposit.text.toString().toLong() > 0L){
                it.putLong("deposit", binding.etDeposit.amountNumber)
            }else{
                Toast.makeText(mainActivity!!.baseContext, "보증금을 입력해주세요", Toast.LENGTH_SHORT).show()
            }

            if(binding.etMonthlyRent.text.toString().toLong() > 0L){
                it.putLong("monthlyRent", binding.etMonthlyRent.amountNumber)
            }else{
                Toast.makeText(mainActivity!!.baseContext, "월 임대료를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun calD(){
        var decimalFormat = DecimalFormat("#,###")
        mainActivity?.bundle?.let{
            binding.textRtiRate.setText("${it.getString("loanRate")?.toDouble()
                ?.plus(1.0)}")

            if(it.getInt("gongsil")==R.id.radio_Gongsil){
                injungMonthlyRent = it.getLong("monthlyRent")?.times(0.7).toLong()
                binding.textInjungRent.setText("${decimalFormat.format(injungMonthlyRent)}")
            }else{
                injungMonthlyRent = it.getLong("monthlyRent")
                binding.textInjungRent.setText("${decimalFormat.format(injungMonthlyRent)}")
            }

            ganjooRent = it.getLong("deposit")?.times(0.012).toLong()
            binding.textGanjooRent.setText("${decimalFormat.format(ganjooRent)}")

            yearlySanghwan = it.getString("amount")?.toLong()?.times(((it.getString("loanRate")
                ?.toDouble() ?:0.0 ) +1.0).div(100.0))?.toLong()!!
            it.putLong("yearlySanghwan", yearlySanghwan)
            binding.textYearlySanghwan.setText("${decimalFormat.format(yearlySanghwan)}")

            yearlyRent = injungMonthlyRent.times(12)+ganjooRent
            it.putLong("yearlyRent", yearlyRent)
            binding.textYearlyImdae.setText("${decimalFormat.format(injungMonthlyRent.times(12)+ganjooRent)}")
        }
    }

}