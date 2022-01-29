package ru.adanil.quartz.service

import org.quartz.JobKey
import ru.adanil.quartz.model.CoreStatus
import ru.adanil.quartz.model.quartz.JobDescription
import ru.adanil.quartz.model.request.CreateConsoleJobRequest

interface ConsoleSchedulerService {
    fun getJobList(): List<JobDescription>
    fun getJobDescription(jobName: String): JobDescription?
    fun createConsoleJob(createRequest: CreateConsoleJobRequest): JobKey
}