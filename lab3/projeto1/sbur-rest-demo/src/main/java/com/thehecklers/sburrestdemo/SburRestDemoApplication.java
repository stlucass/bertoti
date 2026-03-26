package com.thehecklers.sburrestdemo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class SburRestDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SburRestDemoApplication.class, args);
	}

}

@RestController
@RequestMapping("/motos")
class RestApiDemoController {
	private final List<Moto> motos = new ArrayList<>();

	public RestApiDemoController() {
		motos.addAll(List.of(
				new Moto("Honda", "CB 500F", 2023, 471, "Preta", 39500.00, "Naked"),
				new Moto("Yamaha", "MT-07", 2024, 689, "Cyan Storm", 46900.00, "Naked"),
				new Moto("Kawasaki", "Z900", 2023, 948, "Verde Metálico", 62000.00, "Naked"),
				new Moto("BMW", "R 1250 GS", 2024, 1254, "Triple Black", 122000.00, "Big Trail")
		));
	}

	@GetMapping
	Iterable<Moto> getMotos() {
		return motos;
	}

	@GetMapping("/{id}")
	Optional<Moto> getMotoById(@PathVariable String id) {
		for (Moto m : motos) {
			if (m.getId().equals(id)) {
				return Optional.of(m);
			}
		}

		return Optional.empty();
	}

	@PostMapping
	Moto postMoto(@RequestBody Moto moto) {
		motos.add(moto);
		return moto;
	}

	@PutMapping("/{id}")
	ResponseEntity<Moto> putMoto(@PathVariable String id,
								 @RequestBody Moto moto) {
		int motoIndex = -1;

		for (Moto m : motos) {
			if (m.getId().equals(id)) {
				motoIndex = motos.indexOf(m);
				motos.set(motoIndex, moto);
			}
		}

		return (motoIndex == -1) ?
				new ResponseEntity<>(postMoto(moto), HttpStatus.CREATED) :
				new ResponseEntity<>(moto, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	void deleteMoto(@PathVariable String id) {
		motos.removeIf(m -> m.getId().equals(id));
	}
}

class Moto {
	private final String id;
	private String brand;
	private String model;
	private int year;
	private int displacement;
	private String color;
	private double price;
	private String category;

	@JsonCreator
	public Moto(@JsonProperty("id") String id,
				@JsonProperty("brand") String brand,
				@JsonProperty("model") String model,
				@JsonProperty("year") int year,
				@JsonProperty("displacement") int displacement,
				@JsonProperty("color") String color,
				@JsonProperty("price") double price,
				@JsonProperty("category") String category) {
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.year = year;
		this.displacement = displacement;
		this.color = color;
		this.price = price;
		this.category = category;
	}

	public Moto(String brand, String model, int year, int displacement, String color, double price, String category) {
		this(UUID.randomUUID().toString(), brand, model, year, displacement, color, price, category);
	}

	public String getId() {
		return id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getDisplacement() {
		return displacement;
	}

	public void setDisplacement(int displacement) {
		this.displacement = displacement;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}