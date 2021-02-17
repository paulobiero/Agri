package com.kiduyu.njugunaproject.agrifarm.UserFargments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kiduyu.njugunaproject.agrifarm.Adapter.SpecialistAdapter;
import com.kiduyu.njugunaproject.agrifarm.Adapter.chatWithAdapter;
import com.kiduyu.njugunaproject.agrifarm.Constants.Constants;
import com.kiduyu.njugunaproject.agrifarm.Model.Specialist;
import com.kiduyu.njugunaproject.agrifarm.Model.User;
import com.kiduyu.njugunaproject.agrifarm.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link seach_user#newInstance} factory method to
 * create an instance of this fragment.
 */
public class seach_user extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String TAG="SEARCH_USERS";
    public static boolean isRefreshed;
    private chatWithAdapter specialistAdapter;

    RequestQueue mRequestQueue;
    RecyclerView recycler;
    SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<User> specialistArrayList = new ArrayList<>();



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment seach_user.
     */
    // TODO: Rename and change types and number of parameters
    public static seach_user newInstance(String param1, String param2) {
        seach_user fragment = new seach_user();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seach_user, container, false);
    }
    private void fetchData() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Fetching Specialists");
        progressDialog.setMessage("Please wait......");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String urlForJsonObject = Constants.BASE_API + Constants.SPECIALIST_API;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                urlForJsonObject,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray("consultants");

                            if (isRefreshed) {
                                specialistArrayList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject consultant = jsonArray.getJSONObject(i);
                                    String Name = consultant.getString("name");
                                    String phone = consultant.getString("phone");
                                    String idnumber = consultant.getString("id_number");
                                    String location = consultant.getString("location");
                                    String image = consultant.getString("image");
                                    String date = consultant.getString("date");

                                    Log.d("TAG", "onResponse: " + Name + " " + phone + " " + idnumber + " " + location + " " + image + " " + date);
                                    //Loading.showProgressDialog(getActivity(),false);
                                    specialistArrayList.add(new User(Name,phone,idnumber,null,image));
                                    //tipArrayList.add(new Tip(title, description ,image));

                                }

                            } else {
                                specialistArrayList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject consultant = jsonArray.getJSONObject(i);
                                    String Name = consultant.getString("name");
                                    String phone = consultant.getString("phone");
                                    String idnumber = consultant.getString("id_number");
                                    String location = consultant.getString("location");
                                    String image = consultant.getString("image");
                                    String date = consultant.getString("date");

                                    Log.d("TAG", "onResponse: " + Name + " " + phone + " " + idnumber + " " + location + " " + image + " " + date);
                                    //Loading.showProgressDialog(getActivity(),false);

                                    //tipArrayList.add(new Tip(title, description ,image));
                                    specialistArrayList.add(new User(Name,phone,idnumber,null,image));

                                }
                            }


                            progressDialog.dismiss();
                            specialistAdapter = new chatWithAdapter(getActivity(), specialistArrayList);
                            recycler.setAdapter(specialistAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                progressDialog.dismiss();
            }
        });
        mRequestQueue.add(request);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("type", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                int result = bundle.getInt("bundleKey");
                // Do something with the result
                Log.i(TAG, "onFragmentResult: "+result);
                if (result==0)
                {
                    //fetch specialists
                }
                else {
                    //fetch users
                }
            }
        });

        mRequestQueue = Volley.newRequestQueue(getActivity());
        EditText editText = view.findViewById(R.id.search_editText_specialist);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        recycler = view.findViewById(R.id.recyclerview_consultant);

        swipeRefreshLayout = view.findViewById(R.id.consultant_refresh);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        recycler.setLayoutManager(layoutManager);
        recycler.setFocusable(false);

        fetchData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefreshed = true;
                fetchData();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), "Data refreshed", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void filter(String text) {
        ArrayList<User> filteredList = new ArrayList<>();
        for (User item : specialistArrayList) {
            if (item.getFullname().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        specialistAdapter = new chatWithAdapter(getActivity(), filteredList);
        recycler.setAdapter(specialistAdapter);
        specialistAdapter.notifyDataSetChanged();
    }
}