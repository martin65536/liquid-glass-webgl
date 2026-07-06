package defpackage;

import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class a60 extends bd0 implements tp {
    public c60 s;

    @Override // defpackage.tp
    public final void R(b50 b50Var) {
        ArrayList arrayList = this.s.h;
        if (arrayList.size() <= 0) {
            b50Var.r();
        } else {
            d3.z(arrayList.get(0));
            throw null;
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj instanceof a60) && o20.e(this.s, ((a60) obj).s)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return this.s.hashCode();
    }

    @Override // defpackage.bd0
    public final void t0() {
        this.s.getClass();
    }

    public final String toString() {
        return "DisplayingDisappearingItemsNode(animator=" + this.s + ')';
    }

    @Override // defpackage.bd0
    public final void v0() {
        c60 c60Var = this.s;
        c60Var.c();
        c60Var.b = null;
    }

    @Override // defpackage.tp
    public final /* synthetic */ void m0() {
    }
}
