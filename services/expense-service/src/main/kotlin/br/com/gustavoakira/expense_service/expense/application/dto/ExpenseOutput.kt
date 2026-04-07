package br.com.gustavoakira.expense_service.expense.application.dto

import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class ExpenseOutput(
    val id: UUID,
    val description: String,
    val amount: BigDecimal,
    val occurredAt: Instant,
)
