package br.com.gustavoakira.expense_service.expense.application.dto

import java.math.BigDecimal
import java.time.Instant

data class CreateExpenseCommand(
    val description: String,
    val amount: BigDecimal,
    val occurredAt: Instant,
)
