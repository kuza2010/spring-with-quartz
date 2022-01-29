package ru.adanil.quartz.controller

import mu.KotlinLogging
import org.quartz.SchedulerException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.adanil.quartz.exception.CreateCoreResourceFailedException
import ru.adanil.quartz.model.DefaultCoreStatus
import ru.adanil.quartz.model.response.CoreResponse
import ru.adanil.quartz.model.response.toResponseEntity

@ControllerAdvice
class ErrorHandlingControllerAdvice {

    private val logger = KotlinLogging.logger { }

    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun handleIllegalArgumentException(
        illegalArgumentException: IllegalArgumentException
    ): ResponseEntity<CoreResponse> {
        logger.warn("get ${IllegalArgumentException::class.simpleName}", illegalArgumentException)
        return DefaultCoreStatus.NOK("required condition '${illegalArgumentException.message}' is false")
            .toResponseEntity()
    }

    @ExceptionHandler(value = [CreateCoreResourceFailedException::class])
    fun handleCreateCoreResourceException(
        createCoreResourceFailedException: CreateCoreResourceFailedException
    ): ResponseEntity<CoreResponse> {
        logger.error("can not create resource ${createCoreResourceFailedException.resourceName}")
        return DefaultCoreStatus.NOK("Can not create requested resource", HttpStatus.INTERNAL_SERVER_ERROR)
            .toResponseEntity()
    }

    @ExceptionHandler(value = [SchedulerException::class])
    fun handleSchedulerException(
        schedulerException: SchedulerException
    ): ResponseEntity<CoreResponse> {
        logger.error("quartz communication exception occurred: ", schedulerException)
        return DefaultCoreStatus.NOK("An unknown error happened", HttpStatus.INTERNAL_SERVER_ERROR)
            .toResponseEntity()
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleException(
        exception: Exception
    ): ResponseEntity<CoreResponse> {
        logger.error("An unknown error happened:", exception)
        return DefaultCoreStatus.NOK("An unknown error happened", HttpStatus.INTERNAL_SERVER_ERROR)
            .toResponseEntity()
    }

}