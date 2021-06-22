package ir.novin.dev.di.details;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ir.novin.dev.di.ViewModelKey;
import ir.novin.dev.ui.details.DetailsViewModel;
import ir.novin.dev.ui.login.LoginViewModel;

@Module
public abstract class DetailsViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel.class)
    public abstract ViewModel bindDetailsViewModel(DetailsViewModel viewModel);
}
