package cn.cubegarden.damageparticlenuker

import org.bukkit.configuration.file.FileConfiguration

@Suppress("MagicNumber")
class Config(config: FileConfiguration) {
    var minimum = config.getInt("minimum", 0)
        private set
    var maximum = config.getInt("maximum", -1)
        private set
    var damageScale = config.getDouble("damage-scale", 1.0)
        private set

    init {
        Main.logger.info("Reading configuration...")
        if (minimum < 0) {
            Main.logger.severe("Minimum must be greater than 0")
            minimum = 0
            Main.logger.severe("Minimum is automatically set to 0 to prevent unexpected behavior")
        }
        if (maximum != -1 && maximum < minimum) {
            Main.logger.severe("Maximum must be greater than minimum")
            maximum = -1
            Main.logger.severe("Maximum is automatically set to -1 to prevent unexpected behavior")
        }
        if (damageScale <= 0) {
            Main.logger.severe("Damage scale must be greater than 0")
            damageScale = 1.0
            Main.logger.severe("Damage scale is automatically set to 1 to prevent unexpected behavior")
        }
        Main.logger.info("Configuration read")
        Main.logger.info("minimum: $minimum, maximum: $maximum, damage-scale: $damageScale")
    }
}
