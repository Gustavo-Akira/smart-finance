package br.com.gustavoakira.expense_service.expense.infrastructure.persistence.mapper

import br.com.gustavoakira.expense_service.expense.domain.model.Expense
import br.com.gustavoakira.expense_service.expense.domain.model.ExpenseId
import br.com.gustavoakira.expense_service.expense.infrastructure.persistence.entity.ExpenseEntity
import java.util.UUID

object ExpenseMapper {
    fun toEntity(expense: Expense): ExpenseEntity =
        ExpenseEntity(
            id = expense.id.value.toString(),
            description = expense.description,
            amount = expense.amount,
            category = expense.category,
            occurredAt = expense.occurredAt,
        )

    fun toDomain(entity: ExpenseEntity): Expense =
        Expense(
            id = ExpenseId(UUID.fromString(entity.id)),
            description = entity.description,
            amount = entity.amount,
            category = entity.category,
            occurredAt = entity.occurredAt,
        )
}
