package com.kiduyu.njugunaproject.agrifarm.UserFargments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kiduyu.njugunaproject.agrifarm.Adapter.chatAdapter;
import com.kiduyu.njugunaproject.agrifarm.HomeActivity;
import com.kiduyu.njugunaproject.agrifarm.Model.FriendlyMessage;
import com.kiduyu.njugunaproject.agrifarm.R;
import com.kiduyu.njugunaproject.agrifarm.Session.Prevalent;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayout noChatsFounds;
    private chatAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.home_fragment, container, false);
       recyclerView=layout.findViewById(R.id.recyclerview);
       noChatsFounds=layout.findViewById(R.id.no_chats);
       layout.findViewById(R.id.add_chat).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AlertDialog.Builder alert=new AlertDialog.Builder(requireContext());
               alert.setTitle("Choose who to chat with");

               alert.setIcon(null);
               String[]methode=new String[2];
               methode[1]="Other farmers";
               methode[0]="Consultant";

               alert.setSingleChoiceItems(methode, -1, (dialog, which) -> {
                   dialog.dismiss();
                  sendToCharts(which);

                   });
               alert.create();
               alert.show();
           }
       });

        HashMap<String,ArrayList<FriendlyMessage>>messages=new HashMap<>();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users/"+ Prevalent.currentOnlineUser.getUsername().trim()+"/chats").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    ArrayList<FriendlyMessage>chat=new ArrayList<>();
                    for (DataSnapshot snapshot1:dataSnapshot.getChildren())
                    {
                        FriendlyMessage message=snapshot1.getValue(FriendlyMessage.class);
                        chat.add(message);

                    }
                    messages.put(dataSnapshot.getKey(),chat);


                }
                if (messages.isEmpty())
                {
                    recyclerView.setVisibility(View.GONE);
                    noChatsFounds.setVisibility(View.VISIBLE);
                }
                else {
                    recyclerView.setVisibility(View.VISIBLE);
                    noChatsFounds.setVisibility(View.GONE);

                }
                adapter=new chatAdapter(messages,getContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return layout;
    }

    private void sendToCharts(int which) {
        Bundle bundleResult=new Bundle();

        bundleResult.putInt("bundleKey", which);

        getParentFragmentManager().setFragmentResult("type",bundleResult);
        Fragment fragment=new seach_user();
        callFragment(fragment);
        ((HomeActivity) requireActivity()).txtActiontitle.setText("Choose user");


    }
    public void callFragment(Fragment fragmentClass) {

        FragmentManager fragmentManager = ((HomeActivity) requireActivity()).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_frame, fragmentClass).addToBackStack("adds").commit();
        DrawerLayout drawer = (DrawerLayout) ((HomeActivity) requireActivity()).findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }
}
