package br.com.test.githubapitest.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.test.githubapitest.R;
import br.com.test.githubapitest.adapter.repositories.RepositoriesAdapter;
import br.com.test.githubapitest.apiservice.RetrofitClient;
import br.com.test.githubapitest.apiservice.ApiServiceIntf;
import br.com.test.githubapitest.model.repositories.RepoItem;
import br.com.test.githubapitest.util.AndroidUtil;
import br.com.test.githubapitest.util.SharedPreferencesUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private SwipyRefreshLayout swipeRefresh;

    private List<RepoItem> repositoriesList;
    private int page = 1;

    private static final int PER_PAGE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Verify internet
        if (!AndroidUtil.isNetworkAvailable(this)) {
            Intent intent = new Intent(this, OfflineActivity.class);
            startActivity(intent);
            finish();
        }

        start();
    }

    private void start() {

        initViews();
        setListeners();
        loadList();
    }

    private void initViews() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);

        swipeRefresh = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void setListeners() {

        swipeRefresh.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                swipeRefresh.setRefreshing(true);
                page++;
                loadList();
            }
        });
    }

    private void loadList() {

        try {
            progressDialog.show();

            SharedPreferencesUtil preferences = SharedPreferencesUtil.getInstance(getApplicationContext());
            String username = preferences.getValue("username", "");
            String password = preferences.getValue("password", "");

            ApiServiceIntf apiService = RetrofitClient.createService(ApiServiceIntf.class, username, password);

            Call<List<RepoItem>> call = apiService.getRepos(username, page, PER_PAGE);
            call.enqueue(new Callback<List<RepoItem>>() {
                @Override
                public void onResponse(Call<List<RepoItem>> call, Response<List<RepoItem>> response)
                {
                    if (response.code() >= 200 && response.code() <= 299) {
                        List<RepoItem> reposResponse = response.body();
                        if (repositoriesList == null && reposResponse != null) {
                            repositoriesList = new ArrayList<>();
                        }
                        if (repositoriesList != null) {
                            if (reposResponse != null) {
                                if (reposResponse.isEmpty()) {
                                    Toast.makeText(MainActivity.this, getString(R.string.no_more_repositories), Toast.LENGTH_SHORT).show();
                                } else {
                                    repositoriesList.addAll(reposResponse);
                                    if (recyclerView.getAdapter() == null) {
                                        recyclerView.setAdapter(new RepositoriesAdapter(getApplicationContext(), repositoriesList));
                                    } else {
                                        recyclerView.getAdapter().notifyDataSetChanged();
                                    }
                                }
                            }
                        }
                        swipeRefresh.setRefreshing(false);
                    } else {
                        Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.hide();
                }
                @Override
                public void onFailure(Call<List<RepoItem>> call, Throwable t) {
                    progressDialog.hide();
                    Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            progressDialog.hide();
            Toast.makeText(MainActivity.this,getString(R.string.problem_loading_repositories),Toast.LENGTH_SHORT).show();
        }
    }
}
