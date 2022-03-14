package me.scolastico.example.routines.starting

import me.scolastico.example.Application
import me.scolastico.example.dataholders.Config
import me.scolastico.tools.console.ConsoleLoadingAnimation
import me.scolastico.tools.handler.ConfigHandler
import me.scolastico.tools.handler.ErrorHandler
import me.scolastico.tools.routine.Routine
import me.scolastico.tools.routine.RoutineAnswer
import org.fusesource.jansi.Ansi

/**
 * Routine for loading the configuration files.
 */
class ConfigRoutine : Routine {
    @Throws(Exception::class)
    @Suppress("TooGenericExceptionCaught", "LongMethod")
    override fun execute(hashMap: HashMap<String, Any>): RoutineAnswer {
        return try {
            var configMissing = false
            print("Loading config... ")
            ConsoleLoadingAnimation.enable()
            val configHandler = ConfigHandler(Config(), "config.json")
            if (!configHandler.checkIfExists()) {
                configHandler.saveDefaultConfig()
                configMissing = true
            }
            val config = configHandler.loadConfig()
            ConsoleLoadingAnimation.disable()
            if (configMissing) {
                println(Ansi.ansi().fgRed().a("[FAIL]").reset())
            } else {
                println(Ansi.ansi().fgGreen().a("[OK]").reset())
            }
            if (config.debug) {
                configHandler.storeConfig(config)
            }
            if (configMissing) {
                println()
                println("One or more configs are missing. Generated new ones!")
                println("Please edit the default config values and restart the application.")
                println()
                println("Exiting!")
                return RoutineAnswer(true, "config missing")
            }
            Application.config = config
            Application.configHandler = configHandler
            RoutineAnswer(hashMap)
        } catch (e: Exception) {
            try {
                ConsoleLoadingAnimation.disable()
            } catch (ignored: Exception) {
            }
            println(Ansi.ansi().fgRed().a("[FAIL]").reset())
            ErrorHandler.handle(e)
            RoutineAnswer(true, "exception")
        }
    }
}
