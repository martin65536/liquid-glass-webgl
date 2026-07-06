package defpackage;

import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class h11 {
    public final g11 a;
    public final xd0 b;
    public final long c;
    public final float d;
    public final float e;
    public final ArrayList f;

    public h11(g11 g11Var, xd0 xd0Var, long j) {
        float c;
        this.a = g11Var;
        this.b = xd0Var;
        this.c = j;
        ArrayList arrayList = (ArrayList) xd0Var.e;
        float f = 0.0f;
        if (arrayList.isEmpty()) {
            c = 0.0f;
        } else {
            c = ((yj0) arrayList.get(0)).a.d.c(0);
        }
        this.d = c;
        if (!arrayList.isEmpty()) {
            yj0 yj0Var = (yj0) me.X(arrayList);
            f = yj0Var.a.d.c(r4.f - 1) + yj0Var.f;
        }
        this.e = f;
        this.f = (ArrayList) xd0Var.d;
    }

    public final int a(int i) {
        int r;
        xd0 xd0Var = this.b;
        ArrayList arrayList = (ArrayList) xd0Var.e;
        if (i >= ((l7) ((e3) xd0Var.c).a).f.length()) {
            r = jc0.q(arrayList);
        } else if (i < 0) {
            r = 0;
        } else {
            r = m20.r(i, arrayList);
        }
        yj0 yj0Var = (yj0) arrayList.get(r);
        t5 t5Var = yj0Var.a;
        return t5Var.d.e.getLineForOffset(yj0Var.a(i)) + yj0Var.d;
    }

    public final int b(float f) {
        char c;
        ArrayList arrayList = (ArrayList) this.b.e;
        int i = 0;
        if (f > 0.0f) {
            if (f >= ((yj0) me.X(arrayList)).g) {
                i = arrayList.size() - 1;
            } else {
                int size = arrayList.size() - 1;
                int i2 = 0;
                while (true) {
                    if (i2 <= size) {
                        int i3 = (i2 + size) >>> 1;
                        yj0 yj0Var = (yj0) arrayList.get(i3);
                        if (yj0Var.f > f) {
                            c = 1;
                        } else if (yj0Var.g <= f) {
                            c = 65535;
                        } else {
                            c = 0;
                        }
                        if (c < 0) {
                            i2 = i3 + 1;
                        } else if (c > 0) {
                            size = i3 - 1;
                        } else {
                            i = i3;
                            break;
                        }
                    } else {
                        i = -(i2 + 1);
                        break;
                    }
                }
            }
        }
        yj0 yj0Var2 = (yj0) arrayList.get(i);
        int i4 = yj0Var2.c;
        int i5 = yj0Var2.d;
        if (i4 - yj0Var2.b == 0) {
            return i5;
        }
        t5 t5Var = yj0Var2.a;
        float f2 = f - yj0Var2.f;
        f11 f11Var = t5Var.d;
        return f11Var.e.getLineForVertical(((int) f2) - f11Var.g) + i5;
    }

    public final int c(int i) {
        xd0 xd0Var = this.b;
        xd0Var.b(i);
        ArrayList arrayList = (ArrayList) xd0Var.e;
        yj0 yj0Var = (yj0) arrayList.get(m20.s(i, arrayList));
        t5 t5Var = yj0Var.a;
        return t5Var.d.e.getLineStart(i - yj0Var.d) + yj0Var.b;
    }

    public final float d(int i) {
        xd0 xd0Var = this.b;
        xd0Var.b(i);
        ArrayList arrayList = (ArrayList) xd0Var.e;
        yj0 yj0Var = (yj0) arrayList.get(m20.s(i, arrayList));
        t5 t5Var = yj0Var.a;
        return t5Var.d.f(i - yj0Var.d) + yj0Var.f;
    }

    public final aq0 e(int i) {
        int r;
        xd0 xd0Var = this.b;
        l7 l7Var = (l7) ((e3) xd0Var.c).a;
        if (i < 0 || i > l7Var.f.length()) {
            r00.a("offset(" + i + ") is out of bounds [0, " + l7Var.f.length() + ']');
        }
        int length = ((l7) ((e3) xd0Var.c).a).f.length();
        ArrayList arrayList = (ArrayList) xd0Var.e;
        if (i == length) {
            r = jc0.q(arrayList);
        } else {
            r = m20.r(i, arrayList);
        }
        yj0 yj0Var = (yj0) arrayList.get(r);
        t5 t5Var = yj0Var.a;
        int a = yj0Var.a(i);
        f11 f11Var = t5Var.d;
        if (f11Var.e.getParagraphDirection(f11Var.e.getLineForOffset(a)) == 1) {
            return aq0.e;
        }
        return aq0.f;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof h11) {
                h11 h11Var = (h11) obj;
                if (o20.e(this.a, h11Var.a) && this.b == h11Var.b && c20.a(this.c, h11Var.c) && this.d == h11Var.d && this.e == h11Var.e && o20.e(this.f, h11Var.f)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int hashCode = (this.b.hashCode() + (this.a.hashCode() * 31)) * 31;
        long j = this.c;
        return this.f.hashCode() + d3.s(this.e, d3.s(this.d, (((int) (j ^ (j >>> 32))) + hashCode) * 31, 31), 31);
    }

    public final String toString() {
        return "TextLayoutResult(layoutInput=" + this.a + ", multiParagraph=" + this.b + ", size=" + ((Object) c20.b(this.c)) + ", firstBaseline=" + this.d + ", lastBaseline=" + this.e + ", placeholderRects=" + this.f + ')';
    }
}
