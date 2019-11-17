package br.com.test.githubapitest.controller.others;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.appcompat.app.AppCompatActivity;
import br.com.test.githubapitest.R;
import br.com.test.githubapitest.controller.repositories.RepositoriesActivity;
import br.com.test.githubapitest.util.SharedPreferencesUtil;

public class ScanActivity extends AppCompatActivity {

    private Button btnType;
    private Button btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        initViews();
        setListeners();
    }

    private void initViews() {

        btnType = findViewById(R.id.btnType);
        btnScan = findViewById(R.id.btnScan);
    }

    private void setListeners() {

        btnType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScanActivity.this, InitActivity.class);
                intent.putExtra("mustPassword", false);
                startActivity(intent);
                finish();
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new IntentIntegrator(ScanActivity.this).setPrompt("").initiateScan();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            String qrOrBarCodeRead = result.getContents();
            if (qrOrBarCodeRead != null) {
                // Toast.makeText(ScanActivity.this, qrOrBarCodeRead, Toast.LENGTH_LONG).show();
                trateScanResult(qrOrBarCodeRead);
            } // else Canceled
        }
    }

    private void trateScanResult(String qrOrBarCodeRead) {

        // qrOrBarCodeRead example: username:test-name##mode:public

        if (!qrOrBarCodeRead.contains("##")) {
            notifyUser();
            return;
        }
        String[] parts = qrOrBarCodeRead.split("##");
        if (parts.length != 2) {
            notifyUser();
            return;
        }
        String part1 = parts[0];
        String part2 = parts[1];
        if (!part1.contains("username:") && !part2.contains("mode:") &&
                (!part2.contains("public") || !part2.contains("private"))) {
            notifyUser();
            return;
        }
        String username = part1.replace("username:", "");
        SharedPreferencesUtil preferences = SharedPreferencesUtil.getInstance(getApplicationContext());
        preferences.setValue("username", username);
        if (part2.contains("public")) {
            Intent intent = new Intent(ScanActivity.this, RepositoriesActivity.class);
            startActivity(intent);
            finish();
        } else { // private
            Intent intent = new Intent(ScanActivity.this, InitActivity.class);
            intent.putExtra("mustPassword", true);
            startActivity(intent);
            finish();
        }
    }

    private void notifyUser() {

        Toast.makeText(this, getString(R.string.invalid_qrcode), Toast.LENGTH_LONG).show();
    }
}
