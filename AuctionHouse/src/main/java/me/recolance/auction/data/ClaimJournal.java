package me.recolance.auction.data;

import me.recolance.auction.Auction;
import me.recolance.auction.auction.UnclaimedListing;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

/** Interruptions spanning player inventory and SQL require reconciliation, not duplicate grants. */
public final class ClaimJournal {
    private final File record;
    public ClaimJournal(UnclaimedListing listing) {
        record=new File(new File(Auction.plugin.getDataFolder(),"pending-claims"),listing.getId()+".yml");
    }
    public boolean exists() { return record.exists(); }
    public void begin(Player player, UnclaimedListing listing) {
        try {
            record.getParentFile().mkdirs();
            YamlConfiguration state=new YamlConfiguration();
            state.set("player",player.getUniqueId().toString());state.set("listing",listing.getId().toString());
            state.set("item",DataHandler.toString(listing.getItem()));state.set("phase","grant-pending");
            Path temp=new File(record.getParentFile(),record.getName()+".tmp").toPath();
            try(FileOutputStream out=new FileOutputStream(temp.toFile())){out.write(state.saveToString().getBytes(StandardCharsets.UTF_8));out.getFD().sync();}
            Files.move(temp,record.toPath(),StandardCopyOption.ATOMIC_MOVE,StandardCopyOption.REPLACE_EXISTING);
        }catch(Exception error){throw new IllegalStateException("Cannot journal reward claim",error);}
    }
    public void saveInventory(Player player) {
        player.saveData();
        File data=new File(new File(org.bukkit.Bukkit.getWorlds().get(0).getWorldFolder(),"playerdata"),player.getUniqueId()+".dat");
        try(java.nio.channels.FileChannel channel=java.nio.channels.FileChannel.open(data.toPath(),StandardOpenOption.WRITE)) {
            channel.force(true);
        }catch(IOException error){throw new IllegalStateException("Cannot confirm saved reward inventory; preserve claim journal",error);}
    }
    public void finish(UnclaimedListing listing) {
        DataHandler.deleteUnclaimedListing(listing,false);
        try(java.sql.PreparedStatement statement=Database.getConnection().prepareStatement("SELECT id FROM expired WHERE id=?")) {
            statement.setString(1,listing.getId().toString());
            try(java.sql.ResultSet row=statement.executeQuery()){if(row.next())throw new java.sql.SQLException("Claim deletion did not commit");}
            Files.delete(record.toPath());
        }catch(Exception error){throw new IllegalStateException("Claim requires reconciliation; preserve pending-claims record",error);}
    }
}
