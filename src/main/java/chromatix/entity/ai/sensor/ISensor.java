package chromatix.entity.ai.sensor;

import chromatix.entity.EntityIntelligent;
import chromatix.entity.ai.memory.IMemoryStorage;

/**
 * This interface abstracts a sensor<br>
 * The sensor is used to collect environmental information and write a memory {@link chromatix.entity.ai.memory.MemoryType} to the memory storage {@link IMemoryStorage}
 */


public interface ISensor {

    /**
     * @param entity the target entity
     */
    void sense(EntityIntelligent entity);

    /**
     * Returns the refresh period of this sensor, a small refresh period will make the sensor be called more frequently
     *
     * @return the refresh period
     */
    default int getPeriod() {
        return 1;
    }
}
