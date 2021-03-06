/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.context.embedded;

import javax.servlet.MultipartConfigElement;

import org.springframework.util.Assert;

/**
 * Factory that can be used to create a {@link MultipartConfigElement}. Size values can be
 * set using traditional {@literal long} values or using more readable {@literal String}
 * variants that accept KB or MB suffixes, for example:
 * 
 * <pre class="code">
 * factory.setMaxFileSize(&quot;10Mb&quot;);
 * factory.setMaxRequestSize(&quot;100Kb&quot;);
 * </pre>
 * 
 * @author Phillip Webb
 */
public class MultipartConfigFactory {

	private String location;

	private long maxFileSize = -1;

	private long maxRequestSize = -1;

	private int fileSizeThreshold = 0;

	/**
	 * Sets the directory location where files will be stored.
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Sets the maximum size allowed for uploaded files.
	 * @see #setMaxFileSize(String)
	 */
	public void setMaxFileSize(long maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	/**
	 * Sets the maximum size allowed for uploaded files. Values can use the suffixed "MB"
	 * or "KB" to indicate a Megabyte or Kilobyte size.
	 * @see #setMaxFileSize(long)
	 */
	public void setMaxFileSize(String maxFileSize) {
		this.maxFileSize = parseSize(maxFileSize);
	}

	/**
	 * Sets the maximum size allowed for multipart/form-data requests.
	 * @see #setMaxRequestSize(String)
	 */
	public void setMaxRequestSize(long maxRequestSize) {
		this.maxRequestSize = maxRequestSize;
	}

	/**
	 * Sets the maximum size allowed for multipart/form-data requests. Values can use the
	 * suffixed "MB" or "KB" to indicate a Megabyte or Kilobyte size.
	 * @see #setMaxRequestSize(long)
	 */
	public void setMaxRequestSize(String maxRequestSize) {
		this.maxRequestSize = parseSize(maxRequestSize);
	}

	/**
	 * Sets the size threshold after which files will be written to disk.
	 * @see #setFileSizeThreshold(String)
	 */
	public void setFileSizeThreshold(int fileSizeThreshold) {
		this.fileSizeThreshold = fileSizeThreshold;
	}

	/**
	 * Sets the size threshold after which files will be written to disk. Values can use
	 * the suffixed "MB" or "KB" to indicate a Megabyte or Kilobyte size.
	 * @see #setFileSizeThreshold(int)
	 */
	public void setFileSizeThreshold(String fileSizeThreshold) {
		this.fileSizeThreshold = (int) parseSize(fileSizeThreshold);
	}

	private long parseSize(String size) {
		Assert.hasLength(size, "Size must not be empty");
		size = size.toUpperCase();
		if (size.endsWith("KB")) {
			return Long.valueOf(size.substring(0, size.length() - 2)) * 1024;
		}
		if (size.endsWith("MB")) {
			return Long.valueOf(size.substring(0, size.length() - 2)) * 1024 * 1024;
		}
		return Long.valueOf(size);
	}

	/**
	 * Create a new {@link MultipartConfigElement} instance.
	 */
	public MultipartConfigElement createMultipartConfig() {
		return new MultipartConfigElement(this.location, this.maxFileSize,
				this.maxRequestSize, this.fileSizeThreshold);
	}

}
