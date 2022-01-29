package ru.adanil.quartz.service

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

interface DateTimeProvider {

    companion object {
        var DEFAULT_ZONE_STRING: String = "UTC"
        var DEFAULT_ZONE_ID: ZoneId = ZoneId.of(DEFAULT_ZONE_STRING)
    }

    fun now(): LocalDateTime
    fun convertLocalDateTimeToDate(localDateTime: LocalDateTime, zoneId: ZoneId = DEFAULT_ZONE_ID): Date

}