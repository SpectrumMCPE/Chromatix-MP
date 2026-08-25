package chromatix.inventory;

import chromatix.level.Level;
import chromatix.math.Vector3;

public interface InventoryHolder {
    Inventory getInventory();

    Level getLevel();

    double getX();

    double getY();

    double getZ();

    Vector3 getVector3();
}
