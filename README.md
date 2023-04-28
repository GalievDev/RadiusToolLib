# RadiusToolLib
Lib for creating radius tools

# Adding the dependency

```gradle
repositories {
    maven {
        url = "https://api.modrinth.com/maven"
    }
}

dependencies {
    // Option 1: Include Ducky Updater to project for it available within your own jar (additional ~20kb)
    include(modImplementation("maven.modrinth:rt-lib:<mod-version>"))
    
    // Option 2: Depend on Ducky Updater, but require that users install it manually
    modImplementation("maven.modrinth:rt-lib:<mod-version>")
}
```

```json
"depends": {
    "fabricloader": "*",
    ...
    "rt-lib": "*"
},
```

# Usage

```java
public class ModName implements ModInitializer {
                                                                          //Material           //Damage&Speed                   //Radius
    public static final RadiusPickaxe RADIUS_PICKAXE = new RadiusPickaxe(ToolMaterials.NETHERITE, 4, 1.0f, new FabricItemSettings(), 1);
    // For the tool to dig 3x3 - range: 1, 5x5 - range: 2; And so on...
    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM, new Identifier("your_mod_id", "radius_pickaxe"), RADIUS_PICKAXE)
    }
}
```
