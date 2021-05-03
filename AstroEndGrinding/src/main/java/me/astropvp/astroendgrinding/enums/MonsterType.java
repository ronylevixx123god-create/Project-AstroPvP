package me.astropvp.astroendgrinding.enums;
import me.astropvp.astroendgrinding.entities.EndMonster;
import me.astropvp.astroendgrinding.entities.mobs.EndBasicMonster;
import me.astropvp.astroendgrinding.entities.mobs.EndCreeperMonster;
import me.astropvp.astroendgrinding.entities.mobs.EndSkeletonMonster;

public enum MonsterType {
	
	END_BASIC_MONSTER("End Zombie", new EndBasicMonster()),
	END_CREEPER_MONSTER("End Creeper", new EndCreeperMonster()),
	END_SKELETON_MONSTER("End Skeleton", new EndSkeletonMonster());
	
	private String name;
	private EndMonster instance;
	
	private MonsterType(String name, EndMonster instance) {
		this.name = name;
		this.instance = instance;
	}
	
	public EndMonster getInstance() {
		return instance;
	}
	
	public String getName() {
		return this.name;
	}
}
