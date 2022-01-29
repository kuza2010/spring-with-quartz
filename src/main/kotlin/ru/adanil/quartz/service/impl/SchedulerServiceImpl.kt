package ru.adanil.quartz.service.impl

import org.quartz.*
import org.quartz.impl.matchers.GroupMatcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.adanil.quartz.exception.CreateJobResourceFailedException
import ru.adanil.quartz.model.quartz.JobDescription
import ru.adanil.quartz.model.request.CreateConsoleJobRequest
import ru.adanil.quartz.service.ConsoleSchedulerService
import ru.adanil.quartz.service.scheduler.AbstractJobService
import ru.adanil.quartz.service.scheduler.job.PrintToConsoleJob
import java.util.*

@Service
class SchedulerServiceImpl @Autowired constructor(
    scheduler: Scheduler,
    private val dateTimeProvider: DateTimeProviderImpl
) : ConsoleSchedulerService, AbstractJobService(scheduler) {

    override fun getJobDescription(
        jobName: String
    ): JobDescription? {
        val jobDetail = getJobDetailByJobName(jobName) ?: return null
        val jobTriggers = scheduler.getTriggersOfJob(jobDetail.key)

        return JobDescription.fromJobDetail(jobDetail, jobTriggers)
    }

    override fun getJobList(): List<JobDescription> {
        val matcher = GroupMatcher.anyJobGroup()
        val jobDetails = scheduler.getJobKeys(matcher)
            .map { jobKey -> scheduler.getJobDetail(jobKey) }
        val jobTriggers = jobDetails
            .associateWith { scheduler.getTriggersOfJob(it.key) }

        return jobTriggers.entries
            .map { JobDescription.fromJobDetail(it.key, it.value) }
    }

    override fun createConsoleJob(
        createRequest: CreateConsoleJobRequest
    ): JobKey {
        createRequest.validate(dateTimeProvider.now())

        val dto = createRequest.toDTO()

        val job = super.createJob(
            TriggerBuilder
                .newTrigger()
                .withIdentity(generateTriggerKey())
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionIgnoreMisfires())
                .startAt(dateTimeProvider.convertLocalDateTimeToDate(dto.fireTime))
                .withDescription("Trigger-for-console-job")
                .build(),
            JobBuilder.newJob(PrintToConsoleJob::class.java)
                .usingJobData(dto.getJobDataMap())
                .withIdentity(generateJobKey())
                .withDescription("Console-job")
                .build()
        )

        return when (job) {
            null -> throw CreateJobResourceFailedException()
            else -> job.jobKey
        }
    }


    /** <--- super class methods ---> **/
    override fun generateJobKey(): JobKey {
        return JobKey("Console-job-${UUID.randomUUID()}", "Console-job-group")
    }

    override fun generateTriggerKey(): TriggerKey {
        return TriggerKey("Console-trigger-${UUID.randomUUID()}", "Console-trigger-key")
    }

}