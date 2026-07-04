package defpackage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class n41 extends p41 implements Iterable, q30 {
    public final String e;
    public final float f;
    public final float g;
    public final float h;
    public final float i;
    public final float j;
    public final float k;
    public final float l;
    public final List m;
    public final List n;

    public n41(String str, float f, float f2, float f3, float f4, float f5, float f6, float f7, List list, ArrayList arrayList) {
        this.e = str;
        this.f = f;
        this.g = f2;
        this.h = f3;
        this.i = f4;
        this.j = f5;
        this.k = f6;
        this.l = f7;
        this.m = list;
        this.n = arrayList;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof n41)) {
            n41 n41Var = (n41) obj;
            if (o20.e(this.e, n41Var.e) && this.f == n41Var.f && this.g == n41Var.g && this.h == n41Var.h && this.i == n41Var.i && this.j == n41Var.j && this.k == n41Var.k && this.l == n41Var.l && o20.e(this.m, n41Var.m) && o20.e(this.n, n41Var.n)) {
                return true;
            }
            return false;
        }
        return false;
    }

    public final int hashCode() {
        return this.n.hashCode() + ((this.m.hashCode() + d3.s(this.l, d3.s(this.k, d3.s(this.j, d3.s(this.i, d3.s(this.h, d3.s(this.g, d3.s(this.f, this.e.hashCode() * 31, 31), 31), 31), 31), 31), 31), 31)) * 31);
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return new rl0(this);
    }
}
