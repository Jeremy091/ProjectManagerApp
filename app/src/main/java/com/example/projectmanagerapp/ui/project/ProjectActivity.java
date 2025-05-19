package com.example.projectmanagerapp.ui.project;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectmanagerapp.R;
import com.example.projectmanagerapp.data.ProjectDao;
import com.example.projectmanagerapp.model.Project;
import com.example.projectmanagerapp.ui.task.TaskActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class ProjectActivity extends AppCompatActivity
        implements ProjectAdapter.OnItemActionListener {

    private RecyclerView rvProjects;
    private ProjectAdapter adapter;
    private ProjectDao projectDao;
    private long userId;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        userId = getIntent().getLongExtra("user_id", -1);
        projectDao = new ProjectDao(this);

        rvProjects = findViewById(R.id.rvProjects);
        rvProjects.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fabAdd = findViewById(R.id.fabAddProject);
        fabAdd.setOnClickListener(v -> {
            Intent i = new Intent(this, ProjectFormActivity.class);
            i.putExtra("user_id", userId);
            startActivity(i);
        });
    }

    @Override protected void onResume() {
        super.onResume();
        loadProjects();
    }

    private void loadProjects() {
        List<Project> list = projectDao.getAllByUser(userId);
        adapter = new ProjectAdapter(this, list, this);
        rvProjects.setAdapter(adapter);
    }

    @Override
    public void onClick(Project project) {
        Intent i = new Intent(this, TaskActivity.class);
        i.putExtra("project_id", project.getId());
        startActivity(i);
    }

    @Override
    public void onEdit(Project project) {
        Intent i = new Intent(this, ProjectFormActivity.class);
        i.putExtra("user_id", userId);
        i.putExtra("project_id", project.getId());
        startActivity(i);
    }

    @Override
    public void onDelete(Project project) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar proyecto")
                .setMessage("¿Seguro que deseas eliminar este proyecto?")
                .setPositiveButton("Sí", (d, w) -> {
                    projectDao.delete(project.getId());
                    loadProjects();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
