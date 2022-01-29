package ru.adanil.quartz.service.scheduler.job

import mu.KotlinLogging
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.beans.factory.annotation.Autowired
import ru.adanil.quartz.service.DummyService

/**
 * [PrintToConsoleJob] is the simple job that
 * print text to console.
 *
 * Requires 'text' key in data map
 */
class PrintToConsoleJob : Job {

    private val logger = KotlinLogging.logger { }

    @Autowired
    lateinit var dummyService: DummyService


    override fun execute(context: JobExecutionContext) {
        logger.info { "execute job of class ${PrintToConsoleJob::class.simpleName} with job key: ${context.jobDetail.key}" }
        dummyService.doJob(context.jobDetail, context.mergedJobDataMap)
        logger.info { "execute job of class ${PrintToConsoleJob::class.simpleName} finished" }
    }

}