package defpackage;

import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class g11 {
    public final l7 a;
    public final r11 b;
    public final List c;
    public final int d;
    public final boolean e;
    public final int f;
    public final mm g;
    public final m40 h;
    public final wt i;
    public final long j;

    public g11(l7 l7Var, r11 r11Var, List list, int i, boolean z, int i2, mm mmVar, m40 m40Var, wt wtVar, long j) {
        this.a = l7Var;
        this.b = r11Var;
        this.c = list;
        this.d = i;
        this.e = z;
        this.f = i2;
        this.g = mmVar;
        this.h = m40Var;
        this.i = wtVar;
        this.j = j;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof g11) {
                g11 g11Var = (g11) obj;
                if (o20.e(this.a, g11Var.a) && o20.e(this.b, g11Var.b) && this.c.equals(g11Var.c) && this.d == g11Var.d && this.e == g11Var.e && this.f == g11Var.f && o20.e(this.g, g11Var.g) && this.h == g11Var.h && o20.e(this.i, g11Var.i) && si.b(this.j, g11Var.j)) {
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
        int hashCode = (((this.c.hashCode() + ((this.b.hashCode() + (this.a.hashCode() * 31)) * 31)) * 31) + this.d) * 31;
        if (this.e) {
            i = 1231;
        } else {
            i = 1237;
        }
        int hashCode2 = (this.i.hashCode() + ((this.h.hashCode() + ((this.g.hashCode() + ((((hashCode + i) * 31) + this.f) * 31)) * 31)) * 31)) * 31;
        long j = this.j;
        return ((int) (j ^ (j >>> 32))) + hashCode2;
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder("TextLayoutInput(text=");
        sb.append((Object) this.a);
        sb.append(", style=");
        sb.append(this.b);
        sb.append(", placeholders=");
        sb.append(this.c);
        sb.append(", maxLines=");
        sb.append(this.d);
        sb.append(", softWrap=");
        sb.append(this.e);
        sb.append(", overflow=");
        int i = this.f;
        if (i == 1) {
            str = "Clip";
        } else if (i == 2) {
            str = "Ellipsis";
        } else if (i == 5) {
            str = "MiddleEllipsis";
        } else if (i == 3) {
            str = "Visible";
        } else if (i == 4) {
            str = "StartEllipsis";
        } else {
            str = "Invalid";
        }
        sb.append((Object) str);
        sb.append(", density=");
        sb.append(this.g);
        sb.append(", layoutDirection=");
        sb.append(this.h);
        sb.append(", fontFamilyResolver=");
        sb.append(this.i);
        sb.append(", constraints=");
        sb.append((Object) si.k(this.j));
        sb.append(')');
        return sb.toString();
    }
}
