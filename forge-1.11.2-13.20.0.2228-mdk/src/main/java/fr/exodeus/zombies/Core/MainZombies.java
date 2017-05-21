package fr.exodeus.zombies.Core;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.exodeus.zombies.Objects.Capabilities.Capabilities;
import fr.exodeus.zombies.Objects.Capabilities.Capabilities.IPlayerExtendedProperties;
import fr.exodeus.zombies.Objects.Capabilities.PacketSyncPlayerData;
import fr.exodeus.zombies.Objects.Entity.EntityCreator;
import fr.exodeus.zombies.Objects.Game.Thirst.ThirstLogic;
import fr.exodeus.zombies.Objects.Items.CleanChain;
import fr.exodeus.zombies.Objects.Items.IronNugget;
import fr.exodeus.zombies.Objects.Items.RustyChain;
import fr.exodeus.zombies.Objects.Items.SandDust;
import fr.exodeus.zombies.Objects.Items.Drinks.PureWater;
import fr.exodeus.zombies.Objects.Items.Food.CookedFlesh;
import fr.exodeus.zombies.Objects.Items.Food.Donut;
import fr.exodeus.zombies.Objects.Items.Food.PurifiedFlesh;
import fr.exodeus.zombies.Objects.Items.Usable.Antibiotic;
import fr.exodeus.zombies.Objects.Items.Usable.Bandage;
import fr.exodeus.zombies.Objects.Potion.Bonebreak;
import fr.exodeus.zombies.Objects.Potion.Infection;
import fr.exodeus.zombies.Objects.Recipe.Recipes;
import fr.exodeus.zombies.Objects.Tab.ZombiesTab;
import fr.exodeus.zombies.Proxy.CommonProxy;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;


@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)

public class MainZombies {
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public SimpleNetworkWrapper network;
	
	@CapabilityInject(IPlayerExtendedProperties.class)
	public static final Capability<IPlayerExtendedProperties> CAPABILITY_THIRST = null;
	
	@Instance
	public static MainZombies instance;
	
	public static final Logger logger = LogManager.getLogger(Reference.MOD_NAME);
	public static void logString(String msg){
		logger.log(Level.INFO, msg);
	}
	
	//===============================================================================================================
	
	public static final ZombiesTab tabZombies = new ZombiesTab();
	
	//===============================================================================================================
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//Simple Items
		CleanChain.init();
		CleanChain.register();
		IronNugget.init();
		IronNugget.register();
		RustyChain.init();
		RustyChain.register();
		SandDust.init();
		SandDust.register();
		//Food Items
		CookedFlesh.init();
		CookedFlesh.register();
		Donut.init();
		Donut.register();
		PurifiedFlesh.init();
		PurifiedFlesh.register();
		//Usable Items
		Antibiotic.init();
		Antibiotic.register();
		Bandage.init();
		Bandage.register();
		//Drinks
		PureWater.init();
		PureWater.register();
		
		//Starting Network
		network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
	
		//Capabilities
		network.registerMessage(PacketSyncPlayerData.class, PacketSyncPlayerData.class, PacketSyncPlayerData.ID, Side.CLIENT);
		
		Capabilities.register();
		
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
		Recipes.loadRecipes();
		
		EntityCreator.registerEntities();
		
		Infection.init();
		Bonebreak.init();
		
		proxy.registerRenders();
	}
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
		Reference.registerAllPotionEffects();
		
		//Register Events
		proxy.registerEvents();
		
		ThirstLogic.initThirstDamager();
		
	}
	
	
}
