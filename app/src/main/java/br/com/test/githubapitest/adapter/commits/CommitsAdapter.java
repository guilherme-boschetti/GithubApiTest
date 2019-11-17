package br.com.test.githubapitest.adapter.commits;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.test.githubapitest.R;
import br.com.test.githubapitest.model.commits.CommitItem;

public class CommitsAdapter extends RecyclerView.Adapter<CommitsAdapter.ViewHolder> {

    private List<CommitItem> commitItems;

    public CommitsAdapter(List<CommitItem> commitItems) {
        this.commitItems = commitItems;
    }

    @Override
    @NonNull
    public CommitsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.commit_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommitsAdapter.ViewHolder viewHolder, int position) {
        final CommitItem commitItem = commitItems.get(position);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String commitDate = "";
        try {
            commitDate = sdf.format(commitItem.getCommit().getAuthor().getDate());
        } catch (Exception e) {
            //do nothing
        }
        try {
            viewHolder.txtCommitAuthor.setText(commitItem.getCommit().getAuthor().getName());
            viewHolder.txtCommitDate.setText(commitDate);
            viewHolder.txtCommitMessage.setText(commitItem.getCommit().getMessage());
        } catch (Exception e) {
            //do nothing
        }
    }

    @Override
    public int getItemCount() {
        return commitItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtCommitAuthor;
        private TextView txtCommitDate;
        private TextView txtCommitMessage;

        public ViewHolder(View view) {
            super(view);
            txtCommitAuthor = view.findViewById(R.id.txtCommitAuthor);
            txtCommitDate = view.findViewById(R.id.txtCommitDate);
            txtCommitMessage = view.findViewById(R.id.txtCommitMessage);
        }
    }
}
