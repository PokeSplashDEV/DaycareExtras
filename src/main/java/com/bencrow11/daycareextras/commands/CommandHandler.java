package com.bencrow11.daycareextras.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pixelmonmod.pixelmon.comm.CommandChatHandler;
import com.pixelmonmod.pixelmon.command.PixelCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.server.permission.PermissionAPI;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler extends PixelCommand {

	private static final ArrayList<String> SUBCOMMANDS = Lists.newArrayList("addbox", "removebox", "setboxes");

	public CommandHandler(CommandDispatcher<CommandSource> dispatcher) {
		super(dispatcher, "daycareextras", "/daycareextras help", 3);
	}

	@Override
	public List<String> getAliases() {
		return Lists.newArrayList("dce");
	}

	@Override
	public String getUsage(CommandSource sender) {
		return TextFormatting.DARK_AQUA + "" + TextFormatting.BOLD + "DaycareExtras\n" + TextFormatting.YELLOW +
				"Usage:\n" + TextFormatting.WHITE +
				"- addbox <player> <amount>\n" +
				"- removebox <player> <amount>\n" +
				"- setboxes <player> <amount>";
	}

	@Override
	public void execute(CommandSource sender, String[] strings) throws CommandException, CommandSyntaxException {

		if (sender.getEntity() != null) {
			if (!PermissionAPI.hasPermission(sender.asPlayer(), "daycareextras.admin")) {
				CommandChatHandler.sendChat(sender, TextFormatting.RED + "You do not have permission to use this " +
						"command");
				return;
			}
		}

		// Checks for args
		if (strings.length == 0) {
			CommandChatHandler.sendChat(sender, getUsage(sender));
			return;
		}

		switch (strings[0].toLowerCase()) {
			case "addbox":
				break;
			case "removebox":
				break;
			case "setboxes":
				break;
			default:
				CommandChatHandler.sendChat(sender, getUsage(sender));
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, CommandSource sender, String[] args, BlockPos pos) {
		return SUBCOMMANDS;
	}
}
