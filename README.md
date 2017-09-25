## Elevator Competence exercise 

I implemented a simple algorithm that will select the closest free elevator. If there is no free elevator it will select any elevator that is max 3 floors away. If there is no such elevator the user needs to be put in a queue.

I also created a simple web-gui that can be viewed at localhost:8080 when the app is running. If I had more time I would have implemented this fully, so that users occupy the elevators. Right not it just finds an elevator. 

## Build And Run (as is)

As the project is, the Spring app can be started as seen below.

build and run the code with Maven

    mvn package
    mvn spring-boot:run

Then go to localhost:8080 to see a simple gui.

