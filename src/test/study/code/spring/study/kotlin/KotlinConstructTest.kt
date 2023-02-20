package code.spring.study.kotlin

import org.junit.Test

class KotlinConstructTest {

    @Test
    fun explicitParameter() {
        val position = Position(azimuth=11.1, elevation=12.2)
        val position2 = Position(11.1, 12.2)
        println("${position.azimuth}, ${position.elevation}")
        println("${position2.azimuth}, ${position2.elevation}")
    }

    class Position(val azimuth: Double, val elevation: Double)
}