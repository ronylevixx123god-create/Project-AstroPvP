package com.compilex.CosmicArmorSets.enums.armorsets;

public class AstroDamageTracker {
	private int hitsTaken;

	private long lastHitTaken;

	public boolean trackHit() {
		checkHit();
		this.hitsTaken++;
		return (this.hitsTaken >= 4);
	}

	public boolean checkHit() {
		if (this.lastHitTaken == 0L || System.currentTimeMillis() - this.lastHitTaken > 10000L) {
			this.hitsTaken = 0;
			this.lastHitTaken = System.currentTimeMillis();
		}
		return (this.hitsTaken >= 4);
	}
}