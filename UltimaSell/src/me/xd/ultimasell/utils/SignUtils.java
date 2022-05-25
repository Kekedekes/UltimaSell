package me.xd.ultimasell.utils;

import java.util.Optional;
import me.xd.ultimasell.settings.Settings;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;

public class SignUtils {
    public static boolean isAttached(Sign sign) {
        Block attached = sign.getBlock().getRelative(BlockFace.DOWN);
        if (sign.getBlock().getBlockData() instanceof WallSign) {
            WallSign value = (WallSign)sign.getBlock().getBlockData();
            attached = sign.getBlock().getRelative(value.getFacing().getOppositeFace());
        }
        return attached.getType() == Material.CHEST || attached.getType() == Material.TRAPPED_CHEST;
    }

    public static boolean isValid(Sign sign) {
        String line = sign.getLine(0);
        return line.equals(Settings.SIGN_LINE);
    }

    public static Optional<Sign> getAttached(Block block) {
        BlockFace[] faces = new BlockFace[]{BlockFace.UP, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST};
        BlockFace[] blockFaceArray = faces;
        int n = faces.length;
        int n2 = 0;
        while (n2 < n) {
            BlockFace face = blockFaceArray[n2];
            Block relative = block.getRelative(face);
            Material material = relative.getType();
            if (material.name().endsWith("SIGN")) {
                Sign sign = (Sign)relative.getState();
                Block attached = sign.getBlock().getRelative(BlockFace.DOWN);
                if (sign.getBlock().getBlockData() instanceof WallSign) {
                    WallSign value = (WallSign)sign.getBlock().getBlockData();
                    attached = sign.getBlock().getRelative(value.getFacing().getOppositeFace());
                }
                if (attached.equals(block)) {
                    return Optional.of(sign);
                }
            }
            ++n2;
        }
        return Optional.empty();
    }

    public static Optional<Sign> getAttached(DoubleChest doubleChest) {
        Chest rightSide = (Chest)doubleChest.getRightSide();
        Optional<Sign> rightSideSign = SignUtils.getAttached(rightSide.getBlock());
        Chest leftSide = (Chest)doubleChest.getLeftSide();
        Optional<Sign> leftSideSign = SignUtils.getAttached(leftSide.getBlock());
        return rightSideSign.isPresent() ? rightSideSign : leftSideSign;
    }
}

