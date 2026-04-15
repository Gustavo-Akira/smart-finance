package br.com.gustavoakira.expense_service.expense.infrastructure.persistence.mapper

import br.com.gustavoakira.expense_service.expense.domain.model.Expense
import br.com.gustavoakira.expense_service.expense.domain.model.ExpenseId
import br.com.gustavoakira.expense_service.expense.infrastructure.persistence.entity.ExpenseEntity
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class ExpenseMapperTest {

    @Test
    fun `should map expense domain to entity`() {
        val id = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
        val occurredAt = Instant.parse("2026-04-15T12:30:00Z")
        val expense = Expense(
            id = ExpenseId(id),
            description = "Lunch",
            amount = BigDecimal("42.50"),
            category = "Food",
            occurredAt = occurredAt,
        )

        val entity = ExpenseMapper.toEntity(expense)

        assertEquals(id.toString(), entity.id)
        assertEquals("Lunch", entity.description)
        assertEquals(BigDecimal("42.50"), entity.amount)
        assertEquals("Food", entity.category)
        assertEquals(occurredAt, entity.occurredAt)
    }

    @Test
    fun `should map expense entity to domain`() {
        val id = "123e4567-e89b-12d3-a456-426614174001"
        val occurredAt = Instant.parse("2026-04-14T09:15:00Z")
        val entity = ExpenseEntity(
            id = id,
            description = "Uber",
            amount = BigDecimal("18.90"),
            category = "Transport",
            occurredAt = occurredAt,
        )

        val expense = ExpenseMapper.toDomain(entity)

        assertEquals(ExpenseId(UUID.fromString(id)), expense.id)
        assertEquals("Uber", expense.description)
        assertEquals(BigDecimal("18.90"), expense.amount)
        assertEquals("Transport", expense.category)
        assertEquals(occurredAt, expense.occurredAt)
    }
}
