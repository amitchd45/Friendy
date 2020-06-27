package com.solutions.friendy.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.solutions.friendy.Adapters.AdapterInterestUser_2;
import com.solutions.friendy.Adapters.CardStackAdapter;
import com.solutions.friendy.Adapters.ListPersonality_2Adapter;
import com.solutions.friendy.Adapters.VipSliderAdapter;
import com.solutions.friendy.Fragments.HomePageFragment;
import com.solutions.friendy.Models.GetSlideUserProfileModelClass;
import com.solutions.friendy.Models.GetSwipeItemsModelClass;
import com.solutions.friendy.Models.ImageStoreModel;
import com.solutions.friendy.Models.SubscribePlanPojo;
import com.solutions.friendy.R;
import com.solutions.friendy.ViewModel.SwipeItemsViewModelClass;
import com.solutions.friendy.ViewModel.VmReceiveData;
import com.solutions.friendy.ViewModel.VmUserSetting;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private TextView tv_my_privileges, iv_edit, tv_setName;
    private ImageView iv_menu, iv_back, tv_chat;
    private ImageView profile_image;
    //    private AdvanceDrawerLayout drawer;
    private DrawerLayout drawer;
    private NavigationView nav_view;
    private int cardPosition;
    private LayoutInflater layoutInflater;

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


    }
}