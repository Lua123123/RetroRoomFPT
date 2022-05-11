package com.example.roomnek.databaseTopic;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.roomnek.model.Success;

@Database(entities = {Success.class}, version = 1)
public abstract class TopicDatabaseBase extends RoomDatabase {

//    static Migration migration_from_2_to_3 = new Migration(2, 3) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE topics ADD COLUMN soluong TEXT");
//        }
//    };

    private static final String DATABASE_NAME = "topics.db";
    private static TopicDatabaseBase instance;

    public static synchronized TopicDatabaseBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), TopicDatabaseBase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
//                    .addMigrations(migration_from_2_to_3)
                    .build();
        }
        return instance;
    }
    public abstract TopicDAO topicDAO();
}
