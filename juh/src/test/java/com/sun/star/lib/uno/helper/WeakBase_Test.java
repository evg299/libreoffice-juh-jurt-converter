/*
 * This file is part of the LibreOffice project.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * This file incorporates work covered by the following license notice:
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements. See the NOTICE file distributed
 *   with this work for additional information regarding copyright
 *   ownership. The ASF licenses this file to you under the Apache
 *   License, Version 2.0 (the "License"); you may not use this file
 *   except in compliance with the License. You may obtain a copy of
 *   the License at http://www.apache.org/licenses/LICENSE-2.0 .
 */

package com.sun.star.lib.uno.helper;

import com.sun.star.bridge.XBridgeSupplier2;
import com.sun.star.lang.XTypeProvider;
import com.sun.star.uno.Type;
import com.sun.star.uno.XReference;
import com.sun.star.uno.XWeak;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class WeakBase_Test
{

    private static final Logger logger = Logger.getLogger(WeakBase_Test.class.getName());

    @Test public void getTypes() throws Exception
    {
        logger.log(Level.INFO, "Testing WeakBase.getTypes");
        SomeClass comp= new SomeClass();

        Type[] types= comp.getTypes(); //XWeak,XTypeProvider,XReference,XBridgeSupplier2
        assertEquals(types.length, 4);
        for (int c= 0; c < types.length; c++)
        {
            boolean result= false;
            if (types[c].equals( new Type( XWeak.class)))
                result= true;
            else if (types[c].equals(new Type(XTypeProvider.class)))
                result= true;
            else if (types[c].equals(new Type(XReference.class)))
                result= true;
            else if (types[c].equals(new Type(XBridgeSupplier2.class)))
                result= true;
            assertTrue(result);
        }

        Foo1 f1= new Foo1();
        Foo1 f2= new Foo1();
        Type[] t1= f1.getTypes();
        Type[] t2= f2.getTypes();
        assertArrayEquals(t1, t2);
        new Foo2();
    }
}

class OtherClass extends WeakBase implements XBridgeSupplier2
{

    public Object createBridge(Object obj, byte[] values, short param, short param3) throws com.sun.star.lang.IllegalArgumentException
    {
        return null;
    }
}

class SomeClass extends OtherClass implements XReference
{

    public void dispose()
    {
    }

}

class MyRef implements XReference
{
    int nDisposeCalled;

    public void dispose()
    {
        nDisposeCalled++;
    }
}

class Foo1 extends WeakBase
{
}

class Foo2 extends WeakBase
{
}

class Foo3 extends Foo1
{
}
