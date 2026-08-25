package chromatix.block.customblock.data;

import chromatix.nbt.tag.CompoundTag;
import chromatix.nbt.tag.ListTag;
import chromatix.nbt.tag.StringTag;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public record CraftingTable(@NotNull String tableName, @Nullable List<String> craftingTags) implements NBTData {
    public CompoundTag toCompoundTag() {
        var listTag = new ListTag<StringTag>();
        if (craftingTags != null) {
            craftingTags.forEach(t -> listTag.add(new StringTag(t)));
        }
        return new CompoundTag()
                .putList("crafting_tags", listTag)
                .putInt("grid_size", 3)
                .putString("table_name", tableName);
    }
}