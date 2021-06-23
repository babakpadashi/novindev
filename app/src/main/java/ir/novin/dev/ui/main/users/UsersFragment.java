package ir.novin.dev.ui.main.users;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import ir.novin.dev.R;
import ir.novin.dev.modules.Users;
import ir.novin.dev.util.Resource;
import ir.novin.dev.viewmodels.ViewModelProviderFactory;

public class UsersFragment extends DaggerFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "UserFragment";

    private UsersViewModel viewModel;
    private RecyclerView recyclerView;

    @Inject
    UsersRecyclerAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    RequestManager requestManager;

    ProgressBar progressBar;

    SwipeRefreshLayout swipLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);

        swipLayout = view.findViewById(R.id.swipe_layout);

        initRecyclerView();

        swipLayout.setOnRefreshListener(this);

        viewModel = ViewModelProviders.of(this, providerFactory).get(UsersViewModel.class);

        subscribeObserver(1);

        adapter.addContext(this);
        adapter.addRequestManager(requestManager);
    }

    private void subscribeObserver(int page) {

        progressBar.setVisibility(View.VISIBLE);

        viewModel.observerUsers(page).removeObservers(getViewLifecycleOwner());
        viewModel.observerUsers(page).observe(getViewLifecycleOwner(), new Observer<Resource<Users>>() {
            @Override
            public void onChanged(Resource<Users> listResource) {
                if (listResource != null) {


                    switch (listResource.status) {


                        case SUCCESS: {
                            loading = true;
                            progressBar.setVisibility(View.GONE);
                            adapter.setUsers(((Users) listResource.data).data);
                            recyclerView.post(new Runnable() {
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                            swipLayout.setRefreshing(false);
                            break;
                        }

                        case ERROR: {
                            loading = true;
                            progressBar.setVisibility(View.GONE);
                            swipLayout.setRefreshing(false);
                            break;
                        }
                    }
                }

            }
        });
    }

    private boolean loading = true;
    private int count = 1;

    private void initRecyclerView() {

        final LinearLayoutManager aLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(aLinearLayoutManager);
        recyclerView.setAdapter(adapter);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0
                        : recyclerView.getChildAt(0).getTop();


                if (dy > 0) {
                    int visibleItemCount = aLinearLayoutManager.getChildCount();
                    int totalItemCount = aLinearLayoutManager.getItemCount();
                    int pastVisiblesItems = aLinearLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= (totalItemCount)) {

                            loading = false;
                            count++;
                            subscribeObserver(count);

                        }
                    }
                }

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
        });


    }

    @Override
    public void onRefresh() {
        adapter.clearUsers();
        recyclerView.post(new Runnable() {
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
        loading = true;
        count = 1;
        subscribeObserver(count);
    }

}
