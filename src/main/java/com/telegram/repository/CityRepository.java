package com.telegram.repository;

import com.telegram.model.City;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

  Optional<City> findByName(String name);

  Optional<City> findById(Long id);

  @Override
  List<City> findAll();
}
