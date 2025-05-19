package com.example.projectmanagerapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projectmanagerapp.model.Project;

import java.util.ArrayList;

public class ProjectDao {
    private DBHelper dbHelper;

    public ProjectDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long insert(Project project) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.ProjectEntry.COLUMN_NAME, project.getName());
        values.put(Contract.ProjectEntry.COLUMN_DESCRIPTION, project.getDescription());
        values.put(Contract.ProjectEntry.COLUMN_START_DATE, project.getStartDate());
        values.put(Contract.ProjectEntry.COLUMN_END_DATE, project.getEndDate());
        values.put(Contract.ProjectEntry.COLUMN_USER_ID, project.getUserId());

        return db.insert(Contract.ProjectEntry.TABLE_NAME, null, values);
    }

    public ArrayList<Project> getAllByUser(long userId) {
        ArrayList<Project> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                Contract.ProjectEntry.TABLE_NAME,
                null,
                Contract.ProjectEntry.COLUMN_USER_ID + "=?",
                new String[]{String.valueOf(userId)},
                null, null, null
        );

        while (cursor.moveToNext()) {
            Project p = new Project(
                    cursor.getLong(cursor.getColumnIndexOrThrow(Contract.ProjectEntry.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.ProjectEntry.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.ProjectEntry.COLUMN_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.ProjectEntry.COLUMN_START_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.ProjectEntry.COLUMN_END_DATE)),
                    userId
            );
            list.add(p);
        }

        cursor.close();
        return list;
    }

    public void delete(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Contract.ProjectEntry.TABLE_NAME, Contract.ProjectEntry.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int update(Project project) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.ProjectEntry.COLUMN_NAME, project.getName());
        values.put(Contract.ProjectEntry.COLUMN_DESCRIPTION, project.getDescription());
        values.put(Contract.ProjectEntry.COLUMN_START_DATE, project.getStartDate());
        values.put(Contract.ProjectEntry.COLUMN_END_DATE, project.getEndDate());
        return db.update(
                Contract.ProjectEntry.TABLE_NAME,
                values,
                Contract.ProjectEntry.COLUMN_ID + "=?",
                new String[]{String.valueOf(project.getId())}
        );
    }


    public int getProgress(long projectId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor total = db.rawQuery(
                "SELECT COUNT(*) FROM " + Contract.TaskEntry.TABLE_NAME +
                        " WHERE " + Contract.TaskEntry.COLUMN_PROJECT_ID + "=?",
                new String[]{String.valueOf(projectId)}
        );

        Cursor done = db.rawQuery(
                "SELECT COUNT(*) FROM " + Contract.TaskEntry.TABLE_NAME +
                        " WHERE " + Contract.TaskEntry.COLUMN_PROJECT_ID + "=? AND " +
                        Contract.TaskEntry.COLUMN_STATUS + "=?",
                new String[]{String.valueOf(projectId), "Realizado"}
        );

        total.moveToFirst();
        done.moveToFirst();
        int totalTasks = total.getInt(0);
        int completedTasks = done.getInt(0);

        total.close();
        done.close();

        return totalTasks == 0 ? 0 : (completedTasks * 100 / totalTasks);
    }
}
