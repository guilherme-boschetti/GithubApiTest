package br.com.test.githubapitest.model.repositories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RepositoryItem {

    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }
}
