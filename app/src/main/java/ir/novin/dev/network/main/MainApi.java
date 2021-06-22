package ir.novin.dev.network.main;

import ir.novin.dev.modules.Users;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainApi {

    //post?userId=1

    @GET("api/users")
    Flowable<Users> getUsers(
            @Query("page") int page
    );

}
