package ir.novin.dev.di;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.j256.ormlite.dao.Dao;
import ir.novin.dev.R;

import java.sql.SQLException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.novin.dev.database.OrmLite;
import ir.novin.dev.modules.Login;
import ir.novin.dev.util.Constants;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    static RequestOptions provideRequestOptions() {
        return RequestOptions.placeholderOf(R.drawable.ic_launcher_background);
    }

    @Singleton
    @Provides
    static RequestManager provideGlideInstance(Application application, RequestOptions requestOptions) {
        return Glide.with(application).setDefaultRequestOptions(requestOptions);

    }

    @Singleton
    @Provides
    static Drawable provideAppDrawable(Application application) {
        return ContextCompat.getDrawable(application, R.drawable.ic_launcher_background);
    }

    @Singleton
    @Provides
    static OrmLite provideOrmLite(Application application) {
        return new OrmLite(application);
    }

    @Singleton
    @Provides
    static Dao<Login, String> getDaoLeaveTime(OrmLite ormLite) {

        try {
            return ormLite.getDao(Login.class);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
