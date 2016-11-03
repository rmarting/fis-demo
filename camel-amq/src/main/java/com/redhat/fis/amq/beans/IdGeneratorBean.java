package com.redhat.fis.amq.beans;

import java.util.Random;

/**
 * A bean which we use in the route
 */
public class IdGeneratorBean implements IdGenerator {

	private Random random = new Random();

	@Override
	public String generateNewID() {
		return "" + random.nextInt(10000);
	}

}
