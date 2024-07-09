package nl.florie.merijn.plugin1;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
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
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("glock19")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("This command can only be run by a player.");
				return true;
			}

			Player player = (Player) sender;

			// Create a diamond hoe
			ItemStack diamondHoe = new ItemStack(Material.DIAMOND_HOE);
			ItemMeta meta = diamondHoe.getItemMeta();

			// Set display name
			meta.setDisplayName("Glock 19");
			diamondHoe.setItemMeta(meta);

			// Give the item to the player
			player.getInventory().addItem(diamondHoe);
			player.sendMessage("You have received a Glock 19!");

			return true;
		}
		return false;
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
			egg.getWorld().spawnParticle(Particle.FLASH, egg.getLocation(), 1);
			eggHitLoc.getWorld().createExplosion(eggHitLoc, 10);
			Bukkit.broadcastMessage("Someone hit ground with eg.");
		}
	}

@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (event.getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL && player.isGliding()) {
				player.getWorld().createExplosion(player.getLocation(), 20);
				player.setHealth(0.0);
			}
		}
	}


	ItemStack weapon;
	Player player;
	int maxAmmo;
	int currentAmmo;
	float shootCooldown;
	float reloadTime;
	Projectile bullet;
@EventHandler
	public void OnLeftClick(final PlayerInteractEvent event)
	{
		player = event.getPlayer();
		weapon = player.getInventory().getItemInMainHand();
		Action action = event.getAction();

		if (weapon != null && weapon.getType() == Material.DIAMOND_HOE)
		{
			if (weapon.getItemMeta().getDisplayName().equals("Glock 19"))
			{
				if(action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK)
				{
					if (currentAmmo <= maxAmmo && currentAmmo > 0)
					{
						Shoot(player);
					}
					else if (currentAmmo == 0)
					{
						player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_SNARE, 1, 2);
					}
				}
				if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
				{
					Reload(player);
				}
			}
		}
	}
	private void Shoot(Player player) {
		Arrow bullet = player.launchProjectile(Arrow.class);
		Vector direction = player.getLocation().getDirection();
		bullet.setVelocity(direction.multiply(2));
		bullet.setDamage(20);
		player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 2, 1.4f);
		player.sendMessage(ChatColor.GREEN + "Pew Pew Pew! " + ChatColor.RESET + "Ammo Left: " +ChatColor.RED + (currentAmmo-1));
		currentAmmo--;
	}
	private void Reload(Player player)
	{
		maxAmmo = 7;
		player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 0.2f);
		currentAmmo = maxAmmo;
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
