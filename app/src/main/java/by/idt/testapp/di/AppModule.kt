package by.idt.testapp.di

import by.idt.testapp.ui.screen.input.InputViewModel
import by.idt.testapp.ui.screen.table.TableViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::InputViewModel)
    viewModel { params ->
        TableViewModel(rows = params.get(), cols = params.get(), generateTableUseCase = get())
    }
}
