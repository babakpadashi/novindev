package ir.novin.dev.ui.details;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;
import com.google.android.material.appbar.MaterialToolbar;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import ir.novin.dev.R;
import ir.novin.dev.modules.DataDetials;
import ir.novin.dev.util.Resource;
import ir.novin.dev.viewmodels.ViewModelProviderFactory;

public class DetailsActivity extends DaggerAppCompatActivity {

    private DetailsViewModel viewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    RequestManager requestManager;

    ProgressBar progressBar;

    private MaterialToolbar toolbar;

    TextView emailTxt, firstnameTxt, lastnameTxt;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        progressBar = findViewById(R.id.progress_bar);
        image = findViewById(R.id.image);
        emailTxt=findViewById(R.id.email_txt);
        firstnameTxt=findViewById(R.id.firstname_txt);
        lastnameTxt=findViewById(R.id.lastname_txt);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Details");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = ViewModelProviders.of(this, providerFactory).get(DetailsViewModel.class);

        Intent intent = getIntent();

        if (intent != null) {

            subscribeObserver(intent.getIntExtra("id", 0));

        }


    }


    public void subscribeObserver(int id) {

        progressBar.setVisibility(View.VISIBLE);

        viewModel.getUser(id).observe(this, new Observer<Resource<DataDetials>>() {
            @Override
            public void onChanged(Resource<DataDetials> resource) {

                if (resource != null) {

                    switch (resource.status) {

                        case ERROR: {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(DetailsActivity.this, resource.message, Toast.LENGTH_LONG).show();
                            break;
                        }

                        case SUCCESS: {
                            progressBar.setVisibility(View.GONE);
                            setView((DataDetials) resource.data);
                            break;
                        }

                    }

                }
            }
        });
    }

    private void setView(DataDetials data) {

        requestManager.load(data.getData().getAvatar()).into(image);

        emailTxt.setText(data.getData().getEmail());
        firstnameTxt.setText(data.getData().getFirst_name());
        lastnameTxt.setText(data.getData().getLast_name());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {


        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
//