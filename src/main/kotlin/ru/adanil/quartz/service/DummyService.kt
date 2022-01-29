package ru.adanil.quartz.service

import org.quartz.JobDataMap
import org.quartz.JobDetail

interface DummyService {

    fun doJob(jobDetail: JobDetail, dataMap: JobDataMap)

}