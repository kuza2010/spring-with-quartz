package ru.adanil.quartz.exception

open class CreateCoreResourceFailedException(
    val resourceName: String,
) : RuntimeException("Can not create $resourceName") {}

class CreateJobResourceFailedException() : CreateCoreResourceFailedException("Job") {}