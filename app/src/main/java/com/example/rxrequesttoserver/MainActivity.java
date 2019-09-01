package com.example.rxrequesttoserver;

import android.net.Uri;
import android.support.v4.os.ConfigurationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.rxrequesttoserver.model.GetImages;
import com.example.rxrequesttoserver.network.Service;
import com.example.rxrequesttoserver.network.ServiceProvider;
import com.example.rxrequesttoserver.utility.ClientConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    SimpleDraweeView img_home_whats_up,img_home_video,img_home_polls;

    CompositeDisposable disposable;
    ServiceProvider provider = null;

    int activeSurveys;
    int balance;
    int lotteryDays;
    int score;
    String newsUrl = "";
    String surveyUrl = "";
    String videoUrl = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);        // initialize fresco here
        setContentView(R.layout.activity_main);


        provider = new ServiceProvider(this);
        disposable = new CompositeDisposable();



        img_home_whats_up=findViewById(R.id.img_home_whats_up);
        img_home_video=findViewById(R.id.img_home_video);
        img_home_polls=findViewById(R.id.img_home_polls);
        
        
        getImages();
        

    }

    private void getImages() {
        Service service = new ServiceProvider(MainActivity.this).getmService();
        disposable.add(service.getImages(ClientConfig.API_V2) //data we send to server
                .subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribeWith(new DisposableSingleObserver<GetImages>() {

                    @Override
                    public void onSuccess(GetImages result) {
                        if (result != null) {

                            activeSurveys = result.getImagesDetail.getActiveSurveys();
                            balance = result.getImagesDetail.getBalance();
                            lotteryDays = result.getImagesDetail.getLotteryDays();
                            score = result.getImagesDetail.getScore();
                            newsUrl = result.getImagesDetail.getNews();
                            surveyUrl = result.getImagesDetail.getSurvey();
                            videoUrl = result.getImagesDetail.getVideo();

                            setImages(newsUrl, surveyUrl, videoUrl);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void setImages(String newsUrl, String surveyUrl, String videoUrl) {
        //loading image from url
        Uri uriNewsUrl = Uri.parse(newsUrl);
        Uri uriVideoUrl = Uri.parse(videoUrl);
        Uri uriSurveyUrl = Uri.parse(surveyUrl);

        img_home_whats_up.setImageURI(uriNewsUrl);
        img_home_video.setImageURI(uriVideoUrl);
        img_home_polls.setImageURI(uriSurveyUrl);
    }

}
