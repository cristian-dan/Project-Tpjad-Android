package com.tpjad.clienttpjad.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tpjad.clienttpjad.R;
import com.tpjad.clienttpjad.utils.LoginHelper;

public class LoginActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView usernameInput = (TextView)findViewById(R.id.loginUsername);
                TextView passwordInput = (TextView)findViewById(R.id.loginPassword);

                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                LoginHelper loginHelper = LoginHelper.getInstance();
                displayLoadingStatus();
                boolean loggedIn = loginHelper.loginRequest(username, password);
                if (!loggedIn) {
                    hideLoadingStatus();
                    Toast.makeText(LoginActivity.this, getString(R.string.login_error_message), Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        });
    }

    private void displayLoadingStatus() {
        mProgressDialog = ProgressDialog.show(this, getString(R.string.loading_login_title), getString(R.string.loading_login_message), true);
    }

    private void hideLoadingStatus() {
        mProgressDialog.dismiss();
    }
}
