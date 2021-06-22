package ir.novin.dev;



import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import ir.novin.dev.di.DaggerAppComponent;


public class BaseApplication extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
