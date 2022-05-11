//package com.example.roomnek.viewmodel;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.ViewModel;
//
//import com.example.roomnek.R;
//import com.example.roomnek.databaseTopic.TopicDatabaseBase;
//import com.example.roomnek.model.Success;
//import com.example.roomnek.model.Topic;
//import com.example.roomnek.network.API;
//
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class TopicViewModeJava extends ViewModel {
//
//    public MutableLiveData<List<Success>> mutableLiveData = new MutableLiveData<>();
//
//    public List<Success> clickGetTopic(List<Success> postsList, Context context) {
//        API.api.getTopics(1).enqueue(new Callback<Topic>() {
//            @Override
//            public void onResponse(Call<Topic> call, Response<Topic> response) {
//                Topic topic = response.body();
//                for (int i = 0; i < topic.getSuccess().size(); i++) {
//                    Success success = new Success(topic.getSuccess().get(i).getName(),
//                            topic.getSuccess().get(i).getSoluong());
//                    postsList.add(success);
//
//                    if (TopicDatabaseBase.getInstance(context).topicDAO().getListTopic().size() <= postsList.size()) {
//                        String strTopic = topic.getSuccess().get(i).getName();
//                        String strAmount = topic.getSuccess().get(i).getSoluong();
//                        Success successDataRoom = new Success(strTopic, strAmount);
//                        TopicDatabaseBase.getInstance(context).topicDAO().insertTopic(successDataRoom);
//                    }
//                    Log.d("iiiLog", String.valueOf(postsList.size()));
//                }
//                if (response.isSuccessful() && postsList!= null) {
//                    mutableLiveData.postValue(postsList);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Topic> call, Throwable t) {
//                Toast toast = Toast.makeText(context, "LOAD DATA's TOPIC FAILED", Toast.LENGTH_SHORT);
//                customToast(toast);
//            }
//        });
//        return null;
//    }
//
//    public void customToast(Toast toast) {
//        View toastView = toast.getView();
//        TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
//        toastMessage.setTextSize(13);
//        toastMessage.setTextColor(Color.YELLOW);
//        toastMessage.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//        toastMessage.setGravity(Gravity.CENTER);
//        toastMessage.setCompoundDrawablePadding(4);
//        toastView.setBackgroundColor(Color.BLACK);
//        toastView.setBackgroundResource(R.drawable.bg_toast);
//        toast.show();
//
//    }
//}
//
