package ir.novin.dev.di.details;



import dagger.Module;
import dagger.Provides;
import ir.novin.dev.network.details.DetailsApi;
import retrofit2.Retrofit;


@Module
public class DetailsModule {

    @DetailsScope
    @Provides
    static DetailsApi provideAuthApi(Retrofit retrofit){
        return  retrofit.create(DetailsApi.class);
    }
}
