package br.com.gustavoakira.expense_service.expense.infrastructure.persistence.repository

import br.com.gustavoakira.expense_service.expense.infrastructure.persistence.entity.ExpenseEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SpringJpaExpenseRepositoryImpl: JpaRepository<ExpenseEntity, String> {
}