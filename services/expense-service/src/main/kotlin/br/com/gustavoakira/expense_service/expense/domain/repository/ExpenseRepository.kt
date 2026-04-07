package br.com.gustavoakira.expense_service.expense.domain.repository

import br.com.gustavoakira.expense_service.expense.domain.model.Expense
import br.com.gustavoakira.expense_service.expense.domain.model.ExpenseId

interface ExpenseRepository {
    fun save(expense: Expense): Expense
    fun findById(id: ExpenseId): Expense?
}
