package ir.novin.dev.ui.main.users;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ir.novin.dev.modules.Users;
import ir.novin.dev.network.main.MainApi;
import ir.novin.dev.util.Resource;

public class UsersViewModel extends ViewModel {

    private MainApi mainApi;

    MediatorLiveData<Resource<Users>> users;

    @Inject
    public UsersViewModel(MainApi mainApi) {
        this.mainApi = mainApi;
    }

    public MediatorLiveData<Resource<Users>> observerUsers(int page) {


            users = new MediatorLiveData<>();
            users.setValue(Resource.loading((Users) null));

            final LiveData<Resource<Users>> source = LiveDataReactiveStreams
                    .fromPublisher(mainApi.getUsers(page)
                            .onErrorReturn(new Function<Throwable, Users>() {
                                @Override
                                public Users apply(Throwable throwable) throws Exception {

                                    Users users = new Users();
                                    users.setData(null);
                                    return users;
                                }
                            })
                            .map(new Function<Users, Resource<Users>>() {
                                @Override
                                public Resource<Users> apply(Users users) throws Exception {

                                    if (users == null || users.data==null)
                                        return Resource.error("error", null);

                                    return Resource.success(users);
                                }
                            })
                            .subscribeOn(Schedulers.io())

                    );

            users.addSource(source, new Observer<Resource<Users>>() {
                @Override
                public void onChanged(Resource<Users> listResource) {
                    users.setValue(listResource);
                    users.removeSource(source);
                }
            });


        return users;

    }


}
