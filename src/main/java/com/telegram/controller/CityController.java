package com.telegram.controller;

import com.telegram.exception.CityNotFoundException;
import com.telegram.model.City;
import com.telegram.repository.CityRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CityController {

  private final CityRepository repository;

  @GetMapping("/cities")
  List<City> getAll() {
    log.info("Запрос на получение всех городов");
    return repository.findAll();
  }

  @GetMapping("/city/{id}")
  City getById(@PathVariable Long id) {
    log.info("Запрос на получение города с id - " + id);
    return repository.findById(id)
        .orElseThrow(() -> new CityNotFoundException("City with id " + id + " not found"));
  }

  @PostMapping("/city")
  City save(@RequestBody City city) {
    log.info("Запрос на сохранение города");
    return repository.save(city);
  }

  @PutMapping("/city/{id}")
  City replaceCity(@RequestBody City newCity, @PathVariable Long id) {
    log.info("Запрос на изменение данных города");
    return repository.findById(id).map(existCity -> {
      existCity.setName(newCity.getName());
      existCity.setInformation(newCity.getInformation());
      return repository.save(existCity);
    }).orElseGet(() -> {
      log.info("Город с таким id не найден, сохранение нового города");
      return repository.save(newCity);
    });
  }

  @DeleteMapping("/city/{id}")
  void delete(@PathVariable Long id) {
    if (repository.findById(id).isEmpty()) {
      throw new CityNotFoundException("City with id " + id + " not found");
    }
    log.info("Запрос на удаление города");
    repository.deleteById(id);
  }

  @Autowired
  public CityController(CityRepository repository) {
    this.repository = repository;
  }
}
