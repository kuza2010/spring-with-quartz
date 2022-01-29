package ru.adanil.quartz.service.impl

import mu.KotlinLogging
import org.quartz.JobDataMap
import org.quartz.JobDetail
import org.springframework.stereotype.Service
import ru.adanil.quartz.service.DummyService

@Service
class DummyServiceImpl : DummyService {

    private val logger = KotlinLogging.logger { }

    override fun doJob(
        jobDetail: JobDetail,
        dataMap: JobDataMap
    ) {
        val condoleMessage = dataMap["text"] as? String?
        logger.info { "*** '$condoleMessage' ***" }
    }

}