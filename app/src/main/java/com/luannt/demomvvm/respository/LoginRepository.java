package com.luannt.demomvvm.respository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.luannt.demomvvm.dao.UserDAO;
import com.luannt.demomvvm.database.UserDatabase;
import com.luannt.demomvvm.model.Result;
import com.luannt.demomvvm.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginRepository {
    private static volatile LoginRepository instance;
    private  ExecutorService executor;
    private  Handler mainHandler;
    private UserDatabase database;
    private UserDAO userDAO;
    private LiveData<User> user;

    private LoginRepository() {

    }

    private LoginRepository(Context context) {
        database = UserDatabase.getInstance(context);
        userDAO = database.userDAO();
        executor = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public static LoginRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (LoginRepository.class) {
                if (instance == null) {
                    instance = new LoginRepository(context);
                }
            }
        }
        return instance;
    }

    public Result login(String userName, String password) {
        User user = userDAO.getUserByUserIdPassword(userName, password);
        if (user != null) {
            //login success
            userDAO.updateAlreadyLogin(userName);
            return new Result.Success<>(user);
        } else {
            return new Result.Error(new RuntimeException());
        }
    }

    public void getLoggedInUser(OnUserLoadedCallback callback) {
        executor.execute(() -> {
            User user = userDAO.getLoggedInUser();
            mainHandler.post(() -> callback.onUserLoaded(user));
        });
    }

    public interface OnUserLoadedCallback {
        void onUserLoaded(User user);
    }

}
