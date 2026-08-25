package chromatix.entity.ai.executor;

import chromatix.entity.EntityIntelligent;
import chromatix.math.Vector3;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JumpExecutor implements IBehaviorExecutor {

    @Override
    public boolean execute(EntityIntelligent entity) {
        entity.setMotion(new Vector3(0, entity.getJumpingMotion(0.4), 0));
        return true;
    }
}
