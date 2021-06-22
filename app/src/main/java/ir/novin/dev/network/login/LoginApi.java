package ir.novin.dev.network.login;

import ir.novin.dev.modules.Login;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApi {

    @POST("api/login")
    Flowable<Login> getLogin(
            @Body Login login
    );
}
