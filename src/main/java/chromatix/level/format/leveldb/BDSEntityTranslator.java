package chromatix.level.format.leveldb;

import chromatix.nbt.tag.CompoundTag;
import chromatix.nbt.tag.DoubleTag;
import chromatix.nbt.tag.FloatTag;
import chromatix.nbt.tag.LinkedCompoundTag;
import chromatix.nbt.tag.ListTag;
import chromatix.nbt.tag.Tag;
import chromatix.registry.Registries;

public final class BDSEntityTranslator {
    public static CompoundTag translate(CompoundTag from) {
        LinkedCompoundTag linkedCompoundTag = new LinkedCompoundTag();
        if (from.contains("identifier")) {
            String identifier = from.getString("identifier");
            int entityNetworkId = Registries.ENTITY.getEntityNetworkId(identifier);
            if (entityNetworkId == 0) return null;
            linkedCompoundTag.putString("identifier", identifier);
        }
        if (from.containsList("Pos", Tag.TAG_Float)) {
            ListTag<FloatTag> pos = from.getList("Pos", FloatTag.class);
            ListTag<DoubleTag> target = new ListTag<>();
            for (var v : pos.getAll()) {
                target.add(new DoubleTag(v.data));
            }
            linkedCompoundTag.putList("Pos", target);
        } else {
            ListTag<DoubleTag> target = new ListTag<>();
            target.add(new DoubleTag(0));
            target.add(new DoubleTag(0));
            target.add(new DoubleTag(0));
            linkedCompoundTag.putList("Pos", target);
        }
        if (from.containsList("Motion", Tag.TAG_Float)) {
            ListTag<FloatTag> pos = from.getList("Motion", FloatTag.class);
            ListTag<DoubleTag> target = new ListTag<>();
            for (var v : pos.getAll()) {
                target.add(new DoubleTag(v.data));
            }
            from.putList("Motion", target);
            linkedCompoundTag.putList("Motion", target);
        } else {
            ListTag<DoubleTag> target = new ListTag<>();
            target.add(new DoubleTag(0));
            target.add(new DoubleTag(0));
            target.add(new DoubleTag(0));
            linkedCompoundTag.putList("Motion", target);
        }
        linkedCompoundTag.putList("Rotation", from.getList("Rotation"));
        return linkedCompoundTag;
    }
}
