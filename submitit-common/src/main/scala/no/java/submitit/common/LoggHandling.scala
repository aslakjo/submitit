/*
 * Copyright 2009 javaBin
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package no.java.submitit.common

import org.slf4j.{Logger, LoggerFactory}

@serializable
trait LoggHandling {
	
	protected def logger = log
  
	@transient
	private var log = createLogger
 
	private def createLogger = LoggerFactory.getLogger(getClass)
 
	private def readResolve = {
		log = createLogger; 
		this
	}
 
}