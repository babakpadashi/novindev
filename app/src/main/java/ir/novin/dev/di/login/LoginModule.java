package ir.novin.dev.di.login;



import dagger.Module;
import dagger.Provides;
import ir.novin.dev.network.login.LoginApi;
import retrofit2.Retrofit;


@Module
public class LoginModule {

    @LoginScope
    @Provides
    static LoginApi provideAuthApi(Retrofit retrofit){
        return  retrofit.create(LoginApi.class);
    }
}
