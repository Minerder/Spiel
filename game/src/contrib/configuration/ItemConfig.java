package contrib.configuration;

import contrib.utils.components.item.ItemClassification;

import core.configuration.ConfigKey;
import core.configuration.ConfigMap;
import core.configuration.values.ConfigEnumValue;
import core.configuration.values.ConfigStringValue;

/** The default ItemData values */
@ConfigMap(path = {"item"})
public class ItemConfig {
    /** The Description of the Default ItemData */
    public static final ConfigKey<String> DESCRIPTION =
            new ConfigKey<>(new String[] {"description"}, new ConfigStringValue("Default Item"));

    /** The Name of the Default ItemData */
    public static final ConfigKey<String> NAME =
            new ConfigKey<>(new String[] {"name"}, new ConfigStringValue("Defaultname"));

    /** The Type of the Default ItemData */
    public static final ConfigKey<ItemClassification> TYPE =
            new ConfigKey<>(new String[] {"type"}, new ConfigEnumValue<>(ItemClassification.Basic));

    /** The texturepath of the Default ItemData will be used for world and Inventory */
    public static final ConfigKey<String> TEXTURE =
            new ConfigKey<>(
                    new String[] {"texture"},
                    new ConfigStringValue("animation/missingTexture.png"));
}
