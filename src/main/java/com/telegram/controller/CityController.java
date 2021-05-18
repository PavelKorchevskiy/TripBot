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

  private final CityRepository cityRepository;

  @GetMapping("/cities")
  List<City> getAll() {
    log.info("Запрос на получение всех городов");
    return cityRepository.findAll();
  }

  @GetMapping("/city/{id}")
  City getById(@PathVariable Long id) {
    log.info("Запрос на получение города с id - " + id);
    return cityRepository.findById(id)
        .orElseThrow(() -> new CityNotFoundException("City with id " + id + " not found"));
  }

  @PostMapping("/city")
  City save(@RequestBody City city) {
    log.info("Запрос на сохранение города");
    return cityRepository.save(city);
  }

  @PutMapping("/city/{id}")
  City replaceCity(@RequestBody City newCity, @PathVariable Long id) {
    log.info("Запрос на изменение данных города");
    return cityRepository.findById(id).map(city -> {
      city.setName(newCity.getName());
      city.setInformation(newCity.getInformation());
      return cityRepository.save(city);
    }).orElseGet(() -> {
      log.info("Город с таким id не найден, сохранение нового города");
      return cityRepository.save(newCity);});
  }

  @DeleteMapping("/city/{id}")
  void delete(@PathVariable Long id) {
    log.info("Запрос на удаление города");
    cityRepository.deleteById(id);
  }

  @Autowired
  public CityController(CityRepository cityRepository) {
    this.cityRepository = cityRepository;
  }
}
