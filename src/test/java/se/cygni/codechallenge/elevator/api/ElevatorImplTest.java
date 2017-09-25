package se.cygni.codechallenge.elevator.api;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author markusanderssonnoren
 * @since 2017-09-22.
 */
public class ElevatorImplTest {

    Elevator elevator = new ElevatorImpl(1, 0);


    @Test
    public void moveElevator() throws Exception {
        elevator.moveElevator(5);
        Thread.sleep(6000);
        elevator.moveElevator(2);
        Thread.sleep(3000);

    }


}