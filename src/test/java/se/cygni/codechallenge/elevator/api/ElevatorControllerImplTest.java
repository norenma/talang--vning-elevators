package se.cygni.codechallenge.elevator.api;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.*;

/**
 * @author markusanderssonnoren
 * @since 2017-09-22.
 */
public class ElevatorControllerImplTest {

    private Elevator elevator1;
    private Elevator elevator2;
    private Elevator elevator3;
    private ArrayList<Elevator> elevators;
    private ElevatorController elevatorController;


    @Before
    public void setUp() throws Exception {
        elevator1 = new ElevatorImpl(1, 3);
        elevator2 = new ElevatorImpl(1, 1);
        elevator3 = new ElevatorImpl(1, 7);
        elevators = newArrayList(elevator1, elevator2, elevator3);
        elevatorController = new ElevatorControllerImpl(elevators);

    }

    @Test
    public void requestElevator() throws Exception {
        // should get the one that is closest

        ElevatorController elevatorController = new ElevatorControllerImpl(elevators);
        elevator1.moveElevator(1);
        Optional<Elevator> elevator = elevatorController.requestElevator(1);
        assertEquals(elevator.get(), elevator1);




    }

    @Test
    public void requestElevatorClosestElevator() {
        final Optional<Elevator> elevator = elevatorController.requestElevator(7);
        assertEquals(elevator.get(), elevator3);
    }
    @Test
    public void getElevators() throws Exception {
    }

    @Test
    public void releaseElevator() throws Exception {
    }

}