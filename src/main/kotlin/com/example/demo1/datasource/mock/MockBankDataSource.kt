package com.example.demo1.datasource.mock

import com.example.demo1.datasource.BankDataSource
import com.example.demo1.model.Bank
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException


@Repository
class MockBankDataSource: BankDataSource {

    val banks = mutableListOf(
            Bank("1243", 3.43, 17),
            Bank("4567", 3.434, 17),
            Bank("2454", 3.434, 0)

    )

//    override fun getBanks(): Collection<Bank> {
//        return banks
//    }
    override fun retrieveBanks(): Collection<Bank>  = banks
    override fun retrieveBank(accountNumber: String): Bank =
        banks.firstOrNull{ it.accountNumber  == accountNumber }
                ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")

    override fun createBank(bank: Bank): Bank {
        if (banks.any { it.accountNumber == bank.accountNumber}){
            throw IllegalArgumentException("Bank with account number ${bank.accountNumber} already exists")
        }
        banks.add(bank)
        return bank
    }

    override fun updateBank(bank: Bank): Bank {
        val currentBank = banks.firstOrNull{it.accountNumber == bank.accountNumber}
                ?: throw  NoSuchElementException("Could not find a bank with account number $bank.accountNumber")
        banks.remove(currentBank)
        banks.add(bank)
        return bank
    }
}