package nl.florie.merijn.plugin1;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.mineacademy.fo.plugin.SimplePlugin;

/**
 * PluginTemplate is a simple template you can use every time you make
 * a new plugin. This will save you time because you no longer have to
 * recreate the same skeleton and features each time.
 *
 * It uses Foundation for fast and efficient development process.
 */
public final class Plugin1Plugin extends SimplePlugin {

	/**
	* Automatically perform login ONCE when the plugin starts.
	*/
	@Override
	protected void onPluginStart() {
	}

	/**
	 * Automatically perform login when the plugin starts and each time it is reloaded.
	 */
	@Override
	protected void onReloadablesStart() {

		// You can check for necessary plugins and disable loading if they are missing
		//Valid.checkBoolean(HookManager.isVaultLoaded(), "You need to install Vault so that we can work with packets, offline player data, prefixes and groups.");

		// Uncomment to load variables
		// Variable.loadVariables();

		//
		// Add your own plugin parts to load automatically here
		// Please see @AutoRegister for parts you do not have to register manually
		//
	}

	@Override
	protected void onPluginPreReload() {

		// Close your database here if you use one
		//YourDatabase.getInstance().close();
	}

	/* ------------------------------------------------------------------------------- */
	/* Events */
	/* ------------------------------------------------------------------------------- */

	/**
	 * An example event that checks if the right clicked entity is a cow, and makes an explosion.
	 * You can write your events to your main class without having to register a listener.
	 *
	 * @param event
	 */
	@EventHandler
	public void onRightClick(final PlayerInteractEntityEvent event)
	{
		if (event.getRightClicked().getType() == EntityType.FROG)
		{
			event.getRightClicked().getWorld().createExplosion(event.getRightClicked().getLocation(), 5);
		}
	}

	@EventHandler
	public void eggHit(final ProjectileHitEvent hitEvent)
	{
		if (hitEvent.getEntity() instanceof Egg)
		{
			Location eggHitLoc = hitEvent.getEntity().getLocation();
			Egg egg = (Egg) hitEvent.getEntity();
			Player player = (Player) egg.getShooter();
			player.teleport(eggHitLoc);
			egg.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, egg.getLocation(), 100000);
			eggHitLoc.getWorld().createExplosion(eggHitLoc, 0);
			Bukkit.broadcastMessage("Someone hit ground with eg.");
		}
	}

	/* ------------------------------------------------------------------------------- */
	/* Static */
	/* ------------------------------------------------------------------------------- */

	/**
	 * Return the instance of this plugin, which simply refers to a static
	 * field already created for you in SimplePlugin but casts it to your
	 * specific plugin instance for your convenience.
	 *
	 * @return
	 */
	public static Plugin1Plugin getInstance() {
		return (Plugin1Plugin) SimplePlugin.getInstance();
	}
}
