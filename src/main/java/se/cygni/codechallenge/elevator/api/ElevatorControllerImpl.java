package se.cygni.codechallenge.elevator.api;

import org.springframework.stereotype.Component;
import se.cygni.codechallenge.elevator.resources.Constants;

import java.util.*;
import java.util.function.Predicate;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static java.util.Comparator.*;

/**
 * @author markusanderssonnoren
 * @since 2017-09-19.
 */
@Component
public class ElevatorControllerImpl implements ElevatorController, Observer {
    List<Elevator> elevators;
    Queue<User> queue;


    public ElevatorControllerImpl(final List<Elevator> elevators) {
        this.elevators = elevators;
        this.queue = new LinkedList<>();
        this.elevators.stream().forEach(elevator -> elevator.getElevatorFreeObservable().addObserver(this));
    }

    public ElevatorControllerImpl() {
        Elevator elevator1;
        Elevator elevator2;
        Elevator elevator3;
        Elevator elevator4;
        elevator1 = new ElevatorImpl(1, 3);
        elevator2 = new ElevatorImpl(1, 1);
        elevator3 = new ElevatorImpl(1, 7);
        elevator4 = new ElevatorImpl(1, 2);
        elevators = newArrayList(elevator1, elevator2, elevator3, elevator4);
        this.queue = new LinkedList<>();
        this.elevators.stream().forEach(elevator -> elevator.getElevatorFreeObservable().addObserver(this));

    }

    @Override
    public Optional<Elevator> requestElevator(final int toFloor) {
        final Elevator elvator = elevators.stream().filter(elevator -> !elevator.isBusy() && !elevator.isFull())
                .sorted(compareByDistanceToFloor(toFloor)).findFirst()
                        .orElse(elevators.stream().sorted(compareByDistanceToFloor(toFloor))
                                .filter(isNotFullOrTooFarAway(toFloor))
                                .findFirst().orElse(null));
        return Optional.ofNullable(elvator);
    }



    @Override
    public List<Elevator> getElevators() {
        return elevators;
    }

    @Override
    public void releaseElevator(final Elevator elevator) {

    }

    @Override
    public void addToQueue(User user) {
        this.queue.add(user);
    }

    @Override
    public void update(final Observable o, final Object arg) {
        final User user = this.queue.poll();
        requestElevator(user.getRequestedFloor());
    }

    private Predicate<Elevator> isNotFullOrTooFarAway(final int toFloor) {
        return elevator -> elevator.distanceTo(toFloor) <= Constants.MAX_DISTANCE_TO_INTERUPT && !elevator.isFull();
    }

    private Comparator<Elevator> compareByDistanceToFloor(final int toFloor) {
        return comparingInt(elevator -> elevator.distanceTo(toFloor));
    }

}
