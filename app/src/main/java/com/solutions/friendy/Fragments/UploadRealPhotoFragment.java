package com.solutions.friendy.Fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.User;
import com.solutions.friendy.Adapters.AdapterPhotoDemo;
import com.solutions.friendy.App;
import com.solutions.friendy.MainActivity;
import com.solutions.friendy.Models.LoginRegisterPojo;
import com.solutions.friendy.Models.SocialLoginPojo;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;
import com.solutions.friendy.Retrofit.StringContract;
import com.solutions.friendy.ViewModel.VMRegisterUser;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static utils.MediaUtils.isGooglePhotosUri;
import static utils.Utils.getDataColumn;
import static utils.Utils.isDownloadsDocument;
import static utils.Utils.isExternalStorageDocument;
import static utils.Utils.isMediaDocument;


/**
 * A simple {@link Fragment} subclass.
 */
public class UploadRealPhotoFragment extends Fragment implements View.OnClickListener {

    private View view;
    private ImageView iv_back, iv_take_photo;
    private TextView tv_title;
    private Button btn_signup, btn_start;
    private LinearLayout ll_take_photo;
    public Uri resultUri;
    private RecyclerView rv_photoDemo;
    private AdapterPhotoDemo adapterPhotoDemo;
    private int[] realPhoto = {R.drawable.girl_1, R.drawable.girl_2, R.drawable.girl_4, R.drawable.girl_2};
    private int[] check_icon = {R.drawable.ic_check_circle_black_24dp, R.drawable.ic_cancel_black_24dp, R.drawable.ic_cancel_black_24dp, R.drawable.ic_cancel_black_24dp};

    private String genderStr, nameStr, dobStr, passwordStr, id, fb_social_id, fb_userName, fb_phone, fb_image, fb_email, fb_loginType;
    private String imagePath = "";
    private Boolean imageCheck = false;
    private File storeImage;

    private ProgressDialog progressDialog;

    Bitmap bitmap;
    private VMRegisterUser vmRegisterUser;


    public UploadRealPhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_upload_real_photo, container, false);

        vmRegisterUser = ViewModelProviders.of(UploadRealPhotoFragment.this).get(VMRegisterUser.class);

        nameStr = App.getSinlton().getName();
        dobStr = App.getSinlton().getDob();
        passwordStr = App.getSinlton().getPassword();
        genderStr = App.getSinlton().getGender();
        id = App.getAppPreference().GetString("id");

        findIds(view);
        Context context;
        progressDialog = new ProgressDialog(getActivity());
        setUp();

        if (!App.getAppPreference().GetString(AppConstants.REGISTER_TIME_UPLOAD_IMAGE).isEmpty()) {
            imageCheck = true;
            Glide.with(this).load(App.getAppPreference().GetString(AppConstants.REGISTER_TIME_UPLOAD_IMAGE)).into(iv_take_photo);
        }

        return view;
    }

    private void setUp() {
        adapterPhotoDemo = new AdapterPhotoDemo(getActivity(), realPhoto, check_icon, new AdapterPhotoDemo.Select() {
            @Override
            public void Choose(int position) {

            }
        });
        rv_photoDemo.setAdapter(adapterPhotoDemo);
    }

    private void findIds(View view) {

        iv_take_photo = view.findViewById(R.id.iv_take_photo);

        rv_photoDemo = view.findViewById(R.id.rv_photoDemo);

        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("Upload Your Real Photo");

        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        btn_start = view.findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);

        ll_take_photo = view.findViewById(R.id.ll_take_photo);
        ll_take_photo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;

            case R.id.btn_start:

                validation();

//                register();

                break;

            case R.id.ll_take_photo:
                imageCheck = true;
                selectImage();

//                CropImage.activity()
//                        .start(getContext(), this);
                break;
        }
    }

    private void validation() {

//        register();

        if (imagePath.equals("") || imagePath.equals(null)) {
            Toast.makeText(getActivity(), "Please Select image", Toast.LENGTH_SHORT).show();
        } else {
            register();
        }
    }


    private void register() {
        MultipartBody.Part body;

        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        RequestBody nameRequestBody = RequestBody.create(MediaType.parse("text/plain"), nameStr);
        RequestBody dobRequestBody = RequestBody.create(MediaType.parse("text/plain"), dobStr);
        RequestBody passwordRequestBody = RequestBody.create(MediaType.parse("text/plain"), passwordStr);
        RequestBody genderRequestBody = RequestBody.create(MediaType.parse("text/plain"), genderStr);
        RequestBody idRequestBody = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody latitudeRequestBody = RequestBody.create(MediaType.parse("text/plain"), "30.7333");
        RequestBody longitudeRequestBody = RequestBody.create(MediaType.parse("text/plain"), "76.7794");

//        RequestBody latitudeRequestBody = RequestBody.create(MediaType.parse("text/plain"), App.getAppPreference().GetString(AppConstants.LATITUDE));
//        RequestBody longitudeRequestBody = RequestBody.create(MediaType.parse("text/plain"), App.getAppPreference().GetString(AppConstants.LONGITUDE));


//        if (imageCheck) {
        storeImage = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), storeImage);
        body = MultipartBody.Part.createFormData("image", storeImage.getName(), requestFile);

//        }
//        else {
//            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
//            body = MultipartBody.Part.createFormData("image", "", requestFile);
//        }


        vmRegisterUser.registerResults(getActivity(), nameRequestBody, dobRequestBody, genderRequestBody, passwordRequestBody, idRequestBody, latitudeRequestBody, longitudeRequestBody, body).observe(UploadRealPhotoFragment.this, new Observer<LoginRegisterPojo>() {
            @Override
            public void onChanged(LoginRegisterPojo loginRegisterPojo) {
                if (loginRegisterPojo.getSuccess().equalsIgnoreCase("1")) {

                    progressDialog.dismiss();

                    App.getAppPreference().SaveString("newUser", "1");
                    App.getAppPreference().SaveString("name", loginRegisterPojo.getDetails().getName());
                    App.getAppPreference().SaveString("image", loginRegisterPojo.getDetails().getImage());

                    App.getAppPreference().saveUserDetails(AppConstants.REGISTER_DETAILS, loginRegisterPojo);
                    App.getAppPreference().SaveString(AppConstants.TOKEN, "1");
                    NavDirections navDirections = UploadRealPhotoFragmentDirections.actionUploadRealPhotoFragmentToHomePageFragment();
                    Navigation.findNavController(view).navigate(navDirections);

//                    User user = new User();
//                    user.setUid(id); // Replace with your uid for the user to be created.
//                    user.setName(nameStr); // Replace with the name of the user
//                    user.setAvatar(AppConstants.USERIMAGE+loginRegisterPojo.getDetails().getImage());
//
//                    CometChat.createUser(user, StringContract.AppDetails.API_KEY, new CometChat.CallbackListener<User>() {
//                        @Override
//                        public void onSuccess(User user) {
//                            progressDialog.dismiss();
//
//                            App.getAppPreference().SaveString(AppConstants.TOKEN,"1");
//                            NavDirections navDirections = UploadRealPhotoFragmentDirections.actionUploadRealPhotoFragmentToHomePageFragment();
//                            Navigation.findNavController(view).navigate(navDirections);
//
//                            Toast.makeText(getContext(), loginRegisterPojo.getMessage(), Toast.LENGTH_SHORT).show();
//
//                        }
//
//                        @Override
//                        public void onError(CometChatException e) {
//                            progressDialog.dismiss();
//                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });

                } else {
                    Toast.makeText(getContext(), loginRegisterPojo.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode != RESULT_CANCELED) {
//            switch (requestCode) {
//                case 1:
//                    if (resultCode == RESULT_OK && data != null) {
//
//                        resultUri = data.getData();
//
//                        if (resultUri != null) {
//                            imagePath = getRealPathFromURI(resultUri);
//                            App.getAppPreference().SaveString(AppConstants.REGISTER_TIME_UPLOAD_IMAGE,imagePath);
//                            Glide.with(this).load(App.getAppPreference().GetString(AppConstants.REGISTER_TIME_UPLOAD_IMAGE)).placeholder(R.drawable.add).into(iv_take_photo);
//                            rv_photoDemo.setVisibility(View.GONE);
//                            btn_start.setVisibility(View.VISIBLE);
//                        }
//
//                    }
//                    break;
//            }
//        }
//
//    }
//    public String getRealPathFromURI(Uri uri) {
//        String[] projection = { MediaStore.Images.Media.DATA };
//        @SuppressWarnings("deprecation")
//        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
//        int column_index = cursor
//                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }


//    private void selectImage() {
//        final CharSequence[] options = {"Choose from Gallery", "Cancel"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Choose your profile picture");
//
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//
//                if (options[item].equals("Choose from Gallery")) {
//                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(pickPhoto, 1);
//
//                } else if (options[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }

    private void selectImage() {
        final CharSequence[] options = {"Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                compressImage(data.getDataString());
            }
        }
    }

    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        imagePath = Base64.encodeToString(b, Base64.DEFAULT);
        return imagePath;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    //compressor
    public String compressImage(String imageUri) {
        Uri contentUri = Uri.parse(imageUri);
        String filePath = getRealPathFromURI(getActivity(), contentUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            //display
            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(new File(filename)));
            iv_take_photo.setImageBitmap(bitmap);
            imagePath = filename;
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("ERROR", "Error " + e.getMessage());
        }
        return filename;

    }

    //PATH

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "SureBet");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        Log.d("IMAGES", "" + uriSting);
        return uriSting;
    }

    //Image Compression Code
    private String getRealPathFromURI_(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getActivity().getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }

    }

    //PATH
    public static String getRealPathFromURI(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
}
