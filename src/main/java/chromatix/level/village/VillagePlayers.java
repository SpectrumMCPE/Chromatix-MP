package chromatix.level.village;

import chromatix.nbt.tag.CompoundTag;
import chromatix.nbt.tag.ListTag;
import chromatix.nbt.tag.Tag;

public record VillagePlayers(ListTag<? extends Tag> players) {
    public VillagePlayers {
        players = (ListTag<? extends Tag>) players.copy();
    }

    public static VillagePlayers fromCompound(CompoundTag tag) {
        return new VillagePlayers(tag.getList("Players"));
    }

    public CompoundTag toCompound() {
        return new CompoundTag().put("Players", players.copy());
    }
}
