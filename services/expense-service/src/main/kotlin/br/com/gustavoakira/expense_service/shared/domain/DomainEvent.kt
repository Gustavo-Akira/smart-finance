package br.com.gustavoakira.expense_service.shared.domain

import java.time.Instant

interface DomainEvent {
    val occurredAt: Instant
}
