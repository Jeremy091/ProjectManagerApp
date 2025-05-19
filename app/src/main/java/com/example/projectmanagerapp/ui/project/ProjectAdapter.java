package com.example.projectmanagerapp.ui.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectmanagerapp.R;
import com.example.projectmanagerapp.data.ProjectDao;
import com.example.projectmanagerapp.model.Project;
import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {
    public interface OnItemActionListener {
        void onClick(Project project);
        void onEdit(Project project);
        void onDelete(Project project);
    }

    private final List<Project> projects;
    private final OnItemActionListener listener;
    private final ProjectDao projectDao;

    public ProjectAdapter(Context ctx, List<Project> projects, OnItemActionListener listener) {
        this.projects = projects;
        this.listener = listener;
        this.projectDao = new ProjectDao(ctx);
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_project, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Project p = projects.get(position);
        holder.tvName.setText(p.getName());

        int prog = projectDao.getProgress(p.getId());
        holder.tvProgress.setText("Avance: " + prog + "%");
        holder.progressBar.setProgress(prog);

        holder.card.setOnClickListener(v -> listener.onClick(p));
        holder.btnEdit.setOnClickListener(v -> listener.onEdit(p));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(p));
    }

    @Override public int getItemCount() { return projects.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView tvName, tvProgress;
        ProgressBar progressBar;
        ImageButton btnEdit, btnDelete;

        ViewHolder(View v) {
            super(v);
            card = v.findViewById(R.id.cardProject);
            tvName = v.findViewById(R.id.tvProjectName);
            tvProgress = v.findViewById(R.id.tvProjectProgress);
            progressBar = v.findViewById(R.id.progressBar);
            btnEdit = v.findViewById(R.id.btnEditProject);
            btnDelete = v.findViewById(R.id.btnDeleteProject);
        }
    }
}
