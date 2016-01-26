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
import compiler.architecture_base.Dispatcher;
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

public class Encoder<T extends AST> extends SymbolInfoGetters implements GenPhase<T>{

    public List<String> code;
    private int mDir;

    Dispatcher<Void> dispatcher;

    public Encoder(SymbolsTable<TokenKind, SymbolInfo<TokenKind>> st) {
        dispatcher = new Dispatcher<Void>(this, "translate");
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

    public void translate(Object obj) {
        dispatcher.call(obj);
    }

    public void translate(ASTProgram o) {
        List<ASTInstruction> instructions = o.instructions;
        for (int i = 0; i < instructions.size(); i++) {
            ASTInstruction inst = instructions.get(i);
            translate(inst);
        }
        code.add(OperatorsKind.haltOp.toString());
    }

    public void translate(ASTInstructionDeclaration o) {
        translate(o.id);
    }

    public void translate(ASTInstructionAssig o) {
        translate(o.id);
        translate(o.expresion);
        code.add(OperatorsKind.storeOp.toString());
    }

    public void translate(ASTInstructionWrite o) {
        translate(o.expresion);
        code.add(OperatorsKind.writeOp.toString());
    }

    public void translate(ASTExpressionSuma o) {
        translate(o.izq);
        translate(o.der);
        if (o.type == MemoTypes.Int)
            code.add(OperatorsKind.sumIntOp.toString());

        if (o.type == MemoTypes.Float)
            code.add(OperatorsKind.sumFloatOp.toString());
    }

    public void translate(ASTExpressionMult o) {
        translate(o.izq);
        translate(o.der);
        if (o.type == MemoTypes.Int)
            code.add(OperatorsKind.multIntOp.toString());

        if (o.type == MemoTypes.Float)
            code.add(OperatorsKind.multFloatOp.toString());
    }

    public void translate(ASTIdentifierDeclaration o) {
        SymbolInfo<TokenKind> info = o.entry;
        if (type(info) == MemoTypes.Float)
            code.add(OperatorsKind.newFloatOp.toString());
        else
            code.add(OperatorsKind.newIntOp.toString());

        info.put("address", mDir);
        mDir++;
    }

    public void translate(ASTIdentifierReference o) {
        SymbolInfo<TokenKind> info = o.entry;
        code.add(OperatorsKind.loadDirOp + " " + address(info));
    }

    private void genLoadOp(ASTSymbol o) {
        SymbolInfo<TokenKind> info = o.entry;
        code.add(OperatorsKind.loadOp + " " + address(info));
    }

    public void translate(ASTIdentifierValue o) {
        genLoadOp(o);
    }

    public void translate(ASTFloatValue o) {
        genLoadOp(o);
    }

    public void translate(ASTIntValue o) {
        genLoadOp(o);
    }

    @Override
    public List<String> genCode(T tree) {
        translate(tree);
        return code;
    }
}
