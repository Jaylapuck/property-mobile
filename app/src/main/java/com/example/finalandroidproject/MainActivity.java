package com.example.finalandroidproject;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private Button btn_main_add;
    private MyListAdapter myListAdapter;
    private DBHandler dbHandler;
    private ListView listView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_property);
        btn_main_add = findViewById(R.id.btn_main_add);
        searchView = findViewById(R.id.src_property);

        dbHandler = new DBHandler(MainActivity.this);


        ShowPropertiesOnListView();

        btn_main_add.setOnClickListener(v -> {
            Intent  intent = new Intent(MainActivity.this, CreateActivity.class);
            startActivity(intent);
        });

        //Specific ID
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent  intent = new Intent(MainActivity.this, DisplayActivity.class);
            String propertyName = listView.getItemAtPosition(position).toString();
            Toast.makeText(MainActivity.this, propertyName, Toast.LENGTH_SHORT).show();
            intent.putExtra("name", propertyName );
            startActivity(intent);

        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myListAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void ShowPropertiesOnListView() {
        if (dbHandler.getName().length != 0){
            myListAdapter = new MyListAdapter(MainActivity.this, R.layout.sample, dbHandler.getName());
            listView.setAdapter(myListAdapter);
        }

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            //if user pressed "yes", then he is allowed to exit from application
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            //if user select "No", just cancel this dialog and continue with app
            dialog.cancel();
        });
        AlertDialog alert=builder.create();
        alert.show();
    }

    private  class MyListAdapter extends  ArrayAdapter<String>{

        private final int layout;
        public MyListAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
            super(context, resource, objects);
            layout = resource;

        }

        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder mainViewHolder;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) convertView.findViewById(R.id.text_sample_id);
                viewHolder.button = (Button) convertView.findViewById(R.id.button_sample_delete);
                convertView.setTag(viewHolder);
            }
            mainViewHolder = (ViewHolder) convertView.getTag();
            mainViewHolder.button.setOnClickListener(v -> new AlertDialog.Builder(getContext())
                    .setTitle("Property Delete")
                    .setMessage("Do you really want to delete this property?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                        String courseName = listView.getItemAtPosition(position).toString();
                        Toast.makeText(MainActivity.this, courseName, Toast.LENGTH_SHORT).show();
                        dbHandler.deleteOne(courseName);
                        Toast.makeText(MainActivity.this, "delete course: " + courseName, Toast.LENGTH_SHORT).show();
                        ShowPropertiesOnListView();
                    })
                    .setNegativeButton(android.R.string.no, null).show());
            mainViewHolder.textView.setText(getItem(position));

            return convertView;
        }

        @Override
        public Filter getFilter() {
            return super.getFilter();
        }

    }

    public class ViewHolder {
        TextView textView;
        Button button;
    }

}