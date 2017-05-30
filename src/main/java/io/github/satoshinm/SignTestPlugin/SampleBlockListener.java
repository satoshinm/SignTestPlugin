
package io.github.satoshinm.SignTestPlugin;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Sign;

public class SampleBlockListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();

        dumpSign(block);
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();

        dumpSign(block);
    }

    private void dumpSign(Block block) {
        if (block == null) return;

        if (block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST) {
            System.out.println("\nblock = "+block);

            MaterialData materialData = block.getState().getData();

            System.out.println("materialData = "+materialData);
            if (materialData instanceof Sign) {
                Sign sign = (Sign) materialData;

                try {
                    System.out.println("getAttachedFace = "+sign.getAttachedFace());
                } catch (Exception ex) {
                    System.out.println("getAttachedFace exception: " + ex);
                    ex.printStackTrace();
                }

                try {
                    System.out.println("getFacing = "+sign.getFacing());
                } catch (Exception ex) {
                    System.out.println("getFacing exception: " + ex);
                    ex.printStackTrace();
                }

                System.out.println("isWallSign? " + sign.isWallSign());

                System.out.println("getData (deprecated) = " + sign.getData());
            } else {
                System.out.println("material is not Sign: " + materialData + " at " + block);
            }
        }
    }
}
