package fuzs.arcanelanterns;

import fuzs.arcanelanterns.config.ServerConfig;
import fuzs.arcanelanterns.init.ModRegistry;
import fuzs.arcanelanterns.networking.ClientboundBorealParticlesMessage;
import fuzs.arcanelanterns.networking.ClientboundContainingSoundsMessage;
import fuzs.arcanelanterns.networking.ClientboundCraftLanternParticlesMessage;
import fuzs.arcanelanterns.networking.ClientboundWailingSoundsMessage;
import fuzs.puzzleslib.api.networking.v3.NetworkHandlerV3;
import fuzs.puzzleslib.config.ConfigHolder;
import fuzs.puzzleslib.core.CommonFactories;
import fuzs.puzzleslib.core.ModConstructor;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArcaneLanterns implements ModConstructor {
	public static final String MOD_ID = "arcanelanterns";
	public static final String MOD_NAME = "Arcane Lanterns";
	public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

	@SuppressWarnings("Convert2MethodRef")
	public static final ConfigHolder CONFIG = CommonFactories.INSTANCE.serverConfig(ServerConfig.class, () -> new ServerConfig());
	public static final NetworkHandlerV3 NETWORK = NetworkHandlerV3.builder(MOD_ID).registerClientbound(ClientboundBorealParticlesMessage.class).registerClientbound(ClientboundContainingSoundsMessage.class).registerClientbound(ClientboundCraftLanternParticlesMessage.class).registerClientbound(ClientboundWailingSoundsMessage.class).build();

	@Override
	public void onConstructMod() {
		CONFIG.bakeConfigs(MOD_ID);
		ModRegistry.touch();
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
