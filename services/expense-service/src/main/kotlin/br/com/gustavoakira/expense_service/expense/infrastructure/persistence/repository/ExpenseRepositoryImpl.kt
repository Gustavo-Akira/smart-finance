package br.com.gustavoakira.expense_service.expense.infrastructure.persistence.repository

import br.com.gustavoakira.expense_service.expense.domain.model.Expense
import br.com.gustavoakira.expense_service.expense.domain.model.ExpenseId
import br.com.gustavoakira.expense_service.expense.domain.repository.ExpenseRepository
import br.com.gustavoakira.expense_service.expense.infrastructure.persistence.mapper.ExpenseMapper
import org.springframework.stereotype.Repository

@Repository
class ExpenseRepositoryImpl(
    private val repositoryImpl: SpringJpaExpenseRepositoryImpl,
) : ExpenseRepository {
    override fun save(expense: Expense): Expense {
        val expenseEntity = ExpenseMapper.toEntity(expense)
        return ExpenseMapper.toDomain(repositoryImpl.save(expenseEntity))
    }

    override fun findById(id: ExpenseId): Expense? {
        return repositoryImpl.findById(id.value.toString())
            .map(ExpenseMapper::toDomain)
            .orElse(null)
    }
}
