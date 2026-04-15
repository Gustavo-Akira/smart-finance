package br.com.gustavoakira.expense_service.expense.infrastructure.persistence.repository

import br.com.gustavoakira.expense_service.expense.domain.model.Expense
import br.com.gustavoakira.expense_service.expense.domain.model.ExpenseId
import br.com.gustavoakira.expense_service.expense.infrastructure.persistence.entity.ExpenseEntity
import java.lang.reflect.Proxy
import java.math.BigDecimal
import java.time.Instant
import java.util.Optional
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ExpenseRepositoryImplTest {

    @Test
    fun `should save expense through jpa repository and map result back to domain`() {
        val inputExpense = Expense(
            id = ExpenseId(UUID.fromString("123e4567-e89b-12d3-a456-426614174010")),
            description = "Gym",
            amount = BigDecimal("99.90"),
            category = "Health",
            occurredAt = Instant.parse("2026-04-15T10:00:00Z"),
        )
        val persistedEntity = ExpenseEntity(
            id = "123e4567-e89b-12d3-a456-426614174011",
            description = "Gym",
            amount = BigDecimal("99.90"),
            category = "Health",
            occurredAt = Instant.parse("2026-04-15T10:00:00Z"),
        )
        var capturedEntity: ExpenseEntity? = null
        val jpaRepository = fakeJpaRepository(
            saveHandler = {
                capturedEntity = it
                persistedEntity
            },
        )

        val repository = ExpenseRepositoryImpl(jpaRepository)

        val savedExpense = repository.save(inputExpense)

        assertEquals(inputExpense.id.value.toString(), capturedEntity?.id)
        assertEquals("Gym", capturedEntity?.description)
        assertEquals(BigDecimal("99.90"), capturedEntity?.amount)
        assertEquals("Health", capturedEntity?.category)
        assertEquals(Instant.parse("2026-04-15T10:00:00Z"), capturedEntity?.occurredAt)
        assertEquals(ExpenseId(UUID.fromString(persistedEntity.id)), savedExpense.id)
        assertEquals("Gym", savedExpense.description)
        assertEquals(BigDecimal("99.90"), savedExpense.amount)
        assertEquals("Health", savedExpense.category)
        assertEquals(Instant.parse("2026-04-15T10:00:00Z"), savedExpense.occurredAt)
    }

    @Test
    fun `should find expense by id when entity exists`() {
        val id = ExpenseId(UUID.fromString("123e4567-e89b-12d3-a456-426614174020"))
        var capturedId: String? = null
        val jpaRepository = fakeJpaRepository(
            findByIdHandler = {
                capturedId = it
                Optional.of(
                    ExpenseEntity(
                        id = it,
                        description = "Market",
                        amount = BigDecimal("150.75"),
                        category = "Food",
                        occurredAt = Instant.parse("2026-04-13T18:00:00Z"),
                    ),
                )
            },
        )

        val repository = ExpenseRepositoryImpl(jpaRepository)

        val expense = repository.findById(id)

        assertEquals(id.value.toString(), capturedId)
        assertEquals(id, expense?.id)
        assertEquals("Market", expense?.description)
        assertEquals(BigDecimal("150.75"), expense?.amount)
        assertEquals("Food", expense?.category)
        assertEquals(Instant.parse("2026-04-13T18:00:00Z"), expense?.occurredAt)
    }

    @Test
    fun `should return null when expense is not found`() {
        val id = ExpenseId(UUID.fromString("123e4567-e89b-12d3-a456-426614174030"))
        var capturedId: String? = null
        val jpaRepository = fakeJpaRepository(
            findByIdHandler = {
                capturedId = it
                Optional.empty()
            },
        )

        val repository = ExpenseRepositoryImpl(jpaRepository)

        val expense = repository.findById(id)

        assertEquals(id.value.toString(), capturedId)
        assertNull(expense)
    }

    private fun fakeJpaRepository(
        saveHandler: (ExpenseEntity) -> ExpenseEntity = { error("Unexpected save call") },
        findByIdHandler: (String) -> Optional<ExpenseEntity> = { error("Unexpected findById call") },
    ): SpringJpaExpenseRepositoryImpl =
        Proxy.newProxyInstance(
            SpringJpaExpenseRepositoryImpl::class.java.classLoader,
            arrayOf(SpringJpaExpenseRepositoryImpl::class.java),
        ) { _, method, args ->
            when (method.name) {
                "save" -> saveHandler(args[0] as ExpenseEntity)
                "findById" -> findByIdHandler(args[0] as String)
                "toString" -> "FakeSpringJpaExpenseRepositoryImpl"
                "hashCode" -> System.identityHashCode(this)
                "equals" -> false
                else -> error("Unexpected method call: ${method.name}")
            }
        } as SpringJpaExpenseRepositoryImpl
}
