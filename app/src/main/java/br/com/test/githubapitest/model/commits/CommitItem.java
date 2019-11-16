package br.com.test.githubapitest.model.commits;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CommitItem {

    @SerializedName("sha")
    @Expose
    private String sha;

    @SerializedName("commit")
    @Expose
    private Commit commit;

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    // == internal class ==

    public class Commit {

        @SerializedName("message")
        @Expose
        private String message;

        @SerializedName("author")
        @Expose
        private Author author;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Author getAuthor() {
            return author;
        }

        public void setAuthor(Author author) {
            this.author = author;
        }

        // == internal class ==

        public class Author {

            @SerializedName("name")
            @Expose
            private String name;

            @SerializedName("email")
            @Expose
            private String email;

            @SerializedName("date")
            @Expose
            private Date date;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public Date getDate() {
                return date;
            }

            public void setDate(Date date) {
                this.date = date;
            }
        }
    }
}
