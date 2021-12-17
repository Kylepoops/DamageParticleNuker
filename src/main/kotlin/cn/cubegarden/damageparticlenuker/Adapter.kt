package cn.cubegarden.damageparticlenuker

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.*
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket
import org.bukkit.plugin.Plugin
import kotlin.math.floor

class Adapter(plugin: Plugin, private val config: Config) : PacketAdapter(
    plugin,
    ListenerPriority.NORMAL,
    PacketType.Play.Server.WORLD_PARTICLES
) {

    @Suppress("MagicNumber")
    override fun onPacketSending(event: PacketEvent) {
        if (event.packetType != PacketType.Play.Server.WORLD_PARTICLES) return

        val handle = event.packet.handle as ClientboundLevelParticlesPacket

        if (handle.particle != ParticleTypes.DAMAGE_INDICATOR) return

        var result = if (handle.count <= config.minimum) {
            // use minimum value if count is less than it
            config.minimum
        } else {
            // use the minimum value and the quotient of the remaining quantity with the scale
            val remaining = handle.count - config.minimum
            config.minimum + floor(remaining / config.damageScale).toInt()
        }

        // prevent the result from greater than the maximum value if the maximum value is assigned
        if (config.maximum != -1) result = minOf(result, config.maximum)

        // set the result to the seventh value of the packet
        /** see: [net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket] */
        event.packet.modifier.write(7, result)
    }
}
