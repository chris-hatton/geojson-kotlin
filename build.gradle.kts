plugins {
    `build-scan`
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"

    publishAlways()
}

val clean by tasks.creating(Delete::class) {
    delete(rootProject.buildDir)
}
