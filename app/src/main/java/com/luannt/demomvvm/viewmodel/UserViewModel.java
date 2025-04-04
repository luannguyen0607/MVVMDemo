package com.luannt.demomvvm.viewmodel;

import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.luannt.demomvvm.model.User;
import com.luannt.demomvvm.respository.UserRepository;

public class UserViewModel extends ViewModel  implements Observable {
    private final MutableLiveData<String> userName = new MutableLiveData<>();
    private final UserRepository userRepository;
    private User user;
    private final PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

    public UserViewModel() {
        userRepository = new UserRepository();
        loadUser();
    }

    public LiveData<String> getUserName() {
        return userName;
    }

    public void updateName() {
        user.setName(userName.getValue());
        userRepository.updateUser(user);
    }

    private void loadUser() {
        user = userRepository.getUser();
        userName.setValue(user.getName());
    }

    @Bindable
    public String getBindableUserName() {
        return userName.getValue();
    }

    public void setBindableUserName(String name) {
        if (!name.equals(userName.getValue())) {
            userName.setValue(name);
            notifyPropertyChanged(com.luannt.demomvvm.BR.bindableUserName);
        }
    }
    public void notifyPropertyChanged(int fieldId) {
        propertyChangeRegistry.notifyCallbacks(this, fieldId, null);
    }
    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        propertyChangeRegistry.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        propertyChangeRegistry.remove(callback);
    }
}
