package com.example.projectmanagerapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projectmanagerapp.model.Task;

import java.util.ArrayList;

public class TaskDao {
    private DBHelper dbHelper;

    public TaskDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long insert(Task task) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.TaskEntry.COLUMN_TITLE, task.getTitle());
        values.put(Contract.TaskEntry.COLUMN_DESCRIPTION, task.getDescription());
        values.put(Contract.TaskEntry.COLUMN_START_DATE, task.getStartDate());
        values.put(Contract.TaskEntry.COLUMN_END_DATE, task.getEndDate());
        values.put(Contract.TaskEntry.COLUMN_STATUS, task.getStatus());
        values.put(Contract.TaskEntry.COLUMN_PROJECT_ID, task.getProjectId());

        return db.insert(Contract.TaskEntry.TABLE_NAME, null, values);
    }

    public ArrayList<Task> getAllByProject(long projectId) {
        ArrayList<Task> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                Contract.TaskEntry.TABLE_NAME,
                null,
                Contract.TaskEntry.COLUMN_PROJECT_ID + "=?",
                new String[]{String.valueOf(projectId)},
                null, null, null
        );

        while (cursor.moveToNext()) {
            Task t = new Task(
                    cursor.getLong(cursor.getColumnIndexOrThrow(Contract.TaskEntry.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.TaskEntry.COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.TaskEntry.COLUMN_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.TaskEntry.COLUMN_START_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.TaskEntry.COLUMN_END_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.TaskEntry.COLUMN_STATUS)),
                    projectId
            );
            list.add(t);
        }

        cursor.close();
        return list;
    }

    public int update(Task task) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.TaskEntry.COLUMN_TITLE, task.getTitle());
        values.put(Contract.TaskEntry.COLUMN_DESCRIPTION, task.getDescription());
        values.put(Contract.TaskEntry.COLUMN_START_DATE, task.getStartDate());
        values.put(Contract.TaskEntry.COLUMN_END_DATE, task.getEndDate());
        values.put(Contract.TaskEntry.COLUMN_STATUS, task.getStatus());
        return db.update(
                Contract.TaskEntry.TABLE_NAME,
                values,
                Contract.TaskEntry.COLUMN_ID + "=?",
                new String[]{String.valueOf(task.getId())}
        );
    }


    public void delete(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Contract.TaskEntry.TABLE_NAME, Contract.TaskEntry.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }
}
