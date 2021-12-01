package me.scolastico.example

import me.scolastico.example.dataholders.Config
import me.scolastico.example.routines.starting.ConfigRoutine
import me.scolastico.example.routines.starting.ErrorRoutine
import me.scolastico.example.routines.starting.FinishRoutine
import me.scolastico.example.routines.starting.HeaderRoutine
import me.scolastico.example.routines.starting.DatabaseRoutine
import me.scolastico.tools.console.ConsoleLoadingAnimation
import me.scolastico.tools.handler.ConfigHandler
import me.scolastico.tools.handler.ErrorHandler
import me.scolastico.tools.routine.Routine
import me.scolastico.tools.routine.RoutineManager
import me.scolastico.tools.simplified.SimplifiedResourceFileReader

/**
 * Application entry point.
 */
@Suppress("UtilityClassWithPublicConstructor")
class Application {
    companion object {

        /** ConfigHandler for the handling of configuration changes. */
        var configHandler:ConfigHandler<Config>? = null
        /** The main configuration data class. */
        var config:Config? = null
        /** The version string which gets updated on GitHub action builds. */
        val version:String = SimplifiedResourceFileReader.getInstance().getStringFromResources("staticVars/VERSION")
        /** The branch string which gets updated on GitHub action builds. */
        val branch:String = SimplifiedResourceFileReader.getInstance().getStringFromResources("staticVars/BRANCH")
        /** The commit string which gets updated on GitHub action builds. */
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
