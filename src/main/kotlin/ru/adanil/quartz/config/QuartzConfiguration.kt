package ru.adanil.quartz.config

import mu.KotlinLogging
import org.quartz.SchedulerListener
import org.quartz.listeners.SchedulerListenerSupport
import org.springframework.boot.autoconfigure.quartz.QuartzProperties
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import org.springframework.scheduling.quartz.SpringBeanJobFactory
import ru.adanil.quartz.spring.AutoWiringSpringBeanJobFactory
import java.util.*


@Configuration
class QuartzConfiguration {

    @Bean
    fun springBeanJobFactory(
        applicationContext: ApplicationContext
    ): SpringBeanJobFactory {
        return AutoWiringSpringBeanJobFactory().apply {
            setApplicationContext(applicationContext)
        }
    }

    @Bean
    fun schedulerFactory(
        quartzProperties: QuartzProperties,
        schedulerListener: SchedulerListener,
        applicationContext: ApplicationContext
    ): SchedulerFactoryBean {
        val properties = Properties().apply {
            putAll(quartzProperties.properties)
        }

        return SchedulerFactoryBean()
            .apply {
                setQuartzProperties(properties)
                setSchedulerListeners(schedulerListener)
                setJobFactory(springBeanJobFactory(applicationContext))
            }
    }


    @Bean
    fun schedulerListener(
        quartzProperties: QuartzProperties,
    ): SchedulerListener {
        val name = quartzProperties.schedulerName
            ?: quartzProperties.properties["org.quartz.scheduler.instanceName"]
            ?: "UNKNOWN"

        return MySchedulerListener(name)
    }

    internal class MySchedulerListener(
        private val schedulerName: String
    ) : SchedulerListenerSupport() {

        private val logger = KotlinLogging.logger { }

        override fun schedulerStarted() {
            logger.info { "scheduler $schedulerName has just started!" }
            super.schedulerStarted()
        }

        override fun schedulerStarting() {
            logger.info { "scheduler $schedulerName has just starting" }
            super.schedulerStarting()
        }

        override fun schedulerShutdown() {
            logger.info { "scheduler $schedulerName has just shutdown!" }
            super.schedulerShutdown()
        }

        override fun schedulerShuttingdown() {
            logger.info { "scheduler $schedulerName has just started shutting down" }
            super.schedulerShuttingdown()
        }
    }

}