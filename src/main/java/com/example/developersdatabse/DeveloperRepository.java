package com.example.developersdatabse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {

}
