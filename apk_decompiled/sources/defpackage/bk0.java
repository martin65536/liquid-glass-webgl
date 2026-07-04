package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class bk0 {
    public String a;
    public r11 b;
    public wt c;
    public int d;
    public boolean e;
    public int f;
    public int g;
    public mm i;
    public t5 j;
    public boolean k;
    public wc0 m;
    public ak0 n;
    public m40 o;
    public long q;
    public long h = u00.a;
    public long l = 0;
    public long p = ti.g(0, 0, 0, 0);

    public bk0(String str, r11 r11Var, wt wtVar, int i, boolean z, int i2, int i3) {
        this.a = str;
        this.b = r11Var;
        this.c = wtVar;
        this.d = i;
        this.e = z;
        this.f = i2;
        this.g = i3;
    }

    public final boolean a(long j, m40 m40Var) {
        long j2;
        int h;
        int i;
        ak0 ak0Var;
        int i2;
        this.q = (this.q << 2) | 3;
        boolean z = false;
        boolean z2 = true;
        if (this.g > 1) {
            r11 r11Var = this.b;
            wc0 wc0Var = this.m;
            mm mmVar = this.i;
            mmVar.getClass();
            wt wtVar = this.c;
            if ((wc0Var == null || m40Var != wc0Var.a || !o30.v(r11Var, m40Var).equals(wc0Var.b) || mmVar.B() != wc0Var.c.e || wtVar != wc0Var.d) && ((wc0Var = wc0.h) == null || m40Var != wc0Var.a || !o30.v(r11Var, m40Var).equals(wc0Var.b) || mmVar.B() != wc0Var.c.e || wtVar != wc0Var.d)) {
                wc0Var = new wc0(m40Var, o30.v(r11Var, m40Var), new pm(mmVar.B(), mmVar.y()), wtVar);
                wc0.h = wc0Var;
            }
            this.m = wc0Var;
            int i3 = this.g;
            pm pmVar = wc0Var.c;
            float f = wc0Var.g;
            float f2 = wc0Var.f;
            if (Float.isNaN(f) || Float.isNaN(f2)) {
                float b = m20.d(xc0.a, wc0Var.e, ti.b(0, 0, 15), pmVar, wc0Var.d, 1).b();
                f2 = m20.d(xc0.b, wc0Var.e, ti.b(0, 0, 15), pmVar, wc0Var.d, 2).b() - b;
                wc0Var.g = b;
                wc0Var.f = f2;
                f = b;
            }
            if (i3 != 1) {
                i2 = Math.round((f2 * (i3 - 1)) + f);
                if (i2 < 0) {
                    i2 = 0;
                }
                int g = si.g(j);
                if (i2 > g) {
                    i2 = g;
                }
            } else {
                i2 = si.i(j);
            }
            j2 = ti.a(si.j(j), si.h(j), i2, si.g(j));
        } else {
            j2 = j;
        }
        t5 t5Var = this.j;
        if (t5Var != null && (ak0Var = this.n) != null && !ak0Var.b() && m40Var == this.o && (si.b(j2, this.p) || (si.h(j2) == si.h(this.p) && si.j(j2) == si.j(this.p) && si.g(j2) >= t5Var.b() && !t5Var.d.d))) {
            if (!si.b(j2, this.p)) {
                t5 t5Var2 = this.j;
                t5Var2.getClass();
                this.l = ti.d(j2, (y20.d(Math.min(t5Var2.a.i.c(), t5Var2.c())) << 32) | (y20.d(t5Var2.b()) & 4294967295L));
                if (this.d == 3 || (((int) (r12 >> 32)) >= t5Var2.c() && ((int) (4294967295L & r12)) >= t5Var2.b())) {
                    z2 = false;
                }
                this.k = z2;
                this.p = j2;
            }
            return false;
        }
        ak0 ak0Var2 = this.n;
        if (ak0Var2 == null || m40Var != this.o || ak0Var2.b()) {
            this.o = m40Var;
            String str = this.a;
            r11 v = o30.v(this.b, m40Var);
            mm mmVar2 = this.i;
            mmVar2.getClass();
            wt wtVar2 = this.c;
            er erVar = er.e;
            ak0Var2 = new x5(str, v, erVar, erVar, wtVar2, mmVar2);
        }
        this.n = ak0Var2;
        boolean z3 = this.e;
        int i4 = this.d;
        float g2 = ak0Var2.g();
        if ((z3 || i4 == 2 || i4 == 4 || i4 == 5) && si.d(j2)) {
            h = si.h(j2);
        } else {
            h = Integer.MAX_VALUE;
        }
        if (si.j(j2) != h) {
            h = n30.j(y20.d(g2), si.j(j2), h);
        }
        long y = f31.y(0, h, 0, si.g(j2));
        boolean z4 = this.e;
        int i5 = this.d;
        int i6 = this.f;
        if ((!z4 && (i5 == 2 || i5 == 4 || i5 == 5)) || i6 < 1) {
            i = 1;
        } else {
            i = i6;
        }
        t5 t5Var3 = new t5((x5) ak0Var2, i, i5, y);
        this.p = j2;
        this.l = ti.d(j2, (y20.d(t5Var3.c()) << 32) | (y20.d(t5Var3.b()) & 4294967295L));
        if (this.d != 3 && (((int) (r6 >> 32)) < t5Var3.c() || ((int) (r6 & 4294967295L)) < t5Var3.b())) {
            z = true;
        }
        this.k = z;
        this.j = t5Var3;
        return true;
    }

    public final void b() {
        this.j = null;
        this.n = null;
        this.o = null;
        if (!(true & true)) {
            s00.a("width and height must be >= 0");
        }
        this.p = ti.g(0, 0, 0, 0);
        this.l = 0L;
        this.k = false;
    }

    public final void c(mm mmVar) {
        long j;
        mm mmVar2 = this.i;
        if (mmVar != null) {
            int i = u00.b;
            j = u00.a(mmVar.B(), mmVar.y());
        } else {
            j = u00.a;
        }
        if (mmVar2 == null) {
            this.i = mmVar;
            this.h = j;
        } else {
            if (mmVar != null && this.h == j) {
                return;
            }
            this.i = mmVar;
            this.h = j;
            this.q = (this.q << 2) | 1;
            b();
        }
    }

    public final void d(String str, r11 r11Var, wt wtVar, int i, boolean z, int i2, int i3) {
        this.a = str;
        this.b = r11Var;
        this.c = wtVar;
        this.d = i;
        this.e = z;
        this.f = i2;
        this.g = i3;
        this.q = (this.q << 2) | 2;
        b();
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder("ParagraphLayoutCache(paragraph=");
        if (this.j != null) {
            str = "<paragraph>";
        } else {
            str = "null";
        }
        sb.append(str);
        sb.append(", lastDensity=");
        sb.append((Object) u00.b(this.h));
        sb.append(", history=");
        sb.append(this.q);
        sb.append(", constraints=$)");
        return sb.toString();
    }
}
