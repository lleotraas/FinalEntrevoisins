package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> createFavoriteList() {
        List<Neighbour> favoriteNeighbours = new ArrayList<>();
        for(int index = 0;index < neighbours.size();index++){
            if(neighbours.get(index).getFavourite()){
                favoriteNeighbours.add(neighbours.get(index));
            }
        }
        return favoriteNeighbours;
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public int getNeighbourIndex(long id) {
        int neighbourIndex = 0;
        for (int index = 0;index < getNeighbours().size();index++){
            if (id == getNeighbours().get(index).getId()){
                neighbourIndex = index;
            }
        }
        return neighbourIndex;
    }

    /**
     *
     * @param index
     */
    @Override
    public void neighbourIsFavorite(int index) {
        getNeighbours().get(index).setFavourite(!getNeighbours().get(index).getFavourite());
    }
}
