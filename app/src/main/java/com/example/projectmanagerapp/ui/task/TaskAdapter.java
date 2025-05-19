package com.example.projectmanagerapp.ui.task;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectmanagerapp.R;
import com.example.projectmanagerapp.model.Task;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    public interface OnItemActionListener {
        void onDelete(Task task);
        void onClick(Task task);
    }

    private List<Task> tasks;
    private OnItemActionListener listener;

    public TaskAdapter(List<Task> tasks, OnItemActionListener listener) {
        this.tasks = tasks;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        Task t = tasks.get(pos);
        holder.tvTitle.setText(t.getTitle());
        holder.tvStatus.setText("Estado: " + t.getStatus());
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(t));
        holder.card.setOnClickListener(v -> listener.onClick(t));
    }

    @Override public int getItemCount() { return tasks.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView tvTitle, tvStatus;
        ImageButton btnDelete;
        ViewHolder(View v) {
            super(v);
            card = v.findViewById(R.id.cardTask);
            tvTitle = v.findViewById(R.id.tvTaskTitle);
            tvStatus = v.findViewById(R.id.tvTaskStatus);
            btnDelete = v.findViewById(R.id.btnDeleteTask);
        }
    }
}