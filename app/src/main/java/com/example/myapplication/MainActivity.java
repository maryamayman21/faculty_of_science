package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageButton buttondrawerToggle;
    ImageButton profileButtoon;
    WebView webView;


    private static final String CHANNEL_ID = "10"; // Notification channel ID
    private static final int NOTIFICATION_ID = 10; // Notification ID
    private void openFacebookPage() {

        Uri uri = Uri.parse("https://web.facebook.com/profile.php?id=100083170368806&viewas=100000686899395");

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout = findViewById(R.id.darwer);
        navigationView = findViewById(R.id.navigationview);
        buttondrawerToggle = findViewById(R.id.dropdown_menu);
        profileButtoon = findViewById(R.id.person_button);
         webView= findViewById(R.id.webview);

        webView.loadUrl("https://science.asu.edu.eg/ar");
        Intent incomingIntent = getIntent();


        SharedPreferences preferences = getSharedPreferences("userdetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        String UserName =  preferences.getString("UserName", null); // getting String
        String UserEmail =  preferences.getString("UserEmail", null); // getting String


        View headerView = navigationView.getHeaderView(0);
        //get username
        TextView username = headerView.findViewById(R.id.user_name);
        username.setText(UserName);
        TextView usermail = headerView.findViewById(R.id.user_mail);
        usermail.setText(UserEmail);


        //method to open drawer menu
        buttondrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemID = item.getItemId();

                if (itemID == R.id.nav_calcGPA) {
                    //go to calc gpa activity
                    Intent calculateGPAIntent = new Intent(MainActivity.this, CalculateGPA.class);
                    startActivity(calculateGPAIntent);

                }

                if (itemID == R.id.nav_phone) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + "25687932105"));
                    startActivity(intent);

                }


                if (itemID == R.id.nav_email) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    String[] recipients = {"science@asu.edu.eg"};
                    intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Subject text here...");
                    intent.putExtra(Intent.EXTRA_TEXT, "Body of the content here...");
                    intent.putExtra(Intent.EXTRA_CC, recipients);
                    intent.setType("text/html");
                    intent.setPackage("com.google.android.gm");
                    startActivity(Intent.createChooser(intent, "Send mail"));

                }

                if (itemID == R.id.nav_facebook) {

                    openFacebookPage();
                }
                if (itemID ==R.id.nav_home){
                    webView.loadUrl("https://science.asu.edu.eg/ar");
                }
                if (itemID== R.id.nav_events) {
                    webView.loadUrl("https://science.asu.edu.eg/ar/events");
                }
               if (itemID== R.id.nav_news) {
                    webView.loadUrl("https://science.asu.edu.eg/ar/news");
                }



               // LOGOUT PART
                if (itemID==R.id.nav_logout){
                    SharedPreferences preferences = getSharedPreferences("userdetails", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.remove("isUserLogin");
                    editor.remove("UserName");
                    editor.remove("UserEmail");
                    editor.commit();

                    finish();

                    Intent intent = new Intent(MainActivity.this, login.class);
                    startActivity(intent);

                }




                drawerLayout.close();
                return false;

            }


        });



//        //notification part start ::: failed

//        checkWebpage checkWebpage = new checkWebpage();
//        checkWebpage.execute();
        
    }
//    private class checkWebpage extends AsyncTask<Void, Void, String> {
//        public String st;
//
//        @Override
//        protected String doInBackground(Void... Voids) {
//
//            try {
//                Document doc = Jsoup.connect("https://science.asu.edu.eg/ar").get();
//                Elements elemeents=doc.getElementsByClass("news-title");
//                Element e= elemeents.first();
//                st=e.ownText();
//                createNotificationA(st);
//            } catch (Exception ae) {
//                // Handle exception
//            }
//            return st;
//        }
//
//        //        @Override
//        protected void onPostExecute(String result) {
//            // tv1.setText(result);
//            createNotificationA(result);
//        }
//    }
//
//    private void createNotificationA(String notificationText) {
//        createChannel();
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle("News")
//                .setContentText(notificationText)
//                .setSmallIcon(R.drawable.ic_launcher_background);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        notificationManager.notify(NOTIFICATION_ID, builder.build());
//    }
//    private void createChannel() {
//        NotificationChannel notificationChannel = new NotificationChannel(
//                CHANNEL_ID,
//                "channelName",
//                NotificationManager.IMPORTANCE_DEFAULT);
//        notificationChannel.setDescription("Welcome Notification");
//        NotificationManager notificationManager = getSystemService(NotificationManager.class);
//        notificationManager.createNotificationChannel(notificationChannel);
//    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}