package cn.cubegarden.damageparticlenuker

import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import com.comphenix.protocol.events.PacketAdapter
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class Main : JavaPlugin() {

    private lateinit var protocolManager: ProtocolManager
    private lateinit var adapter: PacketAdapter

    override fun onEnable() {
        this.saveDefaultConfig()
        protocolManager = ProtocolLibrary.getProtocolManager()
        adapter = Adapter(this, Config(this.config))
        logger.info("Registering Listener...")
        protocolManager.addPacketListener(adapter)
        logger.info("Plugin is enabled")
    }

    override fun onDisable() {
        logger.info("Removing listener")
        protocolManager.removePacketListener(adapter)
        logger.info("Plugin is disabled")
    }

    companion object PluginInstanceHolder {
        val plugin by lazy { Bukkit.getPluginManager().getPlugin("DamageParticleNuker")!! }
        val logger by lazy { plugin.logger }
    }
}
