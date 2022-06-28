package com.jmy.loanhelper

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import java.text.DecimalFormat

class LoanRateEditText2(context: Context, attrs: AttributeSet?): AppCompatEditText(context, attrs) {
    var loanRateText = ""

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var text = s?.toString()?:""
                if (!TextUtils.isEmpty(text) && !TextUtils.equals(loanRateText,text)){
                    var strNumber: String //정수부
                    var strDecimal = ""   //소수부

                    if (text.contains(".")){
                        strNumber = text.substring(0, text.indexOf("."))
                        strDecimal = text.substring(text.indexOf("."), text.length)
                    }else{
                        strNumber = text
                    }

                    strNumber = strNumber.replace(",", "")
                    var doubleText = strNumber.toDoubleOrNull()?:0.0

                    var decimalFormat = DecimalFormat("#,###")

                    loanRateText = decimalFormat.format(doubleText) + strDecimal

//                    MainActivity().bundle.putString("loanRate", loanRateText)
                    setText(loanRateText)
                    setSelection(loanRateText.length)

//                    MainActivity().bundle.putString("loanRateconfirm", "입력완료")
//                    Toast.makeText(MainActivity().baseContext, "입렵", Toast.LENGTH_SHORT).show()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }
}