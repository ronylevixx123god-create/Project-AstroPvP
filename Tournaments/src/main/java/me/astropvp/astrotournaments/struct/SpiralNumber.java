package me.astropvp.astrotournaments.struct;

public class SpiralNumber {
	
	private int min;
	private int max;
	private int value;
	
	public SpiralNumber(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	public void increment() {
		if(this.value + 1 > this.max) {
			this.value = this.min;
			return;
		}
		this.value++;
	}
	
	public int getMin() {
		return this.min;
	}
	
	public int getMax() {
		return this.max;
	}
	
	public int value() {
		return this.value;
	}
	
	@Override
	public String toString() {
		return "MIN: " + this.min + " MAX: " + this.max + " VALUE: " + this.value;
	}
	
	public static void main(String[] args) {
		SpiralNumber number = new SpiralNumber(0, 2);
		for(int i = 0; i < 9; i++) {
			number.increment();
		}
	}
}
