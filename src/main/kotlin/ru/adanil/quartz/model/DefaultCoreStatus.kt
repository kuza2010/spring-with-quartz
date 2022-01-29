package ru.adanil.quartz.model

import org.springframework.http.HttpStatus

class DefaultCoreStatus(
    override val code: Int,
    override val reason: String? = null,
    override val status: CoreStatus.CoreStatusCode,
    override val extras: Map<String, Any>? = null
) : CoreStatus {

    companion object {

        val OK: CoreStatus = DefaultCoreStatus(
            code = HttpStatus.OK.value(),
            status = CoreStatus.CoreStatusCode.OK
        )

        fun CREATED(extras: Map<String, Any>? = null): CoreStatus = DefaultCoreStatus(
            extras = extras,
            code = HttpStatus.CREATED.value(),
            status = CoreStatus.CoreStatusCode.OK
        )

        fun NOK(
            reason: String?,
            code: HttpStatus = HttpStatus.BAD_REQUEST,
            extras: Map<String, Any>? = null
        ): CoreStatus {
            return DefaultCoreStatus(
                code = code.value(),
                reason = reason,
                status = CoreStatus.CoreStatusCode.NOK,
                extras = extras
            )
        }

    }

}