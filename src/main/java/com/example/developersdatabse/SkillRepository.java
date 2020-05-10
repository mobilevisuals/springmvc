package com.example.developersdatabse;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    public List<Skill> findByLabel(String label);
}
