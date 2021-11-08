package com.example.firebase.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.firebase.R;
import com.example.firebase.activities.ChatActivity;
import com.example.firebase.models.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {


    private User user;
    private ListView userListView;
    private ArrayList<User> users;
    private ArrayAdapter<User>  adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user = (User) getIntent().getExtras().get("user");
        userListView =findViewById(R.id.userListView);
        users = new ArrayList<>();
        adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,users);
        userListView.setAdapter(adapter);

        FirebaseFirestore.getInstance().collection("Users").get().addOnCompleteListener(
                task -> {
                    for(DocumentSnapshot doc : task.getResult()){
                       User user =  doc.toObject(User.class);
                       users.add(user);
                       adapter.notifyDataSetChanged();

                    }
                }
        );
        userListView.setOnItemClickListener((adapterView, view, i, l) -> {

            User contact = users.get(i);
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra("user",user);
            intent.putExtra("contact",contact);
            startActivity(intent);


        });



    }
}