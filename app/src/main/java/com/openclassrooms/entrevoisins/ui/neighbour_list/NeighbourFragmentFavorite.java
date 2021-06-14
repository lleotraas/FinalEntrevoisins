package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class NeighbourFragmentFavorite extends Fragment {

    private static final String TAG = "FavoriteFragment";
    private NeighbourApiService mApiService;
    //private List<Neighbour> mNeighbours;
    private RecyclerView mRecyclerView;

    public static NeighbourFragmentFavorite newInstance(){
        NeighbourFragmentFavorite fragmentFavorite = new NeighbourFragmentFavorite();
        return fragmentFavorite;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
      //  mNeighbours = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView)view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;

    }

//    private void initList(){
//        for (int index = 0;index < mApiService.getNeighbours().size();index++){
//            if(mApiService.getNeighbours().get(index).getFavourite() == true){
//                Log.d(TAG, "initList: N°" + index + " added");
//                mNeighbours.add(mApiService.getNeighbours().get(index));
//            }
//        }
//        mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mNeighbours));
//    }
//
//    private void removeFavoriteList(){
//        Iterator<Neighbour> iterator = mNeighbours.iterator();
//        while(iterator.hasNext()){
//            Object object = iterator.next();
//            iterator.remove();
//        }
//    }



    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: started");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: resumed");
        mApiService.removeFavoriteList();
        mApiService.initFavoriteList();
        mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mApiService.getFavoriteNeighbours()));
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: stopped");
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onDeleteNeighbour(DeleteNeighbourEvent event) {
        mApiService.deleteNeighbour(event.neighbour);
        mApiService.removeFavoriteList();
        mApiService.initFavoriteList();
    }
}