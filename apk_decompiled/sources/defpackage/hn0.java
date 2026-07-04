package defpackage;

import android.os.Trace;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class hn0 implements p60 {
    public final int e;
    public final r7 f;
    public final gv g;
    public si h;
    public fz0 i;
    public m50 j;
    public boolean k;
    public boolean l;
    public boolean m;
    public Object n;
    public boolean o;
    public gn0 p;
    public boolean q;
    public long r;
    public long s;
    public long t;
    public boolean u;
    public final /* synthetic */ c9 v;

    public hn0(c9 c9Var, int i, r7 r7Var, pb pbVar) {
        this.v = c9Var;
        this.e = i;
        this.f = r7Var;
        this.g = pbVar;
        int i2 = hd0.b;
        this.t = System.nanoTime() - hd0.a;
    }

    @Override // defpackage.p60
    public final void a() {
        this.q = true;
    }

    public final void b() {
        hl0 hl0Var;
        m50 m50Var = this.j;
        if (m50Var != null) {
            switch (m50Var.a) {
                case 0:
                    break;
                default:
                    f50 b = m50Var.b();
                    if (b != null) {
                        hl0Var = b.f;
                    } else {
                        hl0Var = null;
                    }
                    if (hl0Var != null) {
                        n50.c(m50Var.b, m50Var.c);
                        break;
                    }
                    break;
            }
        }
        this.j = null;
        fz0 fz0Var = this.i;
        if (fz0Var != null) {
            fz0Var.a();
        }
        this.i = null;
        this.p = null;
    }

    public final boolean c(c6 c6Var) {
        boolean d;
        if (!this.v.b) {
            return false;
        }
        if (this.q) {
            Trace.beginSection("compose:lazy:prefetch:execute:urgent");
            try {
                d = d(c6Var);
            } finally {
                Trace.endSection();
            }
        } else {
            d = d(c6Var);
        }
        f31.V("compose:lazy:prefetch:execute:item", -1L);
        return d;
    }

    @Override // defpackage.p60
    public final void cancel() {
        if (!this.l) {
            this.l = true;
            b();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0236 A[Catch: all -> 0x0255, LOOP:2: B:96:0x0205->B:108:0x0236, LOOP_END, TRY_ENTER, TryCatch #1 {all -> 0x0255, blocks: (B:84:0x0170, B:86:0x0178, B:88:0x017e, B:91:0x018c, B:93:0x0199, B:94:0x01f8, B:95:0x01fe, B:96:0x0205, B:98:0x020d, B:105:0x0223, B:106:0x0228, B:108:0x0236, B:116:0x023c, B:118:0x01a1, B:120:0x01b0, B:121:0x01b6, B:126:0x01c6, B:130:0x01e5, B:131:0x01d4, B:134:0x01ec), top: B:83:0x0170 }] */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0232 A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r9v3, types: [v8, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r9v7 */
    /* JADX WARN: Type inference failed for: r9v8, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r9v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean d(defpackage.c6 r26) {
        /*
            Method dump skipped, instructions count: 796
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.hn0.d(c6):boolean");
    }

    public final boolean e() {
        m50 m50Var;
        if (this.m || ((m50Var = this.j) != null && m50Var.c())) {
            return true;
        }
        return false;
    }

    public final void f(long j) {
        if (this.l) {
            t00.a("Callers should check whether the request is still valid before calling performMeasure()");
        }
        if (this.k) {
            t00.a("Request was already measured!");
        }
        this.k = true;
        fz0 fz0Var = this.i;
        if (fz0Var != null) {
            int b = fz0Var.b();
            for (int i = 0; i < b; i++) {
                fz0Var.c(i, j);
            }
            return;
        }
        t00.b("performComposition() must be called before performMeasure()");
        throw new RuntimeException();
    }

    public final void g(Object obj, Object obj2, final v8 v8Var) {
        hl0 hl0Var;
        m50 m50Var;
        m50 m50Var2 = this.j;
        int i = 0;
        if (m50Var2 == null) {
            c9 c9Var = this.v;
            kv a = ((e60) c9Var.a).a(this.e, obj, obj2);
            n50 a2 = ((hz0) c9Var.c).a();
            if (!a2.e.E()) {
                m50Var = new m50(a2, obj, i);
            } else {
                a2.k(obj, a, true);
                m50Var = new m50(a2, obj, 1);
            }
            m50Var2 = m50Var;
            this.j = m50Var2;
            this.n = obj;
        }
        this.u = false;
        while (!m50Var2.c() && !this.u) {
            iw0 iw0Var = new iw0() { // from class: fn0
                @Override // defpackage.iw0
                public final boolean a() {
                    hn0 hn0Var = hn0.this;
                    if (!hn0Var.u) {
                        hn0Var.j();
                        long j = hn0Var.s;
                        v8 v8Var2 = v8Var;
                        v8Var2.a = v8.a(j, v8Var2.a);
                        hn0Var.u = !hn0Var.i(hn0Var.r, r1 + v8Var2.b);
                    }
                    return hn0Var.u;
                }
            };
            switch (m50Var2.a) {
                case 0:
                    break;
                default:
                    f50 b = m50Var2.b();
                    gv gvVar = null;
                    if (b != null) {
                        hl0Var = b.f;
                    } else {
                        hl0Var = null;
                    }
                    if (hl0Var != null && !hl0Var.c()) {
                        ww0 t = t20.t();
                        if (t != null) {
                            gvVar = t.e();
                        }
                        ww0 C = t20.C(t);
                        try {
                            hl0Var.e(iw0Var);
                            break;
                        } finally {
                        }
                    }
                    break;
            }
        }
        j();
        boolean z = this.u;
        long j = this.s;
        if (z) {
            v8Var.b = v8.a(j, v8Var.b);
        } else {
            v8Var.a = v8.a(j, v8Var.a);
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [ep0, java.lang.Object] */
    public final gn0 h() {
        fz0 fz0Var = this.i;
        if (fz0Var != null) {
            ?? obj = new Object();
            fz0Var.d(new l(10, obj));
            List list = (List) obj.e;
            if (list != null) {
                return new gn0(this, list);
            }
            return null;
        }
        t00.b("Should precompose before resolving nested prefetch states");
        throw new RuntimeException();
    }

    public final boolean i(long j, long j2) {
        if (this.q) {
            j2 = 0;
        }
        if (j > j2) {
            return true;
        }
        return false;
    }

    public final void j() {
        long j;
        long j2;
        long j3;
        long j4;
        int i = hd0.b;
        long nanoTime = System.nanoTime() - hd0.a;
        long j5 = this.t;
        long j6 = 0;
        if (((j5 - 1) | 1) == Long.MAX_VALUE) {
            if (nanoTime == j5) {
                dt0 dt0Var = aq.e;
            } else {
                if (j5 < 0) {
                    j4 = aq.g;
                } else {
                    j4 = aq.f;
                }
                j6 = ((-(j4 >> 1)) << 1) + (((int) j4) & 1);
                int i2 = bq.a;
            }
        } else if ((1 | (nanoTime - 1)) == Long.MAX_VALUE) {
            if (nanoTime < 0) {
                j2 = aq.g;
            } else {
                j2 = aq.f;
            }
            j6 = j2;
        } else {
            long j7 = nanoTime - j5;
            long j8 = (~(j7 ^ j5)) & (j7 ^ nanoTime);
            cq cqVar = cq.NANOSECONDS;
            if (j8 < 0) {
                cq cqVar2 = cq.MILLISECONDS;
                if (cqVar.compareTo(cqVar2) < 0) {
                    long j9 = (nanoTime / 1000000) - (j5 / 1000000);
                    long j10 = (nanoTime % 1000000) - (j5 % 1000000);
                    dt0 dt0Var2 = aq.e;
                    long R = f31.R(j9, cqVar2);
                    long R2 = f31.R(j10, cqVar);
                    int i3 = ((int) R) & 1;
                    if (i3 == (((int) R2) & 1)) {
                        if (i3 == 0) {
                            long j11 = (R >> 1) + (R2 >> 1);
                            if (-4611686018426999999L <= j11 && j11 < 4611686018427000000L) {
                                j6 = j11 << 1;
                                int i4 = bq.a;
                            } else {
                                j6 = f31.u(j11 / 1000000);
                            }
                        } else {
                            long h = f31.h(R >> 1, R2 >> 1);
                            if (h != 9223372036854759646L) {
                                if (h != 4611686018427387903L && h != -4611686018427387903L) {
                                    if (-4611686018426L <= h && h < 4611686018427L) {
                                        j6 = (h * 1000000) << 1;
                                        int i5 = bq.a;
                                    } else {
                                        j6 = f31.u(n30.k(h));
                                    }
                                } else {
                                    j6 = f31.u(h);
                                }
                            } else {
                                v7.m("Summing infinite durations of different signs yields an undefined result.");
                                return;
                            }
                        }
                    } else if (i3 == 1) {
                        j6 = aq.a(R >> 1, R2 >> 1);
                    } else {
                        j6 = aq.a(R2 >> 1, R >> 1);
                    }
                } else {
                    if (j7 < 0) {
                        j = aq.g;
                    } else {
                        j = aq.f;
                    }
                    j6 = ((-(j >> 1)) << 1) + (((int) j) & 1);
                    int i6 = bq.a;
                }
            } else {
                j6 = f31.R(j7, cqVar);
            }
        }
        long j12 = j6 >> 1;
        dt0 dt0Var3 = aq.e;
        if ((((int) j6) & 1) == 0) {
            j3 = j12;
        } else if (j12 > 9223372036854L) {
            j3 = Long.MAX_VALUE;
        } else if (j12 < -9223372036854L) {
            j3 = Long.MIN_VALUE;
        } else {
            j3 = j12 * 1000000;
        }
        this.s = j3;
        long j13 = this.r - j3;
        this.r = j13;
        this.t = nanoTime;
        f31.V("compose:lazy:prefetch:available_time_nanos", j13);
    }

    public final String toString() {
        return "HandleAndRequestImpl { index = " + this.e + ", constraints = " + this.h + ", isComposed = " + e() + ", isMeasured = " + this.k + ", isCanceled = " + this.l + " }";
    }
}
