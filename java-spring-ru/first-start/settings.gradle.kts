rootProject.name = "SpringBootApplication"
// spring.jpa.generate-ddl = true
// spring.jpa.hibernate.ddl-auto
rootProject.name = "SpringBootApplication"
// spring.jpa.generate-ddl = true
// spring.jpa.hibernate.ddl-auto
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven {
            url = uri("https://artifactory.raiffeisen.ru/artifactory/plugins-gradle")
            credentials {
                username = System.getProperty("artifactory_user")
                password = System.getProperty("artifactory_password")
            }
        }
    }
}