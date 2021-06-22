package ir.novin.dev.di.login;

import androidx.lifecycle.ViewModel;

import ir.novin.dev.di.ViewModelKey;


import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ir.novin.dev.ui.login.LoginViewModel;

@Module
public abstract class LoginViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    public abstract ViewModel bindLoginViewModel(LoginViewModel viewModel);
}
