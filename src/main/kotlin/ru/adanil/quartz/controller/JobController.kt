package ru.adanil.quartz.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.adanil.quartz.model.quartz.JobDescription
import ru.adanil.quartz.model.request.CreateConsoleJobRequest
import ru.adanil.quartz.model.response.CreateJobResponse
import ru.adanil.quartz.service.ConsoleSchedulerService

@RestController
@RequestMapping("/api/console-job")
class JobController @Autowired constructor(
    val consoleSchedulerService: ConsoleSchedulerService,
) {

    @GetMapping("/list")
    fun getAllJobs(): List<JobDescription> {
        return consoleSchedulerService.getJobList()
    }

    @GetMapping("/get")
    fun getJobDetail(
        @RequestParam(required = true) jobName: String,
    ): ResponseEntity<Any> {
        return when (val job = consoleSchedulerService.getJobDescription(jobName)) {
            null -> ResponseEntity.notFound().build()
            else -> ResponseEntity.ok(job)
        }
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    fun createJob(
        @RequestBody createRequest: CreateConsoleJobRequest
    ): CreateJobResponse {
        val jobKey = consoleSchedulerService.createConsoleJob(createRequest)
        return CreateJobResponse(jobKey.name, jobKey.group)
    }
}