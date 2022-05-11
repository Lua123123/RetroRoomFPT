package com.example.roomnek

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomnek.adapter.ListTopicAdapter
import com.example.roomnek.databaseTopic.TopicDatabaseBase
import com.example.roomnek.model.Success
import com.example.roomnek.viewmodel.TopicViewModelModel
class TopicActivity : AppCompatActivity() {
//            topicViewModel?.let {  }
//            topicViewModel?.run {  }
//            topicViewModel?.apply {  }

    private lateinit var btnCall: ConstraintLayout
    private var postsList: MutableList<Success> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListTopicAdapter
    private var layoutManager: LinearLayoutManager? = null
    private var context: Context = this@TopicActivity

    private val topicViewModel: TopicViewModelModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic)

        adapter = ListTopicAdapter(postsList, context = this)
        recyclerView = findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(this)
        recyclerView.setLayoutManager(layoutManager)
        btnCall = findViewById(R.id.btn_Add)

        if (haveNetwork()) {
            val toast: Toast =
                Toast.makeText(context, "CONNECT INTERNET SUCCESS", Toast.LENGTH_LONG)
            topicViewModel.customToast(toast)
            topicViewModel.mClickGetTopic(postsList, context)
            if (postsList != null) {
                topicViewModel.mutableLiveData.observe(this, Observer<List<Success>> {
                    adapter.setData(postsList)
                    recyclerView.setAdapter(adapter)
                })
            }
        }
        if (!haveNetwork()) {
            val toast: Toast = Toast.makeText(context, "DON'T HAVE INTERNET", Toast.LENGTH_LONG)
            topicViewModel.customToast(toast)
            postsList = TopicDatabaseBase.getInstance(this).topicDAO()
                .getListTopic() as MutableList<Success>
            adapter.setData(postsList)
            recyclerView.setAdapter(adapter)
        }

        btnCall.setOnClickListener {
            if (!haveNetwork()) {
                topicViewModel.openDialogInsertTopic(Gravity.CENTER, context, postsList)
                topicViewModel.mutableLiveData.observe(this, Observer<List<Success>> {
                    if (postsList != null)
                    postsList = TopicDatabaseBase.getInstance(this).topicDAO()
                        .getListTopic() as MutableList<Success>
                    adapter.setData(postsList)
                })
            } else {
                var toast: Toast = Toast.makeText(context, "PLS INTERNET DISCONNECTED", Toast.LENGTH_LONG)
                topicViewModel.customToast(toast)
            }
        }
    }

    private fun haveNetwork(): Boolean {
        var have_WIFI = false
        var have_MobileData = false
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfos = connectivityManager.allNetworkInfo
        for (info in networkInfos) {
            if (info.typeName.equals("WIFI", ignoreCase = true)) if (info.isConnected) have_WIFI =
                true
            if (info.typeName.equals(
                    "MOBILE",
                    ignoreCase = true
                )
            ) if (info.isConnected) have_MobileData = true
        }
        return have_WIFI || have_MobileData
    }
}