package me.ifamasssxd.cosmicbosses.struct;

import java.util.*;
import org.bukkit.entity.*;

public interface EntityContainer
{
    Set<Entity> getEntities();
    
    void clearEntities();
}
