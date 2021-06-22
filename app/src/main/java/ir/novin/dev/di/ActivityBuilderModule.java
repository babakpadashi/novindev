package ir.novin.dev.di;

import ir.novin.dev.di.details.DetailsModule;
import ir.novin.dev.di.details.DetailsScope;
import ir.novin.dev.di.details.DetailsViewModelsModule;
import ir.novin.dev.di.login.LoginModule;
import ir.novin.dev.di.login.LoginScope;
import ir.novin.dev.di.login.LoginViewModelsModule;
import ir.novin.dev.di.main.MainFragmentBuilderModule;
import ir.novin.dev.di.main.MainModule;
import ir.novin.dev.di.main.MainScope;
import ir.novin.dev.di.main.MainViewModelModule;

import ir.novin.dev.ui.details.DetailsActivity;
import ir.novin.dev.ui.login.LoginActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ir.novin.dev.ui.main.MainActivity;

@Module
public abstract class ActivityBuilderModule {

    @LoginScope
    @ContributesAndroidInjector(
            modules = {LoginViewModelsModule.class, LoginModule.class}
    )
    abstract LoginActivity contributeAuthActivity();

    @MainScope
    @ContributesAndroidInjector(
            modules = {MainFragmentBuilderModule.class, MainViewModelModule.class, MainModule.class}
    )
    abstract MainActivity contributeMainActivity();

    @DetailsScope
    @ContributesAndroidInjector(
            modules = {DetailsViewModelsModule.class, DetailsModule.class}
    )
    abstract DetailsActivity contributeDetailsActivity();

}
