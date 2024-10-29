package me.josh.reportsystem.util

import net.kyori.adventure.text.Component
import org.bukkit.ChatColor
import org.jetbrains.annotations.NotNull

class ColorUtil {

    companion object {
        /**
         * Translate color codes in a string using '&' character
         * @param text The text to colorize
         * @return Colored string
         */
        @NotNull
        fun color(@NotNull text: String?): String {
            return ChatColor.translateAlternateColorCodes('&', text)
        }

        /**
         * Translate color codes in multiple strings
         * @param texts The texts to colorize
         * @return Array of colored strings
         */
        @NotNull
        fun color(@NotNull vararg texts: String?): @NotNull Array<String?>? {
            val colored = arrayOfNulls<String>(texts.size)
            for (i in texts.indices) {
                colored[i] = color(texts[i])
            }
            return colored
        }

        /**
         * Strip all color codes from a string
         * @param text The text to strip colors from
         * @return Text without color codes
         */
        @NotNull
        fun strip(@NotNull text: String?): String {
            return ChatColor.stripColor(text)
        }

        /**
         * Get a colored message with a prefix
         * @param prefix The prefix to add (will be colored)
         * @param message The message to color
         * @return Prefixed and colored message
         */
        @NotNull
        fun prefixed(@NotNull prefix: String, @NotNull message: String): String {
            return color("$prefix $message")
        }

        /**
         * Create a component with colored text
         * @param text The text to colorize
         * @return A component with colored text
         */
        @NotNull
        fun component(text: String) : Component {
            return Component.text(color(text))
        }
    }
}