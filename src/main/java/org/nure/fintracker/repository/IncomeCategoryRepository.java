package org.nure.fintracker.repository;

import org.nure.fintracker.entity.IncomeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IncomeCategoryRepository extends JpaRepository<IncomeCategory, UUID> {

    List<IncomeCategory> findAll();

}
