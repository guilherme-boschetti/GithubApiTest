package br.com.test.githubapitest.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.test.githubapitest.R;
import br.com.test.githubapitest.adapter.commits.CommitsAdapter;
import br.com.test.githubapitest.apiservice.ApiServiceIntf;
import br.com.test.githubapitest.apiservice.RetrofitClient;
import br.com.test.githubapitest.model.commits.CommitItem;
import br.com.test.githubapitest.model.repositories.RepoItem;
import br.com.test.githubapitest.util.SharedPreferencesUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CommitsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    private String repoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commits);

        repoName = getIntent().getStringExtra("repoName");
        if (repoName == null) {
            finish();
        }

        initViews();
        loadList();
    }

    private void initViews() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void loadList() {

        try {
            progressDialog.show();

            SharedPreferencesUtil preferences = SharedPreferencesUtil.getInstance(getApplicationContext());
            String username = preferences.getValue("username", "");
            String password = preferences.getValue("password", "");

            ApiServiceIntf apiService = RetrofitClient.createService(ApiServiceIntf.class, username, password);

            Call<List<CommitItem>> call = apiService.getCommits(username, repoName);
            call.enqueue(new Callback<List<CommitItem>>() {
                @Override
                public void onResponse(Call<List<CommitItem>> call, Response<List<CommitItem>> response)
                {
                    if (response.code() >= 200 && response.code() <= 299) {
                        List<CommitItem> commitsResponse = response.body();
                        if (commitsResponse != null && !commitsResponse.isEmpty()) {
                            List<CommitItem> commits = new ArrayList<>();
                            int size = commitsResponse.size();
                            int max = size > 5 ? 5 : size;
                            for(int i=0; i<max; i++) {
                                commits.add(commitsResponse.get(i));
                            }
                            recyclerView.setAdapter(new CommitsAdapter(getApplicationContext(), commits));
                        }
                    } else {
                        Toast.makeText(CommitsActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.hide();
                }
                @Override
                public void onFailure(Call<List<CommitItem>> call, Throwable t) {
                    progressDialog.hide();
                    Toast.makeText(CommitsActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            progressDialog.hide();
            Toast.makeText(CommitsActivity.this,getString(R.string.problem_loading_commits),Toast.LENGTH_SHORT).show();
        }
    }
}
