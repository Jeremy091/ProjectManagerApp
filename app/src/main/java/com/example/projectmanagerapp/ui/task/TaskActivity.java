package com.example.projectmanagerapp.ui.task;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectmanagerapp.R;
import com.example.projectmanagerapp.data.TaskDao;
import com.example.projectmanagerapp.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class TaskActivity extends AppCompatActivity implements TaskAdapter.OnItemActionListener {
    private RecyclerView rvTasks;
    private TaskAdapter adapter;
    private TaskDao taskDao;
    private long projectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        projectId = getIntent().getLongExtra("project_id", -1);
        taskDao = new TaskDao(this);

        rvTasks = findViewById(R.id.rvTasks);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fabAdd = findViewById(R.id.fabAddTask);
        fabAdd.setOnClickListener(v -> {
            Intent i = new Intent(this, TaskFormActivity.class);
            i.putExtra("project_id", projectId);
            startActivity(i);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTasks();
    }

    private void loadTasks() {
        List<Task> list = taskDao.getAllByProject(projectId);
        adapter = new TaskAdapter(list, this);
        rvTasks.setAdapter(adapter);
    }

    @Override
    public void onDelete(Task task) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar actividad")
                .setMessage("¿Seguro que deseas eliminar esta actividad?")
                .setPositiveButton("Sí", (d, w) -> {
                    taskDao.delete(task.getId());
                    loadTasks();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public void onClick(Task task) {
        Intent intent = new Intent(this, TaskFormActivity.class);
        intent.putExtra("project_id", projectId);
        intent.putExtra("task_id", task.getId()); // ID de la tarea que se va a editar
        startActivity(intent);
    }
}