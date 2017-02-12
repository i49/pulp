package com.github.i49.pulp.core;

import java.io.IOException;
import java.io.InputStream;

interface Content {

	InputStream openStream() throws IOException;
}
