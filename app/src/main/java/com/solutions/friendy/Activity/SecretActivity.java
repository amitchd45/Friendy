package com.solutions.friendy.Activity;

import android.Manifest;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.solutions.friendy.Adapters.ShowContactsAdapter;
import com.solutions.friendy.App;
import com.solutions.friendy.Models.Contact;
import com.solutions.friendy.Models.SendCrushPojo;
import com.solutions.friendy.R;
import com.solutions.friendy.ViewModel.VmUserSetting;

import java.util.ArrayList;
import java.util.List;

public class SecretActivity extends AppCompatActivity {

    private TextView tv_title;
    private SearchView et_search;
    private ImageView iv_back;
    private List<Contact> contactList = new ArrayList<>();
    private String userId, name, phoneNumber, contactImage, strName, strNumber;
    private ShowContactsAdapter showContactsAdapter;
    private RecyclerView rv_contactList;
    private Button btn_send;
    private VmUserSetting vmUserSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret);

        vmUserSetting = ViewModelProviders.of(this).get(VmUserSetting.class);

        userId = App.getAppPreference().GetString("id");

        init();

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_CONTACTS)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        if (permissionGrantedResponse.getPermissionName().equals(Manifest.permission.READ_CONTACTS)) {
                            getContact();
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        Toast.makeText(SecretActivity.this, "Permission should be granted!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    private void getContact() {
        Cursor phone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        while (phone.moveToNext()) {

            name = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contactImage = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

            Contact contact = new Contact(name, phoneNumber, contactImage);
            contactList.add(contact);


        }
        showContactsAdapter = new ShowContactsAdapter(SecretActivity.this, contactList, new ShowContactsAdapter.Select() {
            @Override
            public void onClickPhone(String pName, String pNumber) {
                strName = pName;
                strNumber = pNumber;
                btn_send.setVisibility(View.VISIBLE);
            }
        });
        rv_contactList.setAdapter(showContactsAdapter);
    }

    private void init() {
        et_search = findViewById(R.id.et_search);
        rv_contactList = findViewById(R.id.rv_contactList);
        btn_send = findViewById(R.id.btn_send);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("Secret Crush");
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(task -> {
            onBackPressed();
        });
        btn_send.setOnClickListener(task -> {
            sendCrush();
        });
    }

    private void sendCrush() {
        vmUserSetting.sendCrushFriend(SecretActivity.this, userId, strName, strNumber).observe(SecretActivity.this, new Observer<SendCrushPojo>() {
            @Override
            public void onChanged(SendCrushPojo sendCrushPojo) {

                if (sendCrushPojo.getSuccess().equalsIgnoreCase("1")) {
                    openDialog(sendCrushPojo.getDetails().getPoints());

                } else {
                    exprtDialod();
                }
            }
        });
    }

    private void exprtDialod() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.error_dialog, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();

        TextView tv_gotIt;
        tv_gotIt = dialogView.findViewById(R.id.tv_gotIt);

        DisplayMetrics metrics = new DisplayMetrics(); //get metrics of screen
        int height = (int) (metrics.heightPixels * 0.9); //set height to 90% of total
        int width = (int) (metrics.widthPixels * 0.9); //set width to 90% of total

        alertDialog.getWindow().setLayout(width, height); //set layout

        tv_gotIt.setOnClickListener(task -> {
            alertDialog.dismiss();
        });

        alertDialog.show();

    }

    private void openDialog(String points) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.info_dialog, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();

        TextView tv_gotIt, left;
        tv_gotIt = dialogView.findViewById(R.id.tv_gotIt);
        left = dialogView.findViewById(R.id.left);
        left.setText("You can come back anytime and send more crushes.You have " + points + " " + "left.");

        DisplayMetrics metrics = new DisplayMetrics(); //get metrics of screen
        int height = (int) (metrics.heightPixels * 0.9); //set height to 90% of total
        int width = (int) (metrics.widthPixels * 0.9); //set width to 90% of total

        alertDialog.getWindow().setLayout(width, height); //set layout

        tv_gotIt.setOnClickListener(task -> {
            alertDialog.dismiss();
        });

        alertDialog.show();
    }
}