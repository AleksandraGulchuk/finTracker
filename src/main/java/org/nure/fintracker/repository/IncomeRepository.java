package org.nure.fintracker.repository;

import org.nure.fintracker.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IncomeRepository extends JpaRepository<Income, UUID> {

    List<Income> findAllByUserAccountIdOrderByDateDesc(UUID userAccountId);

    Income save(Income income);

}
