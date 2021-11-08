package com.example.firebase.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.firebase.R;
import com.example.firebase.models.Chat;
import com.example.firebase.models.Message;
import com.example.firebase.models.User;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.Date;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {

   private User user;
   private User contact;

   private Chat chat;



   private EditText messagesET;
   private TextView messagesTV;
   private Button sendBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        user = (User) getIntent().getExtras().get("user");
        contact = (User) getIntent().getExtras().get("contact");

        messagesET = findViewById(R.id.messageET);
        messagesTV = findViewById(R.id.messagesTV);
        messagesTV.setMovementMethod(new ScrollingMovementMethod());
        sendBtn = findViewById(R.id.sendBtn);


        //Si no existe la sala de chat la creamos y si existe la descargamos

        FirebaseFirestore.getInstance().collection("Users").document(user.getId()).collection("chats")
                .whereEqualTo("contactID",contact.getId()).get().addOnCompleteListener(
                        task->{

                            if(task.getResult().size()==0){
                                createChat();
                            }else{
                                for(DocumentSnapshot doc :task.getResult()){
                                    chat =doc.toObject(Chat.class);
                                    break;
                                }
                            }
                            getMessages();
                            
                        }

        );


        sendBtn.setOnClickListener(
                (view)->{

                   Message message = new Message(UUID.randomUUID().toString(),messagesET.getText().toString(),user.getId(),new Date().getTime());
                    FirebaseFirestore.getInstance().collection("chats").document(chat.getId()).collection("messages").document(message.getId()).set(message);
                    messagesET.setText("");
                }
        );


    }

    private void getMessages() {

        FirebaseFirestore.getInstance().collection("chats").document(chat.getId()).collection("messages").orderBy("date").limitToLast(10).addSnapshotListener(
                //value= me llegan los mensajes, error= para identificar error en los mensajes
                (value,error)->{
                 for(DocumentChange doc :value.getDocumentChanges()){

                     switch(doc.getType()){
                         case ADDED:
                             Message message = doc.getDocument().toObject(Message.class);
                              messagesTV.append(message.getMessage()+"\n\n");
                             break;


                     }
                 }

                }
        );


    }

    public void createChat(){
        String id = UUID.randomUUID().toString();
        chat = new Chat(id,contact.getId());
        Chat foreingChat = new Chat(id,user.getId());
        FirebaseFirestore.getInstance().collection("Users").document(user.getId()).collection("chats").document(id).set(chat);
        FirebaseFirestore.getInstance().collection("Users").document(contact.getId()).collection("chats").document(id).set(foreingChat);

    }

}