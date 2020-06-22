package com.solutions.friendy.Fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.User;
import com.solutions.friendy.App;
import com.solutions.friendy.Models.UpdateDobModel;
import com.solutions.friendy.Models.UpdateNameModel;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;
import com.solutions.friendy.Retrofit.StringContract;
import com.solutions.friendy.ViewModel.VmReceiveData;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalInformationFragment extends Fragment implements View.OnClickListener {
    private View view;
    private TextView tv_title, tv_show_name, tv_show_dob,tv_gender;
    private ImageView iv_back;
    private RelativeLayout rl_update_name, rl_update_dob;
    private String name, dob, user_id;
    private VmReceiveData vmReceiveData;
    private int y,m,d;
    private DatePickerDialog picker;


    public PersonalInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_personal_information, container, false);

        user_id = App.getAppPreference().GetString("id");
        vmReceiveData = ViewModelProviders.of(getActivity()).get(VmReceiveData.class);

        tv_gender = view.findViewById(R.id.tv_gender);
        if (App.getAppPreference().GetString(AppConstants.USER_GENDER).equalsIgnoreCase("0")){
            tv_gender.setText("Male");
        }
        else {
            tv_gender.setText("Female");
        }


        tv_show_name = view.findViewById(R.id.tv_show_name);
        tv_show_dob = view.findViewById(R.id.tv_show_dob);


        rl_update_name = view.findViewById(R.id.rl_update_name);
        rl_update_name.setOnClickListener(this);

        rl_update_dob = view.findViewById(R.id.rl_update_dob);
        rl_update_dob.setOnClickListener(this);

        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("Personal Information");

        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                App.getAppPreference().SaveString(AppConstants.CHECK_IMAGE,"5");
                getActivity().onBackPressed();
                break;

            case R.id.rl_update_name:
                NameDialog();
                break;

            case R.id.rl_update_dob:
//                DobDialog();
                spinnerDate();
                break;
        }
    }

    private void spinnerDate() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.update_date_picker_spiner);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        TextView tv_date_of_birth=dialog.findViewById(R.id.tv_date_of_birth);
        tv_date_of_birth.setText(App.getAppPreference().GetString(AppConstants.USER_DOB));
        Button btn_setDate=dialog.findViewById(R.id.btn_setDate);
        DatePicker datePicker=dialog.findViewById(R.id.picker);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        y=calendar.get(Calendar.YEAR);
        m=calendar.get(Calendar.MONTH);
        d=calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(y, m, d, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {

                tv_date_of_birth.setText(year+"-"+(month + 1) + "-"+dayOfMonth);
                y=year;
                m=month;
                d=dayOfMonth;

            }
        });

        btn_setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dobEt.setText(y+"-"+(m + 1) + "-"+d);
                dob=y+"-"+(m + 1) + "-"+d;
                updateDob(dob);
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    private void DobDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dob_dialog);

        EditText strDob = dialog.findViewById(R.id.et_about);
        TextView ok = dialog.findViewById(R.id.btn_ok);
        TextView cancel = dialog.findViewById(R.id.btn_cancel);

        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dob = strDob.getText().toString().trim();
//                tv_show_dob.setText(dob);
//                updateDob();
//                    if (!about.isEmpty()) {
//                        iv_add_about.setVisibility(View.GONE);
//                        tv_about.setText(about);
//                    } else {
//                        iv_add_about.setVisibility(View.GONE);
//                    }

                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void NameDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.name_dialog);

        EditText strName = dialog.findViewById(R.id.et_about);
        strName.setText(App.getAppPreference().GetString(AppConstants.USER_NAME));
        TextView ok = dialog.findViewById(R.id.btn_ok);
        TextView cancel = dialog.findViewById(R.id.btn_cancel);

        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = strName.getText().toString().trim();
                App.getAppPreference().SaveString(AppConstants.USER_NAME,name);

                updateName();
                tv_show_name.setText(App.getAppPreference().GetString(AppConstants.USER_NAME));

                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void updateName() {
        vmReceiveData.get_Update_name(getActivity(), name, user_id).observe(PersonalInformationFragment.this, new Observer<UpdateNameModel>() {
            @Override
            public void onChanged(UpdateNameModel updateNameModel) {
                if (updateNameModel.getSuccess().equalsIgnoreCase("1")) {
                    Toast.makeText(getActivity(), updateNameModel.getMessage(), Toast.LENGTH_SHORT).show();
                    User user = new User();
                    user.setUid(user_id); // Replace with your uid for the user to be updated.
                    user.setName(name); // Replace with the name of the user

                    CometChat.updateUser(user, StringContract.AppDetails.API_KEY, new CometChat.CallbackListener<User>() {
                        @Override
                        public void onSuccess(User user) {
                            Log.d("updateUser", user.toString());
                        }

                        @Override
                        public void onError(CometChatException e) {
                            Log.e("updateUser", e.getMessage());
                        }
                    });
                }
            }
        });
    }

    private void updateDob(String dob) {
        vmReceiveData.get_Update_dob(getActivity(), dob, user_id).observe(PersonalInformationFragment.this, new Observer<UpdateDobModel>() {
            @Override
            public void onChanged(UpdateDobModel updateDobModel) {
                if (updateDobModel.getSuccess().equalsIgnoreCase("1")) {
                    App.getAppPreference().SaveString(AppConstants.USER_DOB,dob);
                    tv_show_dob.setText(App.getAppPreference().GetString(AppConstants.USER_DOB));
                    Toast.makeText(getActivity(), updateDobModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        tv_show_name.setText(App.getAppPreference().GetString(AppConstants.USER_NAME));
        tv_show_dob.setText(App.getAppPreference().GetString(AppConstants.USER_DOB));
//        tv_gender.setText( App.getAppPreference().GetString(AppConstants.USER_GENDER));
    }
}
