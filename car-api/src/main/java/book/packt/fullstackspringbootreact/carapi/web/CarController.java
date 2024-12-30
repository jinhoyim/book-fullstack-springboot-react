package book.packt.fullstackspringbootreact.carapi.web;

import book.packt.fullstackspringbootreact.carapi.domain.Car;
import book.packt.fullstackspringbootreact.carapi.domain.CarRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {

    private final CarRepository carRepository;

    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping("/cars")
    public Iterable<Car> getAllCars() {
        return carRepository.findAll();
    }
}
