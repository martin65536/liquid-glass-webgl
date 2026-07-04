package defpackage;

import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class r41 extends p41 {
    public final String e;
    public final List f;
    public final int g;
    public final jc0 h;
    public final float i;
    public final jc0 j;
    public final float k;
    public final float l;
    public final int m;
    public final int n;
    public final float o;
    public final float p;
    public final float q;
    public final float r;

    public r41(String str, List list, int i, jc0 jc0Var, float f, jc0 jc0Var2, float f2, float f3, int i2, int i3, float f4, float f5, float f6, float f7) {
        this.e = str;
        this.f = list;
        this.g = i;
        this.h = jc0Var;
        this.i = f;
        this.j = jc0Var2;
        this.k = f2;
        this.l = f3;
        this.m = i2;
        this.n = i3;
        this.o = f4;
        this.p = f5;
        this.q = f6;
        this.r = f7;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj != null && r41.class == obj.getClass()) {
                r41 r41Var = (r41) obj;
                if (this.e.equals(r41Var.e) && o20.e(this.h, r41Var.h) && this.i == r41Var.i && o20.e(this.j, r41Var.j) && this.k == r41Var.k && this.l == r41Var.l && this.m == r41Var.m && this.n == r41Var.n && this.o == r41Var.o && this.p == r41Var.p && this.q == r41Var.q && this.r == r41Var.r && this.g == r41Var.g && o20.e(this.f, r41Var.f)) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int i;
        int hashCode = (this.f.hashCode() + (this.e.hashCode() * 31)) * 31;
        int i2 = 0;
        jc0 jc0Var = this.h;
        if (jc0Var != null) {
            i = jc0Var.hashCode();
        } else {
            i = 0;
        }
        int s = d3.s(this.i, (hashCode + i) * 31, 31);
        jc0 jc0Var2 = this.j;
        if (jc0Var2 != null) {
            i2 = jc0Var2.hashCode();
        }
        return d3.s(this.r, d3.s(this.q, d3.s(this.p, d3.s(this.o, (((d3.s(this.l, d3.s(this.k, (s + i2) * 31, 31), 31) + this.m) * 31) + this.n) * 31, 31), 31), 31), 31) + this.g;
    }
}
