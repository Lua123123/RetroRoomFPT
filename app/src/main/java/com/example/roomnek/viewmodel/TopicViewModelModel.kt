package com.example.roomnek.viewmodel

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.example.roomnek.network.API
import com.example.roomnek.model.Topic
import com.example.roomnek.databaseTopic.TopicDatabaseBase
import android.widget.Toast
import android.widget.TextView
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.roomnek.R
import com.example.roomnek.model.Success
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopicViewModelModel : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    var job: Job? = null

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    var mutableLiveData = MutableLiveData<List<Success>>()

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    fun mClickGetTopic(postsList: MutableList<Success>?, context: Context?) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            clickGetTopic(postsList, context)
        }
    }

    fun clickGetTopic(postsList: MutableList<Success>?, context: Context?): List<Success>? {
            API.api.getTopics(1).enqueue(object : Callback<Topic?> {
            override fun onResponse(call: Call<Topic?>, response: Response<Topic?>) {
                val topic = response.body()
                for (i in topic!!.success!!.indices) {
                    val success = Success(
                        topic.success!![i].name,
                        topic.success!![i].soluong
                    )
                    postsList!!.add(success)
                    if (TopicDatabaseBase.getInstance(context).topicDAO()
                            .getListTopic().size <= postsList.size
                    ) {
                        val strTopic = topic.success!![i].name
                        val strAmount = topic.success!![i].soluong
                        val successDataRoom = Success(strTopic, strAmount)
                        TopicDatabaseBase.getInstance(context).topicDAO()
                            .insertTopic(successDataRoom)
                    }
                    Log.d("iiiLog", postsList.size.toString())
                }
                if (response.isSuccessful && postsList != null) {
                    mutableLiveData.postValue(postsList!!)
                }
            }

            override fun onFailure(call: Call<Topic?>, t: Throwable) {
                val toast = Toast.makeText(context, "LOAD DATA's TOPIC FAILED", Toast.LENGTH_SHORT)
                customToast(toast)
            }
        })
        return null
    }

    fun customToast(toast: Toast) {
        val toastView = toast.view
        val toastMessage = toastView!!.findViewById<View>(android.R.id.message) as TextView
        toastMessage.textSize = 13f
        toastMessage.setTextColor(Color.YELLOW)
        toastMessage.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        toastMessage.gravity = Gravity.CENTER
        toastMessage.compoundDrawablePadding = 4
        toastView.setBackgroundColor(Color.BLACK)
        toastView.setBackgroundResource(R.drawable.bg_toast)
        toast.show()
    }

    fun openDialogInsertTopic(gravity: Int, context: Context, postsList: MutableList<Success>?) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_custom)
        val window = dialog.window ?: return
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAttributes = window.attributes
        windowAttributes.gravity = gravity
        window.attributes = windowAttributes
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true)
        } else {
            dialog.setCancelable(false)
        }
        val edtName = dialog.findViewById<EditText>(R.id.edt_name)
        val edtAmount = dialog.findViewById<EditText>(R.id.edt_amount)
        val btnCancel: ConstraintLayout = dialog.findViewById(R.id.btn_cancel)
        val btnConfirm: ConstraintLayout = dialog.findViewById(R.id.btn_confirm)
        btnCancel.setOnClickListener { dialog.dismiss() }
        btnConfirm.setOnClickListener {
            val name = edtName.text.toString().trim { it <= ' ' }
            val amount = edtAmount.text.toString().trim { it <= ' ' }
            addTopic(name, amount, edtName, edtAmount, context, postsList)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun addTopic(name: String, amount: String, edtName: EditText, edtAmount: EditText, context: Context, postsList: MutableList<Success>?) {
        if (name.isEmpty() || amount.isEmpty()) {
            return
        }
        var success: Success = Success(name, amount)
        TopicDatabaseBase.getInstance(context).topicDAO().insertTopic(success)
        var toast: Toast =
            Toast.makeText(context, "INSERT SUCCESS", Toast.LENGTH_LONG)
        customToast(toast)
        edtName.setText("")
        edtAmount.setText("")

        if (postsList != null) {
            mutableLiveData.postValue(postsList!!)
        }
    }
}