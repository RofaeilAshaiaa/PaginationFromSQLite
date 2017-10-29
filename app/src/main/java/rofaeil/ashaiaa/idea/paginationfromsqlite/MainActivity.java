package rofaeil.ashaiaa.idea.paginationfromsqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import static rofaeil.ashaiaa.idea.paginationfromsqlite.Database.getDatabase;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    private AsyncAdapter adapter;
    private SQLiteItemSource itemSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        itemSource = new SQLiteItemSource(getDatabase(this, "database.sqlite"));
        adapter = new AsyncAdapter(itemSource, recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.onStart(recyclerView);
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.onStop(recyclerView);
    }
}
