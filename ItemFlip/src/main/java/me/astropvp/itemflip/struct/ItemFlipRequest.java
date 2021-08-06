 package me.astropvp.itemflip.struct;

 import com.cosmicpvp.cosmicutils.utils.CC;
 import com.google.common.collect.Lists;
 import java.util.List;
 import me.astropvp.itemflip.AstroItemFlipPlugin;
 import org.bukkit.Bukkit;
 import org.bukkit.entity.Player;
 import org.bukkit.plugin.Plugin;
 import org.bukkit.scheduler.BukkitRunnable;


 public class ItemFlipRequest
 {
   private static List<ItemFlipRequest> requests = Lists.newArrayList();

   private Player sender;
   private Player receiver;
   private long created;

   public ItemFlipRequest(Player sender, Player receiver) {
     this.sender = sender;
     this.receiver = receiver;
     requests.add(this);
   }

   public Player getSender() {
     return this.sender;
   }

   public Player getReceiver() {
     return this.receiver;
   }

   public long getTimeout() {
     return this.created + 10000L - System.currentTimeMillis();
   }

   public boolean canTimeout() {
     return (getTimeout() <= 0L);
   }

   public void remove() {
     requests.remove(this);
   }

   public void send() {
     this.created = System.currentTimeMillis();
     this.sender.sendMessage("");
     this.sender.sendMessage(CC.GoldB + "(/ITEMFLIP) " + CC.Gold + "You sent an Item Flip Request to " + CC.Gray + this.receiver.getName() + CC.Gold + "!");
     this.sender.sendMessage("");
     this.receiver.sendMessage("");
     this.receiver.sendMessage(CC.GoldB + "(/ITEMFLIP " + this.sender.getName() + ") " + CC.Gold + "You have received a /itemflip request from " + CC.Gray + this.sender.getName() + CC.Gold + "!");
     this.receiver.sendMessage("");
     final ItemFlipRequest instance = this;
     Bukkit.getScheduler().runTaskLater((Plugin)AstroItemFlipPlugin.getInstance(), (Runnable)new BukkitRunnable()
         {
           public void run() {
             if (ItemFlipRequest.requests.contains(instance)) {
               ItemFlipRequest.requests.remove(instance);
               if (ItemFlipRequest.this.sender.isOnline()) {
                 ItemFlipRequest.this.sender.sendMessage(CC.RedB + "(/ITEMFLIP) " + CC.Red + "Your Item Flip Request to " + ItemFlipRequest.this.receiver.getName() + " has expired!");
               }
               if (ItemFlipRequest.this.receiver.isOnline()) {
                 ItemFlipRequest.this.receiver.sendMessage(CC.RedB + "(/ITEMFLIP) " + CC.Red + ItemFlipRequest.this.sender.getName() + "'s Item Flip Request to you has expired!");
               }
             }
           }
         },  200L);
   }

   public static ItemFlipRequest findInstance(Player player) {
     for (ItemFlipRequest request : requests) {
       if (request.getReceiver() == player || request.getSender() == player) {
         return request;
       }
     }
     return null;
   }
 }
