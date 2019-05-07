package com.example.skincancer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static android.os.Environment.getExternalStoragePublicDirectory;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    ImageView image;
    Integer REQUEST_CAMERA=1, SELECT_FILE=0, REQUEST_MAP=2;
    Button btnCamera, btnGallery, btnMap, btnVideo, btnDetectObject;
    File photoFile =null;
    String pathtoFile;
    private DrawerLayout drawer;
    NavigationView navigationView;
    ImageButton wiki;
    private static final int INPUT_SIZE = 224;
    private static final int IMAGE_MEAN = 117;
    private static final float IMAGE_STD = 1;
    private static final String INPUT_NAME = "input";
    private static final String OUTPUT_NAME = "output";

    private static final String MODEL_FILE = "file:///android_asset/tensorflow_inception_graph.pb";
    private static final String LABEL_FILE =
            "file:///android_asset/imagenet_comp_graph_label_strings.txt";

    private Classifier classifier;
    private Executor executor = Executors.newSingleThreadExecutor();
    private FirebaseAnalytics mFirebaseAnalytics;
    private  static final String TAG = "MainActivity";
    private static final int ERROR_REQUEST=9001;
    private TextView textViewResult;
    private ProgressDialog pd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pd=new ProgressDialog(MainActivity.this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setContentView(R.layout.activity_main);
//        image = (ImageView) findViewById(R.id.image);
//        btnCamera =(Button) findViewById(R.id.btnCamera);
//        if(Build.VERSION.SDK_INT>23){
//            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CAMERA);
//        }
//        btnVideo =(Button) findViewById(R.id.btnVideo);
//        btnVideo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, YoutubeActivity.class);
//                startActivity(intent);
//            }
//        });
//        btnCamera.setOnClickListener(this);
//        btnGallery=(Button) findViewById(R.id.btnGallery);
//        btnGallery.setOnClickListener(this);
//        btnMap=(Button) findViewById(R.id.btnMap);
//        btnMap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
//                startActivity(intent);
//            }
//        });
        btnDetectObject=(Button) findViewById(R.id.btnDetectObject);
        btnDetectObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetectActivity.class);
                startActivity(intent);
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar );
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        wiki = (ImageButton) findViewById(R.id.wiki);
        wiki.setOnClickListener(this);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent home_intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(home_intent);
                break;
            case R.id.nav_map:
                Intent map_intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(map_intent);
                break;
            case R.id.nav_video:
                Intent video_intent = new Intent(MainActivity.this, YoutubeActivity.class);
                startActivity(video_intent);
                break;
            case R.id.nav_detect:
                Intent detect_intent = new Intent(MainActivity.this, DetectActivity.class);
                startActivity(detect_intent);
                break;
            case R.id.nav_about:
                Intent about_intent = new Intent(MainActivity.this, AboutUs.class);
                startActivity(about_intent);
                Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void onClick(View view){
        Intent i = new Intent(view.getContext(), WebViewActivity.class);
        switch (view.getId()){
//            case R.id.btnCamera:
//                Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if(iCamera.resolveActivity(getPackageManager())!=null){
//
//                    photoFile = createPhotoFile();
//                    if(photoFile!=null){
//                        pathtoFile = photoFile.getAbsolutePath();
//                        Uri photoURI = FileProvider.getUriForFile(MainActivity.this,"com.example.skincancer.fileprovider",photoFile);
//                        iCamera.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
//                        startActivityForResult(iCamera,REQUEST_CAMERA);
//
//                    }
//                }
//                break;
//            case R.id.btnGallery:
//                Intent iGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                iGallery.setType("image/*");
//                startActivityForResult(iGallery,SELECT_FILE);
//                break;

            case R.id.wiki:{
                i.putExtra("link", "https://en.wikipedia.org/wiki/Skin_cancer");
                break;
            }


//            case R.id.map:
//                Intent intent = new Intent(MainActivity.this,MapsActivity.class);
//                startActivity(intent);

        }
        startActivity(i);


    }

//    private void openMap() {
//        Intent intent = new Intent(MainActivity.this,MapsActivity.class);
//        startActivity(intent);
//    }

    private File createPhotoFile() {
        String name =new SimpleDateFormat("yyyyMMDD_HHmmss").format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image =null;
        try {
                image =File.createTempFile(name,".jpg", storageDir);

        }catch (IOException e) {
            Log.d("mylog", "Excep : " + e.toString());
        }
        return image;
    }


    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        if(resultCode== Activity.RESULT_OK){

            if(requestCode==REQUEST_CAMERA){
                Bitmap bitmap = BitmapFactory.decodeFile(pathtoFile);
                image.setImageBitmap(bitmap);
//                Bitmap bitmap = (Bitmap)data.getExtras().get("data");
//                image.setImageBitmap(bitmap);


            }else if(requestCode==SELECT_FILE){

                Uri uri = data.getData();
                Bitmap bitmap = null;
                try{
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    image.setImageBitmap(bitmap);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}



