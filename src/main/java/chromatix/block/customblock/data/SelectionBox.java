package chromatix.block.customblock.data;

import chromatix.nbt.tag.CompoundTag;
import chromatix.nbt.tag.FloatTag;
import chromatix.nbt.tag.ListTag;

public record SelectionBox(double originX, double originY, double originZ, double sizeX, double sizeY, double sizeZ) implements NBTData {
    public CompoundTag toCompoundTag() {
        return new CompoundTag()
                .putBoolean("enabled", true)
                .putList("origin", new ListTag<>()
                        .add(new FloatTag((float) originX))
                        .add(new FloatTag((float) originY))
                        .add(new FloatTag((float) originZ)))
                .putList("size", new ListTag<>()
                        .add(new FloatTag((float) sizeX))
                        .add(new FloatTag((float) sizeY))
                        .add(new FloatTag((float) sizeZ)));
    }
}