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

package memo_lang.compiler.code_generator;

import compiler.abstract_syntax_tree.AST;
import compiler.architecture_base.GenPhase;
import compiler.symbols_table.SymbolInfo;
import compiler.symbols_table.SymbolsTable;
import memo_lang.compiler.MemoTypes;
import memo_lang.compiler.OperatorsKind;
import memo_lang.compiler.utils.SymbolInfoGetters;
import memo_lang.compiler.TokenKind;
import memo_lang.compiler.abstract_syntax_tree.*;

import java.util.LinkedList;
import java.util.List;

public class Encoder<T extends AST> extends SymbolInfoGetters implements GenPhase<T>, Visitor<Void> {

    public List<String> code;
    private int mDir;

    public Encoder(SymbolsTable<TokenKind, SymbolInfo<TokenKind>> st) {
        st.fillAddress(TokenKind.FloatLiteral, TokenKind.IntLiteral);
        code = new LinkedList<String>();
        code.add("");
        for (SymbolInfo<TokenKind> info : st.getAllEntriesOf(TokenKind.FloatLiteral, TokenKind.IntLiteral)) {
            code.add(info.kind.toString());
            code.add(address(info).toString());
            code.add(info.lexeme.trim());
        }
        mDir = ((code.size() - 1) / 3);
        code.set(0, mDir + "");
    }

    @Override
    public Void visit(ASTProgram o) {
        List<ASTInstruction> instructions = o.instructions;
        for (int i = 0; i < instructions.size(); i++) {
            ASTInstruction inst = instructions.get(i);
            inst.visit(this);
        }
        code.add(OperatorsKind.haltOp.toString());
        return null;
    }

    @Override
    public Void visit(ASTInstructionDeclaration o) {
        o.id.visit(this);
        return null;
    }

    @Override
    public Void visit(ASTInstructionAssig o) {
        o.id.visit(this);
        o.expresion.visit(this);
        code.add(OperatorsKind.storeOp.toString());
        return null;
    }

    @Override
    public Void visit(ASTInstructionWrite o) {
        o.expresion.visit(this);
        code.add(OperatorsKind.writeOp.toString());
        return null;
    }

    @Override
    public Void visit(ASTExpressionSuma o) {
        o.izq.visit(this);
        o.der.visit(this);
        if (o.type == MemoTypes.Int)
            code.add(OperatorsKind.sumIntOp.toString());

        if (o.type == MemoTypes.Float)
            code.add(OperatorsKind.sumFloatOp.toString());

        return null;
    }

    @Override
    public Void visit(ASTExpressionMult o) {
        o.izq.visit(this);
        o.der.visit(this);
        if (o.type == MemoTypes.Int)
            code.add(OperatorsKind.multIntOp.toString());

        if (o.type == MemoTypes.Float)
            code.add(OperatorsKind.multFloatOp.toString());

        return null;
    }

    @Override
    public Void visit(ASTIdentifierDeclaration o) {
        SymbolInfo<TokenKind> info = o.entry;
        if (type(info) == MemoTypes.Float)
            code.add(OperatorsKind.newFloatOp.toString());
        else
            code.add(OperatorsKind.newIntOp.toString());

        info.put("address", mDir);
        mDir++;
        return null;
    }

    @Override
    public Void visit(ASTIdentifierReference o) {
        SymbolInfo<TokenKind> info = o.entry;
        code.add(OperatorsKind.loadDirOp + " " + address(info));
        return null;
    }

    private void genLoadOp(ASTSymbol o) {
        SymbolInfo<TokenKind> info = o.entry;
        code.add(OperatorsKind.loadOp + " " + address(info));
    }

    @Override
    public Void visit(ASTIdentifierValue o) {
        genLoadOp(o);
        return null;
    }

    @Override
    public Void visit(ASTFloatValue o) {
        genLoadOp(o);
        return null;
    }

    @Override
    public Void visit(ASTIntValue o) {
        genLoadOp(o);
        return null;
    }

    @Override
    public List<String> genCode(T tree) {
        ((ASTVisitor) tree).visit(this);
        return code;
    }
}
