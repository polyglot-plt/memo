/**
 * Author José Albert Cruz Almaguer <jalbertcruz@gmail.com>
 * Copyright 2015 by José Albert Cruz Almaguer.
 * <p>
 * This program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http:www.gnu.org/licenses/agpl-3.0.txt) for more details.
 */

package ui.main.console;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import ui.CompilerController;
import ui.io.ShellConsole;

import java.io.IOException;
import java.net.URL;

public class ConsoleMain {


    public static void main(String[] args) throws IOException {

        CompilerController compiler = new CompilerController(new ShellConsole());

        URL url = Resources.getResource("tests/memoTest1.txt");
        String source = Resources.toString(url, Charsets.UTF_8);

        compiler.Analisis_sintactico(source);

    }

}
