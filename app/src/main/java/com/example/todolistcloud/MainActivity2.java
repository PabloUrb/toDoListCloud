package com.example.todolistcloud;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.todolistcloud.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import Objects.Tarea;

public class MainActivity2 extends AppCompatActivity {
    static ArrayList<Tarea> tareas = new ArrayList<Tarea>();
    private Button switchToSecondActivity;
    static SharedPreferences sharedPref;
    public static JSONObject jsonTarea = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.tareas);
        CustomAdapter adapter = new CustomAdapter(tareas);
        rvContacts.setAdapter(adapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));


        sharedPref = MainActivity2.this.getPreferences(Context.MODE_PRIVATE);
        String aux = sharedPref.getString("1", "");
        if(!aux.isEmpty()){
            try {
                tareas.clear();
                JSONObject obj = new JSONObject(aux);
                Iterator<String> temp = obj.keys();
                while (temp.hasNext()) {
                    String key = temp.next();
                    Object value = obj.get(key);
                    Tarea t = new Tarea();
                    t.setDate(Date.valueOf((String) value));
                    t.setName(key);
                    tareas.add(t);
                    jsonTarea.put(key, value);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        switchToSecondActivity = (Button) findViewById(R.id.button);
        switchToSecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switchActivities();
            }
        });
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private ArrayList<Tarea> localDataSet;

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;
            private final TextView textView2;
            public Button imgViewRemoveIcon;

            public void removeSharedPreferences(String name){

                JSONObject j = new JSONObject();
                for(Tarea t : tareas){
                    try {
                        j.put(t.getName(),t.getDate());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                SharedPreferences.Editor editor = MainActivity2.sharedPref.edit();
                editor.putString(String.valueOf(1), String.valueOf(j));
                editor.apply();
            }

            public ViewHolder(View view) {
                super(view);

                textView = (TextView) view.findViewById(R.id.textView);
                textView2 = (TextView) view.findViewById(R.id.textView2);
                imgViewRemoveIcon = (Button) view.findViewById(R.id.btnAddExpense);
                imgViewRemoveIcon.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        removeAt(getPosition());
                        removeSharedPreferences(textView.getText().toString());
                    }
                });
            }
            public void removeAt(int position) {
                localDataSet.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, localDataSet.size());
            }

            public TextView getTextView() {
                return textView;
            }
        }

        public CustomAdapter(ArrayList<Tarea> dataSet) {
            localDataSet = dataSet;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recycler, viewGroup, false);

            return new ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            Tarea tarea = tareas.get(position);
            TextView textView = viewHolder.textView;
            TextView textView2 = viewHolder.textView2;
            textView.setText(tarea.getName());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            textView2.setText(formatter.format(tarea.getDate()));


        }



        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }

    }
    private class CustomHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public CustomHolder(@NonNull View itemView) {
            super(itemView);
        }


        @Override
        public void onClick(View view) {
            switchActivities();
        }
    }
    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, com.example.todolistcloud.MainActivity3.class);
        startActivity(switchActivityIntent);
    }

}

