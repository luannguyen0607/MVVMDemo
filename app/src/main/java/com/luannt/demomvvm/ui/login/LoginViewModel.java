package com.luannt.demomvvm.ui.login;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.luannt.demomvvm.R;
import com.luannt.demomvvm.model.Result;
import com.luannt.demomvvm.model.User;
import com.luannt.demomvvm.respository.LoginRepository;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private final MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private final MutableLiveData<User> loggedInUser = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        Result result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            User data = ((Result.Success<User>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String password) {
        if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
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