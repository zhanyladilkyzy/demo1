package com.example.demo1.controller

import com.example.demo1.datasource.BankDataSource
import com.example.demo1.model.Bank
import com.example.demo1.service.BankService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/banks")

class BankController(private val service: BankService ) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handlenotFound(e: NoSuchElementException): ResponseEntity<String> =
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @GetMapping
     fun getBanks(): Collection<Bank> = service.getBanks()

    @GetMapping("/{accountNumber}")
    fun getBank(@PathVariable accountNumber: String): Bank = service.getBank(accountNumber)
}