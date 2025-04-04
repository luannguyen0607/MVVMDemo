package com.luannt.demomvvm.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.luannt.demomvvm.R;
import com.luannt.demomvvm.databinding.ActivityMainBinding;
import com.luannt.demomvvm.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {
    private UserViewModel userViewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        binding.setViewModel(userViewModel);
        binding.setLifecycleOwner(this); // Important for LiveData observation
    }
}
