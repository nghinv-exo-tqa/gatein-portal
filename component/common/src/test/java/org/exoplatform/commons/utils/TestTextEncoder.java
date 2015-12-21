/**
 * Copyright (C) 2009 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.exoplatform.commons.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.exoplatform.component.test.AbstractGateInTest;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class TestTextEncoder extends AbstractGateInTest {

    public void testA() throws IOException {
        assertOK("ABCDEFGHIJKLMNOPQRSTUVWXYZ");

        // check if ignore malformed input
        String malformedInput = new String(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x80});
        assertOK("hello you", "hello " + malformedInput + "you");

        /*
         * assertOK("<>&\"\\=+");
         *
         * // Chinese assertOK(new String(new char[]{0x4EAC, 0x4EC5, 0x5C3D, 0x5F84, 0x60CA, 0x740E, 0x7579, 0x7D27, 0x7ECF,
         * 0x8B66, 0x8C28, 0x9CB8}));
         *
         * // Extended Roman assertOK(new String(new char[]{0xEA, 0xFC, 0xE2, 0xCC}));
         *
         * // Russian assertOK(new String(new char[]{0x0433, 0x043E, 0x0432, 0x043E, 0x0440, 0x044E}));
         *
         * // Greek assertOK(new String(new char[]{0x0391, 0x03C5, 0x034, 0x03BF, 0x03C5}));
         */
    }

    private void assertOK(String expectedAndActual) throws IOException {
        assertOK(expectedAndActual, expectedAndActual);
    }

    private void assertOK(String expected, String actual) throws IOException {
        TextEncoder encoder = CharsetTextEncoder.getUTF8();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.encode(actual, 0, actual.length(), baos);
        baos.flush();
        byte[] b1 = baos.toByteArray();

        //
        baos.reset();
        OutputStreamWriter osw = new OutputStreamWriter(baos);
        osw.write(expected);
        osw.close();
        byte[] b2 = baos.toByteArray();

        //
        List<Byte> expectedBytes = toList(b2);
        List<Byte> actualBytes = toList(b1);
        assertEquals(expectedBytes, actualBytes);
    }

    private List<Byte> toList(byte[] bytes) {
        List<Byte> tmp = new ArrayList<Byte>(bytes.length);
        for (byte aByte : bytes) {
            tmp.add(aByte);
        }
        return tmp;
    }

}
