package com.example.skincancer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DetectActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

//    private static final int INPUT_SIZE = 224;
//    private static final int IMAGE_MEAN = 117;
//    private static final float IMAGE_STD = 1;
//    private static final String INPUT_NAME = "input";
//    private static final String OUTPUT_NAME = "output";
//
//    private static final String MODEL_FILE = "file:///android_asset/tensorflow_inception_graph.pb";
//    private static final String LABEL_FILE =
//            "file:///android_asset/imagenet_comp_graph_label_strings.txt";
    private static final int INPUT_SIZE = 299;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128;
    private static final String INPUT_NAME = "Mul";
    private static final String OUTPUT_NAME = "final_result";
    private static final String MODEL_FILE = "file:///android_asset/tensorflow_inception_graph.pb";
    private static final String LABEL_FILE =
            "file:///android_asset/imagenet_comp_graph_label_strings.txt";

    private Classifier classifier;
    private Executor executor = Executors.newSingleThreadExecutor();
    private TextView textViewResult;
    private Button  btnToggleCamera, btnDetectObject;
    private ImageView imageViewResult;
    private CameraView cameraView;
    private DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect);
        cameraView = (CameraView) findViewById(R.id.cameraView);
        imageViewResult = (ImageView) findViewById(R.id.imageViewResult);
        textViewResult = (TextView) findViewById(R.id.textViewResult);
        textViewResult.setMovementMethod(new ScrollingMovementMethod());

        btnToggleCamera = (Button) findViewById(R.id.btnToggleCamera);
        btnDetectObject = (Button) findViewById(R.id.btnDetectObject);

        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {

                Bitmap bitmap = cameraKitImage.getBitmap();

                bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);

                imageViewResult.setImageBitmap(bitmap);

                final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);
                String testresult = "";
                float precent = 0;
                System.out.println(results);

                if (results.size()==1){
                    if(results.get(0).getTitle().equals("melanoma")){

                        if(results.get(0).getConfidence()>0.7){
                            precent =results.get(0).getConfidence()*100;
                            testresult = precent +"% Probability Melanoma, FIND A DOCTOR!!";
                        }
                        else if(results.get(1).getConfidence()<0.7 && results.get(1).getConfidence()>=0.5){
                            precent =results.get(1).getConfidence()*100;
                            testresult = precent +"% Probability Melanoma, Need Care! ";
                        }
                        else {
                            precent =results.get(1).getConfidence()*100;
                            testresult = precent +"% Probability Melanoma, Your Skin Look Health~ ";
                        }

                    }
                    else {
                        if(results.get(0).getConfidence()<0.3){
                            precent =results.get(0).getConfidence()*100;
                            testresult = precent +"% Probability Melanoma, FIND A DOCTOR!!";
                        }
                        else if(results.get(0).getConfidence()>0.3 && results.get(0).getConfidence()<0.5){
                            precent =results.get(0).getConfidence()*100;
                            testresult = precent +"% Probability Melanoma, Need Care! ";
                        }
                        else {
                            precent =results.get(0).getConfidence()*100;
                            testresult = precent +"% Probability Melanoma, Your Skin Look Health~ ";
                        }

                    }

                }
                else{
                    if(results.get(1).getConfidence()>0.7){
                        precent =results.get(1).getConfidence()*100;
                        testresult = precent +"% Probability Melanoma, FIND A DOCTOR!!";
                    }
                    else if(results.get(1).getConfidence()<0.7 && results.get(1).getConfidence()>=0.5){
                        precent =results.get(1).getConfidence()*100;
                        testresult = precent +"% Probability Melanoma, Need Care! ";
                    }
                    else {
                        precent =results.get(1).getConfidence()*100;
                        testresult = precent +"% Probability Melanoma, Your Skin Look Health~ ";
                    }
                }
//                if(results.get(1).getConfidence()>0.7){
//                    precent =results.get(1).getConfidence()*100;
//                    testresult = precent +"% Probability Melanoma, FIND A DOCTOR!!";
//                }
//                else if(results.get(1).getConfidence()<0.7 && results.get(1).getConfidence()>=0.5){
//                    precent =results.get(1).getConfidence()*100;
//                    testresult = precent +"% Probability Melanoma, Need Care! ";
//                }
//                else {
//                    precent =results.get(1).getConfidence()*100;
//                    testresult = precent +"% Probability Melanoma, Your Skin Look Health~ ";
//                }
                textViewResult.setText(testresult);

            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });

        btnToggleCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.toggleFacing();
            }
        });

        btnDetectObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.captureImage();
            }
        });

        initTensorFlowAndLoadModel();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar );
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                classifier.close();
            }
        });
    }

    private void initTensorFlowAndLoadModel() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier = TensorFlowImageClassifier.create(
                            getAssets(),
                            MODEL_FILE,
                            LABEL_FILE,
                            INPUT_SIZE,
                            IMAGE_MEAN,
                            IMAGE_STD,
                            INPUT_NAME,
                            OUTPUT_NAME);
                    makeButtonVisible();
                } catch (final Exception e) {
                    throw new RuntimeException("Error initializing TensorFlow!", e);
                }
            }
        });
    }

    private void makeButtonVisible() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnDetectObject.setVisibility(View.VISIBLE);
            }
        });
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent home_intent = new Intent(DetectActivity.this, MainActivity.class);
                startActivity(home_intent);
                break;
            case R.id.nav_map:
                Intent map_intent = new Intent(DetectActivity.this, MapsActivity.class);
                startActivity(map_intent);
                break;
            case R.id.nav_video:
                Intent video_intent = new Intent(DetectActivity.this, YoutubeActivity.class);
                startActivity(video_intent);
                break;
            case R.id.nav_detect:
                Intent detect_intent = new Intent(DetectActivity.this, DetectActivity.class);
                startActivity(detect_intent);
                break;
            case R.id.nav_about:
                Intent about_intent = new Intent(this, AboutUs.class);
                startActivity(about_intent);
                Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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