package ru.adanil.quartz

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import java.net.InetAddress
import javax.annotation.PreDestroy

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class QuartzApplication @Autowired constructor(
    @Value("\${app.info.name}") val appName: String
) {

    companion object {
        private val log = KotlinLogging.logger {}

        @JvmStatic
        fun main(args: Array<String>) {
            val env = runApplication<QuartzApplication>(*args).environment

            val appPort = env.getProperty("local.server.port")
            val appName = env.getProperty("app.info.name").orEmpty()
            val localAddress = InetAddress.getLocalHost().hostAddress

            log.info {
                """
|---------------------------------------------------------------------------
|>  $appName is running! Access URLs:
|>  Local: http://127.0.0.1:$appPort
|>  In your network: http://$localAddress:$appPort
|---------------------------------------------------------------------------
"""
            }
        }
    }

    @PreDestroy
    fun shutdown() {
        log.info {
            "\n" +
                    "|---------------------------------------------------------------------------\n" +
                    "|>  $appName is shutting down!\n" +
                    "|---------------------------------------------------------------------------\n"
        }
    }

}
