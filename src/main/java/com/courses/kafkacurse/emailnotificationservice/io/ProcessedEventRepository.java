package com.courses.kafkacurse.emailnotificationservice.io;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
Interface to use JPA operations
 */
@Repository
public interface ProcessedEventRepository extends JpaRepository<ProcessedEventRepository, Long> {
}
