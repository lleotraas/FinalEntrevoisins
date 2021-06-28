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

public class DetailActivity extends AppCompatActivity {
    public static final String  TAG = "ShowNeighbourActivity";
    private int mNeighbourIndex;
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
        setContentView(R.layout.activity_detail);
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
                if(mApiService.getNeighbours().get(mNeighbourIndex).getFavourite()){
                    setImageNotFavourite();
                }else{
                    setImageFavourite();
                }
                mApiService.neighbourIsFavorite(mNeighbourIndex);
            }
        });
        Objects.requireNonNull(getSupportActionBar()).hide();

        //id is convert into integer and set
        setNeighbourIndex(mApiService.getNeighbourIndex(getIntent().getLongExtra("neighbour_id", 0)));

        //get neighbour informations and set to the view
        setNeighbourInformations();

        //verify if neighbour is favorite
        if(mApiService.getNeighbours().get(mNeighbourIndex).getFavourite()){
            setImageFavourite();
        }
    }


    /**
     * set all neighbour informations
     */
    private void setNeighbourInformations(){
        Log.d(TAG, "setImage: setting the image and name to widgets.");
        neighbourName.setText(mApiService.getNeighbours().get(mNeighbourIndex).getName());
        neighbourNameContent.setText(mApiService.getNeighbours().get(mNeighbourIndex).getName());
        Glide.with(this)
                .load(mApiService.getNeighbours().get(mNeighbourIndex).getAvatarUrl())
                .apply(RequestOptions.centerCropTransform())
                .into(neighbourAvatar);
        neighbourAboutMe.setText(mApiService.getNeighbours().get(mNeighbourIndex).getAboutMe());
        neighbourAdressTxt.setText(mApiService.getNeighbours().get(mNeighbourIndex).getAddress());
        neighbourPhoneNumberTxt.setText(mApiService.getNeighbours().get(mNeighbourIndex).getPhoneNumber());
        neighbourFacebookTxt.setText(mApiService.getNeighbours().get(mNeighbourIndex).getFacebook());
    }


    public int getNeighbourIndex() {
        return mNeighbourIndex;
    }

    public void setNeighbourIndex(int neighbourIndex){
        Log.d(TAG, "setNeighbourPosition: N°" + neighbourIndex);
        this.mNeighbourIndex = neighbourIndex;
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