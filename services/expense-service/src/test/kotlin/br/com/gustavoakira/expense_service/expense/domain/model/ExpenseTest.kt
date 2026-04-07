package br.com.gustavoakira.expense_service.expense.domain.model

import kotlin.test.Test
import kotlin.test.assertFailsWith
import java.math.BigDecimal
import java.time.Instant

class ExpenseTest {

    @Test
    fun `should not allow blank description`() {
        assertFailsWith<IllegalArgumentException> {
            Expense(
                id = ExpenseId.new(),
                description = "",
                amount = BigDecimal.ONE,
                occurredAt = Instant.now(),
            )
        }
    }
}
