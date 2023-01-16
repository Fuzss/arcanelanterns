package fuzs.arcanelanterns;

import fuzs.arcanelanterns.init.ModRegistry;
import fuzs.arcanelanterns.networking.ClientboundBorealParticlesMessage;
import fuzs.arcanelanterns.networking.ClientboundContainingSoundsMessage;
import fuzs.arcanelanterns.networking.ClientboundCraftLanternParticlesMessage;
import fuzs.arcanelanterns.networking.ClientboundWailingSoundsMessage;
import fuzs.puzzleslib.api.networking.v3.NetworkHandlerV3;
import fuzs.puzzleslib.core.ModConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArcaneLanterns implements ModConstructor {
	public static final String MOD_ID = "arcanelanterns";
	public static final String MOD_NAME = "Arcane Lanterns";
	public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

	public static final NetworkHandlerV3 NETWORK = NetworkHandlerV3.builder(MOD_ID).registerClientbound(ClientboundBorealParticlesMessage.class).registerClientbound(ClientboundContainingSoundsMessage.class).registerClientbound(ClientboundCraftLanternParticlesMessage.class).registerClientbound(ClientboundWailingSoundsMessage.class).build();

	@Override
	public void onConstructMod() {
		ModRegistry.touch();
	}
}
