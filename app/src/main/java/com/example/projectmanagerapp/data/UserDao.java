package com.example.projectmanagerapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.projectmanagerapp.model.User;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserDao {
    private DBHelper dbHelper;

    public UserDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public long insert(User user) {
        if (getByUsername(user.getUsername()) != null) return -1;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.UserEntry.COLUMN_USERNAME, user.getUsername());
        values.put(Contract.UserEntry.COLUMN_PASSWORD, hashPassword(user.getPassword()));
        return db.insert(Contract.UserEntry.TABLE_NAME, null, values);
    }

    public User getUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String hashed = hashPassword(password);
        Cursor cursor = db.query(
                Contract.UserEntry.TABLE_NAME, null,
                Contract.UserEntry.COLUMN_USERNAME + "=? AND " + Contract.UserEntry.COLUMN_PASSWORD + "=?",
                new String[]{username, hashed}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            User u = new User(
                    cursor.getLong(cursor.getColumnIndexOrThrow(Contract.UserEntry.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.UserEntry.COLUMN_USERNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.UserEntry.COLUMN_PASSWORD))
            );
            cursor.close();
            return u;
        }
        return null;
    }

    public User getByUsername(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                Contract.UserEntry.TABLE_NAME, null,
                Contract.UserEntry.COLUMN_USERNAME + "=?",
                new String[]{username}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            User u = new User(
                    cursor.getLong(cursor.getColumnIndexOrThrow(Contract.UserEntry.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.UserEntry.COLUMN_USERNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.UserEntry.COLUMN_PASSWORD))
            );
            cursor.close();
            return u;
        }
        return null;
    }
}