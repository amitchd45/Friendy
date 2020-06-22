package com.solutions.friendy.Retrofit;

public class StringContract {

    public static class IntentStrings {

        public static final String INTENT_GROUP_ID = "INTENT_GROUP_ID";
        public static final String INTENT_GROUP_NAME = "INTENT_GROUP_NAME";
        public static final String INTENT_GROUP_ISOWNER = "INTENT_GROUP_ISOWNER";
        public static final String IMAGE_TYPE = "image/*";
        public static final String AUDIO_TYPE = "audio/*";
        public static final String DOCUMENT_TYPE = "*/*";
        public static final String[] EXTRA_MIME_TYPE = new String[]{"image/*", "video/*"};
        public static final String[] EXTRA_MIME_DOC=new String[]{"text/plane","text/html","application/pdf","application/msword","application/vnd.ms.excel", "application/mspowerpoint","application/zip"};
        public static final String INTENT_CHATROOM = "INTENT_CHATROOM";
        public static final String USER_ID = "uid";
        public static final String USER = "user";
        public static final String USER_NAME = "user_name";
        public static final String USER_AVATAR = "avatar";
        public static final String INCOMING = "incoming";
        public static final String OUTGOING = "outgoing";
        public static final String SESSION_ID = "sessionId";
        public static final String MEDIA_URL = "mediaurl";
        public static final String PROFILE_VIEW="profile_view";
        public static final String NAME = "name";
        public static final String ID = "id";
        public static final String AVATAR = "avatar";
        public static final String ISVIDEO ="isVideo";
        public static final String INTENT_SCOPE = "intent_scope";
        public static final String UID = "uid";
        public static final String USER_PROFILE_ID = "USER_PROFILE_ID";
    }

    public static class PreferenceString{
        public static final String BLOCKED_USERS="blocked_users";
    }

    public static class RequestCode {
        public static final int ADD_GALLERY = 1;
        public static final int ADD_DOCUMENT = 2;
        public static final int ADD_SOUND = 3;
        public static final int TAKE_PHOTO = 5;
        public static final int TAKE_VIDEO = 7;
        public static final int LEFT = 8;
        public static final int FILE_WRITE = 234;
        public static final int READ_STORAGE = 545;
    }

    public static class AppDetails{

        public static final String APP_ID  =  "193667bc4147bb7"; // your app_id
        public static final String API_KEY =  "4980acf5b110c85f60eddc94fc292992d1de797c"; // your api_key
        public static final String REGION = "us";  // your app region

    }

}
