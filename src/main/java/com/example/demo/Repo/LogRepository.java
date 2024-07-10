package com.example.demo.Repo;



import com.example.demo.Entity.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Logs, Long> {
    // Define custom query methods if needed
}

