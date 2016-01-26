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

package ui;

import compiler.CompilerBaseInterface;
import compiler.errors.ErrorReporter;
import compiler.stream.StringSourceStream;
import memo_lang.compiler.MemoCompiler;
import ui.io.GeneralConsole;
import ui.io.Out;

import java.util.List;

public class CompilerController {

    private void newCompiler() {
        compiler = new MemoCompiler();
    }

    private CompilerBaseInterface compiler;

    public CompilerController(GeneralConsole console) {
        Out.setConsole(console);
    }

    private void report(String okMsg) {
        if (existError())
            reportError();
        else
            Out.writeLine(okMsg);
    }

    private boolean existError() {
        return !compiler.getErrorReporter().isEmpty();
    }

    private void reportError() {
        ErrorReporter er = compiler.getErrorReporter();
        for (int i = 0; i < er.size(); i++)
            Out.writeLine(er.get(i).toString());
    }

    public void Obtener_todos_los_tokens(String source) {
        Out.clear();
        newCompiler();
        List tokenList = compiler.scanAll(new StringSourceStream(source));
        if (existError())
            reportError();
        else
            for (int i = 0; i < tokenList.size(); i++)
                Out.writeLine(tokenList.get(i).toString());
    }

    public void Analisis_sintactico(String source) {
        Out.clear();
        newCompiler();
        compiler.syntaxAnalysis(new StringSourceStream(source));
        report("Syntax analysis OK!");
    }

}
