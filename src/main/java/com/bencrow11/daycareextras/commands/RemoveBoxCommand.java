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

public class RemoveBoxCommand extends PixelCommand {

	public static String PERMISSION = "daycareextras.delbox";

	public RemoveBoxCommand(CommandDispatcher<CommandSource> dispatcher) {
		super(dispatcher);
	}

	@Override
	public String getName() {
		return "removebox";
	}

	@Override
	public String getUsage(CommandSource sender) {
		return TextFormatting.DARK_AQUA + "" + TextFormatting.BOLD + "DaycareExtras" + TextFormatting.RESET +
				"- delbox:\n" + "Removes a daycare slot to a person Daycare.\n" + TextFormatting.YELLOW +
				"Usage: delbox <player> <amount>";
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
		int allowedBoxes = daycare.getAllowedBoxes();
		int amount = Integer.parseInt(strings[1]);

		if (allowedBoxes - amount <= 0) {
			CommandChatHandler.sendChat(sender, TextFormatting.RED + "You can not remove more boxes than the player " +
					"has");
			return;
		}

		Utils.RemovePokemonFromBox(daycare, allowedBoxes, amount);

		daycare.setAllowedBoxes(allowedBoxes - amount);

		Utils.updateClientUI(StorageProxy.getParty(player), daycare);
	}
}