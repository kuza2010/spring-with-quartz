package ru.adanil.quartz.model.response

data class CreateJobResponse(
    val jobName: String,
    val jobGroup: String
) {}