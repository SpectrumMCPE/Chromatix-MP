package chromatix.level.generator.holder;

import chromatix.utils.random.RandomSourceProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RandomizedObjectHolder extends ObjectHolder {

    protected RandomSourceProvider randomSourceProvider;

}
