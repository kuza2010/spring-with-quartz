package ru.adanil.quartz.service.impl

import org.springframework.stereotype.Service
import ru.adanil.quartz.service.DateTimeProvider
import ru.adanil.quartz.service.DateTimeProvider.Companion.DEFAULT_ZONE_ID
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@Service
class DateTimeProviderImpl : DateTimeProvider {

    override fun now(): LocalDateTime = ZonedDateTime.now(DEFAULT_ZONE_ID).toLocalDateTime()

    override fun convertLocalDateTimeToDate(
        localDateTime: LocalDateTime,
        zoneId: ZoneId
    ): Date {
        val instant = localDateTime.atZone(DEFAULT_ZONE_ID).toInstant()
        return Date.from(instant)
    }

}