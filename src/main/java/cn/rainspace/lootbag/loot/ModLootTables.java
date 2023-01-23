package cn.rainspace.lootbag.loot;

import cn.rainspace.lootbag.utils.Const;
import com.google.common.collect.Sets;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.Set;

public class ModLootTables {
    private static final Set<ResourceLocation> LOCATIONS = Sets.newHashSet();
    public static final ResourceLocation LOOT_BAG_GIFT = register("gameplay/loot_bag_gift");
    public static final ResourceLocation EXTRA_GIFT = register("gameplay/extra_gift");
    public static final ResourceLocation BASTION_REMNANT = register("gameplay/bastion_remnant");
    public static final ResourceLocation ANCIENT_CITY = register("gameplay/ancient_city");
    public static final ResourceLocation STRONGHOLD = register("gameplay/stronghold");
    private static final Set<ResourceLocation> IMMUTABLE_LOCATIONS = Collections.unmodifiableSet(LOCATIONS);

    private static ResourceLocation register(String p_186373_0_) {
        return register(new ResourceLocation(Const.MOD_ID, p_186373_0_));
    }

    private static ResourceLocation register(ResourceLocation p_186375_0_) {
        if (LOCATIONS.add(p_186375_0_)) {
            return p_186375_0_;
        } else {
            throw new IllegalArgumentException(p_186375_0_ + " is already a registered built-in loot table");
        }
    }

    public static Set<ResourceLocation> all() {
        return IMMUTABLE_LOCATIONS;
    }
}
