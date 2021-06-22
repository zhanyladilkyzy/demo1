package com.example.demo1.datasource.network

import com.example.demo1.datasource.BankDataSource
import com.example.demo1.datasource.network.dto.BankList
import com.example.demo1.model.Bank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.io.IOException


@Repository("network")
class NetworkDataSource(
        @Autowired private val restTemplate: RestTemplate
) : BankDataSource {

    override fun retrieveBanks(): Collection<Bank> {
        val response : ResponseEntity<BankList> =
            restTemplate.getForEntity("http://54.193.31.159/banks")

        return response.body?.results //if there is a body and if its not null take result
                ?:throw  IOException("Couldn't fetch the banks from network")
    }

    override fun retrieveBank(accountNumber: String): Bank {
        TODO("Not yet implemented")
    }

    override fun createBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun updateBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun deleteBank(accountNumber: String) {
        TODO("Not yet implemented")
    }
}