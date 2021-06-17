package com.example.demo1.datasource

import com.example.demo1.model.Bank

interface BankDataSource {
    fun  retrieveBanks(): Collection<Bank>
    fun retrieveBank(accountNumber: String): Bank
}