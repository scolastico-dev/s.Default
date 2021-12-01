package me.scolastico.example.routines.starting

import me.scolastico.tools.handler.ErrorHandler
import me.scolastico.tools.routine.Routine
import me.scolastico.tools.routine.RoutineAnswer

/**
 * Routine for registering the error handler and changing settings on it.
 */
class ErrorRoutine : Routine {
    @Throws(Exception::class)
    override fun execute(hashMap: HashMap<String, Any>): RoutineAnswer {
        ErrorHandler.enableErrorLogFile()
        ErrorHandler.enableCatchUncaughtException()
        //ErrorHandler.enableSentry("sentry dns url here");
        return RoutineAnswer(hashMap)
    }
}
