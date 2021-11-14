package me.astropvp.struct;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.WordUtils;
import org.bukkit.block.BlockFace;

public enum GenBucketDirection {
    HORIZONTAL(BlockFace.NORTH, 100, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST),
    VERTICAL(BlockFace.DOWN, 256, BlockFace.UP, BlockFace.DOWN);

    private final BlockFace defaultBlockFace;
    private final int maxBlocks;
    private final List<BlockFace> blockFaces;

    public BlockFace getDefaultBlockFace() {
        return this.defaultBlockFace;
    }

    public int getMaxBlocks() {
        return this.maxBlocks;
    }

    public List<BlockFace> getBlockFaces() {
        return this.blockFaces;
    }

    private GenBucketDirection(BlockFace defaultBlockFace, int maxBlocks, BlockFace... applicableBlockFaces) {
        this.defaultBlockFace = defaultBlockFace;
        this.maxBlocks = maxBlocks;
        this.blockFaces = Arrays.asList(applicableBlockFaces);
    }

    public String niceName() {
        return WordUtils.capitalizeFully(this.name());
    }

    public boolean isApplicable(BlockFace blockFace) {
        return this.blockFaces.contains(blockFace);
    }
}
