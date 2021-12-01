package me.scolastico.example.routines.starting

import me.scolastico.tools.console.ConsoleLoadingAnimation
import me.scolastico.tools.handler.ErrorHandler
import me.scolastico.tools.routine.Routine
import me.scolastico.tools.routine.RoutineAnswer
import java.time.Duration
import java.time.Instant

/**
 * Routine for printing last message of the startup routine flow.
 */
class FinishRoutine : Routine {

    @Throws(Exception::class)
    @Suppress("TooGenericExceptionCaught")
    override fun execute(hashMap: HashMap<String, Any>): RoutineAnswer {
        return try {
            val startingTime = hashMap["startingTime"] as Instant
            @Suppress("MagicNumber")
            val startingDuration = Duration.between(startingTime, Instant.now()).toMillis() / 1000.0
            println()
            println("Done! Starting took $startingDuration seconds.")
            println()
            RoutineAnswer(hashMap)
        } catch (e: Exception) {
            try {
                ConsoleLoadingAnimation.disable()
            } catch (ignored: Exception) {}
            ErrorHandler.handle(e)
            RoutineAnswer(true, "exception")
        }
    }
}
