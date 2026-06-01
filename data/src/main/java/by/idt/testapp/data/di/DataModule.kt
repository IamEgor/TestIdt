package by.idt.testapp.data.di

import by.idt.testapp.data.repository.TableRepositoryImpl
import by.idt.testapp.domain.repository.TableRepository
import org.koin.dsl.module

val dataModule = module { single<TableRepository> { TableRepositoryImpl() } }
