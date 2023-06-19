package com.huynhngoctai.ttcm_app_altp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.huynhngoctai.ttcm_app_altp.database.dao.QuestionDAO;
import com.huynhngoctai.ttcm_app_altp.database.dao.ScoreUserDAO;
import com.huynhngoctai.ttcm_app_altp.database.entities.Question;
import com.huynhngoctai.ttcm_app_altp.database.entities.ScoreUser;

@Database(entities = {Question.class, ScoreUser.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract QuestionDAO getQuestionDAO();

    public abstract ScoreUserDAO getScoreUserDAO();
}
