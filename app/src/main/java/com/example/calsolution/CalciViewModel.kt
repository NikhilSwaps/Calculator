package com.example.calsolution

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class CalciViewModel : ViewModel() {
    private val _calcText = MutableLiveData("")
    val calcText : LiveData<String> = _calcText

    private val _resText = MutableLiveData("0")
    val resText : LiveData<String> = _resText

    fun onButtonClick(btn : String){

        _calcText.value?.let{
            if(btn == "AC") {
                _calcText.value = ""
                _resText.value = "0"
                return
            }

            if(btn == "C"){
                if(it.isNotEmpty()){
                    _calcText.value = it.substring(0,it.length-1)
                }
                return
            }

            if(btn == "="){
                _calcText.value = _resText.value
                return
            }

            _calcText.value = it+btn

            try{
                _resText.value = calcResu(_calcText.value.toString())
            }catch(_ : Exception){}

        }
    }

    fun calcResu(exp : String) : String{
        val context : Context = Context.enter()
        context.optimizationLevel = -1
        val scriptable : Scriptable = context.initStandardObjects()
        var result = context.evaluateString(scriptable,exp,"Javascript",1,null).toString()
        if(result.endsWith(".0")){
            result = result.replace(".0","")
        }
        return result
    }
}