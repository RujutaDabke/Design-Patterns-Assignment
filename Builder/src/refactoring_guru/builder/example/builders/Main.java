package refactoring_guru.builder.example.builders;
/*** Builder interface defines all possible ways to configure a product.***/
interface builder {
    void setCarType(carType type);
    void setSeats(int seats);
    void setEngine(engine engine);
    void setEngine(CarBuilder.Engine engine);
    void setTransmission(transmission transmission);
    void setTripComputer(tripComputer tripComputer);
    void setGPSNavigator(gpsnavigator gpsNavigator);
}
/*** Concrete builders implement steps defined in the common interface.***/
abstract class CarBuilder implements builder {
    private carType type;
    private int seats;
    private Engine engine;
    private refactoring_guru.builder.example.builders.transmission transmission;
    private refactoring_guru.builder.example.builders.tripComputer tripComputer;
    private gpsnavigator gpsNavigator;
    public void setCarType(carType type) {
        this.type = type;
    }
    @Override
    public void setSeats(int seats) {
        this.seats = seats;
    }
    @Override
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
    @Override
    public void setTransmission(refactoring_guru.builder.example.builders.transmission transmission) {
        this.transmission = transmission;
    }
    @Override
    public void setTripComputer(refactoring_guru.builder.example.builders.tripComputer tripComputer) {
        this.tripComputer = tripComputer;
    }
    @Override
    public void setGPSNavigator(gpsnavigator gpsNavigator) {
        this.gpsNavigator = gpsNavigator;
    }
    public car getResult() {
        return new car(type, seats, engine, transmission, tripComputer, gpsNavigator);
    }
    class Engine extends refactoring_guru.builder.example.builders.engine {
        public Engine(double volume, double mileage) {
            super(volume, mileage);
        }
    }
}
/***Unlike other creational patterns, Builder can construct unrelated products, which don't have the common
 * interface. In this case we build a user manual for a car, using the same steps as we built a car. This allows to
 * produce manuals for specific car models, configured with different features.***/
abstract class CarManualBuilder implements Builder{
    private carType type;
    private int seats;
    private Engine engine;
    private Transmission transmission;
    private TripComputer tripComputer;
    private gpsnavigator gpsNavigator;
    @Override
    public void setCarType(carType type) {
        this.type = type;
    }
    @Override
    public void setSeats(int seats) {
        this.seats = seats;
    }
    @Override
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
    @Override
    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }
    @Override
    public void setTripComputer(TripComputer tripComputer) {
        this.tripComputer = tripComputer;
    }
    @Override
    public void setGPSNavigator(gpsnavigator gpsNavigator) {
        this.gpsNavigator = gpsNavigator;
    }
    public manual getResult() {
        return new manual(type, seats, engine, transmission, tripComputer, gpsNavigator);
    }
}
/***Car is a product class.***/
class car {
    private final refactoring_guru.builder.example.builders.carType carType;
    private final int seats;
    private final refactoring_guru.builder.example.builders.engine engine;
    private final refactoring_guru.builder.example.builders.transmission transmission;
    private final refactoring_guru.builder.example.builders.tripComputer tripComputer;
    private final gpsnavigator gpsNavigator;
    private double fuel = 0;
    public car(refactoring_guru.builder.example.builders.carType carType, int seats, refactoring_guru.builder.example.builders.engine engine, refactoring_guru.builder.example.builders.transmission transmission,
               refactoring_guru.builder.example.builders.tripComputer tripComputer, gpsnavigator gpsNavigator) {
        this.carType = carType;
        this.seats = seats;
        this.engine = engine;
        this.transmission = transmission;
        this.tripComputer = tripComputer;
        if (this.tripComputer != null) {
            this.tripComputer.setCar(this);
        }
        this.gpsNavigator = gpsNavigator;
    }
    public refactoring_guru.builder.example.builders.carType getCarType() {
        return carType;
    }
    public double getFuel() {
        return fuel;
    }
    public void setFuel(double fuel) {
        this.fuel = fuel;
    }
    public int getSeats() {
        return seats;
    }
    public refactoring_guru.builder.example.builders.engine getEngine() {
        return engine;
    }
    public refactoring_guru.builder.example.builders.transmission getTransmission() {
        return transmission;
    }
    public refactoring_guru.builder.example.builders.tripComputer getTripComputer() {
        return tripComputer;
    }
    public gpsnavigator getGpsNavigator() {
        return gpsNavigator;
    }
}
/**
 * Car manual is another product. Note that it does not have the same ancestor
 * as a Car. They are not related.
 */
class manual {
    private final refactoring_guru.builder.example.builders.carType carType;
    private final int seats;
    private final refactoring_guru.builder.example.builders.engine engine;
    private final refactoring_guru.builder.example.builders.transmission transmission;
    private final refactoring_guru.builder.example.builders.tripComputer tripComputer;
    private final gpsnavigator gpsNavigator;
    public manual(refactoring_guru.builder.example.builders.carType carType, int seats, refactoring_guru.builder.example.builders.engine engine,
                  refactoring_guru.builder.example.builders.transmission transmission,
                  refactoring_guru.builder.example.builders.tripComputer tripComputer,
                  gpsnavigator gpsNavigator) {
        this.carType = carType;
        this.seats = seats;
        this.engine = engine;
        this.transmission = transmission;
        this.tripComputer = tripComputer;
        this.gpsNavigator = gpsNavigator;
    }
    public String print() {
        String info = "";
        info += "Type of car: " + carType + "\n";
        info += "Count of seats: " + seats + "\n";
        info += "Engine: volume - " + engine.getVolume() + "; mileage - " + engine.getMileage() + "\n";
        info += "Transmission: " + transmission + "\n";
        if (this.tripComputer != null) {
            info += "Trip Computer: Functional" + "\n";
        } else {
            info += "Trip Computer: N/A" + "\n";
        }
        if (this.gpsNavigator != null) {
            info += "GPS Navigator: Functional" + "\n";
        } else {
            info += "GPS Navigator: N/A" + "\n";
        }
        return info;
    }
}
enum carType {
    CITY_CAR, SPORTS_CAR, SUV
}
/***Just another feature of a car.***/
class engine {
    private final double volume;
    private double mileage;
    private boolean started;
    public engine(double volume, double mileage) {
        this.volume = volume;
        this.mileage = mileage;
    }
    public void on() {
        started = true;
    }
    public void off() {
        started = false;
    }
    public boolean isStarted() {
        return started;
    }
    public void go(double mileage) {
        if (started) {
            this.mileage += mileage;
        } else {
            System.err.println("Cannot go(), you must start engine first!");
        }
    }
    public double getVolume() {
        return volume;
    }
    public double getMileage() {
        return mileage;
    }
}
/***Just another feature of a car.***/
class GPSNavigator {
    private String route;
    public GPSNavigator() {
        this.route = "221b, Baker Street, London  to Scotland Yard, 8-10 Broadway, London";
    }
    public GPSNavigator(String manualRoute) {
        this.route = manualRoute;
    }
    public String getRoute() {
        return route;
    }
}
/***Just another feature of a car.***/
enum Transmission {
    SINGLE_SPEED, MANUAL, AUTOMATIC, SEMI_AUTOMATIC
}
/***Just another feature of a car.***/
class TripComputer {
    private Car car;
    public void setCar(Car car) {
        this.car = car;
    }
    public void showFuelLevel() {
        System.out.println("Fuel level: " + car.getFuel());
    }
    public void showStatus() {
        if (this.car.getEngine().isStarted()) {
            System.out.println("Car is started");
        } else {
            System.out.println("Car isn't started");
        }
    }
}
/***Director defines the order of building steps. It works with a builder object through common Builder interface.
 *  Therefore it may not know what product is being built.***/
class Director {
    public void constructSportsCar(Builder builder) {
        builder.setCarType(CarType.SPORTS_CAR);
        builder.setSeats(2);
        builder.setEngine(new Engine(3.0, 0));
        builder.setTransmission(Transmission.SEMI_AUTOMATIC);
        builder.setTripComputer(new TripComputer());
        builder.setGPSNavigator(new GPSNavigator());
    }
    public void constructCityCar(Builder builder) {
        builder.setCarType(CarType.CITY_CAR);
        builder.setSeats(2);
        builder.setEngine(new Engine(1.2, 0));
        builder.setTransmission(Transmission.AUTOMATIC);
        builder.setTripComputer(new TripComputer());
        builder.setGPSNavigator(new GPSNavigator());
    }
    public void constructSUV(Builder builder) {
        builder.setCarType(CarType.SUV);
        builder.setSeats(4);
        builder.setEngine(new Engine(2.5, 0));
        builder.setTransmission(Transmission.MANUAL);
        builder.setGPSNavigator(new GPSNavigator());
    }
}
/**
 * Demo class. Everything comes together here.
 */
class Demo {
    public static void main(String[] args) {
        Director director = new Director();
        // Director gets the concrete builder object from the client
        // (application code). That's because application knows better which
        // builder to use to get a specific product.
        CarBuilder builder = new CarBuilder() {
            @Override
            public void setEngine(refactoring_guru.builder.example.builders.engine engine) {

            }
        };
        director.constructSportsCar((Builder) builder);

        // The final product is often retrieved from a builder object, since
        // Director is not aware and not dependent on concrete builders and
        // products.
        car car = builder.getResult();
        System.out.println("Car built:\n" + car.getCarType());
        CarManualBuilder manualBuilder = new CarManualBuilder() {
            @Override
            public void setGPSNavigator(Object o) {

            }
        };

        // Director may know several building recipes.
        director.constructSportsCar(manualBuilder);
        manual carManual = manualBuilder.getResult();
        System.out.println("\nCar manual built:\n" + carManual.print());
    }

}