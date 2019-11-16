package br.com.test.githubapitest.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import br.com.test.githubapitest.R;
import br.com.test.githubapitest.util.SharedPreferencesUtil;

public class InitActivity extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        initViews();
        setListeners();
    }

    private void initViews() {

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnContinue = findViewById(R.id.btnContinue);
    }

    private void setListeners() {

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (haveUsername()) {
                    SharedPreferencesUtil preferences = SharedPreferencesUtil.getInstance(getApplicationContext());
                    preferences.setValue("username", edtUsername.getText().toString());
                    preferences.setValue("password", edtPassword.getText().toString());

                    Intent intent = new Intent(InitActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(InitActivity.this, getString(R.string.must_inform_username), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean haveUsername() {
        boolean valid = false;
        if (!edtUsername.getText().toString().isEmpty()) {
            valid = true;
        }
        return valid;
    }
}
