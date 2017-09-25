package se.cygni.codechallenge.elevator.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.cygni.codechallenge.elevator.api.Elevator;
import se.cygni.codechallenge.elevator.api.ElevatorControllerImpl;

import java.util.List;
import java.util.Optional;

/**
 * Rest Resource.
 *
 * @author Sven Wesley
 *
 */
@RestController
@RequestMapping("/rest/v1")
public final class ElevatorControllerEndPoints {

    @Autowired ElevatorControllerImpl elevatorController;
    /**
     * Ping service to test if we are alive.
     *
     * @return String pong
     */
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping() {

        return "pong";
    }

    @RequestMapping(value = "/snap", method = RequestMethod.GET)
    public List<Elevator> getElevators() {
        return this.elevatorController.getElevators();
    }

    @RequestMapping(value = "/request/{floor}", method = RequestMethod.POST)
    public Elevator requestElevator(@PathVariable() final int floor){
        System.out.println(floor);
        final Optional<Elevator> elevatorOptional = elevatorController.requestElevator(floor);
        elevatorOptional.ifPresent(elevator -> elevator.moveElevator(floor));
        return elevatorOptional.orElse(null);
    }
}
