package com.kiduyu.njugunaproject.agrifarm.UserFargments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.kiduyu.njugunaproject.agrifarm.HomeActivity;
import com.kiduyu.njugunaproject.agrifarm.R;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.home_fragment, container, false);

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


        return layout;
    }

    private void sendToCharts(int which) {
        Bundle bundleResult=new Bundle();

        bundleResult.putInt("bundleKey", which);

        getParentFragmentManager().setFragmentResult("type",bundleResult);
        Fragment fragment=new seach_user();
        callFragment(fragment);

    }
    public void callFragment(Fragment fragmentClass) {

        FragmentManager fragmentManager = ((HomeActivity) requireActivity()).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_frame, fragmentClass).addToBackStack("adds").commit();
        DrawerLayout drawer = (DrawerLayout) ((HomeActivity) requireActivity()).findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }
}
