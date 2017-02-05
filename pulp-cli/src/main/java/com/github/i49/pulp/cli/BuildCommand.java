package com.github.i49.pulp.cli;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import com.github.i49.pulp.api.Epub;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationWriter;

public class BuildCommand extends Command {

	private Path source;
	private Path target;

	private Order order = Order.ASCENDING;
	
	BuildCommand() {
	}
	
	@Override
	protected void parseArguments(Iterator<String> args) {
		while (args.hasNext()) {
			String arg = args.next();
			if (arg.startsWith("-")) {
				if (arg.equals("-output")) {
					this.target = Paths.get(args.next());
				} else {
					throw new CommandException("Unknown option: " + arg);
				}
			} else if (source == null) {
				this.source = Paths.get(arg);
			}
		}

		if (this.source == null) {
			throw new CommandException("Source directory is not specified.");
		} else if (!Files.isDirectory(this.source)) {
			throw new CommandException("Source directory does not exist.");
		}
		
		if (this.target == null) {
			this.target = getDefaultTargetPath(this.source);
		}
	}

	@Override
	protected int run() {
		try {
			build(this.source, this.target);
			return 0;
		} catch (Exception e) {
			throw new CommandException(e);
		}
	}
	
	private void build(Path source, Path target) throws Exception {
		PublicationCompiler builder = new PublicationCompiler(source);
		builder.setDocumentOrder(this.order);
		Publication publication = builder.compile();

		try (PublicationWriter writer = Epub.createWriter(Files.newOutputStream(target))) {
			writer.write(publication);
		}
	}

	private static Path getDefaultTargetPath(Path sourceDir) {
		String filename = sourceDir.getFileName().toString();
		Path parent = sourceDir.toAbsolutePath().getParent();
		return parent.resolve(filename + ".epub");
	}
}
