package ia.deepblue.catalogservice.configs

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "polar")
class PolarProperties {
    lateinit var greeting: String
}