package com.example.memoproject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MemoDao {

    @Query("SELECT * FROM MemoItem")
    LiveData<List<MemoItem>> getAll();

    @Insert
    void insert(MemoItem... items);

    @Delete
    void delete(MemoItem... items);

    @Query("DELETE FROM MemoItem")
    void deleteAll();

    @Update
    void update(MemoItem... items);
}
