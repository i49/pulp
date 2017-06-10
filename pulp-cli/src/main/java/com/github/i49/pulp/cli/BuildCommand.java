/* 
 * Copyright 2017 The Pulp Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.i49.pulp.cli;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import com.github.i49.pulp.api.publication.Epub;
import com.github.i49.pulp.api.publication.Publication;
import com.github.i49.pulp.api.publication.PublicationWriter;

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

		try (PublicationWriter writer = Epub.createWriter(target)) {
			writer.write(publication);
		}
	}

	private static Path getDefaultTargetPath(Path sourceDir) {
		String filename = sourceDir.getFileName().toString();
		Path parent = sourceDir.toAbsolutePath().getParent();
		return parent.resolve(filename + ".epub");
	}
}
