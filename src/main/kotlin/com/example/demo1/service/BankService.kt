package com.example.demo1.service

import com.example.demo1.datasource.BankDataSource
import com.example.demo1.model.Bank
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service


@Service
class BankService(@Qualifier("network")private val dataSourse: BankDataSource) {
    fun getBanks(): Collection<Bank> = dataSourse.retrieveBanks()

    fun getBank(accountNumber: String): Bank = dataSourse.retrieveBank(accountNumber)

    fun addBank(bank: Bank): Bank = dataSourse.createBank(bank)

    fun  updateBank(bank: Bank): Bank = dataSourse.updateBank(bank)

    fun deleteBank(accountNumber: String): Unit = dataSourse.deleteBank(accountNumber)

}