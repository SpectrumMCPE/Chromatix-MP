package chromatix.block.customblock.data;

import chromatix.nbt.tag.CompoundTag;
import chromatix.nbt.tag.ListTag;
import chromatix.nbt.tag.StringTag;

/**
 * The type Permutation builder.
 */

public record Permutation(Component component, String condition, String[] blockTags) implements NBTData {

    public Permutation(Component component, String condition) {
        this(component, condition, new String[]{});
    }

    @Override
    public CompoundTag toCompoundTag() {
        CompoundTag result = new CompoundTag()
                .putCompound("components", component.toCompoundTag())
                .putString("condition", condition);
        ListTag<StringTag> stringTagListTag = new ListTag<>();
        for (String s : blockTags) {
            stringTagListTag.add(new StringTag(s));
        }
        if (stringTagListTag.size() > 0) {
            result.putList("blockTags", stringTagListTag);
        }
        return result;
    }
}