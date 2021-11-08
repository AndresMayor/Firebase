package com.example.firebase.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.firebase.R;
import com.example.firebase.activities.HomeActivity;
import com.example.firebase.models.User;
import com.example.firebase.util.NotificationUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {



    private TextInputEditText usernameET;
    private TextInputEditText passET;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameET = findViewById(R.id.usernameET);
        passET = findViewById(R.id.passET);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(this::login);


        NotificationUtil.showNotification(this,"AppMoviles","Hola Mundo de las notificaciones");

        FirebaseMessaging.getInstance().subscribeToTopic("promo");

    }

    private void login(View view) {
        String username= usernameET.getText().toString();
        String pass  = passET.getText().toString();
        User user = new User(UUID.randomUUID().toString(),username,pass);

        Query query = FirebaseFirestore.getInstance().collection("Users").whereEqualTo("username",username);
        query.get().addOnCompleteListener(

                task ->{
                    //Si el usuario no existe lo creamos e inicamos sesion con el
                    if (task.getResult().size()==0){
                        FirebaseFirestore.getInstance().collection("Users").document(user.getId()).set(user);
                        Intent intent = new Intent(this, HomeActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                    }else{
                        User userEmpy = null;
                        for(DocumentSnapshot doc : task.getResult()){
                            userEmpy = doc.toObject(User.class);

                            break;
                        }
                        if(userEmpy.getPassword().equals(pass)){
                            Intent intent= new Intent(this,HomeActivity.class);
                            intent.putExtra("user",userEmpy);
                            startActivity(intent) ;
                        }else{

                            Toast.makeText(this,"Contraseña incorrecta",Toast.LENGTH_LONG).show();
                        }
                    }

                    //Si el usuario existe, decargamos el usUARIO E INICIAMOS SESION CON EL

                }
        );

        //FirebaseFirestore.getInstance().collection("Users").document(user.getId()).set(user);

    }


}