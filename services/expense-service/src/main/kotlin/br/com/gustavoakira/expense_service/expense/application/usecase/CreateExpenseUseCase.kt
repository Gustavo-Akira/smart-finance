package br.com.gustavoakira.expense_service.expense.application.usecase

import br.com.gustavoakira.expense_service.expense.application.dto.CreateExpenseCommand
import br.com.gustavoakira.expense_service.expense.application.dto.ExpenseOutput
import br.com.gustavoakira.expense_service.shared.application.UseCase

interface CreateExpenseUseCase : UseCase<CreateExpenseCommand, ExpenseOutput>
