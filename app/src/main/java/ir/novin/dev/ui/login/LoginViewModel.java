package ir.novin.dev.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ir.novin.dev.modules.Login;
import ir.novin.dev.network.login.LoginApi;
import ir.novin.dev.util.Resource;

public class LoginViewModel extends ViewModel {

    private final LoginApi loginApi;

    @Inject
    public LoginViewModel(LoginApi loginApi) {
        this.loginApi = loginApi;
    }


    public LiveData<Resource<Login>> loginUser(Login login){

        LiveData<Resource<Login>> error = LiveDataReactiveStreams
                .fromPublisher(loginApi.getLogin(login)
                        .onErrorReturn(new Function<Throwable, Login>() {
                            @Override
                            public Login apply(Throwable throwable) throws Exception {
                                Login loginError = new Login();
                                loginError.setToken("-1");

                                return loginError;
                            }
                        })
                        .map(new Function<Login, Resource<Login>>() {
                            @Override
                            public Resource<Login> apply(Login login) throws Exception {

                                if (login.getToken().equals("-1")) {
                                    return Resource.error("user not found", (Login)null);
                                }
                                return Resource.success(login);
                            }
                        })
                        .subscribeOn(Schedulers.io())

                );
        return error;
    }

}
