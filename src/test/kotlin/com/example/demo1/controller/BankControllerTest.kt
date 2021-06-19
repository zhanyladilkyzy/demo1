package com.example.demo1.controller

import com.example.demo1.model.Bank
import com.fasterxml.jackson.databind.ObjectMapper
import com.sun.xml.internal.ws.api.pipe.ContentType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity.status
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
){

    val baseUrl = "/api/banks"


    @Nested
    @DisplayName("GET /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {
        @Test
        fun  `should return all banks`(){
            // when/then
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content{contentType(MediaType.APPLICATION_JSON)}
                    jsonPath("$[0].accountNumber"){ value( "1243") }
                }
        }
    }

    @Nested
    @DisplayName("GET /api/bank/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank {
        @Test
        fun `should return the bank with given account number`() {
            // given
            val accountNumber = 1243
            // when/then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust") { value(3.43) }
                    jsonPath("$.transactionFee") { value(17) }

                }
        }
        @Test
        fun `should return Not Found if The account number does not exist`(){
            // given
            val accountNumber = "does_not_exist"

            // when/then
            mockMvc.get("$baseUrl/$accountNumber")
                    .andDo { print() }
                    .andExpect { status  { isNotFound() } }

        }
    }
    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class  PostNewBank{
        @Test
        fun `should add new bank`(){
            // given
            val newBank = Bank("acc123", 31.415, 2)

            // when
            val performPost = mockMvc.post(baseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }
            //then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType((MediaType.APPLICATION_JSON))
                        json(objectMapper.writeValueAsString(newBank))

                    }

                }
            mockMvc.get("$baseUrl/${newBank.accountNumber}")
                    .andExpect { content { json(objectMapper.writeValueAsString(newBank)) } }

        }

        @Test
        fun `should return BAD REQUEST if with given account number already exists`(){
            // given
            var invalidBank = Bank("1243", 1.0, 1)

            // when
            val performPost = mockMvc.post(baseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            //then
            performPost
                    .andDo { print() }
                    .andExpect { status { isBadRequest() } }
        }
    }
    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PathExistingBank {
        
        @Test
        fun `should update an existing bank`(){
            // given
            val updatedBank = Bank("1243", 3.43, 17)
            // when
            val performPatchRequest = mockMvc.patch(baseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank)

            }
            //then
            performPatchRequest
                    .andDo { print() }
                    .andExpect {
                        status { isOk() }
                        content {
                            contentType((MediaType.APPLICATION_JSON))
                            json(objectMapper.writeValueAsString(updatedBank))
                        }
                    }
            
            mockMvc.get("$baseUrl/${updatedBank.accountNumber}")
                    .andExpect { content { json(objectMapper.writeValueAsString(updatedBank)) } }
        }
        @Test
        fun `should return BAD REQUEST if no bank with given account number exists`(){
            // given
            val invalidBank = Bank("does_not_exist", 1.0,1)

            // when
            val performPatchRequest = mockMvc.patch(baseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)

            }
            //then
            performPatchRequest
                    .andDo { print() }
                    .andExpect { status { isNotFound() }                    }

        }
        
    }
 }
