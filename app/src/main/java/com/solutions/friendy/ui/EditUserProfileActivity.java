package com.solutions.friendy.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.User;
import com.solutions.friendy.Adapters.AdapterAddImage;
import com.solutions.friendy.Adapters.BooksListAdapter;
import com.solutions.friendy.Adapters.FoodListAdapter;
import com.solutions.friendy.Adapters.IndustryListAdapter;
import com.solutions.friendy.Adapters.MoviesListAdapter;
import com.solutions.friendy.Adapters.MusicListAdapter;
import com.solutions.friendy.Adapters.PersonalityListAdapter;
import com.solutions.friendy.Adapters.ShowBooksListAdapter;
import com.solutions.friendy.Adapters.ShowMovieListAdapter;
import com.solutions.friendy.Adapters.ShowMusicListAdapter;
import com.solutions.friendy.Adapters.ShowPersonalityAdapter;
import com.solutions.friendy.Adapters.ShowSportListAdapter;
import com.solutions.friendy.Adapters.ShowfoodListAdapter;
import com.solutions.friendy.Adapters.SportsListAdapter;
import com.solutions.friendy.App;
import com.solutions.friendy.Fragments.EditProfileFragment;
import com.solutions.friendy.Fragments.EditProfileFragmentDirections;
import com.solutions.friendy.Models.GetIndustryListModel;
import com.solutions.friendy.Models.GetInterestListModel;
import com.solutions.friendy.Models.GetPersonalityListModel;
import com.solutions.friendy.Models.GetProfileDataModel;
import com.solutions.friendy.Models.GetUpdateProfileModel;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;
import com.solutions.friendy.Retrofit.StringContract;
import com.solutions.friendy.ViewModel.VmReceiveData;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class EditUserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout ll_layout;
    private EditText et_address;
    private TextView tv_title, tv_cancel, tv_save, tv_about, tv_industry, tv_company, tv_hometown, tv_user_name, tv_age;
    private ImageView iv_back, iv_add_about, iv_add_company, iv_add_industry, iv_add_hometown;
    private RecyclerView rv_add_image;
    private AdapterAddImage adapterAddImage;
    private Uri resultUri;
    private RelativeLayout rl_about_me, rl_industry, rl_company, rl_hometown, rl_edit_personal_info,
            rl_personality, rl_sport, rl_music, rl_food, rl_movie_and_tv, rl_book;
    private String about = "", company = "", industryName = "", hometown = "", industry_id = "";
    private List<GetIndustryListModel.Detail> industryList = new ArrayList<>();
    private List<GetPersonalityListModel.Detail> personalityList = new ArrayList<>();

    private IndustryListAdapter industryListAdapter;
    private PersonalityListAdapter personalityListAdapter;

    private List<String> datalist = new ArrayList<>();
    private List<GetInterestListModel.Detail> sport_datalist = new ArrayList<>();
    private List<String> sport = new ArrayList<>();
    private List<String> music_datalist = new ArrayList<>();
    private List<String> food_datalist = new ArrayList<>();
    private List<String> movie_datalist = new ArrayList<>();
    private List<String> books_datalist = new ArrayList<>();

    private RecyclerView rv_personality_show, rv_sportList_show, rv_music_show, rv_food_show, rv_movie_show, rv_books_show;
    private ShowPersonalityAdapter showPersonalityAdapter;

    private SportsListAdapter sportsListAdapter;
    private ShowSportListAdapter showSportListAdapter;
    private ShowMusicListAdapter showMusicListAdapter;
    private ShowfoodListAdapter showfoodListAdapter;
    private ShowMovieListAdapter showMovieListAdapter;
    private ShowBooksListAdapter showBooksListAdapter;

    private MusicListAdapter musicListAdapter;
    private String[] music_list = {"Alternative", "Alternative Rock", "Indie Rock", "Punk", "Blues", "Classical", "Opera"};

    private FoodListAdapter foodListAdapter;
    private String[] food_list = {"Pizza", "Fried rice", "Chili crab"};

    private MoviesListAdapter moviesListAdapter;
    private String[] movie_list = {"War", "Kabir Singh", "Bharat", "Good News"};

    private BooksListAdapter booksListAdapter;
    private String[] books_list = {"Anna Karenina", "To Kill a Mockingbird", "The Great Gatsby", "Invisible Man", "Beloved"};

    private VmReceiveData vmReceiveData;
    private Dialog dialog;
    private EditText et_note;

    private Map<String, String> map = new HashMap<>();

    private String position = "", strList = "", strList_personality = "", user_id;
    private List<String> ids = new ArrayList<>();
    private List<String> ids_personality = new ArrayList<>();

    private ImageView iv_take_image, iv_take_image1, iv_take_image2, iv_take_image3, iv_take_image4, iv_take_image5, iv_plus_icon;
    private Boolean imageCheck = false, image1Check = false, image2Check = false, image3Check = false, image4Check = false, image5Check = false;
    private File image_file, image1_file, image2_file, image3_file, image4_file, image5_file;
    private MultipartBody.Part body_image, body_image1, body_image2, body_image3, body_image4, body_image5;

    private String imagePath = "";
    private Activity activity=EditUserProfileActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        vmReceiveData = ViewModelProviders.of(this).get(VmReceiveData.class);

        findIds();

        user_id = App.getAppPreference().GetString("id");
    }

    private void findIds() {
        ll_layout = findViewById(R.id.ll_layout);

        et_address = findViewById(R.id.et_address);
        et_address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    Toast.makeText(activity, "hide KeyPad", Toast.LENGTH_SHORT).show();
                }
            }
        });

        iv_take_image = findViewById(R.id.iv_take_image);
        Glide.with(activity).load(AppConstants.USERIMAGE + App.getSinlton().getUser_image()).placeholder(R.drawable.add).into(iv_take_image);
        iv_take_image.setOnClickListener(this);

        iv_take_image1 = findViewById(R.id.iv_take_image1);
        Glide.with(activity).load(AppConstants.USERIMAGE + App.getSinlton().getUser_image1()).placeholder(R.drawable.add).into(iv_take_image1);
        iv_take_image1.setOnClickListener(this);

        iv_take_image2 = findViewById(R.id.iv_take_image2);
        Glide.with(activity).load(AppConstants.USERIMAGE + App.getSinlton().getUser_image2()).placeholder(R.drawable.add).into(iv_take_image2);
        iv_take_image2.setOnClickListener(this);

        iv_take_image3 = findViewById(R.id.iv_take_image3);
        Glide.with(activity).load(AppConstants.USERIMAGE + App.getSinlton().getUser_image3()).placeholder(R.drawable.add).into(iv_take_image3);
        iv_take_image3.setOnClickListener(this);

        iv_take_image4 = findViewById(R.id.iv_take_image4);
        Glide.with(activity).load(AppConstants.USERIMAGE + App.getSinlton().getUser_image4()).placeholder(R.drawable.add).into(iv_take_image4);
        iv_take_image4.setOnClickListener(this);

        iv_take_image5 = findViewById(R.id.iv_take_image5);
        Glide.with(activity).load(AppConstants.USERIMAGE + App.getSinlton().getUser_image5()).placeholder(R.drawable.add).into(iv_take_image5);
        iv_take_image5.setOnClickListener(this);


        tv_age = findViewById(R.id.tv_age);
//        tv_age.setText(App.getSinlton().getAge() + " years");

        et_note = findViewById(R.id.edit_query);
        et_note.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    Toast.makeText(activity, "hide KeyPad", Toast.LENGTH_SHORT).show();
                }
            }
        });
        et_note.setText(App.getSinlton().getNote());

        tv_user_name = findViewById(R.id.tv_user_name);
        tv_user_name.setText(App.getSinlton().getName());

//        iv_plus_icon = findViewById(R.id.iv_plus_icon);
        rv_personality_show = findViewById(R.id.rv_personality_show);
        rv_sportList_show = findViewById(R.id.rv_sportList_show);
        rv_music_show = findViewById(R.id.rv_music_show);
        rv_food_show = findViewById(R.id.rv_food_show);
        rv_movie_show = findViewById(R.id.rv_movie_show);
        rv_books_show = findViewById(R.id.rv_books_show);

        rl_edit_personal_info = findViewById(R.id.rl_edit_personal_info);
        rl_edit_personal_info.setOnClickListener(this);

        rl_about_me = findViewById(R.id.rl_about_me);
        rl_about_me.setOnClickListener(this);

        rl_industry = findViewById(R.id.rl_industry);
        rl_industry.setOnClickListener(this);

        rl_company = findViewById(R.id.rl_company);
        rl_company.setOnClickListener(this);

        rl_hometown = findViewById(R.id.rl_hometown);
        rl_hometown.setOnClickListener(this);

        rl_personality = findViewById(R.id.rl_personality);
        rl_personality.setOnClickListener(this);

        rl_sport = findViewById(R.id.rl_sport);
        rl_sport.setOnClickListener(this);

        rl_music = findViewById(R.id.rl_music);
        rl_music.setOnClickListener(this);

        rl_food = findViewById(R.id.rl_food);
        rl_food.setOnClickListener(this);

        rl_movie_and_tv = findViewById(R.id.rl_movie_and_tv);
        rl_movie_and_tv.setOnClickListener(this);

        rl_book = findViewById(R.id.rl_book);
        rl_book.setOnClickListener(this);


        iv_add_about = findViewById(R.id.iv_add_about);   //add icon here
        tv_about = findViewById(R.id.tv_about);
        if (App.getSinlton().getAbout() == null) {
            iv_add_about.setVisibility(View.GONE);
            tv_about.setText(App.getSinlton().getAbout());
        } else {
            iv_add_about.setVisibility(View.VISIBLE);
        }

        tv_company = findViewById(R.id.tv_company);
        iv_add_company = findViewById(R.id.iv_add_company);
        if (App.getSinlton().getCompany() == null) {
            iv_add_company.setVisibility(View.GONE);
            tv_company.setText(App.getSinlton().getCompany());
        } else {
            iv_add_company.setVisibility(View.VISIBLE);
        }

        iv_add_industry = findViewById(R.id.iv_add_industry);
        tv_industry = findViewById(R.id.tv_industry);
        if (App.getSinlton().getIndustry() == null) {
            iv_add_industry.setVisibility(View.GONE);
            tv_industry.setText(App.getSinlton().getIndustry());
        } else {
            iv_add_industry.setVisibility(View.VISIBLE);
        }

        iv_add_hometown = findViewById(R.id.iv_add_hometown);
        tv_hometown = findViewById(R.id.tv_hometown);
        if (App.getSinlton().getHometown() == null) {
            iv_add_hometown.setVisibility(View.GONE);
            tv_hometown.setText(App.getSinlton().getHometown());
        } else {
            iv_add_hometown.setVisibility(View.VISIBLE);
        }


        rv_add_image = findViewById(R.id.rv_add_image);

        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_back.setVisibility(View.VISIBLE);


        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("Edit Profile");

        tv_save = findViewById(R.id.tv_save);
        tv_save.setOnClickListener(this);
        tv_save.setVisibility(View.VISIBLE);

        tv_cancel = findViewById(R.id.tv_cancel);
        tv_cancel.setVisibility(View.GONE);
//        tv_cancel.setOnClickListener(this);
    }


    private void selectImage() {
        final CharSequence[] options = {"Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                activity.onBackPressed();
                break;

//            case R.id.ll_layout:
//
//                hideSoftKeyboard(activity);
//                break;

            case R.id.tv_save:
                UpdateProfile();
                break;

            case R.id.iv_take_image:
                position = "00";
                imageCheck = true;
                selectImage();
//                CropImage.activity()
//                        .start(getContext(), this);

                break;

            case R.id.iv_take_image1:
                position = "01";
                image1Check = true;
                selectImage();
//                CropImage.activity()
//                        .start(getContext(), this);

                break;

            case R.id.iv_take_image2:
                position = "02";
                image2Check = true;
                selectImage();
//                CropImage.activity()
//                        .start(getContext(), this);

                break;

            case R.id.iv_take_image3:
                position = "03";
                image3Check = true;
                selectImage();
//                CropImage.activity()
//                        .start(getContext(), this);

                break;

            case R.id.iv_take_image4:
                position = "04";
                image4Check = true;
                selectImage();
//                CropImage.activity()
//                        .start(getContext(), this);

                break;

            case R.id.iv_take_image5:
                position = "05";
                image5Check = true;
                selectImage();
//                CropImage.activity()
//                        .start(getContext(), this);

                break;


            case R.id.rl_edit_personal_info:
//                NavDirections navDirections = EditProfileFragmentDirections.actionEditProfileFragmentToPersonalInformationFragment();
//                Navigation.findNavController(view).navigate(navDirections);
                break;

            case R.id.rl_about_me:
                aboutDialog();
                break;

            case R.id.rl_industry:
                addIndustry();
                break;

            case R.id.rl_company:
                addCompany();
                break;

            case R.id.rl_hometown:
                addHomeTown();
                break;

            case R.id.rl_personality:
                addPersonality();
                break;

            case R.id.rl_sport:
//                addSport();
                break;

            case R.id.rl_music:
                addMusic();
                break;

            case R.id.rl_food:
                addFood();
                break;

            case R.id.rl_movie_and_tv:
                addMovieAndTv();
                break;

            case R.id.rl_book:
                addBooks();
                break;

        }
    }

    private void addBooks() {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.books_list);

//        EditText about_me = dialog.findViewById(R.id.et_about);
        TextView btn_ok_books_list = dialog.findViewById(R.id.btn_ok_books_list);
        TextView btn_cancel_books = dialog.findViewById(R.id.btn_cancel_books);

        RecyclerView rv_books_list = dialog.findViewById(R.id.rv_books_list);

        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        booksListAdapter = new BooksListAdapter(activity, books_list, new BooksListAdapter.Select() {
            @Override
            public void Choose(int position, String title, int status) {
                if (status == 1) {
                    Toast.makeText(activity, "" + title, Toast.LENGTH_SHORT).show();
                    books_datalist.add(title);
                } else {
                    Toast.makeText(activity, "" + title, Toast.LENGTH_SHORT).show();
                    books_datalist.remove(position);
                }
            }
        });
        rv_books_list.setAdapter(booksListAdapter);

        btn_ok_books_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

                showBooksListAdapter = new ShowBooksListAdapter(activity, books_datalist);
                rv_books_show.setAdapter(showBooksListAdapter);

            }
        });

        btn_cancel_books.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }

    private void addMovieAndTv() {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.movie_list);

//        EditText about_me = dialog.findViewById(R.id.et_about);
        TextView btn_ok_movie_list = dialog.findViewById(R.id.btn_ok_movie_list);
        TextView cancel = dialog.findViewById(R.id.btn_cancel_movie);

        RecyclerView rv_movie_list = dialog.findViewById(R.id.rv_movie_list);

        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        moviesListAdapter = new MoviesListAdapter(activity, movie_list, new MoviesListAdapter.Select() {
            @Override
            public void Choose(int position, String title, int status) {
                if (status == 1) {
                    Toast.makeText(activity, "" + title, Toast.LENGTH_SHORT).show();
                    movie_datalist.add(title);
                } else {
                    Toast.makeText(activity, "" + title, Toast.LENGTH_SHORT).show();
                    movie_datalist.remove(position);
                }
            }
        });
        rv_movie_list.setAdapter(moviesListAdapter);

        btn_ok_movie_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showMovieListAdapter = new ShowMovieListAdapter(activity, movie_datalist);
                rv_movie_show.setAdapter(showMovieListAdapter);
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

    private void addFood() {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.food_list);

//        EditText about_me = dialog.findViewById(R.id.et_about);
        TextView btn_ok_music_list = dialog.findViewById(R.id.btn_ok_food_list);
        TextView cancel = dialog.findViewById(R.id.btn_cancel_food);

        RecyclerView rv_food_list = dialog.findViewById(R.id.rv_food_list);

        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        foodListAdapter = new FoodListAdapter(activity, food_list, new FoodListAdapter.Select() {
            @Override
            public void Choose(int position, String title, int status) {
                if (status == 1) {
                    Toast.makeText(activity, "" + title, Toast.LENGTH_SHORT).show();
                    food_datalist.add(title);
                } else {
                    Toast.makeText(activity, "" + title, Toast.LENGTH_SHORT).show();
                    food_datalist.remove(position);
                }
            }
        });
        rv_food_list.setAdapter(foodListAdapter);

        btn_ok_music_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showfoodListAdapter = new ShowfoodListAdapter(activity, food_datalist);
                rv_food_show.setAdapter(showfoodListAdapter);
            }
        });

        dialog.show();

    }

    private void addMusic() {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.music_list);

//        EditText about_me = dialog.findViewById(R.id.et_about);
        TextView btn_ok_music_list = dialog.findViewById(R.id.btn_ok_music_list);
        TextView cancel = dialog.findViewById(R.id.btn_cancel_music);

        RecyclerView rv_music_list = dialog.findViewById(R.id.rv_music_list);

        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        musicListAdapter = new MusicListAdapter(activity, music_list, new MusicListAdapter.Select() {
            @Override
            public void Choose(int position, String title, int status) {
                if (status == 1) {
                    Toast.makeText(activity, "" + title, Toast.LENGTH_SHORT).show();
                    music_datalist.add(title);
                } else {
                    Toast.makeText(activity, "" + title, Toast.LENGTH_SHORT).show();
                    music_datalist.remove(position);
                }
            }
        });
        rv_music_list.setAdapter(musicListAdapter);

        btn_ok_music_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showMusicListAdapter = new ShowMusicListAdapter(activity, music_datalist);
                rv_music_show.setAdapter(showMusicListAdapter);

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


    private void addSport(String heading, List<GetInterestListModel.Detail.Type> list) {
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.sport_list);

//        EditText about_me = dialog.findViewById(R.id.et_about);
        TextView btn_ok_sport_list = dialog.findViewById(R.id.btn_ok_sport_list);
        TextView cancel = dialog.findViewById(R.id.btn_cancel_sport);
        TextView title = dialog.findViewById(R.id.tv_interestName);
        title.setText(heading);

        RecyclerView rv_sport_list = dialog.findViewById(R.id.rv_sport_list);

        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rv_sport_list.setAdapter(showSportListAdapter);

        btn_ok_sport_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Log.d("data", "ids=" + ids);

                strList = ids.toString();

                strList = strList.replace("[", "")
                        .replace("]", "")
                        .replace(" ", "");

                Log.d("data", "ids=" + strList);
//                showSportListAdapter = new ShowSportListAdapter(activity, sport);
//                rv_sportList_show.setAdapter(showSportListAdapter);


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


    private void addPersonality() {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.personality_list);

//        EditText about_me = dialog.findViewById(R.id.et_about);
        TextView btn_ok_personality_list = dialog.findViewById(R.id.btn_ok_personality_list);
        TextView cancel = dialog.findViewById(R.id.btn_cancel_personality);

        RecyclerView rv_personality = dialog.findViewById(R.id.rv_personality);

        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        personalityListAdapter = new PersonalityListAdapter(activity, personalityList, new PersonalityListAdapter.Select() {
            @Override
            public void Choose(int position, String title, String id, int status) {

                if (status == 1) {

                    ids_personality.add(id);
                    Toast.makeText(activity, "" + title, Toast.LENGTH_SHORT).show();
                    datalist.add(title);


                    strList_personality = ids_personality.toString();

                    strList_personality = strList_personality.replace("[", "")
                            .replace("]", "")
                            .replace(" ", "");


                } else {
                    Toast.makeText(activity, "" + title, Toast.LENGTH_SHORT).show();
//                    datalist.remove(position);
                    ids_personality.remove(id);
                }


            }
        });

        rv_personality.setAdapter(personalityListAdapter);

        btn_ok_personality_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showPersonalityAdapter = new ShowPersonalityAdapter(activity, datalist);
                rv_personality_show.setAdapter(showPersonalityAdapter);

                dialog.dismiss();

                Log.d(TAG, "onClick: " + ids_personality);
                Log.d(TAG, "onClick: " + strList_personality);

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

    private void addHomeTown() {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.hometown_layout);

        EditText et_add_hometown = dialog.findViewById(R.id.et_add_hometown);
        et_add_hometown.setText(App.getSinlton().getHometown());
        TextView btn_ok_hometown = dialog.findViewById(R.id.btn_ok_hometown);
        TextView cancel = dialog.findViewById(R.id.btn_cancel_hometown);

        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        btn_ok_hometown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hometown = et_add_hometown.getText().toString().trim();
                if (!hometown.isEmpty()) {
                    iv_add_hometown.setVisibility(View.GONE);
                    tv_hometown.setText(hometown);
                } else {
                    iv_add_hometown.setVisibility(View.GONE);
                }
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

    private void addCompany() {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.compant_layout);

        EditText et_add_company = dialog.findViewById(R.id.et_add_company);
        TextView btn_ok_company = dialog.findViewById(R.id.btn_ok_company);
        TextView cancel = dialog.findViewById(R.id.btn_cancel_company);

        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        btn_ok_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                company = et_add_company.getText().toString().trim();
                if (!company.isEmpty()) {
                    iv_add_company.setVisibility(View.GONE);
                    tv_company.setText(company);
                } else {
                    iv_add_company.setVisibility(View.GONE);
                }
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

    private void addIndustry() {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.industry_list);

        EditText about_me = dialog.findViewById(R.id.et_about);
        TextView btn_cancel_industry_list = dialog.findViewById(R.id.btn_cancel_industry_list);
        TextView cancel = dialog.findViewById(R.id.btn_cancel);

        RecyclerView rv_industry = dialog.findViewById(R.id.rv_industry);

        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        industryListAdapter = new IndustryListAdapter(activity, industryList, new IndustryListAdapter.Select() {
            @Override
            public void Choose(int position, GetIndustryListModel.Detail name, GetIndustryListModel.Detail id) {
                Toast.makeText(activity, "" + id.getId() + name.getTitile(), Toast.LENGTH_SHORT).show();
                industryName = name.getTitile();
                industry_id = id.getId();

                if (!industryName.isEmpty()) {
                    iv_add_industry.setVisibility(View.GONE);
                    tv_industry.setText(industryName);
                    dialog.dismiss();
                } else {
                    iv_add_industry.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                }

            }
        });

        rv_industry.setAdapter(industryListAdapter);

        btn_cancel_industry_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void aboutDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.about_me);

        EditText about_me = dialog.findViewById(R.id.et_about);
        about_me.setText(App.getSinlton().getAbout());
        TextView ok = dialog.findViewById(R.id.btn_ok);
        TextView cancel = dialog.findViewById(R.id.btn_cancel);

        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                about = about_me.getText().toString().trim();

                if (!about.isEmpty()) {
                    iv_add_about.setVisibility(View.GONE);
                    tv_about.setText(about);
                } else {
                    iv_add_about.setVisibility(View.GONE);
                }

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        resultUri = data.getData();

                        if (position.equals("00")) {
                            imagePath = getRealPathFromURI(resultUri);
                            image_file = new File(imagePath);
                            App.getAppPreference().SaveString("image", imagePath);
                            Glide.with(activity).load(App.getAppPreference().GetString("image")).into(iv_take_image);
                        } else if (position.equals("01")) {
                            String image1Path = getRealPathFromURI(resultUri);
                            image1_file = new File(image1Path);
                            App.getAppPreference().SaveString("image1", image1Path);
                            Glide.with(activity).load(App.getAppPreference().GetString("image1")).into(iv_take_image1);

                        } else if (position.equals("02")) {
                            String image2Path = getRealPathFromURI(resultUri);
                            image2_file = new File(image2Path);
                            App.getAppPreference().SaveString("image2", image2Path);
                            Glide.with(activity).load(App.getAppPreference().GetString("image2")).into(iv_take_image2);
                        } else if (position.equals("03")) {
                            String image3Path = getRealPathFromURI(resultUri);
                            image3_file = new File(image3Path);
                            App.getAppPreference().SaveString("image3", image3Path);
                            Glide.with(activity).load(App.getAppPreference().GetString("image3")).into(iv_take_image3);
                        } else if (position.equals("04")) {
                            String image4Path = getRealPathFromURI(resultUri);
                            image4_file = new File(image4Path);
                            App.getAppPreference().SaveString("image4", image4Path);
                            Glide.with(activity).load(App.getAppPreference().GetString("image4")).into(iv_take_image4);
                        } else if (position.equals("05")) {
                            String image5Path = getRealPathFromURI(resultUri);
                            image5_file = new File(image5Path);
                            App.getAppPreference().SaveString("image5", image5Path);
                            Glide.with(activity).load(App.getAppPreference().GetString("image5")).into(iv_take_image5);
                        } else {
                            Toast.makeText(activity, "Not found any position", Toast.LENGTH_SHORT).show();
                        }

                    }
                    break;
            }
        }
        
    }
    

    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void getIndustryApi() {
        vmReceiveData.get_industrylist(activity).observe(EditUserProfileActivity.this, new Observer<GetIndustryListModel>() {
            @Override
            public void onChanged(GetIndustryListModel getIndustryListModel) {
                if (getIndustryListModel.getSuccess().equalsIgnoreCase("1")) {

                    industryList = getIndustryListModel.getDetails();
//                    Toast.makeText(activity, ""+getIndustryListModel.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private void getPersonalityApi() {
        vmReceiveData.get_personalityList(activity, user_id).observe(EditUserProfileActivity.this, new Observer<GetPersonalityListModel>() {
            @Override
            public void onChanged(GetPersonalityListModel getPersonalityListModel) {
                if (getPersonalityListModel.getSuccess().equalsIgnoreCase("1")) {
                    personalityList = getPersonalityListModel.getDetails();

                }
            }
        });
    }

    private void getInterestApi() {
        vmReceiveData.get_interestList(activity, user_id).observe(EditUserProfileActivity.this, new Observer<GetInterestListModel>() {
            @Override
            public void onChanged(GetInterestListModel getInterestListModel) {
                if (getInterestListModel.getSuccess().equalsIgnoreCase("1")) {

                    sport_datalist = getInterestListModel.getDetails();
                    SportsListAdapter sportsListAdapter = new SportsListAdapter(activity, sport_datalist, new SportsListAdapter.Select() {
                        @Override
                        public void Choose(int position, String title, int status) {

                        }

                        @Override
                        public void onClick(int pos, String title) {
                            List<GetInterestListModel.Detail.Type> list2 = new ArrayList<>();
                            list2.clear();
                            for (int i = 0; i < sport_datalist.get(pos).getType().size(); i++) {
                                list2.add(sport_datalist.get(pos).getType().get(i));
                            }
                            showSportListAdapter = new ShowSportListAdapter(activity, list2, new ShowSportListAdapter.Select() {
                                @Override
                                public void Choose(int position, String title, String id) {

                                    if (map.get(list2.get(position).getId()) != null) {
                                        map.remove(list2.get(position).getId());
                                        Toast.makeText(activity, "Removed--" + position + " " + title, Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    map.put(list2.get(position).getId(), list2.get(position).getId());
//                                    map.put("position", String.valueOf(position));


                                    ids.add(id);
                                    Toast.makeText(activity, "Added" + position + " " + title, Toast.LENGTH_SHORT).show();
                                }
                            });
                            addSport(title, list2);
                        }
                    });
                    rv_sportList_show.setAdapter(sportsListAdapter);

                } else {
                    Toast.makeText(activity, "response error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        getIndustryApi();
        getPersonalityApi();
        getInterestApi();
        super.onResume();
        if (App.getAppPreference().GetString(AppConstants.CHECK_IMAGE).equalsIgnoreCase("5")) {
            getProfile();
        }
    }

    private void getProfile() {
        vmReceiveData.get_profile(activity, user_id).observe(EditUserProfileActivity.this, new Observer<GetProfileDataModel>() {
            @Override
            public void onChanged(GetProfileDataModel getProfileDataModel) {
                if (getProfileDataModel.getSuccess().equalsIgnoreCase("1")) {
                    App.getSinlton().setUser_image(getProfileDataModel.getDetails().getImage());
                    App.getSinlton().setUser_image1(getProfileDataModel.getDetails().getImage1());
                    App.getSinlton().setUser_image2(getProfileDataModel.getDetails().getImage2());
                    App.getSinlton().setUser_image3(getProfileDataModel.getDetails().getImage3());
                    App.getSinlton().setUser_image4(getProfileDataModel.getDetails().getImage4());
                    App.getSinlton().setUser_image5(getProfileDataModel.getDetails().getImage5());

                    App.getAppPreference().SaveString(AppConstants.CHECK_IMAGE, "4");

                    Glide.with(activity).load(AppConstants.USERIMAGE + App.getSinlton().getUser_image()).placeholder(R.drawable.add).into(iv_take_image);
                    Glide.with(activity).load(AppConstants.USERIMAGE + App.getSinlton().getUser_image1()).placeholder(R.drawable.add).into(iv_take_image1);

                    Glide.with(activity).load(AppConstants.USERIMAGE + App.getSinlton().getUser_image2()).placeholder(R.drawable.add).into(iv_take_image2);

                    Glide.with(activity).load(AppConstants.USERIMAGE + App.getSinlton().getUser_image3()).placeholder(R.drawable.add).into(iv_take_image3);

                    Glide.with(activity).load(AppConstants.USERIMAGE + App.getSinlton().getUser_image4()).placeholder(R.drawable.add).into(iv_take_image4);

                    Glide.with(activity).load(AppConstants.USERIMAGE + App.getSinlton().getUser_image5()).placeholder(R.drawable.add).into(iv_take_image5);

                    tv_user_name.setText(getProfileDataModel.getDetails().getName());
                    tv_age.setText(getProfileDataModel.getDetails().getAge() + " years");

                    App.getAppPreference().SaveString(AppConstants.USER_NAME, getProfileDataModel.getDetails().getName());
                    App.getAppPreference().SaveString(AppConstants.USER_DOB, getProfileDataModel.getDetails().getDob());
                } else {
                    Toast.makeText(activity, "response error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void UpdateProfile() {

        String my_note = et_note.getText().toString().trim();
        String strAddress = et_address.getText().toString().trim();

        if (imageCheck) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), image_file);
            body_image = MultipartBody.Part.createFormData("image", image_file.getName(), requestFile);

        } else {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            body_image = MultipartBody.Part.createFormData("image", "", requestFile);
        }

        if (image1Check) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), image1_file);
            body_image1 = MultipartBody.Part.createFormData("image1", image1_file.getName(), requestFile);

        } else {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            body_image1 = MultipartBody.Part.createFormData("image1", "", requestFile);
        }

        if (image2Check) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), image2_file);
            body_image2 = MultipartBody.Part.createFormData("image2", image2_file.getName(), requestFile);

        } else {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            body_image2 = MultipartBody.Part.createFormData("image2", "", requestFile);
        }

        if (image3Check) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), image3_file);
            body_image3 = MultipartBody.Part.createFormData("image3", image3_file.getName(), requestFile);

        } else {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            body_image3 = MultipartBody.Part.createFormData("image3", "", requestFile);
        }

        if (image4Check) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), image4_file);
            body_image4 = MultipartBody.Part.createFormData("image4", image4_file.getName(), requestFile);

        } else {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            body_image4 = MultipartBody.Part.createFormData("image4", "", requestFile);
        }

        if (image5Check) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), image5_file);
            body_image5 = MultipartBody.Part.createFormData("image5", image5_file.getName(), requestFile);

        } else {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            body_image5 = MultipartBody.Part.createFormData("image5", "", requestFile);
        }

        RequestBody aboutRequestBody = RequestBody.create(MediaType.parse("text/plain"), about);
        RequestBody industryRequestBody = RequestBody.create(MediaType.parse("text/plain"), industry_id);
        RequestBody companyRequestBody = RequestBody.create(MediaType.parse("text/plain"), company);
        RequestBody home_townRequestBody = RequestBody.create(MediaType.parse("text/plain"), hometown);
        RequestBody my_personalityRequestBody = RequestBody.create(MediaType.parse("text/plain"), strList_personality);
        RequestBody my_interestsRequestBody = RequestBody.create(MediaType.parse("text/plain"), strList);
        RequestBody et_noteRequestBody = RequestBody.create(MediaType.parse("text/plain"), my_note);
        RequestBody user_idRequestBody = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody addressRequestBody = RequestBody.create(MediaType.parse("text/plain"), strAddress);


        vmReceiveData.get_updateProfile(EditUserProfileActivity.this, body_image, body_image1, body_image2, body_image3, body_image4, body_image5, aboutRequestBody, industryRequestBody, companyRequestBody, home_townRequestBody, my_personalityRequestBody, my_interestsRequestBody, et_noteRequestBody, user_idRequestBody, addressRequestBody).observe(EditUserProfileActivity.this, new Observer<GetUpdateProfileModel>() {
            @Override
            public void onChanged(GetUpdateProfileModel getUpdateProfileModel) {
                if (getUpdateProfileModel.getSuccess().equalsIgnoreCase("1")) {

                    User user = new User();
                    user.setUid(getUpdateProfileModel.getDetails().getId()); // Replace with your uid for the user to be updated.
                    user.setName(getUpdateProfileModel.getDetails().getName());
                    user.setAvatar(AppConstants.USERIMAGE + getUpdateProfileModel.getDetails().getImage()); // Replace with the name of the user

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
                    Toast.makeText(activity, "" + getUpdateProfileModel.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "" + getUpdateProfileModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    

//    public void setupUI(View view) {
//
//        //Set up touch listener for non-text box views to hide keyboard.
//        if(!(view instanceof EditText)) {
//
//            view.setOnTouchListener(new View.OnTouchListener() {
//
//                public boolean onTouch(View v, MotionEvent event) {
//                    hideSoftKeyboard();
//                    return false;
//                }
//
//            });
//        }
//
//        //If a layout container, iterate over children and seed recursion.
//        if (view instanceof ViewGroup) {
//
//            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
//
//                View innerView = ((ViewGroup) view).getChildAt(i);
//
//                setupUI(innerView);
//            }
//        }
//    }


}