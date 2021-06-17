package com.example.demo1.datasource.mock

import com.example.demo1.datasource.BankDataSource
import com.example.demo1.model.Bank
import org.springframework.stereotype.Repository


@Repository
class MockBankDataSource: BankDataSource {

    val banks = listOf(
            Bank("1243", 3.43, 17),
            Bank("4567", 3.434, 17),
            Bank("2454", 3.434, 0)

    )

//    override fun getBanks(): Collection<Bank> {
//        return banks
//    }
    override fun retrieveBanks(): Collection<Bank>  = banks
    override fun retrieveBank(accountNumber: String): Bank =
        banks.firstOrNull() { it.accountNumber  == accountNumber }
                ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")

}