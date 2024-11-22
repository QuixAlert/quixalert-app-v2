package org.quixalert.br

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform