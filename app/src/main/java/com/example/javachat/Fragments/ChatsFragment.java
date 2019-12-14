package com.example.javachat.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.javachat.Adapter.UserAdapter;
import com.example.javachat.MessageActivity;
import com.example.javachat.Model.Chat;
import com.example.javachat.Model.User;
import com.example.javachat.Notifications.Token;
import com.example.javachat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ChatsFragment extends Fragment {
    private RecyclerView recyclerView;


    private UserAdapter userAdapter;
    private List<User> mUsers;
    private List<User> mAllUsers;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    private List<String> usersList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        usersList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);

                    if (chat.getSender().equals(firebaseUser.getUid())){
                        usersList.add(chat.getReceiver());
                    }

                    if(chat.getReceiver().equals(firebaseUser.getUid())){
                        usersList.add(chat.getSender());
                    }

                }

                readChats();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        UpdateToken(FirebaseInstanceId.getInstance().getToken());



        return view;
    }

    private void UpdateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(firebaseUser.getUid()).setValue(token1);
    }

    private void readChats() {
        mAllUsers = new ArrayList<>();
        mUsers = new ArrayList<>();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                // Burda saçma bi olay var direkt Userların listesini alamıyorum
                // O yüzden bütün userları bir listeye aktarıp
                // senin algoritmayı kullanıyorum
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                    User user = snapshot.getValue(User.class);
                    Collections.sort(usersList);

                    // Tüm mesajların userlarını çekiyor
                    for (String id: usersList){
                        if(user.getId().equals(id)){
                            mAllUsers.add(user);
                        }
                    }

                    // Senin kodun implementasyonu
                    int x=0;
                    mUsers.clear();
                    for(User user1: mAllUsers){
                        if(x==0)
                            mUsers.add(mAllUsers.get(x));
                        else{
                            if(mAllUsers.get(x - 1) != mAllUsers.get(x))
                               mUsers.add(mAllUsers.get(x));
                        }
                        x++;
                    }
                }
                //mUsers adapter yoluyla arayüze göneriliyor
                userAdapter = new UserAdapter(getContext(), mUsers);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
