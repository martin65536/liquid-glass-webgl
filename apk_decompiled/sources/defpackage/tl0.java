package defpackage;

import java.util.Iterator;
import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class tl0 extends k0 {
    public final /* synthetic */ int e;
    public final ml0 f;

    public /* synthetic */ tl0(ml0 ml0Var, int i) {
        this.e = i;
        this.f = ml0Var;
    }

    @Override // defpackage.m
    public final int a() {
        int i = this.e;
        ml0 ml0Var = this.f;
        switch (i) {
            case 0:
                ml0Var.getClass();
                return ml0Var.f;
            default:
                ml0Var.getClass();
                return ml0Var.f;
        }
    }

    @Override // defpackage.m, java.util.Collection, java.util.List
    public final boolean contains(Object obj) {
        int i = this.e;
        ml0 ml0Var = this.f;
        switch (i) {
            case 0:
                if (obj instanceof Map.Entry) {
                    Map.Entry entry = (Map.Entry) obj;
                    Object obj2 = ml0Var.get(entry.getKey());
                    if (obj2 != null) {
                        return obj2.equals(entry.getValue());
                    }
                    if (entry.getValue() == null && ml0Var.containsKey(entry.getKey())) {
                        return true;
                    }
                }
                return false;
            default:
                return ml0Var.containsKey(obj);
        }
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        switch (this.e) {
            case 0:
                a31 a31Var = this.f.e;
                b31[] b31VarArr = new b31[8];
                for (int i = 0; i < 8; i++) {
                    b31VarArr[i] = new c31(0);
                }
                return new nl0(a31Var, b31VarArr);
            default:
                a31 a31Var2 = this.f.e;
                b31[] b31VarArr2 = new b31[8];
                for (int i2 = 0; i2 < 8; i2++) {
                    b31VarArr2[i2] = new c31(1);
                }
                return new nl0(a31Var2, b31VarArr2);
        }
    }
}
