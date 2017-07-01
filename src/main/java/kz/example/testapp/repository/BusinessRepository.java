package kz.example.testapp.repository;

import kz.example.testapp.model.Business;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Integer> {

    Page<Business> findByDescriptionContaining(String description, Pageable pageable);

    Page<Business> findByBusinessStatus(Business.BusinessStatus businessStatus, Pageable pageable);

}
