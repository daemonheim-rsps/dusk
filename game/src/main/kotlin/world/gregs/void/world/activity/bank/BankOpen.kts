package world.gregs.void.world.activity.bank

import world.gregs.void.engine.action.ActionType
import world.gregs.void.engine.action.action
import world.gregs.void.engine.client.ui.awaitInterface
import world.gregs.void.engine.client.ui.close
import world.gregs.void.engine.client.ui.event.InterfaceOpened
import world.gregs.void.engine.client.ui.open
import world.gregs.void.engine.client.variable.*
import world.gregs.void.engine.entity.character.contain.sendContainer
import world.gregs.void.engine.event.then
import world.gregs.void.engine.event.where
import world.gregs.void.network.codec.game.encode.sendScript
import world.gregs.void.world.activity.bank.Bank.tabs
import world.gregs.void.world.command.Command
import world.gregs.void.world.interact.entity.player.display.InterfaceOption

IntVariable(4893, Variable.Type.VARBIT, persistent = true, defaultValue = 1).register("open_bank_tab")
IntVariable(4885, Variable.Type.VARBIT, persistent = true).register("bank_tab_1")
IntVariable(4886, Variable.Type.VARBIT, persistent = true).register("bank_tab_2")
IntVariable(4887, Variable.Type.VARBIT, persistent = true).register("bank_tab_3")
IntVariable(4888, Variable.Type.VARBIT, persistent = true).register("bank_tab_4")
IntVariable(4889, Variable.Type.VARBIT, persistent = true).register("bank_tab_5")
IntVariable(4890, Variable.Type.VARBIT, persistent = true).register("bank_tab_6")
IntVariable(4891, Variable.Type.VARBIT, persistent = true).register("bank_tab_7")
IntVariable(4892, Variable.Type.VARBIT, persistent = true).register("bank_tab_8")

Command where { prefix == "bank" } then {
    player.open("bank")
    if(player.bank.isEmpty()) {
        for(i in 1038 until 1048 step 2) {
            player.bank.add(i, 1)
        }
        player.bank.add(995, 1000)
        player.bank.add(4151, 1)
        player.bank.add(11694, 1)
        player.sendContainer("bank")
    }
}

InterfaceOpened where { name == "bank" } then {
    player.action(ActionType.Bank) {
        try {
            player.open("bank_side")
            player.sendVar("open_bank_tab")
            player.sendVar("bank_item_mode")
            for(tab in tabs) {
                player.sendVar("bank_tab_$tab")
            }
            player.sendVar("last_bank_amount")
            player.sendScript(1465)
            player.interfaceOptions.unlockAll("bank", "container", 0 until 516)
            player.interfaceOptions.unlockAll("bank_side", "container", 0 until 28)
            awaitInterface(name)
        } finally {
            player.open("inventory")
            player.close("bank")
        }
    }
}

InterfaceOption where { name == "bank" && component == "equipment" && option == "Show Equipment Stats" } then {
    player.open("equipment_bonuses")
    player.setVar("equipment_banking", true)
}

InterfaceOption where { name == "equipment_bonuses" && component == "bank" && option == "Show bank" && player.getVar("equipment_banking", false) } then {
    player.open("bank")
}