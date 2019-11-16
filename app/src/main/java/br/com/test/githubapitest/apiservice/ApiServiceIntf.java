package br.com.test.githubapitest.apiservice;

import java.util.List;

import br.com.test.githubapitest.model.commits.CommitItem;
import br.com.test.githubapitest.model.repositories.RepoItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServiceIntf {

    @GET("users/{user}/repos") // ?page=1&per_page=10
    Call<List<RepoItem>> getRepos(@Path("user") String user, @Query("page") int page, @Query("per_page") int perPage);

    @GET("repos/{user}/{repoName}/commits")
    Call<List<CommitItem>> getCommits(@Path("user") String user, @Path("repoName") String repoName);
}
