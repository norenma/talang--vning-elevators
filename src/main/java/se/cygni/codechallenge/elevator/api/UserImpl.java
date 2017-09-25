package se.cygni.codechallenge.elevator.api;

/**
 * @author markusanderssonnoren
 * @since 2017-09-22.
 */
public class UserImpl implements User {

    private final int id;
    private final int requestedFloor;


    public UserImpl(final int id, final int requestedFloor) {
        this.id = id;
        this.requestedFloor = requestedFloor;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getRequestedFloor() {
        return requestedFloor;
    }
}
