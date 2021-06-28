package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void createFavoriteListWithSuccess(){
        //ARRANGE
        service.getNeighbours().get(0).setFavourite(true);
        //ACT

        //ASSERT
        assertEquals(1, service.createFavoriteList().size());
    }

    @Test
    public void getNeighbourIndexWithSuccess(){
        //ARRANGE
        Neighbour neighbour = service.getNeighbours().get(0);
        //ACT
        Neighbour neighbourExpected =  service.getNeighbours().get(service.getNeighbourIndex(1));
        //ASSERT
        assertEquals(neighbour, neighbourExpected);
    }

    @Test
    public void neighbourIsFavoriteWithSuccess(){
        //ARRANGE
        service.getNeighbours().get(0).setFavourite(true);
        //ACT
        service.neighbourIsFavorite(0);
        //ASSERT
        assertFalse(service.getNeighbours().get(0).getFavourite());
    }
}
