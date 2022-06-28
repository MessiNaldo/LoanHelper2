package com.jmy.loanhelper

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.core.text.getSpans
import com.jmy.loanhelper.databinding.FragmentBBinding
import java.text.DecimalFormat


class BFragment : Fragment() {
    lateinit var binding: FragmentBBinding
    var mainActivity: MainActivity? = null
    var inputText = ""
    var amountNumber:Long = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBBinding.inflate(inflater, container, false)

        mainActivity?.bundle?.putInt("businessform", R.id.radio_Soho)
        mainActivity?.bundle?.putInt("yongdo", R.id.radio_For_Rent)
        mainActivity?.bundle?.putInt("dambo", R.id.radio_Not_Dambo)
        mainActivity?.bundle?.putInt("jungchaek", R.id.radio_Not_Jungchaek)

        if (mainActivity?.bundle?.getInt("gonext") != 3){
            mainActivity?.bundle?.putInt("gonext", 0)
        }

        binding.etLoanAmount.setText(mainActivity?.bundle?.getString("amountComma", ""))
        Log.d("loanhelper", "Amount? ${mainActivity?.bundle?.getString("amount")}")

        binding.radioGroupBussinessForm.setOnCheckedChangeListener { radioGroup, checkedId ->
            mainActivity?.bundle?.putInt("businessform", checkedId)
        }

        binding.radioGroupYongdo.setOnCheckedChangeListener { radioGroup, checkedId ->
            mainActivity?.bundle?.putInt("yongdo", checkedId)
        }

        binding.radioGroupDambo.setOnCheckedChangeListener { radioGroup, checkedId ->
            mainActivity?.bundle?.putInt("dambo", checkedId)
        }

        binding.radioGroupJungchaek.setOnCheckedChangeListener { radioGroup, checkedId ->
            mainActivity?.bundle?.putInt("jungchaek", checkedId)
        }

        binding.etLoanAmount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var text = s?.toString()?:""
                if (!TextUtils.isEmpty(text) && !TextUtils.equals(inputText,text)){
                    var strNumber: String

                    if (text.contains(".")){
                        strNumber = text.substring(0, text.indexOf("."))
                    }else{
                        strNumber = text
                    }

                    strNumber = strNumber.replace(",", "")
                    var longText = strNumber.toLongOrNull()?:0
                    amountNumber = longText
                    var decimalFormat = DecimalFormat("#,###")
                    inputText = decimalFormat.format(longText)

                    binding.etLoanAmount.setText(inputText)
                    binding.etLoanAmount.setSelection(inputText.length)
                }
            }

            override fun afterTextChanged(s: Editable?) {
//                s?.let { highlightText(it) }
            }
        })

        binding.btnConfirmB.setOnClickListener {
            if(mainActivity?.bundle?.getInt("gonext") == 3 ){
                confirmB()
            } else {
                mainActivity?.bundle?.putString("amount", "${amountNumber}")
                mainActivity?.bundle?.putString("amountComma", "${inputText}")
                confirmB()
            }
        }

        binding.btnNextB.setOnClickListener{

            mainActivity?.bundle?.let {
                if (it.getInt("gonext") == 1){
                    mainActivity?.setNextFragment(1)
                    it.putInt("gonext", 0)
                } else {
                    binding.textResult1.setText("다음 단계 진행 불가")
                }
            }
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MainActivity) mainActivity = context
    }

    fun confirmB(){
        mainActivity?.bundle?.let {
            if ((it.getInt("businessform") == R.id.radio_Soho) && (it.getInt("yongdo") == R.id.radio_For_Rent)
                && (it.getInt("dambo") == R.id.radio_Not_Dambo) && (it.getInt("jungchaek") == R.id.radio_Not_Jungchaek)
                && (it.getString("amount")?.toLongOrNull()!! >100_000_000)){
                binding.textResult1.setText("RTI 대상입니다")
                it.putInt("gonext",1)
            } else if ((it.getString("amount") == null) || (it.getString("amount")!!.toLong() <= 0L)){
                binding.textResult1.setText("대출(예정) 금액을 입력해주세요")
                Log.d("loanhelper", "대출 금액은? ${it.getString("amount")}")
                it.putInt("gonext",0)
            }else{
                binding.textResult1.setText("RTI 대상이 아닙니다")
                it.putInt("gonext",0)
            }
        }
    }

//    private fun highlightText(text: Editable){
//        binding.priceEditTextB.text?.let { editable ->
//            val spans = editable.getSpans(0, editable.length, ForegroundColorSpan::class.java)
//            spans.forEach { span ->
//                editable.removeSpan(span)
//            }
//        }
//        val startIndex: Int?
//        val endIndex: Int?
//        if((text.length-1) % 3 == 0 && text.length > 3){
//            startIndex = text.length
//            endIndex = text.length
//            Log.d("loanhelper", "${text.length}")
//        }else{
//            startIndex = text.length-1
//            endIndex = text.length
//        }
//
//        binding.priceEditTextB.text?.setSpan(
//            ForegroundColorSpan(ContextCompat.getColor(mainActivity!!.baseContext, R.color.purple_200)),
//            startIndex,
//            endIndex,
//            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//        )
//    }
}