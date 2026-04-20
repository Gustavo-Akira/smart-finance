package br.com.gustavoakira.expense_service.expense.application.usecase.impl

import br.com.gustavoakira.expense_service.expense.application.dto.CreateExpenseCommand
import br.com.gustavoakira.expense_service.expense.application.dto.ExpenseOutput
import br.com.gustavoakira.expense_service.expense.application.usecase.CreateExpenseUseCase
import br.com.gustavoakira.expense_service.expense.domain.model.Expense
import br.com.gustavoakira.expense_service.expense.domain.model.ExpenseId
import br.com.gustavoakira.expense_service.expense.domain.repository.ExpenseRepository
import org.springframework.stereotype.Service

@Service
class CreateExpenseUseCaseImpl(
    val createExpenseRepository: ExpenseRepository
): CreateExpenseUseCase {
    override fun execute(input: CreateExpenseCommand): ExpenseOutput {
        val expense = Expense(
            id = ExpenseId.new(),
            description = input.description,
            amount = input.amount,
            category = input.category,
            occurredAt = input.occurredAt,
        )
        val savedExpense = createExpenseRepository.save(expense)
        return ExpenseOutput(savedExpense.id.value,savedExpense.description,savedExpense.amount,savedExpense.occurredAt)
    }
}