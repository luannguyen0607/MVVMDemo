package com.luannt.demomvvm.ui.main;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.luannt.demomvvm.R;
import com.luannt.demomvvm.model.Result;
import com.luannt.demomvvm.model.User;
import com.luannt.demomvvm.respository.LoginRepository;


public class MainViewModel extends ViewModel {
    private final MutableLiveData<User> loggedInUser = new MutableLiveData<>();
    private LoginRepository loginRepository;

    MainViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }
    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    public LiveData<User> getLoggedInUser() {
        return loggedInUser;
    }

    public void loadLoggedInUser() {
        loginRepository.getLoggedInUser(new LoginRepository.OnUserLoadedCallback() {
            @Override
            public void onUserLoaded(User user) {
                loggedInUser.setValue(user); // push to LiveData
            }
        });
    }
}
