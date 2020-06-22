package com.solutions.friendy.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import com.google.android.gms.common.api.GoogleApiClient;
import com.solutions.friendy.App;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {
    private ImageView img_logo;
    private GoogleApiClient googleApiClient;
    private View view;


    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_splash, container, false);

//        PackageInfo info;
//        try {
//            info = getActivity().getPackageManager().getPackageInfo("com.solutions.friendy", PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md;
//                md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                String something = new String(Base64.encode(md.digest(), 0));
//                //String something = new String(Base64.encodeBytes(md.digest()));
//                Log.d("hash key", something);
//            }
//        } catch (PackageManager.NameNotFoundException e1) {
//            Log.e("name not found", e1.toString());
//        } catch (NoSuchAlgorithmException e) {
//            Log.e("no such an algorithm", e.toString());
//        } catch (Exception e) {
//            Log.e("exception", e.toString());
//        }
//        init(view);
        initz(view);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                String check = App.getAppPreference().GetString(AppConstants.TOKEN);
//                if (check.equalsIgnoreCase("1")) {
//                    NavDirections navDirections = SplashFragmentDirections.actionSplashFragmentToHomePageFragment();
//                    Navigation.findNavController(view).navigate(navDirections);
//                } else {
//
//                    NavDirections navDirections = SplashFragmentDirections.actionSplashFragmentToLoginFragment();
//                    Navigation.findNavController(view).navigate(navDirections);
//                }
//            }
//        },200);

//        tv_text.stop();
//
//        animateImageView();

        return view;
    }

    private void initz(final View view) {
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(3000);
//                    if(App.getAppPreference().GetString(AppConstants.TOKEN).equalsIgnoreCase("1")){
//                        NavDirections directions = SplashFragmentDirections.actionSplashFragmentToHomePageFragment();
//                        Navigation.findNavController(view).navigate(directions);
//                    }else {
//                        NavDirections directions = SplashFragmentDirections.actionSplashFragmentToLoginFragment();
//                        Navigation.findNavController(view).navigate(directions);
//                    }
//
//                } catch (InterruptedException e) {
//
//
//                }
//            }
//        });
//        thread.start();
    }
//    private void init(View view) {
//        pv_circular_inout = view.findViewById(R.id.progress_pv_circular_inout);
//        img_logo = view.findViewById(R.id.img_logo);
//        tv_text = view.findViewById(R.id.tv_text);
//    }
//
//    private void animateImageView() {
//
//        ViewCompat.animate(img_logo)
//                .translationY(-250)
//                .setStartDelay(2)
//                .setDuration(1500).setInterpolator(
//                new DecelerateInterpolator(1.2f)).setListener(new ViewPropertyAnimatorListener() {
//            @Override
//            public void onAnimationStart(View view) {
//                tv_text.setVisibility(View.GONE);
//
//            }
//
//            @Override
//            public void onAnimationEnd(View view) {
//
//                tv_text.setVisibility(View.VISIBLE);
//                tv_text.restart();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        goToNextActivity();
//
//                    }
//                }, 500);
//
//
//            }
//
//            @Override
//            public void onAnimationCancel(View view) {
//
//            }
//        }).start();
//
//    }
//
//    private void goToNextActivity() {
//
//        tv_text.stop();
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                gotoScreen();
//            }
//        }, 2000);
//
//
//    }
//
//    private void gotoScreen() {
//
//                String check = App.getAppPreference().GetString(AppConstants.TOKEN);
//                if (check.equalsIgnoreCase("1")) {
//                    NavDirections navDirections = SplashFragmentDirections.actionSplashFragmentToHomePageFragment();
//                    NavHostFragment.findNavController(getParentFragment()).navigate(navDirections);
//                } else {
//
//                    NavDirections navDirections2 = SplashFragmentDirections.actionSplashFragmentToLoginFragment();
//                    NavHostFragment.findNavController(getParentFragment()).navigate(navDirections2);
//                }
//    }
//


    @Override
    public void onResume() {
        super.onResume();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    if(App.getAppPreference().GetString(AppConstants.TOKEN).equalsIgnoreCase("1")){
                        NavDirections directions = SplashFragmentDirections.actionSplashFragmentToHomePageFragment();
                        Navigation.findNavController(view).navigate(directions);
                    }else {
//                        NavDirections directions = SplashFragmentDirections.actionSplashFragmentToLoginFragment();
                        NavDirections directions = SplashFragmentDirections.actionSplashFragmentToProfileImageUploadFragment();
                        Navigation.findNavController(view).navigate(directions);
                    }

                } catch (InterruptedException e) {


                }
            }
        });
        thread.start();

    }
}
