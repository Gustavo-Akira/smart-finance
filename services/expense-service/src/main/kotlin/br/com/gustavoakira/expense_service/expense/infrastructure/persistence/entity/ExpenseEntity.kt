package br.com.gustavoakira.expense_service.expense.infrastructure.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.math.BigDecimal
import java.time.Instant

@Entity
data class ExpenseEntity(
    @Id
    val id: String,
    val description: String,
    val amount: BigDecimal,
    val category: String,
    val occurredAt: Instant,
) {

}