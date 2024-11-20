package org.nure.fintracker.repository;

import org.nure.fintracker.model.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    List<Expense> findAllByUserAccountIdOrderByDateDesc(UUID userAccountId);

}
