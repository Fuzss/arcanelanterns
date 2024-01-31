package fuzs.arcanelanterns.client;

import fuzs.arcanelanterns.ArcaneLanterns;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;

public class ArcaneLanternsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(ArcaneLanterns.MOD_ID, ArcaneLanternsClient::new);
    }
}
