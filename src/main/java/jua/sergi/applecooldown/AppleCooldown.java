package jua.sergi.applecooldown;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class AppleCooldown extends JavaPlugin implements Listener {

    private ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        getLogger().info("AppleCooldownPlugin has been enabled");

        protocolManager = ProtocolLibrary.getProtocolManager();
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("AppleCooldownPlugin has been disabled");
    }

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.GOLDEN_APPLE) {
            addCooldown(event.getPlayer(), Material.GOLDEN_APPLE, 100); // 5 segundos de cooldown (100 ticks)
        }
    }

    private void addCooldown(Player player, Material material, int ticks) {
        try {
            PacketContainer packet = protocolManager.createPacket(com.comphenix.protocol.PacketType.Play.Server.SET_COOLDOWN);
            packet.getModifier().writeDefaults();
            packet.getItemModifier().write(0, new ItemStack(material));  // Creamos un ItemStack a partir del Material
            packet.getIntegers().write(0, ticks);
            protocolManager.sendServerPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}