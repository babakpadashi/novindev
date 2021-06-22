package ir.novin.dev.ui.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import ir.novin.dev.R;
import ir.novin.dev.modules.Login;
import ir.novin.dev.ui.main.MainActivity;
import ir.novin.dev.util.Resource;
import ir.novin.dev.viewmodels.ViewModelProviderFactory;

public class LoginActivity extends DaggerAppCompatActivity {

    private LoginViewModel viewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    RequestManager requestManager;

    ProgressBar progressBar;
    EditText emailEdt, passwordEdt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progress_bar);
        emailEdt = findViewById(R.id.email_edt);
        passwordEdt = findViewById(R.id.password_edt);

        viewModel = ViewModelProviders.of(this, providerFactory).get(LoginViewModel.class);


    }


    public void subscribeObserver(Login login) {

        progressBar.setVisibility(View.VISIBLE);

        viewModel.loginUser(login).observe(this, new Observer<Resource<Login>>() {
            @Override
            public void onChanged(Resource<Login> resource) {

                if (resource != null) {

                    switch (resource.status) {

                        case ERROR: {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, resource.message, Toast.LENGTH_LONG).show();
                            break;
                        }

                        case SUCCESS: {
                            progressBar.setVisibility(View.GONE);
                            onLoginSucces();
                            break;
                        }

                    }

                }
            }
        });
    }

    public void onLoginSucces() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void login(View view) {

        if (TextUtils.isEmpty(emailEdt.getText().toString())) {
            Toast.makeText(this, "Email is empty", Toast.LENGTH_LONG);
            emailEdt.setError("Email is empty");
            return;
        } else {
            emailEdt.setError(null);
        }

        if (TextUtils.isEmpty(passwordEdt.getText().toString())) {
            Toast.makeText(this, "Password is empty", Toast.LENGTH_LONG);
            passwordEdt.setError("Password is empty");
            return;
        } else {
            passwordEdt.setError(null);
        }

        Login login = new Login();
        login.setEmail(emailEdt.getText().toString());
        login.setPassword(passwordEdt.getText().toString());
        hideKeyboard();
        subscribeObserver(login);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
