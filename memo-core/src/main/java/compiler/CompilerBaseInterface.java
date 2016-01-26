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
package compiler;

import compiler.abstract_syntax_tree.AST;
import compiler.errors.ErrorReporter;
import compiler.stream.SourceStream;

import java.util.List;

public interface CompilerBaseInterface {

    ErrorReporter getErrorReporter();

    List scanAll(SourceStream source);

    AST syntaxAnalysis(SourceStream source);

}
