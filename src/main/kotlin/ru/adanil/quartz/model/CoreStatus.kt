package ru.adanil.quartz.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
interface CoreStatus {
    @get:JsonIgnore val code: Int
    val reason: String?
    val status: CoreStatusCode
    val extras: Map<String, Any>?

    fun isOk(): Boolean = status == CoreStatusCode.OK

    enum class CoreStatusCode {
        OK, NOK
    }
}