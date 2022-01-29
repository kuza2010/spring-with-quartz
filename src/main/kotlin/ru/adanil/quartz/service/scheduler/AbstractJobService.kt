package ru.adanil.quartz.service.scheduler

import mu.KotlinLogging
import org.quartz.*
import org.quartz.impl.matchers.GroupMatcher

abstract class AbstractJobService constructor(
    protected val scheduler: Scheduler
) {

    private var logger = KotlinLogging.logger { }

    protected abstract fun generateJobKey(): JobKey
    protected abstract fun generateTriggerKey(): TriggerKey

    protected fun createJob(
        trigger: Trigger,
        jobDetail: JobDetail,
    ): Trigger? {
        return try {
            scheduler.scheduleJob(jobDetail, trigger)
            return scheduler.getTrigger(trigger.key)
        } catch (ex: SchedulerException) {
            logger.error("Can not schedule job with key: ${jobDetail.key}, message: ${ex.message}", ex)
            null
        }
    }

    protected fun getJobDetailByJobName(
        jobName: String
    ): JobDetail? {
        val jobKey = scheduler.getJobKeys(GroupMatcher.anyJobGroup())
            .find { it.name.equals(jobName) }
            ?: return null

        return scheduler.getJobDetail(jobKey)
    }

}