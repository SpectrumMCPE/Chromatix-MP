package chromatix.level.generator.object;

import chromatix.level.generator.object.legacytree.LegacyTreeGenerator;
import chromatix.math.Vector3;
import chromatix.utils.random.RandomSourceProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ObjectLegacyObjectWrapper extends TreeGenerator {

    private LegacyTreeGenerator legacyTreeGenerator;

    @Override
    public boolean generate(BlockManager level, RandomSourceProvider rand, Vector3 position) {
        if(legacyTreeGenerator != null) {
            legacyTreeGenerator.placeObject(level, position.getFloorX(), position.getFloorY(), position.getFloorZ(), rand);
            return true;
        }
        return false;
    }

}