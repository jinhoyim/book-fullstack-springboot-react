package book.packt.fullstackspringbootreact.carapi;

import book.packt.fullstackspringbootreact.carapi.domain.Car;
import book.packt.fullstackspringbootreact.carapi.domain.CarRepository;
import book.packt.fullstackspringbootreact.carapi.domain.Owner;
import book.packt.fullstackspringbootreact.carapi.domain.OwnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class CarApiApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CarApiApplication.class);
    private final CarRepository carRepository;
    private final OwnerRepository ownerRepository;

    public CarApiApplication(CarRepository carRepository, OwnerRepository ownerRepository) {
        this.carRepository = carRepository;
        this.ownerRepository = ownerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(CarApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Owner owner1 = new Owner("John", "Doe");
        Owner owner2 = new Owner("Mary", "Smith");
        ownerRepository.saveAll(List.of(owner1, owner2));

        carRepository.save(new Car("Ford", "Mustang", "Red", "ADF-1121", 2023, 59000, owner1));
        carRepository.save(new Car("Nissan", "Leaf", "White", "SSJ-3002", 2020, 29000, owner2));
        carRepository.save(new Car("Toyota", "Prius", "Silver", "KKO-0212", 2022, 39000, owner2));

        for (Car car : carRepository.findAll()) {
            logger.info("brand: {}, model: {}", car.getBrand(), car.getModel());
        }
    }
}
