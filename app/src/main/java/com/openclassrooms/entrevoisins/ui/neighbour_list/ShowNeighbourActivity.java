package com.openclassrooms.entrevoisins.ui.neighbour_list;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowNeighbourActivity extends AppCompatActivity {
    public static final String  TAG = "ShowNeighbourActivity";
    public int mNeighbourPosition;
    public NeighbourApiService mApiService;


    @BindView(R.id.activity_show_neighbour_return_ib)
    ImageButton returnIBtn;
    @BindView(R.id.activity_show_neighbour_name_txt)
    TextView neighbourName;
    @BindView(R.id.activity_show_neighbour_name_content_txt)
    TextView neighbourNameContent;
    @BindView(R.id.activity_show_neighbour_portrait_img)
    ImageView neighbourAvatar;
    @BindView(R.id.activity_show_neighbour_about_me_txt)
    TextView neighbourAboutMe;
    @BindView(R.id.activity_show_neighbour_adress_txt)
    TextView neighbourAdressTxt;
    @BindView(R.id.activity_show_neighbour_phone_txt)
    TextView neighbourPhoneNumberTxt;
    @BindView(R.id.activity_show_neighbour_facebook_txt)
    TextView neighbourFacebookTxt;
    @BindView(R.id.activity_show_neighbour_favorite_fab)
    FloatingActionButton favoriteNeighbourFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_neighbour);
        Log.d(TAG, "onCreate: started");
        mApiService = DI.getNeighbourApiService();
        ButterKnife.bind(this);

        returnIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        favoriteNeighbourFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mApiService.getNeighbours().get(mNeighbourPosition).getFavourite()){
                    Log.d(TAG, "onClick: isFavorite = " + mApiService.getNeighbours().get(mNeighbourPosition).getFavourite());
                    mApiService.getNeighbours().get(mNeighbourPosition).setFavourite(false);
                    setImageNotFavourite();
                }else{
                    Log.d(TAG, "onClick: isFavorite = " + mApiService.getNeighbours().get(mNeighbourPosition).getFavourite());
                    mApiService.getNeighbours().get(mNeighbourPosition).setFavourite(true);
                    setImageFavourite();
                }

            }
        });
        Objects.requireNonNull(getSupportActionBar()).hide();
        getIncomingIntent();
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents");

        if(getIntent().hasExtra("neighbour_id")){
            Log.d(TAG, "getIncomingIntent: found intent extras");

            long id = getIntent().getLongExtra("neighbour_id", 0) ;
            for(int index = 0;index < mApiService.getNeighbours().size();index++){
                if(id == mApiService.getNeighbours().get(index).getId()){
                    mNeighbourPosition = index;
                    Log.d(TAG, "getIncomingIntent: position = " + mNeighbourPosition);
                    String imageUrl = mApiService.getNeighbours().get(mNeighbourPosition).getAvatarUrl();
                    String imageName = mApiService.getNeighbours().get(mNeighbourPosition).getName();
                    String aboutMe = mApiService.getNeighbours().get(mNeighbourPosition).getAboutMe();
                    String address = mApiService.getNeighbours().get(mNeighbourPosition).getAddress();
                    String phoneNumber = mApiService.getNeighbours().get(mNeighbourPosition).getPhoneNumber();
                    boolean isFavourite = mApiService.getNeighbours().get(mNeighbourPosition).getFavourite();
                    String facebook = mApiService.getNeighbours().get(mNeighbourPosition).getFacebook();

                    setImage(imageUrl, imageName, aboutMe, address, phoneNumber, facebook);

                    setNeighbourPosition(mNeighbourPosition);
                    Log.d(TAG, "getIncomingIntent: isFavourite = " + isFavourite);
                    if(isFavourite){
                        setImageFavourite();
                    }else {
                        setImageNotFavourite();
                    }
                }
            }



        }
    }

    private void setImage(String imageUrl, String name, String aboutMe, String address, String phoneNumber, String facebook){
        Log.d(TAG, "setImage: setting the image and name to widgets.");
        neighbourName.setText(name);
        neighbourNameContent.setText(name);
        Glide.with(this)
                .load(imageUrl)
                .apply(RequestOptions.centerCropTransform())
                .into(neighbourAvatar);
        neighbourAboutMe.setText(aboutMe);
        neighbourAdressTxt.setText(address);
        neighbourPhoneNumberTxt.setText(phoneNumber);
        neighbourFacebookTxt.setText(facebook);

    }

    public void setNeighbourPosition(int neighbourPosition){
        Log.d(TAG, "setNeighbourPosition: N°" + neighbourPosition);
        this.mNeighbourPosition = neighbourPosition;
    }

    private void setImageNotFavourite(){
        Log.d(TAG, "setImage: notFavorite");
        Glide.with(this)
                .load(R.drawable.ic_star_border_white_24dp)
                .into(favoriteNeighbourFab);
    }

    private void setImageFavourite(){
        Log.d(TAG, "setImageFavourite: favorite");
        Glide.with(this)
                .load(R.drawable.ic_star_white_24dp)
                .into(favoriteNeighbourFab);
    }


}