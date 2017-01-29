package com.github.i49.pulp.cli;

import java.util.Iterator;

/**
 * A command to be executed.
 */
public abstract class Command {

	public int execute(Iterator<String> args) {
		parseArguments(args);
		return run();
	}
	
	abstract protected void parseArguments(Iterator<String> args);
	
	abstract protected int run();
}
