package ru.adanil.quartz.model.quartz

import com.fasterxml.jackson.annotation.JsonFormat
import org.quartz.JobDetail
import org.quartz.Trigger
import java.util.*

data class JobDescription(
    val jobName: String,
    val jobGroup: String,
    val description: String,
    val jobProperties: Map<String, Any?> = mapOf(),
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    val utcFireTime: Date
) {
    companion object {

        fun fromJobDetail(
            jobDetail: JobDetail,
            jobTriggers: List<Trigger>
        ): JobDescription {
            return JobDescription(
                jobName = jobDetail.key.name,
                jobGroup = jobDetail.key.group,
                description = jobDetail.description,
                jobProperties = jobDetail.jobDataMap.toMap(),
                utcFireTime = jobTriggers.first().nextFireTime
            )
        }

    }
}
