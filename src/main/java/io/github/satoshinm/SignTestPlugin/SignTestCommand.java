
package io.github.satoshinm.SignTestPlugin;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.logging.Level;

// based on:
/**
 * Handler for the /pos sample command.
 * @author SpaceManiac
 */
public class SignTestCommand implements CommandExecutor {
    private boolean setSign(Player player, Block block) {
        org.bukkit.material.Sign signDirection = new org.bukkit.material.Sign();
        BlockFace blockFace = BlockFace.NORTH;
        signDirection.setFacingDirection(blockFace);

        BlockState blockState = block.getState();
        blockState.setType(Material.WALL_SIGN);
        blockState.setData(signDirection);
        boolean force = true;
        boolean applyPhysics = false;
        blockState.update(force, applyPhysics);
        player.sendMessage("Set state to "+blockState);

        blockState = block.getState();
        if (!(blockState instanceof Sign)) {
            player.sendMessage("Failed to place sign, BlockStace not instanceof Sign");
            return false;
        }
        Sign sign = (Sign) blockState;

        // TODO: text lines by 15 characters into 5 lines
        sign.setLine(0, "test");
        sign.update(force, applyPhysics);

        if (!(blockState.getData() instanceof org.bukkit.material.Sign)) {
            player.sendMessage("MaterialData not instanceof material.Sign");
            return false;
        }

        return true; // ok
    }

    private void getSign(Player player, Block block) {
        BlockState blockState = block.getState();

        org.bukkit.material.Sign sign = (org.bukkit.material.Sign) blockState.getData();
        try {
            BlockFace blockFace = sign.getFacing();
            player.sendMessage("blockFace = "+blockFace);

        } catch (NullPointerException ex) {
            // Bukkit will crash but Glowkit will
            // ignore invalid data, https://github.com/GlowstoneMC/Glowstone/issues/484
            player.sendMessage("Invalid sign data: "+ex);
            ex.printStackTrace();
        }
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;


        Set<Material> transparentMaterials = null;
        Block block = player.getTargetBlock(transparentMaterials, 100);

        player.sendMessage("Previous block: " + block);

        if (!setSign(player, block)) {
            return true;
        }

        getSign(player, block);

        return true;
    }
}
