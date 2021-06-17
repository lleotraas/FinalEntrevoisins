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
    public List<Neighbour> favoriteNeighbours = new ArrayList<>();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    @Override
    public List<Neighbour> getFavoriteNeighbours() {
        return favoriteNeighbours;
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


    @Override
    public void initFavoriteList(List<Neighbour> neighbour) {
        for(int index = 0;index < neighbours.size();index++){
            if(neighbours.get(index).getFavourite()){
                neighbour.add(neighbours.get(index));
            }
        }
    }

    @Override
    public void removeFavoriteList(List<Neighbour> neighbour) {
        Iterator<Neighbour> iterator = neighbour.iterator();
        while(iterator.hasNext()){
            Object object = iterator.next();
            iterator.remove();
        }
    }
}
