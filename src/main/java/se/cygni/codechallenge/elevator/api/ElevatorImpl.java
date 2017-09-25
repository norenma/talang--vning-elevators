package se.cygni.codechallenge.elevator.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.tomcat.util.bcel.Const;
import se.cygni.codechallenge.elevator.resources.Constants;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author markusanderssonnoren
 * @since 2017-09-19.
 */
@JsonIgnoreProperties(value = { "floorTicker", "execService", "elevatorFreeObservable" })
public class ElevatorImpl implements Elevator {


    private final int id;
    private int currentFloor;
    private int addressedFloor;
    private ScheduledExecutorService execService;
    private List<User> users;
    private final Observable elevatorFreeObservable;
    private ScheduledFuture<?> floorTicker;


    public Observable getElevatorFreeObservable() {
        return elevatorFreeObservable;
    }

    @Override
    public void addUser(final User user) {
        this.users.add(user);
    }



    public ElevatorImpl(final int id, final int currentFloor) {
        this.id = id;
        this.currentFloor = currentFloor;
        this.addressedFloor = currentFloor;
        this.users = newArrayList();
        this.elevatorFreeObservable = new Observable();
        this.execService = Executors.newScheduledThreadPool(5);

    }

    @Override
    public Direction getDirection() {
        if(currentFloor - addressedFloor < 0) return Direction.UP;
        else if(currentFloor - addressedFloor > 0) return Direction.DOWN;
        else return Direction.NONE;
    }

    @JsonProperty("addressedFloor")
    @Override
    public int getAddressedFloor() {
        return addressedFloor;
    }

    @JsonProperty("id")
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void moveElevator(final int toFloor) {
        this.addressedFloor = toFloor;
        if(floorTicker != null) {floorTicker.cancel(true);}
        floorTicker = execService.scheduleAtFixedRate(floorTick(toFloor), Constants.TIME_PER_FLOOR, Constants.TIME_PER_FLOOR, TimeUnit.MILLISECONDS);
    }

    private Runnable floorTick(final int toFloor) {
        return () -> {
            if (getDirection() == Direction.UP) currentFloor++;
            else if (getDirection() == Direction.DOWN) currentFloor--;
            if (currentFloor == addressedFloor) {
                System.out.println("Elevator is ready!");
                floorTicker.cancel(true);
            }
            removeUsers(toFloor);
        };
    }


    @Override
    public boolean isBusy() {
        return currentFloor != addressedFloor;
    }

    @JsonProperty("currentFloor")
    @Override
    public int currentFloor() {
        return currentFloor;
    }

    @Override
    public int distanceTo(final int toFloor) {
        return Math.abs(currentFloor - toFloor);
    }


    // TODO: the users are not really used atm. An elevator should keep track of the users that
    // are in it, and if it is full or not.
    @Override
    public boolean isFull() {
        return users.size() >= Constants.MAX_USERS;
    }

    private void removeUsers(final int toFloor) {
        users.removeAll(users.stream().filter(user -> user.getRequestedFloor() == toFloor).collect(Collectors.toList()));
    }


}
