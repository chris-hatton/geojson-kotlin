
val clean by tasks.creating(Delete::class) {
    delete(rootProject.buildDir)
}
