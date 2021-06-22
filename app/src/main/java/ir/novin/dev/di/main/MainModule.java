package ir.novin.dev.di.main;

import dagger.Module;
import dagger.Provides;
import ir.novin.dev.network.main.MainApi;
import ir.novin.dev.ui.main.users.UsersRecyclerAdapter;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @MainScope
    @Provides
    static MainApi provideMainApi(Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }

    @MainScope
    @Provides
    static UsersRecyclerAdapter usersRecyclerAdapter() {
        return new UsersRecyclerAdapter();
    }

}
