package br.com.test.githubapitest.controller.others;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import br.com.test.githubapitest.R;
import br.com.test.githubapitest.controller.repositories.RepositoriesActivity;
import br.com.test.githubapitest.util.SharedPreferencesUtil;

public class InitActivity extends AppCompatActivity {

    private TextView txtMsgUsername;
    private TextView txtMsgPassword;
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnContinue;

    private boolean mustPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        initViews();
        setListeners();

        mustPassword = getIntent().getBooleanExtra("mustPassword", false);
        if (mustPassword) {
            txtMsgUsername.setVisibility(View.GONE);
            txtMsgPassword.setText(R.string.inform_password);
            edtUsername.setVisibility(View.GONE);
        }
    }

    private void initViews() {

        txtMsgUsername = findViewById(R.id.txtMsgUsername);
        txtMsgPassword = findViewById(R.id.txtMsgPassword);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnContinue = findViewById(R.id.btnContinue);
    }

    private void setListeners() {

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mustPassword) {
                    if (haveUsername()) {
                        SharedPreferencesUtil preferences = SharedPreferencesUtil.getInstance(getApplicationContext());
                        preferences.setValue("username", edtUsername.getText().toString());
                        preferences.setValue("password", edtPassword.getText().toString());

                        Intent intent = new Intent(InitActivity.this, RepositoriesActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(InitActivity.this, getString(R.string.must_inform_username), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (havePassword()) {
                        SharedPreferencesUtil preferences = SharedPreferencesUtil.getInstance(getApplicationContext());
                        preferences.setValue("password", edtPassword.getText().toString());

                        Intent intent = new Intent(InitActivity.this, RepositoriesActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(InitActivity.this, getString(R.string.must_inform_password), Toast.LENGTH_SHORT).show();
                    }
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

    private boolean havePassword() {
        boolean valid = false;
        if (!edtPassword.getText().toString().isEmpty()) {
            valid = true;
        }
        return valid;
    }
}
