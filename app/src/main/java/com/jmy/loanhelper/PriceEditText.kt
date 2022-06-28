package com.jmy.loanhelper

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText
import java.text.DecimalFormat

class PriceEditText(context: Context, attrs: AttributeSet?): AppCompatEditText(context, attrs) {
    var inputText = ""
    var amountNumber:Long = 0L

    init {
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var text = s?.toString()?:""
                if (!TextUtils.isEmpty(text) && !TextUtils.equals(inputText,text)){
                    var strNumber: String //정수부
//                    var strDecimal = ""   //소수부

                    if (text.contains(".")){
                        strNumber = text.substring(0, text.indexOf("."))
//                        strDecimal = text.substring(text.indexOf("."), text.length)
                    }else{
                        strNumber = text
                    }

                    strNumber = strNumber.replace(",", "")
//                    var doubleText = strNumber.toDoubleOrNull()?:0.0
                    var longText = strNumber.toLongOrNull()?:0
                    amountNumber = longText
                    var decimalFormat = DecimalFormat("#,###")

//                    inputText = decimalFormat.format(doubleText) + strDecimal
                    inputText = decimalFormat.format(longText)

                    setText(inputText)
                    setSelection(inputText.length)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }
}