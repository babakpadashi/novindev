package ir.novin.dev.ui.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ir.novin.dev.modules.DataDetials;
import ir.novin.dev.modules.Login;
import ir.novin.dev.network.details.DetailsApi;
import ir.novin.dev.util.Resource;

public class DetailsViewModel extends ViewModel {

    private final DetailsApi detailsApi;

    @Inject
    public DetailsViewModel(DetailsApi detailsApi) {
        this.detailsApi= detailsApi;

    }


    public LiveData<Resource<DataDetials>> getUser(int id){

        LiveData<Resource<DataDetials>> error = LiveDataReactiveStreams
                .fromPublisher(detailsApi.getDetials(id)
                        .onErrorReturn(new Function<Throwable, DataDetials>() {
                            @Override
                            public DataDetials apply(Throwable throwable) throws Exception {
                                DataDetials dataDetialsError = new DataDetials();
                                dataDetialsError.setData(null);

                                return dataDetialsError;
                            }
                        })
                        .map(new Function<DataDetials, Resource<DataDetials>>() {
                            @Override
                            public Resource<DataDetials> apply(DataDetials dataDetials) throws Exception {

                                if (dataDetials.getData()==null) {
                                    return Resource.error("Details not found", (DataDetials)null);
                                }
                                return Resource.success(dataDetials);
                            }
                        })
                        .subscribeOn(Schedulers.io())

                );
        return error;
    }

}
