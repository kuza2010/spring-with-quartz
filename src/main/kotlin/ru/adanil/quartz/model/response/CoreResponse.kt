package ru.adanil.quartz.model.response

import org.springframework.http.ResponseEntity
import ru.adanil.quartz.model.CoreStatus
import ru.adanil.quartz.service.DateTimeProvider
import java.time.LocalDateTime
import java.time.ZonedDateTime

class CoreResponse constructor(
    val status: CoreStatus,
    val timeStamp: LocalDateTime = ZonedDateTime.now(DateTimeProvider.DEFAULT_ZONE_ID).toLocalDateTime()
) {

    fun toResponseEntity(): ResponseEntity<CoreResponse> {
        return ResponseEntity(this, null, status.code)
    }

}

fun CoreStatus.toResponseEntity(): ResponseEntity<CoreResponse> {
    return CoreResponse(this).toResponseEntity()
}