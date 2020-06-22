package com.solutions.friendy.Fragments;



import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;


import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.solutions.friendy.Adapters.ContactAdapter;
import com.solutions.friendy.R;
import com.solutions.friendy.Utills.FastScrollRecyclerViewItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class SecretCrushFragment extends Fragment implements View.OnClickListener {

    private View view;
    private TextView tv_title;
    private ImageView iv_back,ic_search;

    private RecyclerView recyclerView;
    private SearchManager searchManager;
    private SearchView searchView;
    private Button contact_list_btn;
    private HashMap<String, Integer> mapIndex;
    private ContactAdapter contactAdapter;
//    private ArrayList<String> StoreContacts;
    private ArrayList<String> nameContact;
    private ArrayList<String> phoneNumber1;
    private Cursor cursor;
    private String name, phonenumber;




    public SecretCrushFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_secret_crush, container, false);

        findIds(view);
        return view;
    }

    private void findIds(View view) {

        ic_search =view.findViewById(R.id.ic_search);
        ic_search.setVisibility(View.GONE);

        iv_back =view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);


        tv_title =view.findViewById(R.id.tv_title);
        tv_title.setText("Secret Crush");

        recyclerView = view.findViewById(R.id.recycler_contact);
        searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        contact_list_btn = view.findViewById(R.id.show_data);

//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getContactsIntoArrayList();
        calculateIndexesForName(nameContact,phoneNumber1);
        contactAdapter = new ContactAdapter(nameContact,phoneNumber1, mapIndex, getActivity());
        recyclerView.setAdapter(contactAdapter);

        FastScrollRecyclerViewItemDecoration decoration = new FastScrollRecyclerViewItemDecoration(getActivity());
        recyclerView.addItemDecoration(decoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        searchView = view.findViewById(R.id.action_search);
        searchView.setVisibility(View.VISIBLE);
        SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.WHITE);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Search contact");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                contactAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contactAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void getContactsIntoArrayList() {

        cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

//            StoreContacts.add(name + " " + ":" + " " + phonenumber);
            nameContact.add(name);
            phoneNumber1.add(phonenumber);
        }

        cursor.close();

    }
    private void calculateIndexesForName(ArrayList<String> nameContact, ArrayList<String> phoneNumber1) {
        mapIndex = new LinkedHashMap<>();

        for (int i = 0; i < nameContact.size(); i++) {
            String name = nameContact.get(i);
            String index = name.substring(0, 1);
            index = index.toUpperCase();

            if (!mapIndex.containsKey(index)) {
                mapIndex.put(index, i);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;
        }
    }
}
