@file:Suppress("WildcardImport")
package me.scolastico.example

import me.scolastico.example.dataholders.Config
import me.scolastico.example.routines.starting.*
import me.scolastico.tools.console.ConsoleLoadingAnimation
import me.scolastico.tools.handler.ConfigHandler
import me.scolastico.tools.handler.ErrorHandler
import me.scolastico.tools.routine.Routine
import me.scolastico.tools.routine.RoutineManager
import me.scolastico.tools.simplified.SimplifiedResourceFileReader

/**
 * Application entry point.
 */
class Application private constructor() {
    companion object {
        var configHandler:ConfigHandler<Config>? = null
        var config:Config? = null
        val version:String = SimplifiedResourceFileReader.getInstance().getStringFromResources("staticVars/VERSION")
        val branch:String = SimplifiedResourceFileReader.getInstance().getStringFromResources("staticVars/BRANCH")
        val commit:String = SimplifiedResourceFileReader.getInstance().getStringFromResources("staticVars/COMMIT")

        /**
         * Main function of the Application.
         */
        @JvmStatic
        @Suppress("TooGenericExceptionCaught")
        fun main(args: Array<String>) {
            try {
                val routines = ArrayList<Routine>()
                routines.add(ErrorRoutine())
                routines.add(HeaderRoutine())
                routines.add(ConfigRoutine())
                routines.add(DatabaseRoutine())
                routines.add(FinishRoutine())
                val manager = RoutineManager(routines)
                manager.startNotAsynchronously()
            } catch (e: Exception) {
                try {
                    ConsoleLoadingAnimation.disable()
                } catch (ignored: Exception) {}
                ErrorHandler.handleFatal(e)
            }
        }
    }
}
