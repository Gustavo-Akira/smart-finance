package br.com.gustavoakira.expense_service.expense.application.usecase.impl

import br.com.gustavoakira.expense_service.expense.application.dto.CreateExpenseCommand
import br.com.gustavoakira.expense_service.expense.domain.model.Expense
import br.com.gustavoakira.expense_service.expense.domain.model.ExpenseId
import br.com.gustavoakira.expense_service.expense.domain.repository.ExpenseRepository
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNotEquals

class CreateExpenseUseCaseImplTest {

    @Test
    fun `should create expense, persist it, and return saved output`() {
        val command = CreateExpenseCommand(
            description = "Lunch",
            amount = BigDecimal("42.50"),
            category = "Food",
            occurredAt = Instant.parse("2026-04-20T12:30:00Z"),
        )
        val persistedExpenseId = ExpenseId(UUID.fromString("123e4567-e89b-12d3-a456-426614174100"))
        val repository = RecordingExpenseRepository { expense ->
            expense.copy(id = persistedExpenseId)
        }
        val useCase = CreateExpenseUseCaseImpl(repository)

        val output = useCase.execute(command)

        val capturedExpense = assertNotNull(repository.savedExpense)
        assertEquals("Lunch", capturedExpense.description)
        assertEquals(BigDecimal("42.50"), capturedExpense.amount)
        assertEquals("Food", capturedExpense.category)
        assertEquals(Instant.parse("2026-04-20T12:30:00Z"), capturedExpense.occurredAt)
        assertNotEquals(persistedExpenseId, capturedExpense.id)
        assertEquals(1, repository.saveCalls)
        assertEquals(persistedExpenseId.value, output.id)
        assertEquals("Lunch", output.description)
        assertEquals(BigDecimal("42.50"), output.amount)
        assertEquals(Instant.parse("2026-04-20T12:30:00Z"), output.occurredAt)
    }

    @Test
    fun `should not save expense when description is blank`() {
        val command = CreateExpenseCommand(
            description = "   ",
            amount = BigDecimal("42.50"),
            category = "Food",
            occurredAt = Instant.parse("2026-04-20T12:30:00Z"),
        )
        val repository = RecordingExpenseRepository()
        val useCase = CreateExpenseUseCaseImpl(repository)

        val exception = assertFailsWith<IllegalArgumentException> {
            useCase.execute(command)
        }

        assertEquals("description must not be blank", exception.message)
        assertEquals(0, repository.saveCalls)
    }

    @Test
    fun `should not save expense when amount is negative`() {
        val command = CreateExpenseCommand(
            description = "Taxi",
            amount = BigDecimal("-10.00"),
            category = "Transport",
            occurredAt = Instant.parse("2026-04-20T18:45:00Z"),
        )
        val repository = RecordingExpenseRepository()
        val useCase = CreateExpenseUseCaseImpl(repository)

        val exception = assertFailsWith<IllegalArgumentException> {
            useCase.execute(command)
        }

        assertEquals("amount must not be negative", exception.message)
        assertEquals(0, repository.saveCalls)
    }

    private class RecordingExpenseRepository(
        private val saveHandler: (Expense) -> Expense = { it },
    ) : ExpenseRepository {
        var saveCalls: Int = 0
            private set
        var savedExpense: Expense? = null
            private set

        override fun save(expense: Expense): Expense {
            saveCalls++
            savedExpense = expense
            return saveHandler(expense)
        }

        override fun findById(id: ExpenseId): Expense? = error("Unexpected findById call")
    }
}
