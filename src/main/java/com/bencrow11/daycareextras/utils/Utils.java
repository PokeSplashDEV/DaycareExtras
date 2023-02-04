package com.bencrow11.daycareextras.utils;

import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.breeding.DayCareBox;
import com.pixelmonmod.pixelmon.api.storage.breeding.PlayerDayCare;

import java.util.Optional;

public class Utils {
	public static void RemovePokemonFromBox(PlayerDayCare daycare, int currentAmount, int removedAmount) {
		for (int index = currentAmount - removedAmount; index < currentAmount; index++) {
			Optional<DayCareBox> opt = daycare.getBox(index);
			if (opt.isPresent()) {
				DayCareBox box = opt.get();
				box.returnToStorage();
			}
		}
	}

	// TODO figure out how to update client.
	public static void updateClientUI(PlayerPartyStorage storage) {
	}
}
