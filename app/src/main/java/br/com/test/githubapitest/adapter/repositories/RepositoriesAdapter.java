package br.com.test.githubapitest.adapter.repositories;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import br.com.test.githubapitest.R;
import br.com.test.githubapitest.controller.CommitsActivity;
import br.com.test.githubapitest.model.repositories.RepoItem;

public class RepositoriesAdapter extends RecyclerView.Adapter<RepositoriesAdapter.ViewHolder> {

    private List<RepoItem> repoItems;
    private Context context;

    public RepositoriesAdapter(Context context, List<RepoItem> repoItems) {
        this.context = context;
        this.repoItems = repoItems;
    }

    @Override
    public RepositoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.repo_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RepositoriesAdapter.ViewHolder viewHolder, int position) {
        final RepoItem repoItem = repoItems.get(position);
        viewHolder.txtRepoName.setText(repoItem.getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CommitsActivity.class);
                intent.putExtra("repoName", repoItem.getName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return repoItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtRepoName;

        public ViewHolder(View view) {
            super(view);
            txtRepoName = view.findViewById(R.id.txtRepoName);
        }
    }
}
