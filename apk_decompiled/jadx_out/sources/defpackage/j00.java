package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class j00 {
    public final zo a;
    public e00 b;
    public h00 c;
    public g00 d;
    public f00 e;
    public dl f;
    public u41 g;
    public long h = 9205357640488583168L;
    public j21 i;
    public final pu j;
    public final pu k;
    public long l;

    /* JADX WARN: Type inference failed for: r3v1, types: [pu, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v2, types: [pu, java.lang.Object] */
    public j00(zo zoVar) {
        this.a = zoVar;
        ?? obj = new Object();
        obj.b = new pe0();
        this.j = obj;
        ?? obj2 = new Object();
        obj2.b = new ke0();
        this.k = obj2;
        this.l = 0L;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [g00, java.lang.Object] */
    public static void c(j00 j00Var, c00 c00Var, long j, long j2, int i) {
        if ((i & 4) != 0) {
            j2 = 0;
        }
        zo zoVar = j00Var.a;
        g00 g00Var = j00Var.d;
        g00 g00Var2 = g00Var;
        if (g00Var == null) {
            ?? obj = new Object();
            obj.v = null;
            obj.w = Long.MAX_VALUE;
            obj.x = false;
            j00Var.d = obj;
            g00Var2 = obj;
        }
        g00Var2.v = c00Var;
        g00Var2.w = j;
        j21 j21Var = j00Var.i;
        dj0 dj0Var = zoVar.u;
        if (j21Var == null) {
            j00Var.i = new j21(dj0Var, 0);
        } else {
            j21Var.a = dj0Var;
            j21Var.b = j2;
        }
        g00Var2.x = false;
        j00Var.f = g00Var2;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [e00, java.lang.Object] */
    public final void a() {
        e00 e00Var = this.b;
        d00 d00Var = d00.g;
        e00 e00Var2 = e00Var;
        if (e00Var == null) {
            ?? obj = new Object();
            obj.v = d00Var;
            obj.w = false;
            this.b = obj;
            e00Var2 = obj;
        }
        e00Var2.v = d00Var;
        e00Var2.w = false;
        this.f = e00Var2;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [f00, java.lang.Object] */
    public final void b(c00 c00Var, long j, j21 j21Var) {
        f00 f00Var = this.e;
        f00 f00Var2 = f00Var;
        if (f00Var == null) {
            ?? obj = new Object();
            obj.v = null;
            obj.w = Long.MAX_VALUE;
            this.e = obj;
            f00Var2 = obj;
        }
        f00Var2.v = c00Var;
        f00Var2.w = j;
        j21Var.b = 0L;
        this.f = f00Var2;
    }

    public final u41 d() {
        u41 u41Var = this.g;
        if (u41Var != null) {
            return u41Var;
        }
        v7.m("Velocity Tracker not initialized.");
        return null;
    }

    public final void e(c00 c00Var, b00 b00Var, long j) {
        long j2;
        zo zoVar = this.a;
        long u = k81.D(zoVar).u(0L);
        if (!ch0.c(this.h, 9205357640488583168L) && !ch0.c(u, this.h)) {
            this.l = ch0.g(this.l, ch0.f(u, this.h));
        }
        this.h = u;
        dj0 dj0Var = zoVar.u;
        dj0Var.getClass();
        hp hpVar = ip.a;
        if (dj0Var == dj0.e) {
            j2 = j & 4294967295L;
        } else {
            j2 = j >> 32;
        }
        if (Math.abs(Float.intBitsToFloat((int) j2)) > 2.0f) {
            n20.d(d(), c00Var, zoVar.u, b00Var, this.j, this.l);
            pu puVar = this.k;
            ke0 ke0Var = (ke0) puVar.b;
            int i = ke0Var.b;
            if (i == 3) {
                int i2 = puVar.a;
                puVar.a = i2 + 1;
                if (i2 >= 0 && i2 < i) {
                    long[] jArr = ke0Var.a;
                    long j3 = jArr[i2];
                    jArr[i2] = j;
                } else {
                    v7.f("Index must be between 0 and size");
                    return;
                }
            } else {
                ke0Var.a(j);
            }
            if (puVar.a == 3) {
                puVar.a = 0;
            }
            long[] jArr2 = ke0Var.a;
            int i3 = ke0Var.b;
            float f = 0.0f;
            float f2 = 0.0f;
            for (int i4 = 0; i4 < i3; i4++) {
                f2 += Float.intBitsToFloat((int) (jArr2[i4] >> 32));
            }
            int i5 = ke0Var.b;
            float f3 = f2 / i5;
            long[] jArr3 = ke0Var.a;
            for (int i6 = 0; i6 < i5; i6++) {
                f += Float.intBitsToFloat((int) (jArr3[i6] & 4294967295L));
            }
            float f4 = f / ke0Var.b;
            zoVar.O0(new oo((Float.floatToRawIntBits(f3) << 32) | (Float.floatToRawIntBits(f4) & 4294967295L), true));
        }
    }

    public final void f(c00 c00Var, c00 c00Var2, b00 b00Var, long j) {
        if (this.g == null) {
            this.g = new u41();
        }
        this.l = 0L;
        u41 d = d();
        zo zoVar = this.a;
        n20.d(d, c00Var, zoVar.u, b00Var, this.j, this.l);
        long f = ch0.f(n20.I(c00Var2, zoVar.u, b00Var), j);
        if (((Boolean) zoVar.v.e(new an0(1))).booleanValue()) {
            this.h = k81.D(zoVar).u(0L);
            zoVar.O0(new po(f));
        }
        pu puVar = this.k;
        puVar.a = 0;
        ((ke0) puVar.b).b = 0;
    }
}
