package ir.novin.dev.network.details;

import io.reactivex.Flowable;
import ir.novin.dev.modules.DataDetials;
import ir.novin.dev.modules.Login;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DetailsApi {

    @GET("api/users/{Id}")
    Flowable<DataDetials> getDetials(
            @Path("Id") int id
    );
}
