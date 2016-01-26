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
import compiler.architecture_base.SemanticPhase;
import compiler.errors.ErrorReporter;
import compiler.errors.SemanticError;
import compiler.symbols_table.SymbolInfo;
import memo_lang.compiler.MemoTypes;
import memo_lang.compiler.utils.SymbolInfoGetters;
import memo_lang.compiler.TokenKind;
import memo_lang.compiler.abstract_syntax_tree.*;

import java.util.List;

public class TypesChecker extends SymbolInfoGetters implements Visitor<Object>, SemanticPhase<AST> {

    private ErrorReporter errorReporter;

    public TypesChecker(ErrorReporter errorReporter) {
        this.errorReporter = errorReporter;
    }

    @Override
    public AST apply(AST o) {
        ((ASTVisitor) o).visit(this);
        return o;
    }

    @Override
    public Object visit(ASTProgram o) {
        List<ASTInstruction> list = o.instructions;
        for (int i = 0; i < list.size(); i++) {
            ASTInstruction instruction = list.get(i);
            instruction.visit(this);
        }
        return null;
    }

    @Override
    public Object visit(ASTInstructionDeclaration o) {
        o.id.visit(this);
        SymbolInfo<TokenKind> symbol = o.id.entry;
        symbol.put("type", o.type);
        return null;
    }

    @Override
    public Object visit(ASTInstructionAssig o) {
        MemoTypes type1 = (MemoTypes) o.id.visit(this);
        MemoTypes type2 = (MemoTypes) o.expresion.visit(this);
        if (type1 != type2) {
            errorReporter.add(new SemanticError(o.id.line, "Incompatible types"));
        }
        return null;
    }

    @Override
    public Object visit(ASTInstructionWrite o) {
        o.expresion.visit(this);
        return null;
    }

    public Object visitExpressionBinary(ASTExpressionBinary o) {
        MemoTypes type1 = (MemoTypes) o.izq.visit(this);
        MemoTypes type2 = (MemoTypes) o.der.visit(this);
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

    @Override
    public Object visit(ASTExpressionSuma o) {
        return visitExpressionBinary(o);
    }

    @Override
    public Object visit(ASTExpressionMult o) {
        return visitExpressionBinary(o);
    }

    @Override
    public Object visit(ASTIdentifierDeclaration o) {
        SymbolInfo<TokenKind> symbol = o.entry;
        if (declared(symbol))
            errorReporter.add(new SemanticError(o.line, "Variable " + o.entry.lexeme + " is already defined in the scope"));
        else
            symbol.put("declared", true);

        return null;
    }

    @Override
    public Object visit(ASTIdentifierReference o) {
        SymbolInfo<TokenKind> symbol = o.entry;
        if (declared(symbol))
            symbol.put("init", true);
        else
            errorReporter.add(new SemanticError(o.line, "Cannot resolve variable " + o.entry.lexeme));

        return declared(symbol) ? type(symbol) : MemoTypes.Undefined;
    }

    @Override
    public Object visit(ASTIdentifierValue o) {
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


    @Override
    public Object visit(ASTFloatValue o) {
        return MemoTypes.Float;
    }

    @Override
    public Object visit(ASTIntValue o) {
        return MemoTypes.Int;
    }

}
