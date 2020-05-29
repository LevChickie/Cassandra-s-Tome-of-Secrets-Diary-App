package com.example.exercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListEntriesActivity extends AppCompatActivity {

    static List<Entry> entries;
    RecyclerView listOfEntriesRecycle;
    RecyclerView.Adapter listOfEntriesAdapter;
    AppDataBase dataBase;
    RecyclerView.LayoutManager listOfEntriesLayoutManager;
    ListView listOfEntries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_entries);
        listOfEntriesRecycle=(RecyclerView)findViewById(R.id.listOfEntriesRecycle);
        dataBase = Room.databaseBuilder(getApplicationContext(),AppDataBase.class,"diaryDatabase").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        entries = dataBase.entryDao().getAllEntry();
        listOfEntriesLayoutManager=new LinearLayoutManager(this);
        listOfEntriesAdapter=new EntryAdapter(entries);
        listOfEntriesRecycle.setLayoutManager(listOfEntriesLayoutManager);
        listOfEntriesRecycle.setAdapter(listOfEntriesAdapter);
        ItemTouchHelper swipeToDelete = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder target, int direction) {
                int position = target.getAdapterPosition();
                Entry entry = entries.get(position);
                dataBase.entryDao().delete(entry);
                entries.remove(position);
                 listOfEntriesAdapter.notifyDataSetChanged();
            }
        });
        swipeToDelete.attachToRecyclerView(listOfEntriesRecycle) ;
        configureAddNewButton();
        configureDeleteAllButton();
        configureLogoutButton();
        configureChangePasswordButton();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Refresh();
    }
    private void configureChangePasswordButton(){
        Button changePasswordButton = (Button)findViewById(R.id.newPasswordRequest);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListEntriesActivity.this, ChangePassword.class));
            }
        });
    }
    private void configureLogoutButton(){
        Button logoutButton = (Button)findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void configureAddNewButton(){
        Button addNewEntryButton = (Button)findViewById(R.id.addNewEntryButton);
        addNewEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListEntriesActivity.this, AddNewEntryActivity.class));

            }
        });
    }
    private void configureDeleteAllButton(){
        Button deleteAllButton=(Button)findViewById(R.id.DeleteAllButton);
        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            entries.clear();
            dataBase.entryDao().deleteAll();
            listOfEntriesAdapter.notifyDataSetChanged() ;
            }
        });
    }
    public static void addToEntries(Entry newEntry){
        entries.add(newEntry);
    }
    private void Refresh(){
        listOfEntriesAdapter.notifyDataSetChanged();
    }
    public static void setPassword(String newPassword){
        MainActivity.editor.remove("password");
        MainActivity.editor.putString("password",newPassword);
        MainActivity.editor.commit();
    }
}
