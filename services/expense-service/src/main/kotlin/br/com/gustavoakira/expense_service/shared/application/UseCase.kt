package br.com.gustavoakira.expense_service.shared.application

fun interface UseCase<in INPUT, out OUTPUT> {
    fun execute(input: INPUT): OUTPUT
}
