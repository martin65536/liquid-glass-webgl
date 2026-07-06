package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ux0 implements j7 {
    public final a11 a;
    public final long b;
    public final nu c;
    public final lu d;
    public final mu e;
    public final sl f;
    public final String g;
    public final long h;
    public final t9 i;
    public final b11 j;
    public final ua0 k;
    public final long l;
    public final w01 m;
    public final tv0 n;
    public final jc0 o;

    public ux0(long j, long j2, nu nuVar, lu luVar, mu muVar, sl slVar, String str, long j3, t9 t9Var, b11 b11Var, ua0 ua0Var, long j4, w01 w01Var, tv0 tv0Var, int i) {
        this((i & 1) != 0 ? se.g : j, (i & 2) != 0 ? t11.c : j2, (i & 4) != 0 ? null : nuVar, (i & 8) != 0 ? null : luVar, (i & 16) != 0 ? null : muVar, (i & 32) != 0 ? null : slVar, (i & 64) != 0 ? null : str, (i & 128) != 0 ? t11.c : j3, (i & 256) != 0 ? null : t9Var, (i & 512) != 0 ? null : b11Var, (i & 1024) != 0 ? null : ua0Var, (i & 2048) != 0 ? se.g : j4, (i & 4096) != 0 ? null : w01Var, (i & 8192) != 0 ? null : tv0Var);
    }

    public final boolean a(ux0 ux0Var) {
        if (this == ux0Var) {
            return true;
        }
        if (t11.a(this.b, ux0Var.b) && o20.e(this.c, ux0Var.c) && o20.e(this.d, ux0Var.d) && o20.e(this.e, ux0Var.e) && o20.e(this.f, ux0Var.f) && o20.e(this.g, ux0Var.g) && t11.a(this.h, ux0Var.h) && o20.e(this.i, ux0Var.i) && o20.e(this.j, ux0Var.j) && o20.e(this.k, ux0Var.k) && se.c(this.l, ux0Var.l)) {
            return true;
        }
        return false;
    }

    public final boolean b(ux0 ux0Var) {
        if (!o20.e(this.a, ux0Var.a) || !o20.e(this.m, ux0Var.m) || !o20.e(this.n, ux0Var.n) || !o20.e(this.o, ux0Var.o)) {
            return false;
        }
        return true;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ux0)) {
            return false;
        }
        ux0 ux0Var = (ux0) obj;
        if (a(ux0Var) && b(ux0Var)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        a11 a11Var = this.a;
        int i12 = se.i(a11Var.a()) * 31;
        jc0 d = a11Var.d();
        int i13 = 0;
        if (d != null) {
            i = d.hashCode();
        } else {
            i = 0;
        }
        int d2 = (t11.d(this.b) + ((Float.floatToIntBits(a11Var.r()) + ((i12 + i) * 31)) * 31)) * 31;
        nu nuVar = this.c;
        if (nuVar != null) {
            i2 = nuVar.e;
        } else {
            i2 = 0;
        }
        int i14 = (d2 + i2) * 31;
        lu luVar = this.d;
        if (luVar != null) {
            i3 = luVar.a;
        } else {
            i3 = 0;
        }
        int i15 = (i14 + i3) * 31;
        mu muVar = this.e;
        if (muVar != null) {
            i4 = muVar.a;
        } else {
            i4 = 0;
        }
        int i16 = (i15 + i4) * 31;
        sl slVar = this.f;
        if (slVar != null) {
            i5 = slVar.hashCode();
        } else {
            i5 = 0;
        }
        int i17 = (i16 + i5) * 31;
        String str = this.g;
        if (str != null) {
            i6 = str.hashCode();
        } else {
            i6 = 0;
        }
        int d3 = (t11.d(this.h) + ((i17 + i6) * 31)) * 31;
        t9 t9Var = this.i;
        if (t9Var != null) {
            i7 = Float.floatToIntBits(t9Var.a);
        } else {
            i7 = 0;
        }
        int i18 = (d3 + i7) * 31;
        b11 b11Var = this.j;
        if (b11Var != null) {
            i8 = b11Var.hashCode();
        } else {
            i8 = 0;
        }
        int i19 = (i18 + i8) * 31;
        ua0 ua0Var = this.k;
        if (ua0Var != null) {
            i9 = ua0Var.e.hashCode();
        } else {
            i9 = 0;
        }
        int i20 = (se.i(this.l) + ((i19 + i9) * 31)) * 31;
        w01 w01Var = this.m;
        if (w01Var != null) {
            i10 = w01Var.a;
        } else {
            i10 = 0;
        }
        int i21 = (i20 + i10) * 31;
        tv0 tv0Var = this.n;
        if (tv0Var != null) {
            i11 = tv0Var.hashCode();
        } else {
            i11 = 0;
        }
        int i22 = (i21 + i11) * 961;
        jc0 jc0Var = this.o;
        if (jc0Var != null) {
            i13 = jc0Var.hashCode();
        }
        return i22 + i13;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SpanStyle(color=");
        a11 a11Var = this.a;
        sb.append((Object) se.j(a11Var.a()));
        sb.append(", brush=");
        sb.append(a11Var.d());
        sb.append(", alpha=");
        sb.append(a11Var.r());
        sb.append(", fontSize=");
        sb.append((Object) t11.e(this.b));
        sb.append(", fontWeight=");
        sb.append(this.c);
        sb.append(", fontStyle=");
        sb.append(this.d);
        sb.append(", fontSynthesis=");
        sb.append(this.e);
        sb.append(", fontFamily=");
        sb.append(this.f);
        sb.append(", fontFeatureSettings=");
        sb.append(this.g);
        sb.append(", letterSpacing=");
        sb.append((Object) t11.e(this.h));
        sb.append(", baselineShift=");
        sb.append(this.i);
        sb.append(", textGeometricTransform=");
        sb.append(this.j);
        sb.append(", localeList=");
        sb.append(this.k);
        sb.append(", background=");
        sb.append((Object) se.j(this.l));
        sb.append(", textDecoration=");
        sb.append(this.m);
        sb.append(", shadow=");
        sb.append(this.n);
        sb.append(", platformStyle=null, drawStyle=");
        sb.append(this.o);
        sb.append(')');
        return sb.toString();
    }

    public ux0(a11 a11Var, long j, nu nuVar, lu luVar, mu muVar, sl slVar, String str, long j2, t9 t9Var, b11 b11Var, ua0 ua0Var, long j3, w01 w01Var, tv0 tv0Var, jc0 jc0Var) {
        this.a = a11Var;
        this.b = j;
        this.c = nuVar;
        this.d = luVar;
        this.e = muVar;
        this.f = slVar;
        this.g = str;
        this.h = j2;
        this.i = t9Var;
        this.j = b11Var;
        this.k = ua0Var;
        this.l = j3;
        this.m = w01Var;
        this.n = tv0Var;
        this.o = jc0Var;
    }

    public ux0(long j, long j2, nu nuVar, lu luVar, mu muVar, sl slVar, String str, long j3, t9 t9Var, b11 b11Var, ua0 ua0Var, long j4, w01 w01Var, tv0 tv0Var) {
        this(j != 16 ? new bf(j) : z01.a, j2, nuVar, luVar, muVar, slVar, str, j3, t9Var, b11Var, ua0Var, j4, w01Var, tv0Var, (jc0) null);
    }
}
