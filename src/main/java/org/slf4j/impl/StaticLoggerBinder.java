/**
 * Logback: the reliable, generic, fast and flexible logging framework.
 * Copyright (C) 1999-2013, QOS.ch. All rights reserved.
 *
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 *
 *   or (per the licensee's choosing)
 *
 * under the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation.
 */
package org.slf4j.impl;


import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

import be.tombaeyens.cbe.util.log.SimpleSlf4jILoggerFactory;

/**
 * The binding of {@link LoggerFactory} class with an actual instance of
 * {@link ILoggerFactory} is performed using information returned by this class.
 * 
 * @author Tom Baeyens
 * @author Ceki G&uuml;lc&uuml;
 */
public class StaticLoggerBinder implements LoggerFactoryBinder {

  /**
   * Declare the version of the SLF4J API this implementation is compiled
   * against. The value of this field is usually modified with each release.
   */
  // to avoid constant folding by the compiler, this field must *not* be final
  public static String REQUESTED_API_VERSION = "1.6"; // !final

  final static String NULL_CS_URL = "tombaeyens.be#null_CS";

  /**
   * The unique instance of this class.
   */
  private static StaticLoggerBinder SINGLETON = new StaticLoggerBinder();

  static {
    SINGLETON.init();
  }

  private StaticLoggerBinder() {
  }

  public static StaticLoggerBinder getSingleton() {
    return SINGLETON;
  }

  /**
   * Package access for testing purposes.
   */
  static void reset() {
  }

  /**
   * Package access for testing purposes.
   */
  void init() {
  }

  public ILoggerFactory getLoggerFactory() {
    return SimpleSlf4jILoggerFactory.INSTANCE;
  }

  public String getLoggerFactoryClassStr() {
    return SimpleSlf4jILoggerFactory.class.getName();
  }
}
