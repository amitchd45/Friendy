package com.solutions.friendy.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.omninos.util_data.CommonUtils;
import com.solutions.friendy.Activity.ExplorActivity;
import com.solutions.friendy.Activity.UserProfileActivity;
import com.solutions.friendy.Adapters.AdapterInterestUser_2;
import com.solutions.friendy.Adapters.CardStackAdapter;
import com.solutions.friendy.Adapters.ListPersonality_2Adapter;
import com.solutions.friendy.Adapters.PlanAdapter;
import com.solutions.friendy.Adapters.VipSliderAdapter;
import com.solutions.friendy.App;
import com.solutions.friendy.ClickListner.SwipeClickListener;
import com.solutions.friendy.Fragments.HomePageFragment;
import com.solutions.friendy.Fragments.HomePageFragmentDirections;
import com.solutions.friendy.Models.GetProfileDataModel;
import com.solutions.friendy.Models.GetSlideUserProfileModelClass;
import com.solutions.friendy.Models.GetSwipeItemsModelClass;
import com.solutions.friendy.Models.GetSwipeStatusModelClass;
import com.solutions.friendy.Models.ImageStoreModel;
import com.solutions.friendy.Models.PurchagePlanPojo;
import com.solutions.friendy.Models.SubscribePlanPojo;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;
import com.solutions.friendy.Retrofit.StringContract;
import com.solutions.friendy.ViewModel.SwipeItemsViewModelClass;
import com.solutions.friendy.ViewModel.VmReceiveData;
import com.solutions.friendy.ViewModel.VmUserSetting;
import com.squareup.picasso.Picasso;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, SwipeClickListener, NavigationView.OnNavigationItemSelectedListener {
    private TextView tv_my_privileges, iv_edit, tv_setName;
    private ImageView iv_menu, iv_back, tv_chat;
    private ImageView profile_image;
    //    private AdvanceDrawerLayout drawer;
    private DrawerLayout drawer;
    private NavigationView nav_view;
    private int cardPosition;
    private LayoutInflater layoutInflater;
    private Activity activity = HomeActivity.this;
    private VmReceiveData vmReceiveData;

    private RelativeLayout ll_nav_home, rl_nav_verification, rl_nav_secret, rl_nav_setting, rl_nav_help,
            rl_nav_invite, rl_nav_logout, rl_nav_explore;

    private CardStackLayoutManager manager;
    private CardStackView cardStackView;
    LinearLayout down;

    private MaterialCardView superLikeCardView, likeCardView, dislikeCardView, laterCardView;
    ImageView superLikeImageView, ic_flash;

    private SwipeItemsViewModelClass swipeItemsViewModelClass;
    private List<GetSwipeItemsModelClass.Detail> imagesList = new ArrayList<>();
    private String user_id;
    private String status = "";
    private String friendId = "";
    private GetSwipeItemsModelClass.Detail currentItem;
    private TextView centerTextView;
    private RelativeLayout buttonRelativeView;
    private CardStackAdapter adapter;
    private TextView tv_title;
    //-----dialog content----
    private VipSliderAdapter sliderAdapter;
    private Dialog dialog;
    private int a = 1;
    private int b = 0;

    //show user profile

    private ViewPager viewPager;
    private LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private String uq_id, id, name, image;

    private ImageView iv_edit_profile, iv_image;
    private TextView edit_query;

    private List<GetSlideUserProfileModelClass.Details.PersonalityTitle> list1 = new ArrayList<>();
    private List<GetSlideUserProfileModelClass.Details.InterestTitle> list2 = new ArrayList<>();

    private ListPersonality_2Adapter listPersonality_2Adapter;
    private RecyclerView rv_List_personality;

    private TextView tv_user_name, tv_dob, tv_about, tv_industry, tv_company, tv_hometown;
    private RecyclerView interestRecyclerView;

    private AdapterInterestUser_2 adapterInterestUser_2;
    List<ImageStoreModel> image_store = new ArrayList<>();
    private static final int PERMISSION_ID = 44;
    private FusedLocationProviderClient mFusedLocationClient;
    private List<SubscribePlanPojo.Detail> planList = new ArrayList<>();
    private String userId, planId;
    private VmUserSetting vmUserSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        vmUserSetting = ViewModelProviders.of(this).get(VmUserSetting.class);

        vmReceiveData = ViewModelProviders.of(this).get(VmReceiveData.class);
        swipeItemsViewModelClass = ViewModelProviders.of(this).get(SwipeItemsViewModelClass.class);

        findIds();
        user_id = App.getAppPreference().GetString("id");
        userId = App.getAppPreference().GetString("id");
        uq_id = App.getSinlton().getUq_id();

        name = App.getAppPreference().GetString("name");
        image = App.getAppPreference().GetString("image");
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (App.getAppPreference().GetString("newUser").equalsIgnoreCase("1")) {
            registerCommetChat(user_id, name, image);
        }

        getLastLocation();

        startAnim();
        setAdapt();
        Loader();
        prepareCardStack();

        showPlan();
    }

    private void registerCommetChat(String user_id, String name, String image) {
        User user = new User();
        user.setUid(user_id);
        user.setName(name);
        user.setAvatar(AppConstants.USERIMAGE + image);

        CometChat.createUser(user, StringContract.AppDetails.API_KEY, new CometChat.CallbackListener<User>() {
            @Override
            public void onSuccess(User user) {
                App.getAppPreference().SaveString("newUser", "2");
//                App.getAppPreference().SaveString(AppConstants.TOKEN,"1");
//                NavDirections navDirections = UploadRealPhotoFragmentDirections.actionUploadRealPhotoFragmentToHomePageFragment();
//                Navigation.findNavController(view).navigate(navDirections);


            }

            @Override
            public void onError(CometChatException e) {
                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prepareCardStack() {
        manager = new CardStackLayoutManager(activity.getBaseContext(), new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {

            }

            @Override
            public void onCardSwiped(Direction direction) {
                App.getAppPreference().SaveString("swiped", "1");
                cardPosition = manager.getTopPosition();
                if (imagesList.size() != 0) {

                    if (cardPosition == imagesList.size()) {
                        manager.setTopPosition(0);
                        centerTextView.setVisibility(View.VISIBLE);
                    }
                    currentItem = imagesList.get(cardPosition - 1);
                    friendId = currentItem.getId();
                    if (direction == Direction.Top) {
//                        Toast.makeText(activity, "top", Toast.LENGTH_SHORT).show();
                        status = "2";
                    } else if (direction == Direction.Bottom) {
                        status = "3";
//                        Toast.makeText(activity, "bottom", Toast.LENGTH_SHORT).show();
                    } else if (direction == Direction.Left) {
                        status = "0";
//                        Toast.makeText(activity, "left", Toast.LENGTH_SHORT).show();
                    } else if (direction == Direction.Right) {
                        status = "1";
//                        Toast.makeText(activity, "right", Toast.LENGTH_SHORT).show();
                    }
                }
                if (cardPosition == imagesList.size()) {
                    imagesList.clear();
                }
                swipeItemsViewModelClass.getSwipeStatus(activity, user_id, friendId, status).observe(HomeActivity.this, new Observer<GetSwipeStatusModelClass>() {
                    @Override
                    public void onChanged(@Nullable GetSwipeStatusModelClass getSwipeStatusModelClass) {
                        if (getSwipeStatusModelClass.getSuccess().equalsIgnoreCase("1")) {

                            Toast.makeText(activity, getSwipeStatusModelClass.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        if (getSwipeStatusModelClass.getStatus().equalsIgnoreCase("4")) {


                            Toast.makeText(activity, "Add in friend list", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            @Override
            public void onCardRewound() {

            }

            @Override
            public void onCardCanceled() {

            }

            @Override
            public void onCardAppeared(View view, int position) {

            }

            @Override
            public void onCardDisappeared(View view, int position) {

            }
        });


        cardStackView.setLayoutManager(manager);
        manager.setStackFrom(StackFrom.Bottom);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8f);
        manager.setMaxDegree(16f);
        manager.setDirections(Direction.FREEDOM);
        manager.setSwipeThreshold(0.3f);
        manager.setScaleInterval(0.95f);

        superLikeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VipDialog(v);
                Toast.makeText(activity, "okkkk", Toast.LENGTH_SHORT).show();
                if (imagesList.size() != 0) {
                    SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                            .setDirection(Direction.Top)
                            .setDuration(800)
                            .setInterpolator(new AccelerateInterpolator())
                            .build();

                    manager.setSwipeAnimationSetting(setting);
//                    cardStackView.swipe();
//                    Toast.makeText(activity, "hello", Toast.LENGTH_SHORT).show();
                }
            }
        });

        likeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imagesList.size() != 0) {
                    SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                            .setDirection(Direction.Right)
                            .setDuration(800)
                            .setInterpolator(new AccelerateInterpolator())
                            .build();

                    manager.setSwipeAnimationSetting(setting);
                    cardStackView.swipe();
                }
            }
        });

        dislikeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imagesList.size() != 0) {
                    SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                            .setDirection(Direction.Left)
                            .setDuration(800)
                            .setInterpolator(new AccelerateInterpolator())
                            .build();

                    manager.setSwipeAnimationSetting(setting);
                    cardStackView.swipe();
                }
//                Toast.makeText(activity, "hello", Toast.LENGTH_SHORT).show();
            }
        });

        laterCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imagesList.size() != 0) {
                    SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                            .setDirection(Direction.Bottom)
                            .setDuration(800)
                            .setInterpolator(new AccelerateInterpolator())
                            .build();

                    manager.setSwipeAnimationSetting(setting);
                    cardStackView.swipe();
                }
            }
        });

    }

    private void findIds() {

        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        profile_image = findViewById(R.id.profile_image);
        tv_setName = findViewById(R.id.tv_setName);


        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("Friendy");

        ic_flash = findViewById(R.id.ic_flash);
        ic_flash.setOnClickListener(this);

        iv_edit = findViewById(R.id.iv_edit);
        iv_edit.setOnClickListener(this);

        ll_nav_home = findViewById(R.id.ll_nav_home);
        ll_nav_home.setOnClickListener(this);

        rl_nav_explore = findViewById(R.id.rl_nav_explore);
        rl_nav_explore.setOnClickListener(this);

        rl_nav_verification = findViewById(R.id.rl_nav_verification);
        rl_nav_verification.setOnClickListener(this);

        rl_nav_secret = findViewById(R.id.rl_nav_secret);
        rl_nav_secret.setOnClickListener(this);

        rl_nav_setting = findViewById(R.id.rl_nav_setting);
        rl_nav_setting.setOnClickListener(this);

        rl_nav_help = findViewById(R.id.rl_nav_help);
        rl_nav_help.setOnClickListener(this);

        rl_nav_invite = findViewById(R.id.rl_nav_invite);
        rl_nav_invite.setOnClickListener(this);

        rl_nav_logout = findViewById(R.id.rl_nav_logout);
        rl_nav_logout.setOnClickListener(this);


        cardStackView = findViewById(R.id.card_stack_view);
//        cardStackView.setOnClickListener(this);

        drawer = findViewById(R.id.drawer_layout);
        nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(this);

        tv_my_privileges = findViewById(R.id.tv_my_privileges);
        tv_my_privileges.setOnClickListener(this);

        tv_chat = findViewById(R.id.tv_chat);
        tv_chat.setVisibility(View.GONE);
        tv_chat.setOnClickListener(this);

        iv_back = findViewById(R.id.iv_back);
        iv_back.setVisibility(View.GONE);

        iv_menu = findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(this);
        iv_menu.setVisibility(View.VISIBLE);

        cardStackView = findViewById(R.id.card_stack_view);
        cardStackView.setOnClickListener(this);

        superLikeCardView = findViewById(R.id.superlike_card_view);
        superLikeCardView.setOnClickListener(this);

        user_id = App.getAppPreference().GetString("id");

        dislikeCardView = findViewById(R.id.dislike_card_view);
        superLikeImageView = findViewById(R.id.superlike_image_view);
        centerTextView = findViewById(R.id.center_text_view);
        likeCardView = findViewById(R.id.like_card_view);
        laterCardView = findViewById(R.id.later_card_view);
        buttonRelativeView = findViewById(R.id.button_relative_view);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ic_flash:
                step_up_Dialog(v);
                tested_Dialog(v);
                break;

            case R.id.tv_chat:

                break;

            case R.id.iv_edit:
                drawer.closeDrawer(GravityCompat.START);
//                NavDirections navDirections = HomePageFragmentDirections.actionHomePageFragmentToProfileFragment();
//                Navigation.findNavController(view).navigate(navDirections);
                break;

            case R.id.tv_my_privileges:
                drawer.closeDrawer(GravityCompat.START);
//                NavDirections navDirections2 = HomePageFragmentDirections.actionHomePageFragmentToMyPrivilegesFragment();
//                Navigation.findNavController(view).navigate(navDirections2);
                break;

            case R.id.iv_menu:
                drawer.openDrawer(GravityCompat.START);
                break;
            case R.id.ll_nav_home:
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.rl_nav_explore:
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(activity, ExplorActivity.class));
                break;

            case R.id.rl_nav_verification:
                drawer.closeDrawer(GravityCompat.START);
//                NavDirections verification = HomePageFragmentDirections.actionHomePageFragmentToVerificationFragment();
//                Navigation.findNavController(view).navigate(verification);

                break;

            case R.id.rl_nav_secret:

//                drawer.closeDrawer(GravityCompat.START);
//                NavDirections secret = HomePageFragmentDirections.actionHomePageFragmentToSecretCrushFragment();
//                Navigation.findNavController(view).navigate(secret);

                break;

            case R.id.rl_nav_setting:
//                drawer.closeDrawer(GravityCompat.START);
//                NavDirections navDirections3 = HomePageFragmentDirections.actionHomePageFragmentToSettingFragment();
//                Navigation.findNavController(view).navigate(navDirections3);

                break;

            case R.id.rl_nav_help:
                drawer.closeDrawer(GravityCompat.START);
//                NavDirections navDirections1 = HomePageFragmentDirections.actionHomePageFragmentToHelpAndFeedbackFragment();
//                Navigation.findNavController(view).navigate(navDirections1);

                break;

            case R.id.rl_nav_invite:

                drawer.closeDrawer(GravityCompat.START);
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Insert Subject here");
                String app_url = "App download link put soon hear...";
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, app_url);
                startActivity(Intent.createChooser(shareIntent, "Share via"));

                break;

            case R.id.rl_nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                App.getAppPreference().Logout();
                CometChat.logout(new CometChat.CallbackListener<String>() {
                    @Override
                    public void onSuccess(String s) {
//                        NavDirections navDirections4 = HomePageFragmentDirections.actionHomePageFragmentToLoginFragment();
//                        Navigation.findNavController(view).navigate(navDirections4);
                    }

                    @Override
                    public void onError(CometChatException e) {
//                        Snackbar.make(view, "Login Error:" + e.getCode(), Snackbar.LENGTH_LONG).show();
                    }
                });

//                NavDirections navDirections4=HomePageFragmentDirections.actionHomePageFragmentToLoginFragment();
//                Navigation.findNavController(view).navigate(navDirections4);

                break;
        }

    }

    private void tested_Dialog(View v) {

    }

    private void startAnim() {
        final Animation myAnim = AnimationUtils.loadAnimation(activity.getBaseContext(), R.anim.swipe_transition_animation);
        buttonRelativeView.startAnimation(myAnim);
        myAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                onResume();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void setAdapt() {

        adapter = new CardStackAdapter(activity, imagesList);
        cardStackView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setItemClickListener(this);

    }

    private void prepareData() {
        if (CommonUtils.isNetworkConnected(activity)) {
            swipeItemsViewModelClass.getSwipeItems(activity, user_id, App.getAppPreference().GetString(AppConstants.LATITUDE), App.getAppPreference().GetString(AppConstants.LONGITUDE)).observe(HomeActivity.this, new Observer<GetSwipeItemsModelClass>() {
                @Override
                public void onChanged(@Nullable GetSwipeItemsModelClass getSwipeItemsModelClass) {
                    dialog.dismiss();
                    if (getSwipeItemsModelClass.getSuccess().equalsIgnoreCase("1")) {
                        centerTextView.setVisibility(View.INVISIBLE);
                        imagesList = getSwipeItemsModelClass.getDetails();
                        App.getAppPreference().SaveString("superLikeCount", getSwipeItemsModelClass.getCountSuperLikeUser());
                        setAdapt();
                    } else if (getSwipeItemsModelClass.getSuccess().equalsIgnoreCase("0")) {
                        imagesList.clear();
                        setAdapt();
                        centerTextView.setVisibility(View.VISIBLE);
                    }
                }
            });

        } else {
            Toast.makeText(activity, "Check your internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void leftClick(View view, int position) {

        Toast.makeText(activity, "left click", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void rightClick(View view, int position) {
        Toast.makeText(activity, "right click", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void bottomClick(View view, int position, String id) {
        App.getSinlton().setUq_id(id);
        startActivity(new Intent(activity, UserProfileActivity.class));

    }

    @Override
    public void onResume() {
        if (checkPermissions()) {
            getLastLocation();
        }

        App.getAppPreference().SaveString("fragmentClick", "0");
        if (App.getSinlton().getFilterActive().equalsIgnoreCase("1")) {
            imagesList = App.getSinlton().getFilteredUser();
            if (imagesList.size() == 0) {
                centerTextView.setVisibility(View.VISIBLE);
            } else {
                centerTextView.setVisibility(View.GONE);
                setAdapt();
            }

        } else {
            prepareData();

        }

        super.onResume();
        getProfile();

    }

    private void Loader() {
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.custom_loader);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


        dialog.show();
    }

    private void VipDialog(final View view) {

        final View confirmdailog = layoutInflater.inflate(R.layout.get_vip_superlike, null);
        final Dialog dailogbox = new Dialog(activity, R.style.Theme_MaterialComponents_Light_Dialog_Alert);
        dailogbox.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        RecyclerView rv_choosePlan = confirmdailog.findViewById(R.id.rv_choosePlan);

        PlanAdapter planAdapter = new PlanAdapter(activity, planList, new PlanAdapter.Select() {
            @Override
            public void planClick(String id) {
                planId = id;
            }
        });
        rv_choosePlan.setAdapter(planAdapter);

        dailogbox.setCancelable(false);
        dailogbox.setContentView(confirmdailog);
        confirmdailog.findViewById(R.id.iv_close).setOnClickListener(view1 -> dailogbox.dismiss());
        confirmdailog.findViewById(R.id.btn_Step_continue_with_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                dailogbox.dismiss();
                purchagePlan(planId);
            }
        });
        dailogbox.show();
    }

    private void step_up_Dialog(final View view) {

        final View confirmdailog = layoutInflater.inflate(R.layout.get_step_up, null);
        final Dialog dailogbox = new Dialog(activity, R.style.Theme_MaterialComponents_Light_Dialog_Alert);
        dailogbox.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        dailogbox.setCancelable(false);
        dailogbox.setContentView(confirmdailog);

        LinearLayout ll_twelvemonth1 = confirmdailog.findViewById(R.id.ll_twelvemonth1);
        TextView tv_one1 = confirmdailog.findViewById(R.id.tv_one1);
        TextView tv_twelve1 = confirmdailog.findViewById(R.id.tv_twelve1);
        TextView tv_month_11 = confirmdailog.findViewById(R.id.tv_month_11);
        TextView tv_money1 = confirmdailog.findViewById(R.id.tv_money1);


        LinearLayout ll_threemonth2 = confirmdailog.findViewById(R.id.ll_threemonth2);
        TextView tv_two2 = confirmdailog.findViewById(R.id.tv_two2);
        TextView tv_three2 = confirmdailog.findViewById(R.id.tv_three2);
        TextView tv_month_22 = confirmdailog.findViewById(R.id.tv_month_22);
//        TextView tv_money_mol_12 = confirmdailog.findViewById(R.id.tv_money_mol_12);
        TextView tv_money_3202 = confirmdailog.findViewById(R.id.tv_money_3202);

        LinearLayout ll_onemonth3 = confirmdailog.findViewById(R.id.ll_onemonth3);
        TextView tv_one_month3 = confirmdailog.findViewById(R.id.tv_one_month3);
        TextView tv_month_33 = confirmdailog.findViewById(R.id.tv_month_33);
        TextView tv_money_mol_33 = confirmdailog.findViewById(R.id.tv_money_mol_33);


        if (b == 0) {
            ll_twelvemonth1.setBackgroundResource(R.drawable.stroke_bg);
            tv_one1.setVisibility(View.VISIBLE);
            tv_twelve1.setTextColor(Color.parseColor("#fc5068"));
            tv_month_11.setTextColor(Color.parseColor("#fc5068"));
//            tv_money_mol.setTextColor(Color.parseColor("#fc5068"));
            tv_money1.setTextColor(Color.parseColor("#fc5068"));

        }

        confirmdailog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                dailogbox.dismiss();

            }
        });
        confirmdailog.findViewById(R.id.btn_Step_continue_with_pay1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Toast toast = Toast.makeText(activity, R.string.comming_soon, Toast.LENGTH_SHORT);
                ViewGroup group = (ViewGroup) toast.getView();
                TextView messageTextView = (TextView) group.getChildAt(0);
                messageTextView.setTextSize(15);
                toast.show();

                dailogbox.dismiss();

            }
        });
        confirmdailog.findViewById(R.id.ll_twelvemonth1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                a = 2;
                ll_twelvemonth1.setBackgroundResource(R.drawable.stroke_bg);
                tv_one1.setVisibility(View.VISIBLE);
                tv_twelve1.setTextColor(Color.parseColor("#fc5068"));
                tv_month_11.setTextColor(Color.parseColor("#fc5068"));
//                tv_money_mol.setTextColor(Color.parseColor("#fc5068"));
                tv_money1.setTextColor(Color.parseColor("#fc5068"));

                if (a == 2) {
                    ll_threemonth2.setBackgroundResource(R.drawable.strock_white);
                    tv_two2.setVisibility(View.GONE);
                    tv_three2.setTextColor(Color.parseColor("#000000"));
                    tv_month_22.setTextColor(Color.parseColor("#000000"));
//                    tv_money_mol_12.setTextColor(Color.parseColor("#000000"));
                    tv_money_3202.setTextColor(Color.parseColor("#000000"));

                    ll_onemonth3.setBackgroundResource(R.drawable.strock_white);
                    tv_one_month3.setTextColor(Color.parseColor("#000000"));
                    tv_month_33.setTextColor(Color.parseColor("#000000"));
                    tv_money_mol_33.setTextColor(Color.parseColor("#000000"));
                }

            }
        });

        confirmdailog.findViewById(R.id.ll_threemonth2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                a = 4;

                ll_threemonth2.setBackgroundResource(R.drawable.stroke_bg);
                tv_two2.setVisibility(View.VISIBLE);
                tv_three2.setTextColor(Color.parseColor("#fc5068"));
                tv_month_22.setTextColor(Color.parseColor("#fc5068"));
//                tv_money_mol_12.setTextColor(Color.parseColor("#fc5068"));
                tv_money_3202.setTextColor(Color.parseColor("#fc5068"));

                if (a == 4) {
                    ll_onemonth3.setBackgroundResource(R.drawable.strock_white);
                    tv_one_month3.setTextColor(Color.parseColor("#000000"));
                    tv_month_33.setTextColor(Color.parseColor("#000000"));
                    tv_money_mol_33.setTextColor(Color.parseColor("#000000"));

                    ll_twelvemonth1.setBackgroundResource(R.drawable.strock_white);
                    tv_one1.setVisibility(View.GONE);
                    tv_twelve1.setTextColor(Color.parseColor("#000000"));
                    tv_month_11.setTextColor(Color.parseColor("#000000"));
//                    tv_money_mol.setTextColor(Color.parseColor("#000000"));
                    tv_money1.setTextColor(Color.parseColor("#000000"));


                }


            }
        });

        confirmdailog.findViewById(R.id.ll_onemonth3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                a = 3;

                ll_onemonth3.setBackgroundResource(R.drawable.stroke_bg);
                tv_one_month3.setTextColor(Color.parseColor("#fc5068"));
                tv_month_33.setTextColor(Color.parseColor("#fc5068"));
                tv_money_mol_33.setTextColor(Color.parseColor("#fc5068"));

                if (a == 3) {
                    ll_twelvemonth1.setBackgroundResource(R.drawable.strock_white);
                    tv_one1.setVisibility(View.GONE);
                    tv_twelve1.setTextColor(Color.parseColor("#000000"));
                    tv_month_11.setTextColor(Color.parseColor("#000000"));
//                    tv_money_mol.setTextColor(Color.parseColor("#000000"));
                    tv_money1.setTextColor(Color.parseColor("#000000"));


                    ll_threemonth2.setBackgroundResource(R.drawable.strock_white);
                    tv_two2.setVisibility(View.GONE);
                    tv_three2.setTextColor(Color.parseColor("#000000"));
                    tv_month_22.setTextColor(Color.parseColor("#000000"));
//                    tv_money_mol_12.setTextColor(Color.parseColor("#000000"));
                    tv_money_3202.setTextColor(Color.parseColor("#000000"));
                }


            }
        });


        dailogbox.show();

    }

    private void getProfile() {
        vmReceiveData.get_profile(activity, user_id).observe(HomeActivity.this, new Observer<GetProfileDataModel>() {
            @Override
            public void onChanged(GetProfileDataModel getProfileDataModel) {
                if (getProfileDataModel.getSuccess().equalsIgnoreCase("1")) {

                    App.getSinlton().setUser_image(getProfileDataModel.getDetails().getImage());

                    App.getAppPreference().SaveString(AppConstants.USER_NAME, getProfileDataModel.getDetails().getName());
                    App.getAppPreference().SaveString(AppConstants.USER_DOB, getProfileDataModel.getDetails().getDob());

                    App.getSinlton().setName(getProfileDataModel.getDetails().getName());
                    App.getSinlton().setAge(getProfileDataModel.getDetails().getAge());
                    App.getSinlton().setAbout(getProfileDataModel.getDetails().getAbout());
                    App.getSinlton().setIndustry(getProfileDataModel.getDetails().getIndustryTitle());
                    App.getSinlton().setCompany(getProfileDataModel.getDetails().getCompany());
                    App.getSinlton().setHometown(getProfileDataModel.getDetails().getHomeTown());
                    App.getSinlton().setNote(getProfileDataModel.getDetails().getMyNote());

                    Picasso.with(activity).load(AppConstants.USERIMAGE + getProfileDataModel.getDetails().getImage()).into(profile_image);
                    tv_setName.setText(App.getSinlton().getName());


                } else {
                    Toast.makeText(activity, "response error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    App.getAppPreference().SaveString(AppConstants.LATITUDE, String.valueOf(location.getLatitude()));
                                    App.getAppPreference().SaveString(AppConstants.LONGITUDE, String.valueOf(location.getLongitude()));

                                }
                            }
                        }
                );
            } else {
                Toast.makeText(activity, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();

            App.getAppPreference().SaveString(AppConstants.LATITUDE, String.valueOf(mLastLocation.getLatitude()));
            App.getAppPreference().SaveString(AppConstants.LONGITUDE, String.valueOf(mLastLocation.getLongitude()));

        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                activity,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    private void showPlan() {

        vmUserSetting.planShow(activity).observe(HomeActivity.this, new Observer<SubscribePlanPojo>() {
            @Override
            public void onChanged(SubscribePlanPojo subscribePlanPojo) {
                if (subscribePlanPojo.getSuccess().equalsIgnoreCase("1")) {
//                    Toast.makeText(activity, ""+subscribePlanPojo.getMessage(), Toast.LENGTH_SHORT).show();
                    planList = subscribePlanPojo.getDetails();

                } else {
                    Toast.makeText(activity, "" + subscribePlanPojo.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void purchagePlan(String planId) {

        vmUserSetting.planBuy(activity, userId, planId).observe(HomeActivity.this, new Observer<PurchagePlanPojo>() {
            @Override
            public void onChanged(PurchagePlanPojo purchagePlanPojo) {
                if (purchagePlanPojo.getSuccess().equalsIgnoreCase("1")) {
                    Toast.makeText(activity, "You Purchased this plan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_home:

                break;

            case R.id.nav_explore:
                startActivity(new Intent(activity, ExplorActivity.class));
                break;

            case R.id.nav_verification:

                break;

            case R.id.nav_setting:

                break;

            case R.id.nav_help:

                break;

            case R.id.nav_share:

                break;
            case R.id.nav_logout:

                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}