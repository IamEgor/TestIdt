package by.idt.testapp.domain.di

import by.idt.testapp.domain.TableConfig
import by.idt.testapp.domain.usecase.GenerateTableUseCase
import by.idt.testapp.domain.usecase.ValidateInputUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    single<TableConfig> { TableConfig.Default }
    factoryOf(::GenerateTableUseCase)
    factoryOf(::ValidateInputUseCase)
}
