package com.bencrow11.daycareextras.commands;

import com.bencrow11.daycareextras.utils.Utils;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.api.storage.breeding.PlayerDayCare;
import com.pixelmonmod.pixelmon.comm.CommandChatHandler;
import com.pixelmonmod.pixelmon.command.PixelCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.server.permission.PermissionAPI;

public class AddBoxCommand extends PixelCommand {

	public static String PERMISSION = "daycareextras.addbox";

	public AddBoxCommand(CommandDispatcher<CommandSource> dispatcher) {
		super(dispatcher);
	}

	@Override
	public String getName() {
		return "addbox";
	}

	@Override
	public String getUsage(CommandSource sender) {
		return TextFormatting.DARK_AQUA + "" + TextFormatting.BOLD + "DaycareExtras" + TextFormatting.RESET +
				"- addbox:\n" + "Adds a daycare slot to a person Daycare.\n" + TextFormatting.YELLOW +
				"Usage: addbox <player> <amount>";
	}

	@Override
	public void execute(CommandSource sender, String[] strings) throws CommandException, CommandSyntaxException {
		if (sender.getEntity() != null) {
			if (!PermissionAPI.hasPermission(sender.asPlayer(), PERMISSION)) {
				CommandChatHandler.sendChat(sender, TextFormatting.RED + "You need the permission " + PERMISSION);
				return;
			}
		}

		if (strings.length != 2) {
			CommandChatHandler.sendChat(sender, getUsage(sender));
			return;
		}

		if (ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerByUsername(strings[0]) == null) {
			CommandChatHandler.sendChat(sender, TextFormatting.RED + "This player does not exist");
			return;
		}

		ServerPlayerEntity player =
				ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerByUsername(strings[0]);

		PlayerDayCare daycare = StorageProxy.getParty(player).getDayCare();
		daycare.setAllowedBoxes(daycare.getAllowedBoxes() + Integer.parseInt(strings[1]));

		Utils.updateClientUI(StorageProxy.getParty(player), daycare);
	}
}
