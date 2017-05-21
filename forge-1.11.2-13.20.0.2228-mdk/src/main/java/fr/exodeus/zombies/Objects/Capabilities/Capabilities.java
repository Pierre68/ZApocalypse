package fr.exodeus.zombies.Objects.Capabilities;

import fr.exodeus.zombies.Core.MainZombies;
import fr.exodeus.zombies.Core.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class Capabilities {
  public static void register() {
    CapabilityManager.INSTANCE.register(IPlayerExtendedProperties.class, new Storage(),
        InstancePlayerExtendedProperties.class);
  }
  public static IPlayerExtendedProperties getPlayerProperties(EntityPlayer player) {
	  
    if (player == null) {
      return null;
    }
    
    IPlayerExtendedProperties props = player.getCapability(MainZombies.CAPABILITY_THIRST, null);
    return props;
  }
  public interface IPlayerExtendedProperties {
    boolean isEPearlUnlocked();
    void setEPearlUnlocked(boolean value);
    
    boolean isEChestUnlocked();
    void setEChestUnlocked(boolean value);
    
    int getThirstLevel();
    void setThirstLevel(int value);
    
    float getThirstSaturation();
    void setThirstSaturation(float value);
    
    float getThirstExhaustion();
    void setThirstExhaustion(float value);
    
    
    // ---------------
    NBTTagCompound getDataAsNBT();
    void setDataFromNBT(NBTTagCompound nbt);
    //summary
    boolean hasStorage(int k);
  }
  public static class InstancePlayerExtendedProperties implements IPlayerExtendedProperties {
    private boolean hasEPearl = false;
    private boolean hasEChest = false;
    private int thirstLevel = Reference.INITIAL_THIRST_LEVEL;
    private float thirstSaturation = Reference.INITIAL_SATURATION;
    private float thirstExhaustion = 0;
    
    @Override
    public NBTTagCompound getDataAsNBT() {
      NBTTagCompound tags = new NBTTagCompound();
      tags.setByte("isEPearlUnlocked", (byte) (this.isEPearlUnlocked() ? 1 : 0));
      tags.setByte("isEChestUnlocked", (byte) (this.isEChestUnlocked() ? 1 : 0));
      tags.setInteger("getThirstLevel", this.getThirstLevel());
      tags.setFloat("getThirstSaturation", this.getThirstSaturation());
      tags.setFloat("getThirstExhaustion", this.getThirstExhaustion());
      return tags;
    }
    @Override
    public void setDataFromNBT(NBTTagCompound nbt) {
      NBTTagCompound tags;
      if (nbt instanceof NBTTagCompound == false) {
        tags = new NBTTagCompound();
      }
      else {
        tags = (NBTTagCompound) nbt;
      }
      this.setEPearlUnlocked(tags.getByte("isEPearlUnlocked") == 1);
      this.setEChestUnlocked(tags.getByte("isEChestUnlocked") == 1);
      this.setThirstLevel(tags.getInteger("getThirstLevel"));
      this.setThirstSaturation(tags.getFloat("getThirstSaturation"));
      this.setThirstExhaustion(tags.getFloat("getThirstExhaustion"));
    };
    @Override
    public boolean isEPearlUnlocked() {
      return this.hasEPearl;
    }
    @Override
    public void setEPearlUnlocked(boolean value) {
      this.hasEPearl = value;
    }
    @Override
    public boolean isEChestUnlocked() {
      return this.hasEChest;
    }
    @Override
    public void setEChestUnlocked(boolean value) {
      this.hasEChest = value;
    }
    @Override
    public int getThirstLevel() {
      /*if (this.storageCount == 20) {
        this.storageCount -= 20;//??HAXS. still not sure about why that happened
      }*/ //TODO moi ca peut etre 20 donc pas de reset !
      return this.thirstLevel;
    }
    @Override
    public void setThirstLevel(int value) {
      this.thirstLevel = value;
    }
    @Override
    public boolean hasStorage(int k) {
      return this.getThirstLevel() >= k;
    }
	@Override
	public float getThirstSaturation() {
		return this.thirstSaturation;
	}
	@Override
	public void setThirstSaturation(float value) {
		this.thirstSaturation = value;
	}
	@Override
	public float getThirstExhaustion() {
		return this.thirstExhaustion;
	}
	@Override
	public void setThirstExhaustion(float value) {
		this.thirstExhaustion = value;
	}
  }
  public static class Storage implements IStorage<IPlayerExtendedProperties> {
    @Override
    public NBTTagCompound writeNBT(Capability<IPlayerExtendedProperties> capability, IPlayerExtendedProperties instance, EnumFacing side) {
      return instance.getDataAsNBT();
    }
    @Override
    public void readNBT(Capability<IPlayerExtendedProperties> capability, IPlayerExtendedProperties instance, EnumFacing side, NBTBase nbt) {
      try {
        instance.setDataFromNBT((NBTTagCompound) nbt);
      }
      catch (Exception e) {
        //Invalid NBT compound
        e.printStackTrace();
      }
    }
  }
  public static void syncServerDataToClient(EntityPlayerMP p) {
    if (p == null) { return; }
    IPlayerExtendedProperties props = Capabilities.getPlayerProperties(p);
    if (props != null) {
      MainZombies.instance.network.sendTo(new PacketSyncPlayerData(props.getDataAsNBT()), p);
      //MainZombies.logString("Capabilities: syncClient");
    }
  }
}