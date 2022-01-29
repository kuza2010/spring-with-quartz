package ru.adanil.quartz.model.request

import org.springframework.util.Assert
import ru.adanil.quartz.model.dto.ConsoleJobDto
import java.time.LocalDateTime

data class CreateConsoleJobRequest(
    val text: String,
    val utcFireTime: LocalDateTime,
) {

    fun validate(now: LocalDateTime) {
        Assert.isTrue(utcFireTime.isAfter(now), "fire time: $utcFireTime should be at least $now")
    }

    fun toDTO(): ConsoleJobDto {
        return ConsoleJobDto(
            fireTime = utcFireTime,
            text = text.trim()
        )
    }

}
