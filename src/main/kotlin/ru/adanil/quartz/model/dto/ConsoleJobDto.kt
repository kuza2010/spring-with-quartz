package ru.adanil.quartz.model.dto

import org.quartz.JobDataMap
import java.time.LocalDateTime

data class ConsoleJobDto(
    val fireTime: LocalDateTime,
    val text: String
) {

    fun getJobDataMap(): JobDataMap = JobDataMap(mapOf("text" to text))

}