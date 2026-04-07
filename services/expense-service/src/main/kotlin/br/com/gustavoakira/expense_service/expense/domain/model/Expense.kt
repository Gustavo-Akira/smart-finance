package br.com.gustavoakira.expense_service.expense.domain.model

import br.com.gustavoakira.expense_service.shared.domain.AggregateRoot
import java.math.BigDecimal
import java.time.Instant

data class Expense(
    val id: ExpenseId,
    val description: String,
    val amount: BigDecimal,
    val occurredAt: Instant,
) : AggregateRoot {
    init {
        require(description.isNotBlank()) { "description must not be blank" }
        require(amount >= BigDecimal.ZERO) { "amount must not be negative" }
    }
}
