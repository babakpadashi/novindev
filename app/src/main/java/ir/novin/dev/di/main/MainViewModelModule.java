package ir.novin.dev.di.main;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ir.novin.dev.di.ViewModelKey;
import ir.novin.dev.ui.main.users.UsersViewModel;

@Module
public  abstract  class MainViewModelModule {



   @Binds
    @IntoMap
    @ViewModelKey(UsersViewModel.class)
    public abstract ViewModel bindUsersViewModel(UsersViewModel viewModel);

}
