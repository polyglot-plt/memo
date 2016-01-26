/**
 * Author José Albert Cruz Almaguer <jalbertcruz@gmail.com>
 * Copyright 2015 by José Albert Cruz Almaguer.
 * <p/>
 * This program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http:www.gnu.org/licenses/agpl-3.0.txt) for more details.
 */

package memo_lang.compiler.semantic_analyzer;

import compiler.abstract_syntax_tree.AST;
import compiler.architecture_base.Dispatcher;
import compiler.architecture_base.SemanticPhase;
import compiler.errors.ErrorReporter;
import compiler.errors.SemanticError;
import compiler.symbols_table.SymbolInfo;
import compiler.symbols_table.SymbolsTable;
import memo_lang.compiler.MemoTypes;
import memo_lang.compiler.TokenKind;
import memo_lang.compiler.abstract_syntax_tree.*;
import memo_lang.compiler.utils.SymbolInfoGetters;

import java.util.List;

public class TypesChecker extends SymbolInfoGetters implements SemanticPhase<AST> {

    private ErrorReporter errorReporter;

    private Dispatcher<MemoTypes> dispatcher;

    public TypesChecker(ErrorReporter errorReporter) {
        dispatcher = new Dispatcher<MemoTypes>(this, "check", MemoTypes.Undefined);
        this.errorReporter = errorReporter;
    }

    public MemoTypes check(AST obj) {
        return dispatcher.call(obj);
    }

    public void check(ASTInstructionDeclaration o) {
        check(o.id);
        SymbolInfo<TokenKind> symbol = o.id.entry;
        symbol.put("type", o.type);
    }

    public void check(ASTInstructionAssig o) {
        MemoTypes type1 = check(o.id);
        MemoTypes type2 = check(o.expresion);
        if (type1 != type2) {
            errorReporter.add(new SemanticError(o.id.line, "Incompatible types"));
        }
    }

    public void check(ASTInstructionWrite o) {
        check(o.expresion);
    }

    public MemoTypes check(ASTExpressionBinary o) {// En este caso las dos únicas expresiones (+, *) preservan los tipos (es decir se comportan igual).
        MemoTypes type1 = check(o.izq);
        MemoTypes type2 = check(o.der);
        if (type1 != type2) {
            o.type = MemoTypes.Undefined;
            errorReporter.add(new SemanticError(o.line, "Incompatible types"));
            return MemoTypes.Undefined;
        }
        if (type1 == MemoTypes.Float && type2 == MemoTypes.Float) {
            o.type = MemoTypes.Float;
            return MemoTypes.Float;
        }
        o.type = MemoTypes.Int;
        return MemoTypes.Int;
    }

    public void check(ASTIdentifierDeclaration o) {
        SymbolInfo<TokenKind> symbol = o.entry;
        if (declared(symbol))
            errorReporter.add(new SemanticError(o.line, "Variable " + o.entry.lexeme + " is already defined in the scope"));
        else
            symbol.put("declared", true);
    }

    public MemoTypes check(ASTIdentifierReference o) {
        SymbolInfo<TokenKind> symbol = o.entry;
        if (declared(symbol))
            symbol.put("init", true);
        else
            errorReporter.add(new SemanticError(o.line, "Cannot resolve variable " + o.entry.lexeme));

        return declared(symbol) ? type(symbol) : MemoTypes.Undefined;
    }

    public MemoTypes check(ASTIdentifierValue o) {
        SymbolInfo<TokenKind> symbol = o.entry;
        if (declared(symbol) && init(symbol)) {
            return type(symbol);
        }
        if (!declared(symbol)) {
            errorReporter.add(new SemanticError(o.line, "Cannot resolve symbol " + o.entry.lexeme));
        }
        if (!init(symbol)) {
            errorReporter.add(new SemanticError(o.line, "Variable " + o.entry.lexeme + " might not have been initialized"));
        }
        return MemoTypes.Undefined;
    }

    public MemoTypes check(ASTFloatValue o) {
        return MemoTypes.Float;
    }

    public MemoTypes check(ASTIntValue o) {
        return MemoTypes.Int;
    }

    public void check(ASTProgram o) {
        List<ASTInstruction> list = o.instructions;
        for (int i = 0; i < list.size(); i++) {
            ASTInstruction instruction = list.get(i);
            check(instruction);
        }
    }

    @Override
    public AST apply(AST o) {
        check(o);
        return o;
    }
}
