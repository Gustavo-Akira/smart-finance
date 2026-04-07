package br.com.gustavoakira.expense_service.expense.domain.model

import java.util.UUID

@JvmInline
value class ExpenseId(val value: UUID) {
    companion object {
        fun new(): ExpenseId = ExpenseId(UUID.randomUUID())
    }
}
