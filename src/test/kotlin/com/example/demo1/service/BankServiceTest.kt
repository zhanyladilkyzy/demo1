package com.example.demo1.service

import com.example.demo1.datasource.BankDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import com.example.demo1.service.BankService as BankService

internal class BankServiceTest{
    private val dataSource: BankDataSource = mockk(relaxed = true )

    private val bankService = BankService(dataSource)

    @Test
    fun `should call its data source to retrive banks`(){
        // when
        bankService.getBanks()

        //then
        verify(exactly = 1){dataSource.retrieveBanks()}

    }
}