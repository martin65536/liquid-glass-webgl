package defpackage;

import java.util.Iterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class vl0 extends m {
    public final ml0 e;

    public vl0(ml0 ml0Var) {
        this.e = ml0Var;
    }

    @Override // defpackage.m
    public final int a() {
        ml0 ml0Var = this.e;
        ml0Var.getClass();
        return ml0Var.f;
    }

    @Override // defpackage.m, java.util.Collection, java.util.List
    public final boolean contains(Object obj) {
        return this.e.containsValue(obj);
    }

    @Override // java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        a31 a31Var = this.e.e;
        b31[] b31VarArr = new b31[8];
        for (int i = 0; i < 8; i++) {
            b31VarArr[i] = new c31(2);
        }
        return new nl0(a31Var, b31VarArr);
    }
}
