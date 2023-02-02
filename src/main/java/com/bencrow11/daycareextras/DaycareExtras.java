package com.bencrow11.daycareextras;

import com.bencrow11.daycareextras.commands.AddBoxCommand;
import com.bencrow11.daycareextras.commands.SetBoxCommand;
import com.pixelmonmod.pixelmon.Pixelmon;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("daycareextras")
public class DaycareExtras {

	private static final Logger LOGGER = LogManager.getLogger();

	public DaycareExtras() {
		MinecraftForge.EVENT_BUS.register(this);
		Pixelmon.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void registerCommand(RegisterCommandsEvent event) {
		new AddBoxCommand(event.getDispatcher());
		new SetBoxCommand(event.getDispatcher());
	}

	// You can use SubscribeEvent and let the Event Bus discover methods to call
	@SubscribeEvent
	public void onServerStarting(FMLServerStartingEvent event) {
		// do something when the server starts
		LOGGER.info("Loaded.");
	}

}
