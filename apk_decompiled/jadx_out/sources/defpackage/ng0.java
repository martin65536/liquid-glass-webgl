package defpackage;

import android.os.Build;
import android.view.ViewParent;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class ng0 extends ob0 implements kc0, l40, nj0 {
    public static final pq0 Q = new pq0();
    public static final f40 R = new f40();
    public static final rt S = new rt(13);
    public static final rt T = new rt(14);
    public qc0 B;
    public oe0 C;
    public float E;
    public ue0 F;
    public f40 G;
    public boolean I;
    public boolean J;
    public hx K;
    public uc L;
    public v2 M;
    public boolean O;
    public lj0 P;
    public final z40 s;
    public ng0 t;
    public ng0 u;
    public boolean v;
    public boolean w;
    public gv x;
    public mm y;
    public m40 z;
    public float A = 0.8f;
    public long D = 0;
    public zv0 H = o20.o;
    public final mg0 N = new mg0(this, 1);

    public ng0(z40 z40Var) {
        this.s = z40Var;
        this.y = z40Var.A;
        this.z = z40Var.B;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5, types: [bd0] */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8, types: [bd0] */
    /* JADX WARN: Type inference failed for: r1v9, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3, types: [ef0] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6, types: [ef0] */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    @Override // defpackage.em0, defpackage.kc0
    public final Object A() {
        z40 z40Var = this.s;
        if (!z40Var.H.d(64)) {
            return null;
        }
        P0();
        Object obj = null;
        for (bd0 bd0Var = z40Var.H.e; bd0Var != null; bd0Var = bd0Var.i) {
            if ((bd0Var.g & 64) != 0) {
                jm jmVar = bd0Var;
                ?? r4 = 0;
                while (jmVar != 0) {
                    if (jmVar instanceof jk0) {
                        obj = ((jk0) jmVar).l0(obj);
                    } else if ((jmVar.g & 64) != 0 && (jmVar instanceof jm)) {
                        bd0 bd0Var2 = jmVar.t;
                        int i = 0;
                        jmVar = jmVar;
                        r4 = r4;
                        while (bd0Var2 != null) {
                            if ((bd0Var2.g & 64) != 0) {
                                i++;
                                r4 = r4;
                                if (i == 1) {
                                    jmVar = bd0Var2;
                                } else {
                                    if (r4 == 0) {
                                        r4 = new ef0(new bd0[16]);
                                    }
                                    if (jmVar != 0) {
                                        r4.b(jmVar);
                                        jmVar = 0;
                                    }
                                    r4.b(bd0Var2);
                                }
                            }
                            bd0Var2 = bd0Var2.j;
                            jmVar = jmVar;
                            r4 = r4;
                        }
                        if (i == 1) {
                        }
                    }
                    jmVar = k81.h(r4);
                }
            }
        }
        return obj;
    }

    @Override // defpackage.mm
    public final float B() {
        return this.s.A.B();
    }

    @Override // defpackage.l40
    public final l40 C() {
        boolean z = P0().r;
        z40 z40Var = this.s;
        if (!z) {
            StringBuilder sb = new StringBuilder("LayoutCoordinate operations are only valid when isAttached is true");
            for (z40 z40Var2 = z40Var; z40Var2 != null; z40Var2 = z40Var2.s()) {
                sb.append("\n|");
                sb.append(z40Var2);
                sb.append(" isAttached=");
                sb.append(z40Var2.E());
                sb.append(" modifier=");
                sb.append(z40Var2.M);
                sb.append(" tail=");
                sb.append(P0());
            }
            q00.b(sb.toString());
        }
        Z0();
        return z40Var.H.d.u;
    }

    @Override // defpackage.ob0
    public final void D0() {
        i0(this.D, this.E, this.x);
    }

    public final void E0(ng0 ng0Var, ue0 ue0Var, boolean z) {
        if (ng0Var != this) {
            ng0 ng0Var2 = this.u;
            if (ng0Var2 != null) {
                ng0Var2.E0(ng0Var, ue0Var, z);
            }
            long j = this.D;
            float f = (int) (j >> 32);
            ue0Var.a -= f;
            ue0Var.c -= f;
            float f2 = (int) (j & 4294967295L);
            ue0Var.b -= f2;
            ue0Var.d -= f2;
            lj0 lj0Var = this.P;
            if (lj0Var != null) {
                kx kxVar = (kx) lj0Var;
                float[] a = kxVar.a();
                if (!kxVar.w) {
                    if (a == null) {
                        ue0Var.a = 0.0f;
                        ue0Var.b = 0.0f;
                        ue0Var.c = 0.0f;
                        ue0Var.d = 0.0f;
                    } else {
                        m20.z(a, ue0Var);
                    }
                }
                if (this.w && z) {
                    long j2 = this.g;
                    ue0Var.a(0.0f, 0.0f, (int) (j2 >> 32), (int) (j2 & 4294967295L));
                }
            }
        }
    }

    public final long F0(ng0 ng0Var, long j) {
        if (ng0Var == this) {
            return j;
        }
        ng0 ng0Var2 = this.u;
        if (ng0Var2 != null && !o20.e(ng0Var, ng0Var2)) {
            return M0(ng0Var2.F0(ng0Var, j));
        }
        return M0(j);
    }

    public final long G0(long j) {
        float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32)) - g0();
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L)) - f0();
        float max = Math.max(0.0f, intBitsToFloat / 2.0f);
        float max2 = Math.max(0.0f, intBitsToFloat2 / 2.0f);
        return (Float.floatToRawIntBits(max) << 32) | (Float.floatToRawIntBits(max2) & 4294967295L);
    }

    @Override // defpackage.nj0
    public final boolean H() {
        if (this.P != null && !this.v && this.s.E()) {
            return true;
        }
        return false;
    }

    public final float H0(long j, long j2) {
        float g0;
        float f0;
        if (g0() >= Float.intBitsToFloat((int) (j2 >> 32)) && f0() >= Float.intBitsToFloat((int) (j2 & 4294967295L))) {
            return Float.POSITIVE_INFINITY;
        }
        long G0 = G0(j2);
        float intBitsToFloat = Float.intBitsToFloat((int) (G0 >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (G0 & 4294967295L));
        float intBitsToFloat3 = Float.intBitsToFloat((int) (j >> 32));
        if (intBitsToFloat3 < 0.0f) {
            g0 = -intBitsToFloat3;
        } else {
            g0 = intBitsToFloat3 - g0();
        }
        float max = Math.max(0.0f, g0);
        float intBitsToFloat4 = Float.intBitsToFloat((int) (j & 4294967295L));
        if (intBitsToFloat4 < 0.0f) {
            f0 = -intBitsToFloat4;
        } else {
            f0 = intBitsToFloat4 - f0();
        }
        float max2 = Math.max(0.0f, f0);
        long floatToRawIntBits = (Float.floatToRawIntBits(max) << 32) | (Float.floatToRawIntBits(max2) & 4294967295L);
        if (intBitsToFloat > 0.0f || intBitsToFloat2 > 0.0f) {
            int i = (int) (floatToRawIntBits >> 32);
            if (Float.intBitsToFloat(i) <= intBitsToFloat) {
                int i2 = (int) (floatToRawIntBits & 4294967295L);
                if (Float.intBitsToFloat(i2) <= intBitsToFloat2) {
                    float intBitsToFloat5 = Float.intBitsToFloat(i);
                    float intBitsToFloat6 = Float.intBitsToFloat(i2);
                    return (intBitsToFloat6 * intBitsToFloat6) + (intBitsToFloat5 * intBitsToFloat5);
                }
            }
        }
        return Float.POSITIVE_INFINITY;
    }

    public final void I0(uc ucVar, hx hxVar) {
        lj0 lj0Var = this.P;
        if (lj0Var != null) {
            kx kxVar = (kx) lj0Var;
            yc ycVar = kxVar.q;
            kxVar.g();
            kxVar.e.a.L();
            r7 r7Var = ycVar.f;
            r7Var.D(ucVar);
            r7Var.g = hxVar;
            n20.r(ycVar, kxVar.e);
            return;
        }
        long j = this.D;
        float f = (int) (j >> 32);
        float f2 = (int) (j & 4294967295L);
        ucVar.d(f, f2);
        J0(ucVar, hxVar);
        ucVar.d(-f, -f2);
    }

    public final void J0(uc ucVar, hx hxVar) {
        ng0 ng0Var;
        uc ucVar2;
        hx hxVar2;
        bd0 Q0 = Q0(4);
        if (Q0 == null) {
            f1(ucVar, hxVar);
            return;
        }
        z40 z40Var = this.s;
        z40Var.getClass();
        b50 sharedDrawScope = ((b4) c50.a(z40Var)).getSharedDrawScope();
        long J = d20.J(this.g);
        sharedDrawScope.getClass();
        ef0 ef0Var = null;
        while (Q0 != null) {
            if (Q0 instanceof tp) {
                ng0Var = this;
                ucVar2 = ucVar;
                hxVar2 = hxVar;
                sharedDrawScope.u(ucVar2, J, ng0Var, (tp) Q0, hxVar2);
            } else {
                ng0Var = this;
                ucVar2 = ucVar;
                hxVar2 = hxVar;
                if ((Q0.g & 4) != 0 && (Q0 instanceof jm)) {
                    int i = 0;
                    for (bd0 bd0Var = ((jm) Q0).t; bd0Var != null; bd0Var = bd0Var.j) {
                        if ((bd0Var.g & 4) != 0) {
                            i++;
                            if (i == 1) {
                                Q0 = bd0Var;
                            } else {
                                if (ef0Var == null) {
                                    ef0Var = new ef0(new bd0[16]);
                                }
                                if (Q0 != null) {
                                    ef0Var.b(Q0);
                                    Q0 = null;
                                }
                                ef0Var.b(bd0Var);
                            }
                        }
                    }
                    if (i == 1) {
                        ucVar = ucVar2;
                        this = ng0Var;
                        hxVar = hxVar2;
                    }
                }
            }
            Q0 = k81.h(ef0Var);
            ucVar = ucVar2;
            this = ng0Var;
            hxVar = hxVar2;
        }
    }

    public abstract void K0();

    public final ng0 L0(ng0 ng0Var) {
        z40 z40Var = ng0Var.s;
        z40 z40Var2 = this.s;
        if (z40Var == z40Var2) {
            bd0 P0 = ng0Var.P0();
            bd0 P02 = P0();
            if (!P02.e.r) {
                q00.b("visitLocalAncestors called on an unattached node");
            }
            for (bd0 bd0Var = P02.e.i; bd0Var != null; bd0Var = bd0Var.i) {
                if ((bd0Var.g & 2) != 0 && bd0Var == P0) {
                    return ng0Var;
                }
            }
            return this;
        }
        while (z40Var.s > z40Var2.s) {
            z40Var = z40Var.s();
            z40Var.getClass();
        }
        z40 z40Var3 = z40Var2;
        while (z40Var3.s > z40Var.s) {
            z40Var3 = z40Var3.s();
            z40Var3.getClass();
        }
        while (z40Var != z40Var3) {
            z40Var = z40Var.s();
            z40Var3 = z40Var3.s();
            if (z40Var == null || z40Var3 == null) {
                v7.m("layouts are not part of the same hierarchy");
                return null;
            }
        }
        if (z40Var3 != z40Var2) {
            if (z40Var != ng0Var.s) {
                return z40Var.H.c;
            }
            return ng0Var;
        }
        return this;
    }

    public final long M0(long j) {
        long j2 = this.D;
        float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32)) - ((int) (j2 >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L)) - ((int) (j2 & 4294967295L));
        long floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(intBitsToFloat) << 32);
        lj0 lj0Var = this.P;
        if (lj0Var != null) {
            kx kxVar = (kx) lj0Var;
            float[] a = kxVar.a();
            if (a == null) {
                return 9187343241974906880L;
            }
            if (!kxVar.w) {
                return m20.y(a, floatToRawIntBits);
            }
        }
        return floatToRawIntBits;
    }

    public abstract qb0 N0();

    public final long O0() {
        return this.y.Z(this.s.C.e());
    }

    @Override // defpackage.l40
    public final long P(l40 l40Var, long j) {
        return R(l40Var, j);
    }

    public abstract bd0 P0();

    @Override // defpackage.l40
    public final boolean Q() {
        return P0().r;
    }

    public final bd0 Q0(int i) {
        boolean f = og0.f(i);
        bd0 P0 = P0();
        if (f || (P0 = P0.i) != null) {
            for (bd0 R0 = R0(f); R0 != null && (R0.h & i) != 0; R0 = R0.j) {
                if ((R0.g & i) != 0) {
                    return R0;
                }
                if (R0 == P0) {
                    return null;
                }
            }
            return null;
        }
        return null;
    }

    @Override // defpackage.l40
    public final long R(l40 l40Var, long j) {
        rb0 rb0Var;
        ng0 ng0Var;
        boolean z = l40Var instanceof rb0;
        if (z) {
            rb0 rb0Var2 = (rb0) l40Var;
            rb0Var2.e.s.Z0();
            return rb0Var2.R(this, j ^ (-9223372034707292160L)) ^ (-9223372034707292160L);
        }
        if (z) {
            rb0Var = (rb0) l40Var;
        } else {
            rb0Var = null;
        }
        if (rb0Var == null || (ng0Var = rb0Var.e.s) == null) {
            l40Var.getClass();
            ng0Var = (ng0) l40Var;
        }
        ng0Var.Z0();
        ng0 L0 = L0(ng0Var);
        while (ng0Var != L0) {
            lj0 lj0Var = ng0Var.P;
            if (lj0Var != null) {
                kx kxVar = (kx) lj0Var;
                float[] b = kxVar.b();
                if (!kxVar.w) {
                    j = m20.y(b, j);
                }
            }
            j = f31.J(j, ng0Var.D);
            ng0Var = ng0Var.u;
            ng0Var.getClass();
        }
        return F0(L0, j);
    }

    public final bd0 R0(boolean z) {
        bd0 P0;
        lg0 lg0Var = this.s.H;
        if (lg0Var.d == this) {
            return lg0Var.f;
        }
        ng0 ng0Var = this.u;
        if (z) {
            if (ng0Var != null && (P0 = ng0Var.P0()) != null) {
                return P0.j;
            }
            return null;
        }
        if (ng0Var != null) {
            return ng0Var.P0();
        }
        return null;
    }

    public final void S0(bd0 bd0Var, rt rtVar, long j, py pyVar, int i, boolean z) {
        if (bd0Var == null) {
            V0(rtVar, j, pyVar, i, z);
            return;
        }
        if (!rtVar.l(bd0Var)) {
            S0(n30.e(bd0Var, rtVar.i()), rtVar, j, pyVar, i, z);
            return;
        }
        int i2 = pyVar.g;
        pe0 pe0Var = pyVar.e;
        pyVar.b(i2 + 1, pe0Var.b);
        pyVar.g++;
        pe0Var.a(bd0Var);
        pyVar.f.a(jc0.c(-1.0f, z, false));
        S0(n30.e(bd0Var, rtVar.i()), rtVar, j, pyVar, i, z);
        pyVar.g = i2;
    }

    public final void T0(bd0 bd0Var, rt rtVar, long j, py pyVar, int i, boolean z, float f) {
        if (bd0Var == null) {
            V0(rtVar, j, pyVar, i, z);
            return;
        }
        if (!rtVar.l(bd0Var)) {
            T0(n30.e(bd0Var, rtVar.i()), rtVar, j, pyVar, i, z, f);
            return;
        }
        int i2 = pyVar.g;
        pe0 pe0Var = pyVar.e;
        pyVar.b(i2 + 1, pe0Var.b);
        pyVar.g++;
        pe0Var.a(bd0Var);
        pyVar.f.a(jc0.c(f, z, false));
        e1(n30.e(bd0Var, rtVar.i()), rtVar, j, pyVar, i, z, f, true);
        pyVar.g = i2;
    }

    @Override // defpackage.l40
    public final wo0 U(l40 l40Var, boolean z) {
        rb0 rb0Var;
        ng0 ng0Var;
        if (!P0().r) {
            q00.b("LayoutCoordinate operations are only valid when isAttached is true");
        }
        if (!l40Var.Q()) {
            q00.b("LayoutCoordinates " + l40Var + " is not attached!");
        }
        if (l40Var instanceof rb0) {
            rb0Var = (rb0) l40Var;
        } else {
            rb0Var = null;
        }
        if (rb0Var == null || (ng0Var = rb0Var.e.s) == null) {
            ng0Var = (ng0) l40Var;
        }
        ng0Var.Z0();
        ng0 L0 = L0(ng0Var);
        ue0 ue0Var = this.F;
        if (ue0Var == null) {
            ue0Var = new ue0();
            this.F = ue0Var;
        }
        ue0Var.a = 0.0f;
        ue0Var.b = 0.0f;
        ue0Var.c = (int) (l40Var.X() >> 32);
        ue0Var.d = (int) (l40Var.X() & 4294967295L);
        while (ng0Var != L0) {
            ng0Var.h1(ue0Var, z, false);
            if (ue0Var.b()) {
                return wo0.e;
            }
            ng0Var = ng0Var.u;
            ng0Var.getClass();
        }
        E0(L0, ue0Var, z);
        return new wo0(ue0Var.a, ue0Var.b, ue0Var.c, ue0Var.d);
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x00c4, code lost:
    
        if (defpackage.f31.q(r18.a(), defpackage.jc0.c(r2, r7, false)) > 0) goto L38;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void U0(defpackage.rt r15, long r16, defpackage.py r18, int r19, boolean r20) {
        /*
            r14 = this;
            r3 = r16
            r5 = r18
            r6 = r19
            int r0 = r15.i()
            bd0 r1 = r14.Q0(r0)
            boolean r0 = r14.o1(r3)
            r8 = 0
            r9 = 2139095040(0x7f800000, float:Infinity)
            r10 = 2147483647(0x7fffffff, float:NaN)
            r11 = 1
            if (r0 != 0) goto L4c
            if (r6 != r11) goto L4b
            long r12 = r14.O0()
            float r0 = r14.H0(r3, r12)
            int r2 = java.lang.Float.floatToRawIntBits(r0)
            r2 = r2 & r10
            if (r2 >= r9) goto L4b
            int r2 = r5.g
            pe0 r7 = r5.e
            int r7 = r7.b
            int r7 = r7 - r11
            if (r2 != r7) goto L36
            goto L44
        L36:
            long r7 = defpackage.jc0.c(r0, r8, r8)
            long r9 = r5.a()
            int r2 = defpackage.f31.q(r9, r7)
            if (r2 <= 0) goto L4b
        L44:
            r7 = 0
            r2 = r15
            r8 = r0
            r0 = r14
            r0.T0(r1, r2, r3, r5, r6, r7, r8)
        L4b:
            return
        L4c:
            if (r1 != 0) goto L52
            r14.V0(r15, r16, r18, r19, r20)
            return
        L52:
            r0 = 32
            long r2 = r16 >> r0
            int r0 = (int) r2
            float r0 = java.lang.Float.intBitsToFloat(r0)
            r2 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r2 = r16 & r2
            int r2 = (int) r2
            float r2 = java.lang.Float.intBitsToFloat(r2)
            r3 = 0
            int r4 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r4 < 0) goto L90
            int r3 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r3 < 0) goto L90
            int r3 = r14.g0()
            float r3 = (float) r3
            int r0 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r0 >= 0) goto L90
            int r0 = r14.f0()
            float r0 = (float) r0
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 >= 0) goto L90
            r0 = r14
            r2 = r15
            r3 = r16
            r5 = r18
            r6 = r19
            r7 = r20
            r0.S0(r1, r2, r3, r5, r6, r7)
            return
        L90:
            r3 = r16
            r5 = r18
            r6 = r19
            if (r6 != r11) goto La1
            long r12 = r14.O0()
            float r2 = r14.H0(r3, r12)
            goto La3
        La1:
            r2 = 2139095040(0x7f800000, float:Infinity)
        La3:
            int r7 = java.lang.Float.floatToRawIntBits(r2)
            r7 = r7 & r10
            if (r7 >= r9) goto Lcb
            int r7 = r5.g
            pe0 r9 = r5.e
            int r9 = r9.b
            int r9 = r9 - r11
            if (r7 != r9) goto Lb6
            r7 = r20
            goto Lc6
        Lb6:
            r7 = r20
            long r9 = defpackage.jc0.c(r2, r7, r8)
            long r12 = r5.a()
            int r9 = defpackage.f31.q(r12, r9)
            if (r9 <= 0) goto Lcd
        Lc6:
            r9 = r11
        Lc7:
            r0 = r14
            r8 = r2
            r2 = r15
            goto Lcf
        Lcb:
            r7 = r20
        Lcd:
            r9 = r8
            goto Lc7
        Lcf:
            r0.e1(r1, r2, r3, r5, r6, r7, r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ng0.U0(rt, long, py, int, boolean):void");
    }

    public void V0(rt rtVar, long j, py pyVar, int i, boolean z) {
        ng0 ng0Var = this.t;
        if (ng0Var != null) {
            ng0Var.U0(rtVar, ng0Var.M0(j), pyVar, i, z);
        }
    }

    public final void W0() {
        lj0 lj0Var = this.P;
        if (lj0Var != null) {
            ((kx) lj0Var).c();
            return;
        }
        ng0 ng0Var = this.u;
        if (ng0Var != null) {
            ng0Var.W0();
        }
    }

    @Override // defpackage.l40
    public final long X() {
        return this.g;
    }

    public final boolean X0() {
        if (this.P != null && this.A <= 0.0f) {
            return true;
        }
        ng0 ng0Var = this.u;
        if (ng0Var != null) {
            return ng0Var.X0();
        }
        return false;
    }

    public final long Y0(long j) {
        if (!P0().r) {
            q00.b("LayoutCoordinate operations are only valid when isAttached is true");
        }
        Z0();
        while (this != null) {
            z40 z40Var = this.s;
            if (this == z40Var.H.d && !z40Var.g) {
                long b = ((b4) c50.a(z40Var)).getRectManager().b(z40Var);
                if (!v10.a(b, 9223372034707292159L)) {
                    return f31.J(j, b);
                }
            }
            lj0 lj0Var = this.P;
            if (lj0Var != null) {
                kx kxVar = (kx) lj0Var;
                float[] b2 = kxVar.b();
                if (!kxVar.w) {
                    j = m20.y(b2, j);
                }
            }
            j = f31.J(j, this.D);
            this = this.u;
        }
        return j;
    }

    public final void Z0() {
        this.s.I.b();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v15 */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5, types: [bd0] */
    /* JADX WARN: Type inference failed for: r7v7, types: [bd0] */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v2, types: [ef0] */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v6, types: [ef0] */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Type inference failed for: r8v9 */
    public final void a1() {
        gv gvVar;
        bd0 bd0Var;
        boolean f = og0.f(128);
        bd0 R0 = R0(f);
        if (R0 != null && (R0.e.h & 128) != 0) {
            ww0 t = t20.t();
            if (t != null) {
                gvVar = t.e();
            } else {
                gvVar = null;
            }
            ww0 C = t20.C(t);
            try {
                if (f) {
                    bd0Var = P0();
                } else {
                    bd0Var = P0().i;
                    if (bd0Var == null) {
                    }
                }
                for (bd0 R02 = R0(f); R02 != null; R02 = R02.j) {
                    if ((R02.h & 128) == 0) {
                        break;
                    }
                    if ((R02.g & 128) != 0) {
                        jm jmVar = R02;
                        ?? r8 = 0;
                        while (jmVar != 0) {
                            if (jmVar instanceof sc0) {
                                ((sc0) jmVar).C(this.g);
                            } else if ((jmVar.g & 128) != 0 && (jmVar instanceof jm)) {
                                bd0 bd0Var2 = jmVar.t;
                                int i = 0;
                                jmVar = jmVar;
                                r8 = r8;
                                while (bd0Var2 != null) {
                                    if ((bd0Var2.g & 128) != 0) {
                                        i++;
                                        r8 = r8;
                                        if (i == 1) {
                                            jmVar = bd0Var2;
                                        } else {
                                            if (r8 == 0) {
                                                r8 = new ef0(new bd0[16]);
                                            }
                                            if (jmVar != 0) {
                                                r8.b(jmVar);
                                                jmVar = 0;
                                            }
                                            r8.b(bd0Var2);
                                        }
                                    }
                                    bd0Var2 = bd0Var2.j;
                                    jmVar = jmVar;
                                    r8 = r8;
                                }
                                if (i == 1) {
                                }
                            }
                            jmVar = k81.h(r8);
                        }
                    }
                    if (R02 == bd0Var) {
                        break;
                    }
                }
            } finally {
                t20.K(t, C, gvVar);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1, types: [bd0] */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v4, types: [bd0] */
    /* JADX WARN: Type inference failed for: r4v5, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v3, types: [ef0] */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6, types: [ef0] */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r5v9 */
    public final void b1() {
        boolean f = og0.f(4194304);
        bd0 P0 = P0();
        if (f || (P0 = P0.i) != null) {
            for (bd0 R0 = R0(f); R0 != null && (R0.h & 4194304) != 0; R0 = R0.j) {
                if ((R0.g & 4194304) != 0) {
                    jm jmVar = R0;
                    ?? r5 = 0;
                    while (jmVar != 0) {
                        if (jmVar instanceof j40) {
                            ((j40) jmVar).x(this);
                        } else if ((jmVar.g & 4194304) != 0 && (jmVar instanceof jm)) {
                            bd0 bd0Var = jmVar.t;
                            int i = 0;
                            jmVar = jmVar;
                            r5 = r5;
                            while (bd0Var != null) {
                                if ((bd0Var.g & 4194304) != 0) {
                                    i++;
                                    r5 = r5;
                                    if (i == 1) {
                                        jmVar = bd0Var;
                                    } else {
                                        if (r5 == 0) {
                                            r5 = new ef0(new bd0[16]);
                                        }
                                        if (jmVar != 0) {
                                            r5.b(jmVar);
                                            jmVar = 0;
                                        }
                                        r5.b(bd0Var);
                                    }
                                }
                                bd0Var = bd0Var.j;
                                jmVar = jmVar;
                                r5 = r5;
                            }
                            if (i == 1) {
                            }
                        }
                        jmVar = k81.h(r5);
                    }
                }
                if (R0 == P0) {
                    return;
                }
            }
        }
    }

    public final void c1() {
        this.v = true;
        this.N.a();
        i1();
        if (!v10.a(this.D, 0L)) {
            this.s.L(this);
        }
    }

    public final void d1() {
        boolean f = og0.f(1048576);
        bd0 R0 = R0(f);
        if (R0 != null && (R0.e.h & 1048576) != 0) {
            bd0 P0 = P0();
            if (f || (P0 = P0.i) != null) {
                for (bd0 R02 = R0(f); R02 != null && (R02.h & 1048576) != 0; R02 = R02.j) {
                    if ((R02.g & 1048576) != 0) {
                        bd0 bd0Var = R02;
                        ef0 ef0Var = null;
                        while (bd0Var != null) {
                            if ((bd0Var.g & 1048576) != 0 && (bd0Var instanceof jm)) {
                                int i = 0;
                                for (bd0 bd0Var2 = ((jm) bd0Var).t; bd0Var2 != null; bd0Var2 = bd0Var2.j) {
                                    if ((bd0Var2.g & 1048576) != 0) {
                                        i++;
                                        if (i == 1) {
                                            bd0Var = bd0Var2;
                                        } else {
                                            if (ef0Var == null) {
                                                ef0Var = new ef0(new bd0[16]);
                                            }
                                            if (bd0Var != null) {
                                                ef0Var.b(bd0Var);
                                                bd0Var = null;
                                            }
                                            ef0Var.b(bd0Var2);
                                        }
                                    }
                                }
                                if (i == 1) {
                                }
                            }
                            bd0Var = k81.h(ef0Var);
                        }
                    }
                    if (R02 == P0) {
                        return;
                    }
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1, types: [bd0] */
    /* JADX WARN: Type inference failed for: r3v25 */
    public final void e1(bd0 bd0Var, rt rtVar, long j, py pyVar, int i, boolean z, float f, boolean z2) {
        int e;
        int e2;
        bd0 h;
        if (bd0Var == null) {
            V0(rtVar, j, pyVar, i, z);
            return;
        }
        if (!rtVar.l(bd0Var)) {
            e1(n30.e(bd0Var, rtVar.i()), rtVar, j, pyVar, i, z, f, z2);
            return;
        }
        int i2 = i;
        boolean z3 = z;
        char c = 3;
        if (i2 == 3 || i2 == 4) {
            jm jmVar = bd0Var;
            ef0 ef0Var = null;
            while (true) {
                if (jmVar == 0) {
                    break;
                }
                int i3 = 0;
                if (jmVar instanceof xm0) {
                    long A = ((xm0) jmVar).A();
                    int i4 = (int) (j >> 32);
                    float intBitsToFloat = Float.intBitsToFloat(i4);
                    z40 z40Var = this.s;
                    m40 m40Var = z40Var.B;
                    long j2 = Long.MIN_VALUE & A;
                    m40 m40Var2 = m40.e;
                    if (j2 != 0 && m40Var != m40Var2) {
                        e = ey0.e(2, A);
                    } else {
                        e = ey0.e(0, A);
                    }
                    if (intBitsToFloat >= (-e)) {
                        float intBitsToFloat2 = Float.intBitsToFloat(i4);
                        int g0 = g0();
                        m40 m40Var3 = z40Var.B;
                        if (j2 != 0 && m40Var3 != m40Var2) {
                            e2 = ey0.e(0, A);
                        } else {
                            e2 = ey0.e(2, A);
                        }
                        if (intBitsToFloat2 < g0 + e2) {
                            int i5 = (int) (j & 4294967295L);
                            if (Float.intBitsToFloat(i5) >= (-ey0.e(1, A))) {
                                if (Float.intBitsToFloat(i5) < ey0.e(3, A) + f0()) {
                                    ke0 ke0Var = pyVar.f;
                                    pe0 pe0Var = pyVar.e;
                                    int i6 = pyVar.g;
                                    int i7 = pe0Var.b;
                                    if (i6 == i7 - 1) {
                                        pyVar.b(i6 + 1, i7);
                                        pyVar.g++;
                                        pe0Var.a(bd0Var);
                                        ke0Var.a(jc0.c(0.0f, z3, true));
                                        e1(n30.e(bd0Var, rtVar.i()), rtVar, j, pyVar, i2, z3, f, z2);
                                        pyVar.g = i6;
                                        return;
                                    }
                                    long a = pyVar.a();
                                    int i8 = pyVar.g;
                                    if (f31.E(a)) {
                                        int i9 = pe0Var.b;
                                        int i10 = i9 - 1;
                                        pyVar.g = i10;
                                        pyVar.b(i9, pe0Var.b);
                                        pyVar.g++;
                                        pe0Var.a(bd0Var);
                                        ke0Var.a(jc0.c(0.0f, z3, true));
                                        e1(n30.e(bd0Var, rtVar.i()), rtVar, j, pyVar, i, z3, f, z2);
                                        pyVar.g = i10;
                                        if (f31.B(pyVar.a()) < 0.0f) {
                                            pyVar.b(i8 + 1, pyVar.g + 1);
                                        }
                                        pyVar.g = i8;
                                        return;
                                    }
                                    if (f31.B(a) > 0.0f) {
                                        int i11 = pyVar.g;
                                        pyVar.b(i11 + 1, pe0Var.b);
                                        pyVar.g++;
                                        pe0Var.a(bd0Var);
                                        ke0Var.a(jc0.c(0.0f, z3, true));
                                        e1(n30.e(bd0Var, rtVar.i()), rtVar, j, pyVar, i, z3, f, z2);
                                        pyVar.g = i11;
                                        return;
                                    }
                                    return;
                                }
                            }
                        }
                    }
                } else {
                    char c2 = c;
                    if ((jmVar.g & 16) != 0 && (jmVar instanceof jm)) {
                        bd0 bd0Var2 = jmVar.t;
                        h = jmVar;
                        ef0Var = ef0Var;
                        while (bd0Var2 != null) {
                            if ((bd0Var2.g & 16) != 0) {
                                i3++;
                                ef0Var = ef0Var;
                                if (i3 == 1) {
                                    h = bd0Var2;
                                } else {
                                    if (ef0Var == null) {
                                        ef0Var = new ef0(new bd0[16]);
                                    }
                                    if (h != null) {
                                        ef0Var.b(h);
                                        h = null;
                                    }
                                    ef0Var.b(bd0Var2);
                                }
                            }
                            bd0Var2 = bd0Var2.j;
                            h = h;
                            ef0Var = ef0Var;
                        }
                        if (i3 == 1) {
                            i2 = i;
                            z3 = z;
                            c = c2;
                            jmVar = h;
                            ef0Var = ef0Var;
                        }
                    }
                    h = k81.h(ef0Var);
                    i2 = i;
                    z3 = z;
                    c = c2;
                    jmVar = h;
                    ef0Var = ef0Var;
                }
            }
        }
        if (z2) {
            T0(bd0Var, rtVar, j, pyVar, i, z, f);
        } else {
            k1(bd0Var, rtVar, j, pyVar, i, z, f);
        }
    }

    public abstract void f1(uc ucVar, hx hxVar);

    public final void g1(long j, float f, gv gvVar) {
        m1(gvVar, false);
        boolean a = v10.a(this.D, j);
        z40 z40Var = this.s;
        if (!a) {
            ((b4) c50.a(z40Var)).N(-4.0f);
            this.D = j;
            lj0 lj0Var = this.P;
            if (lj0Var != null) {
                ((kx) lj0Var).d(j);
            } else {
                ng0 ng0Var = this.u;
                if (ng0Var != null) {
                    ng0Var.W0();
                }
            }
            z40Var.L(this);
            ob0.A0(this);
            mj0 mj0Var = z40Var.r;
            if (mj0Var != null) {
                ((b4) mj0Var).A(z40Var);
            }
        }
        this.E = f;
        if (this == z40Var.H.d) {
            ((b4) c50.a(z40Var)).getRectManager().f(z40Var);
        }
        if (!this.o) {
            q0(w0());
        }
    }

    @Override // defpackage.rc0
    public final m40 getLayoutDirection() {
        return this.s.B;
    }

    public final void h1(ue0 ue0Var, boolean z, boolean z2) {
        long j;
        lj0 lj0Var = this.P;
        if (lj0Var != null) {
            if (this.w) {
                if (z2) {
                    long O0 = O0();
                    float f = ue0Var.a;
                    float f2 = ue0Var.b;
                    if (ue0Var.c >= 0.0f) {
                        long j2 = this.g;
                        if (f <= ((int) (j2 >> 32)) && ue0Var.d >= 0.0f && f2 <= ((int) (j2 & 4294967295L))) {
                            float intBitsToFloat = Float.intBitsToFloat((int) (O0 >> 32));
                            float intBitsToFloat2 = Float.intBitsToFloat((int) (O0 & 4294967295L));
                            float f3 = (intBitsToFloat - (ue0Var.c - ue0Var.a)) / 2.0f;
                            if (f3 > 0.0f) {
                                f -= f3;
                            } else {
                                float f4 = (-intBitsToFloat) / 2.0f;
                                if (f < f4) {
                                    f = f4;
                                }
                            }
                            float f5 = (intBitsToFloat2 - (ue0Var.d - ue0Var.b)) / 2.0f;
                            if (f5 > 0.0f) {
                                f2 -= f5;
                            } else {
                                float f6 = (-intBitsToFloat2) / 2.0f;
                                if (f2 < f6) {
                                    f2 = f6;
                                }
                            }
                            j = (Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(f2) & 4294967295L);
                            float intBitsToFloat3 = Float.intBitsToFloat((int) (j >> 32));
                            float intBitsToFloat4 = Float.intBitsToFloat((int) (j & 4294967295L));
                            long j3 = this.g;
                            float f7 = (int) (j3 >> 32);
                            int i = (int) (O0 >> 32);
                            float f8 = (int) (j3 & 4294967295L);
                            int i2 = (int) (O0 & 4294967295L);
                            ue0Var.a(intBitsToFloat3, intBitsToFloat4, Math.min(Float.intBitsToFloat(i) + f7, Math.max(f7, Float.intBitsToFloat(i) + intBitsToFloat3)), Math.min(Float.intBitsToFloat(i2) + f8, Math.max(f8, Float.intBitsToFloat(i2) + intBitsToFloat4)));
                        }
                    }
                    j = 0;
                    float intBitsToFloat32 = Float.intBitsToFloat((int) (j >> 32));
                    float intBitsToFloat42 = Float.intBitsToFloat((int) (j & 4294967295L));
                    long j32 = this.g;
                    float f72 = (int) (j32 >> 32);
                    int i3 = (int) (O0 >> 32);
                    float f82 = (int) (j32 & 4294967295L);
                    int i22 = (int) (O0 & 4294967295L);
                    ue0Var.a(intBitsToFloat32, intBitsToFloat42, Math.min(Float.intBitsToFloat(i3) + f72, Math.max(f72, Float.intBitsToFloat(i3) + intBitsToFloat32)), Math.min(Float.intBitsToFloat(i22) + f82, Math.max(f82, Float.intBitsToFloat(i22) + intBitsToFloat42)));
                } else if (z) {
                    long j4 = this.g;
                    ue0Var.a(0.0f, 0.0f, (int) (j4 >> 32), (int) (j4 & 4294967295L));
                }
                if (ue0Var.b()) {
                    return;
                }
            }
            kx kxVar = (kx) lj0Var;
            float[] b = kxVar.b();
            if (!kxVar.w) {
                if (b == null) {
                    ue0Var.a = 0.0f;
                    ue0Var.b = 0.0f;
                    ue0Var.c = 0.0f;
                    ue0Var.d = 0.0f;
                } else {
                    m20.z(b, ue0Var);
                }
            }
        }
        long j5 = this.D;
        float f9 = (int) (j5 >> 32);
        ue0Var.a += f9;
        ue0Var.c += f9;
        float f10 = (int) (j5 & 4294967295L);
        ue0Var.b += f10;
        ue0Var.d += f10;
    }

    public final void i1() {
        if (this.P != null) {
            m1(null, false);
            this.s.S(false);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1, types: [bd0] */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v13 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4, types: [bd0] */
    /* JADX WARN: Type inference failed for: r8v5, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v6 */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Type inference failed for: r8v9 */
    /* JADX WARN: Type inference failed for: r9v13 */
    /* JADX WARN: Type inference failed for: r9v14 */
    /* JADX WARN: Type inference failed for: r9v15 */
    /* JADX WARN: Type inference failed for: r9v16 */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v3 */
    /* JADX WARN: Type inference failed for: r9v4 */
    /* JADX WARN: Type inference failed for: r9v5, types: [ef0] */
    /* JADX WARN: Type inference failed for: r9v6 */
    /* JADX WARN: Type inference failed for: r9v7 */
    /* JADX WARN: Type inference failed for: r9v8, types: [ef0] */
    public final void j1(qc0 qc0Var) {
        ng0 ng0Var;
        qc0 qc0Var2 = this.B;
        if (qc0Var != qc0Var2) {
            this.B = qc0Var;
            z40 z40Var = this.s;
            int i = 0;
            if (qc0Var2 == null || qc0Var.d() != qc0Var2.d() || qc0Var.b() != qc0Var2.b()) {
                int d = qc0Var.d();
                int b = qc0Var.b();
                lj0 lj0Var = this.P;
                if (lj0Var != null) {
                    ((kx) lj0Var).e((d << 32) | (b & 4294967295L));
                } else if (z40Var.F() && (ng0Var = this.u) != null) {
                    ng0Var.W0();
                }
                k0((b & 4294967295L) | (d << 32));
                if (this.x != null) {
                    n1(false);
                }
                boolean f = og0.f(4);
                bd0 P0 = P0();
                if (f || (P0 = P0.i) != null) {
                    for (bd0 R0 = R0(f); R0 != null && (R0.h & 4) != 0; R0 = R0.j) {
                        if ((R0.g & 4) != 0) {
                            jm jmVar = R0;
                            ?? r9 = 0;
                            while (jmVar != 0) {
                                if (jmVar instanceof tp) {
                                    ((tp) jmVar).m0();
                                } else if ((jmVar.g & 4) != 0 && (jmVar instanceof jm)) {
                                    bd0 bd0Var = jmVar.t;
                                    int i2 = 0;
                                    jmVar = jmVar;
                                    r9 = r9;
                                    while (bd0Var != null) {
                                        if ((bd0Var.g & 4) != 0) {
                                            i2++;
                                            r9 = r9;
                                            if (i2 == 1) {
                                                jmVar = bd0Var;
                                            } else {
                                                if (r9 == 0) {
                                                    r9 = new ef0(new bd0[16]);
                                                }
                                                if (jmVar != 0) {
                                                    r9.b(jmVar);
                                                    jmVar = 0;
                                                }
                                                r9.b(bd0Var);
                                            }
                                        }
                                        bd0Var = bd0Var.j;
                                        jmVar = jmVar;
                                        r9 = r9;
                                    }
                                    if (i2 == 1) {
                                    }
                                }
                                jmVar = k81.h(r9);
                            }
                        }
                        if (R0 == P0) {
                            break;
                        }
                    }
                }
                mj0 mj0Var = z40Var.r;
                if (mj0Var != null) {
                    ((b4) mj0Var).A(z40Var);
                }
                z40Var.L(this);
            }
            oe0 oe0Var = this.C;
            if ((oe0Var != null && oe0Var.e != 0) || !qc0Var.r().isEmpty()) {
                oe0 oe0Var2 = this.C;
                Map r = qc0Var.r();
                if (oe0Var2 != null && oe0Var2.e == r.size()) {
                    Object[] objArr = oe0Var2.b;
                    int[] iArr = oe0Var2.c;
                    long[] jArr = oe0Var2.a;
                    int length = jArr.length - 2;
                    if (length >= 0) {
                        int i3 = 0;
                        loop0: while (true) {
                            long j = jArr[i3];
                            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                                int i4 = 8 - ((~(i3 - length)) >>> 31);
                                for (int i5 = i; i5 < i4; i5++) {
                                    if ((255 & j) < 128) {
                                        int i6 = (i3 << 3) + i5;
                                        Object obj = objArr[i6];
                                        int i7 = iArr[i6];
                                        Integer num = (Integer) r.get((ry) obj);
                                        if (num == null || num.intValue() != i7) {
                                            break loop0;
                                        }
                                    }
                                    j >>= 8;
                                }
                                if (i4 != 8) {
                                    return;
                                }
                            }
                            if (i3 != length) {
                                i3++;
                                i = 0;
                            } else {
                                return;
                            }
                        }
                    } else {
                        return;
                    }
                }
                z40Var.I.p.A.f();
                oe0 oe0Var3 = this.C;
                if (oe0Var3 == null) {
                    oe0 oe0Var4 = xg0.a;
                    oe0Var3 = new oe0();
                    this.C = oe0Var3;
                }
                oe0Var3.a();
                for (Map.Entry entry : qc0Var.r().entrySet()) {
                    oe0Var3.g(((Number) entry.getValue()).intValue(), entry.getKey());
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1, types: [bd0] */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v16 */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5, types: [bd0] */
    /* JADX WARN: Type inference failed for: r4v6, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3, types: [ef0] */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6, types: [ef0] */
    /* JADX WARN: Type inference failed for: r6v8 */
    /* JADX WARN: Type inference failed for: r6v9 */
    public final void k1(bd0 bd0Var, rt rtVar, long j, py pyVar, int i, boolean z, float f) {
        boolean z2;
        int i2;
        int i3;
        if (bd0Var == null) {
            V0(rtVar, j, pyVar, i, z);
            return;
        }
        if (!rtVar.l(bd0Var)) {
            k1(n30.e(bd0Var, rtVar.i()), rtVar, j, pyVar, i, z, f);
            return;
        }
        switch (rtVar.e) {
            case 13:
                jm jmVar = bd0Var;
                ?? r6 = 0;
                while (jmVar != 0) {
                    if (jmVar instanceof xm0) {
                        if (((xm0) jmVar).n0()) {
                            z2 = true;
                            break;
                        }
                    } else if ((jmVar.g & 16) != 0 && (jmVar instanceof jm)) {
                        bd0 bd0Var2 = jmVar.t;
                        int i4 = 0;
                        jmVar = jmVar;
                        r6 = r6;
                        while (bd0Var2 != null) {
                            if ((bd0Var2.g & 16) != 0) {
                                i4++;
                                r6 = r6;
                                if (i4 == 1) {
                                    jmVar = bd0Var2;
                                } else {
                                    if (r6 == 0) {
                                        r6 = new ef0(new bd0[16]);
                                    }
                                    if (jmVar != 0) {
                                        r6.b(jmVar);
                                        jmVar = 0;
                                    }
                                    r6.b(bd0Var2);
                                }
                            }
                            bd0Var2 = bd0Var2.j;
                            jmVar = jmVar;
                            r6 = r6;
                        }
                        if (i4 == 1) {
                        }
                    }
                    jmVar = k81.h(r6);
                }
                break;
            default:
                z2 = false;
                break;
        }
        if (z2) {
            ke0 ke0Var = pyVar.f;
            pe0 pe0Var = pyVar.e;
            int i5 = pyVar.g;
            int i6 = pe0Var.b;
            if (i5 == i6 - 1) {
                int i7 = i5 + 1;
                pyVar.b(i7, i6);
                pyVar.g++;
                pe0Var.a(bd0Var);
                ke0Var.a(jc0.c(f, z, false));
                e1(n30.e(bd0Var, rtVar.i()), rtVar, j, pyVar, i, z, f, false);
                pyVar.g = i5;
                if (i7 != pe0Var.b - 1 && !f31.E(pyVar.a())) {
                    return;
                }
                int i8 = pyVar.g;
                int i9 = i8 + 1;
                pe0Var.k(i9);
                if (i9 >= 0 && i9 < (i3 = ke0Var.b)) {
                    long[] jArr = ke0Var.a;
                    long j2 = jArr[i9];
                    if (i9 != i3 - 1) {
                        i8.M(jArr, jArr, i9, i8 + 2, i3);
                    }
                    ke0Var.b--;
                    return;
                }
                v7.f("Index must be between 0 and size");
                return;
            }
            long a = pyVar.a();
            int i10 = pyVar.g;
            int i11 = pe0Var.b;
            int i12 = i11 - 1;
            pyVar.g = i12;
            pyVar.b(i11, pe0Var.b);
            pyVar.g++;
            pe0Var.a(bd0Var);
            ke0Var.a(jc0.c(f, z, false));
            e1(n30.e(bd0Var, rtVar.i()), rtVar, j, pyVar, i, z, f, false);
            pyVar.g = i12;
            long a2 = pyVar.a();
            if (pyVar.g + 1 < pe0Var.b - 1 && f31.q(a, a2) > 0) {
                int i13 = i10 + 1;
                boolean E = f31.E(a2);
                int i14 = pyVar.g;
                if (E) {
                    i2 = i14 + 2;
                } else {
                    i2 = i14 + 1;
                }
                pyVar.b(i13, i2);
            } else {
                pyVar.b(pyVar.g + 1, pe0Var.b);
            }
            pyVar.g = i10;
            return;
        }
        e1(n30.e(bd0Var, rtVar.i()), rtVar, j, pyVar, i, z, f, false);
    }

    public final wo0 l1() {
        if (P0().r) {
            l40 n = o30.n(this);
            ue0 ue0Var = this.F;
            if (ue0Var == null) {
                ue0Var = new ue0();
                this.F = ue0Var;
            }
            long G0 = G0(O0());
            int i = (int) (G0 >> 32);
            ue0Var.a = -Float.intBitsToFloat(i);
            int i2 = (int) (G0 & 4294967295L);
            ue0Var.b = -Float.intBitsToFloat(i2);
            ue0Var.c = Float.intBitsToFloat(i) + g0();
            ue0Var.d = Float.intBitsToFloat(i2) + f0();
            while (this != n) {
                this.h1(ue0Var, false, true);
                if (!ue0Var.b()) {
                    this = this.u;
                    this.getClass();
                }
            }
            return new wo0(ue0Var.a, ue0Var.b, ue0Var.c, ue0Var.d);
        }
        return wo0.e;
    }

    public final void m1(gv gvVar, boolean z) {
        boolean z2;
        mj0 mj0Var;
        ef0 ef0Var;
        Reference poll;
        v2 v2Var;
        ef0 ef0Var2;
        Reference poll2;
        Object obj;
        int i = 0;
        z40 z40Var = this.s;
        if (!z && this.x == gvVar && o20.e(this.y, z40Var.A) && this.z == z40Var.B) {
            z2 = false;
        } else {
            z2 = true;
        }
        this.y = z40Var.A;
        this.z = z40Var.B;
        boolean E = z40Var.E();
        mg0 mg0Var = this.N;
        if (E && gvVar != null) {
            this.x = gvVar;
            if (this.P == null) {
                mj0 a = c50.a(z40Var);
                v2 v2Var2 = this.M;
                if (v2Var2 == null) {
                    v2 v2Var3 = new v2(5, this, new mg0(this, i));
                    this.M = v2Var3;
                    v2Var = v2Var3;
                } else {
                    v2Var = v2Var2;
                }
                b4 b4Var = (b4) a;
                c4 c4Var = b4Var.A0;
                do {
                    ReferenceQueue referenceQueue = (ReferenceQueue) c4Var.g;
                    ef0Var2 = (ef0) c4Var.f;
                    poll2 = referenceQueue.poll();
                    if (poll2 != null) {
                        ef0Var2.j(poll2);
                    }
                } while (poll2 != null);
                while (true) {
                    int i2 = ef0Var2.g;
                    if (i2 != 0) {
                        obj = ((Reference) ef0Var2.k(i2 - 1)).get();
                        if (obj != null) {
                            break;
                        }
                    } else {
                        obj = null;
                        break;
                    }
                }
                lj0 lj0Var = (lj0) obj;
                if (lj0Var != null) {
                    kx kxVar = (kx) lj0Var;
                    ex exVar = kxVar.f;
                    if (exVar != null) {
                        if (!kxVar.e.s) {
                            q00.a("layer should have been released before reuse");
                        }
                        kxVar.e = ((n5) exVar).a();
                        kxVar.k = false;
                        kxVar.h = v2Var;
                        kxVar.i = mg0Var;
                        kxVar.u = false;
                        kxVar.v = false;
                        kxVar.w = true;
                        m20.D(kxVar.l);
                        float[] fArr = kxVar.m;
                        if (fArr != null) {
                            m20.D(fArr);
                        }
                        kxVar.s = s21.a;
                        kxVar.j = 9223372034707292159L;
                        kxVar.t = null;
                        kxVar.r = 0;
                    } else {
                        throw d3.t("currently reuse is only supported when we manage the layer lifecycle");
                    }
                } else {
                    lj0Var = new kx(((n5) b4Var.getGraphicsContext()).a(), b4Var.getGraphicsContext(), b4Var, v2Var, mg0Var);
                }
                kx kxVar2 = (kx) lj0Var;
                kxVar2.e(this.g);
                kxVar2.d(this.D);
                this.P = lj0Var;
                n1(true);
                z40Var.L = true;
                mg0Var.a();
                return;
            }
            if (z2) {
                n1(true);
                return;
            }
            return;
        }
        this.x = null;
        lj0 lj0Var2 = this.P;
        if (lj0Var2 != null) {
            kx kxVar3 = (kx) lj0Var2;
            if (!t20.A(kxVar3.b())) {
                z40Var.L(this);
            }
            kxVar3.h = null;
            kxVar3.i = null;
            kxVar3.k = true;
            kxVar3.f(false);
            ex exVar2 = kxVar3.f;
            if (exVar2 != null) {
                ((n5) exVar2).c(kxVar3.e);
                b4 b4Var2 = kxVar3.g;
                c4 c4Var2 = b4Var2.A0;
                do {
                    ReferenceQueue referenceQueue2 = (ReferenceQueue) c4Var2.g;
                    ef0Var = (ef0) c4Var2.f;
                    poll = referenceQueue2.poll();
                    if (poll != null) {
                        ef0Var.j(poll);
                    }
                } while (poll != null);
                ef0Var.b(new WeakReference(kxVar3, (ReferenceQueue) c4Var2.g));
                b4Var2.I.j(kxVar3);
            }
            this.P = null;
            z40Var.L = true;
            mg0Var.a();
            if (P0().r && z40Var.F() && (mj0Var = z40Var.r) != null) {
                ((b4) mj0Var).A(z40Var);
            }
        }
        this.O = false;
    }

    public final void n1(boolean z) {
        float f;
        int i;
        boolean z2;
        boolean z3;
        mj0 mj0Var;
        vu vuVar;
        int i2;
        boolean z4;
        lj0 lj0Var = this.P;
        gv gvVar = this.x;
        if (lj0Var != null) {
            if (gvVar != null) {
                pq0 pq0Var = Q;
                pq0Var.r();
                z40 z40Var = this.s;
                pq0Var.t = z40Var.A;
                pq0Var.u = z40Var.B;
                pq0Var.s = d20.J(this.g);
                ((b4) c50.a(z40Var)).getSnapshotObserver().a.b(this, w3.D, new u3(6, gvVar, this));
                f40 f40Var = this.G;
                if (f40Var == null) {
                    f40Var = new f40();
                    this.G = f40Var;
                }
                f40 f40Var2 = R;
                f40Var2.getClass();
                f40Var2.a = f40Var.a;
                f40Var2.b = f40Var.b;
                f40Var2.c = f40Var.c;
                f40Var2.d = f40Var.d;
                f40Var2.e = f40Var.e;
                f40Var2.f = f40Var.f;
                f40Var2.g = f40Var.g;
                f40Var2.h = f40Var.h;
                f40Var2.i = f40Var.i;
                float f2 = pq0Var.f;
                f40Var.a = f2;
                f40Var.b = pq0Var.g;
                f40Var.c = pq0Var.i;
                f40Var.d = pq0Var.j;
                f40Var.e = 0.0f;
                f40Var.f = 0.0f;
                f40Var.g = pq0Var.m;
                f40Var.h = pq0Var.n;
                long j = pq0Var.o;
                f40Var.i = j;
                kx kxVar = (kx) lj0Var;
                b4 b4Var = kxVar.g;
                int i3 = pq0Var.e | kxVar.r;
                kxVar.p = pq0Var.u;
                kxVar.o = pq0Var.t;
                int i4 = i3 & 4096;
                if (i4 != 0) {
                    kxVar.s = j;
                }
                if ((i3 & 1) != 0) {
                    jx jxVar = kxVar.e.a;
                    if (jxVar.c() != f2) {
                        jxVar.i(f2);
                    }
                }
                if ((i3 & 2) != 0) {
                    hx hxVar = kxVar.e;
                    float f3 = pq0Var.g;
                    jx jxVar2 = hxVar.a;
                    if (jxVar2.t() != f3) {
                        jxVar2.q(f3);
                    }
                }
                if ((i3 & 4) != 0) {
                    kxVar.e.g(pq0Var.h);
                }
                if ((i3 & 8) != 0) {
                    hx hxVar2 = kxVar.e;
                    float f4 = pq0Var.i;
                    jx jxVar3 = hxVar2.a;
                    if (jxVar3.C() != f4) {
                        jxVar3.n(f4);
                    }
                }
                if ((i3 & 16) != 0) {
                    hx hxVar3 = kxVar.e;
                    float f5 = pq0Var.j;
                    jx jxVar4 = hxVar3.a;
                    if (jxVar4.w() != f5) {
                        jxVar4.g(f5);
                    }
                }
                if ((i3 & 32) != 0) {
                    hx hxVar4 = kxVar.e;
                    jx jxVar5 = hxVar4.a;
                    if (jxVar5.L() != 0.0f) {
                        jxVar5.l();
                        hxVar4.g = true;
                        hxVar4.a();
                    }
                }
                if ((i3 & 64) != 0) {
                    hx hxVar5 = kxVar.e;
                    long j2 = pq0Var.k;
                    jx jxVar6 = hxVar5.a;
                    f = 0.0f;
                    if (!se.c(j2, jxVar6.P())) {
                        jxVar6.h(j2);
                    }
                } else {
                    f = 0.0f;
                }
                if ((i3 & 128) != 0) {
                    hx hxVar6 = kxVar.e;
                    long j3 = pq0Var.l;
                    jx jxVar7 = hxVar6.a;
                    if (!se.c(j3, jxVar7.y())) {
                        jxVar7.o(j3);
                    }
                }
                if ((i3 & 1024) != 0) {
                    hx hxVar7 = kxVar.e;
                    float f6 = pq0Var.m;
                    jx jxVar8 = hxVar7.a;
                    if (jxVar8.N() != f6) {
                        jxVar8.e(f6);
                    }
                }
                if ((i3 & 256) != 0) {
                    jx jxVar9 = kxVar.e.a;
                    if (jxVar9.F() != f) {
                        jxVar9.a();
                    }
                }
                if ((i3 & 512) != 0) {
                    jx jxVar10 = kxVar.e.a;
                    if (jxVar10.K() != f) {
                        jxVar10.f();
                    }
                }
                if ((i3 & 2048) != 0) {
                    hx hxVar8 = kxVar.e;
                    float f7 = pq0Var.n;
                    jx jxVar11 = hxVar8.a;
                    if (jxVar11.A() != f7) {
                        jxVar11.s(f7);
                    }
                }
                if (i4 != 0) {
                    long j4 = kxVar.s;
                    if (j4 == s21.a) {
                        z4 = true;
                    } else {
                        z4 = false;
                    }
                    hx hxVar9 = kxVar.e;
                    if (z4) {
                        if (!ch0.c(hxVar9.v, 9205357640488583168L)) {
                            hxVar9.v = 9205357640488583168L;
                            hxVar9.a.O(9205357640488583168L);
                        }
                    } else {
                        float intBitsToFloat = Float.intBitsToFloat((int) (j4 >> 32)) * ((int) (kxVar.j >> 32));
                        long floatToRawIntBits = (Float.floatToRawIntBits(Float.intBitsToFloat((int) (kxVar.s & 4294967295L)) * ((int) (kxVar.j & 4294967295L))) & 4294967295L) | (Float.floatToRawIntBits(intBitsToFloat) << 32);
                        if (!ch0.c(hxVar9.v, floatToRawIntBits)) {
                            hxVar9.v = floatToRawIntBits;
                            hxVar9.a.O(floatToRawIntBits);
                        }
                    }
                }
                if ((i3 & 16384) != 0) {
                    hx hxVar10 = kxVar.e;
                    boolean z5 = pq0Var.q;
                    if (hxVar10.w != z5) {
                        hxVar10.w = z5;
                        hxVar10.g = true;
                        hxVar10.a();
                    }
                }
                if ((131072 & i3) != 0) {
                    hx hxVar11 = kxVar.e;
                    gh ghVar = pq0Var.v;
                    jx jxVar12 = hxVar11.a;
                    if (!o20.e(jxVar12.x(), ghVar)) {
                        jxVar12.p(ghVar);
                    }
                }
                if ((262144 & i3) != 0) {
                    hx hxVar12 = kxVar.e;
                    te teVar = pq0Var.w;
                    jx jxVar13 = hxVar12.a;
                    if (!o20.e(jxVar13.G(), teVar)) {
                        jxVar13.d(teVar);
                    }
                }
                if ((524288 & i3) != 0) {
                    hx hxVar13 = kxVar.e;
                    int i5 = pq0Var.x;
                    jx jxVar14 = hxVar13.a;
                    if (jxVar14.u() != i5) {
                        jxVar14.k(i5);
                    }
                }
                if ((32768 & i3) != 0) {
                    hx hxVar14 = kxVar.e;
                    int i6 = pq0Var.r;
                    if (i6 == 0) {
                        i2 = 0;
                    } else if (i6 == 1) {
                        i2 = 1;
                    } else {
                        i2 = 2;
                        if (i6 != 2) {
                            v7.o("Not supported composition strategy");
                            return;
                        }
                    }
                    jx jxVar15 = hxVar14.a;
                    if (jxVar15.E() != i2) {
                        jxVar15.H(i2);
                    }
                }
                if ((i3 & 7963) != 0) {
                    kxVar.u = true;
                    kxVar.v = true;
                }
                if (!o20.e(kxVar.t, pq0Var.y)) {
                    g30 g30Var = pq0Var.y;
                    kxVar.t = g30Var;
                    if (g30Var == null) {
                        i = i3;
                    } else {
                        hx hxVar15 = kxVar.e;
                        if (g30Var instanceof gj0) {
                            wo0 wo0Var = ((gj0) g30Var).a;
                            float f8 = wo0Var.a;
                            float f9 = wo0Var.b;
                            hxVar15.h((Float.floatToRawIntBits(f8) << 32) | (Float.floatToRawIntBits(f9) & 4294967295L), (Float.floatToRawIntBits(wo0Var.c - f8) << 32) | (Float.floatToRawIntBits(wo0Var.d - f9) & 4294967295L), 0.0f);
                        } else if (g30Var instanceof fj0) {
                            y5 y5Var = ((fj0) g30Var).a;
                            hxVar15.k = null;
                            hxVar15.i = 9205357640488583168L;
                            hxVar15.h = 0L;
                            hxVar15.j = 0.0f;
                            hxVar15.g = true;
                            hxVar15.n = false;
                            hxVar15.l = y5Var;
                            hxVar15.a();
                        } else if (g30Var instanceof hj0) {
                            hj0 hj0Var = (hj0) g30Var;
                            y5 y5Var2 = hj0Var.b;
                            if (y5Var2 != null) {
                                hxVar15.k = null;
                                hxVar15.i = 9205357640488583168L;
                                hxVar15.h = 0L;
                                hxVar15.j = 0.0f;
                                hxVar15.g = true;
                                hxVar15.n = false;
                                hxVar15.l = y5Var2;
                                hxVar15.a();
                            } else {
                                gr0 gr0Var = hj0Var.a;
                                float f10 = gr0Var.b;
                                float f11 = gr0Var.a;
                                i = i3;
                                hxVar15.h((Float.floatToRawIntBits(f11) << 32) | (Float.floatToRawIntBits(f10) & 4294967295L), (Float.floatToRawIntBits(gr0Var.c - f11) << 32) | (Float.floatToRawIntBits(gr0Var.d - f10) & 4294967295L), Float.intBitsToFloat((int) (gr0Var.h >> 32)));
                                if (Build.VERSION.SDK_INT < 33 && (((g30Var instanceof fj0) || ((g30Var instanceof hj0) && !m20.x(((hj0) g30Var).a))) && (vuVar = kxVar.i) != null)) {
                                    vuVar.a();
                                }
                            }
                        } else {
                            v7.k();
                            return;
                        }
                        i = i3;
                        if (Build.VERSION.SDK_INT < 33) {
                            vuVar.a();
                        }
                    }
                    z2 = true;
                } else {
                    i = i3;
                    z2 = false;
                }
                kxVar.r = pq0Var.e;
                if (i != 0 || z2) {
                    if (Build.VERSION.SDK_INT >= 26) {
                        ViewParent parent = b4Var.getParent();
                        if (parent != null) {
                            parent.onDescendantInvalidated(b4Var, b4Var);
                        }
                    } else {
                        b4Var.invalidate();
                    }
                    if (b4.s()) {
                        b4Var.N(0.0f);
                    }
                }
                boolean z6 = this.w;
                boolean z7 = pq0Var.q;
                this.w = z7;
                this.A = pq0Var.h;
                if (f40Var2.a == f40Var.a && f40Var2.b == f40Var.b && f40Var2.c == f40Var.c && f40Var2.d == f40Var.d && f40Var2.e == f40Var.e && f40Var2.f == f40Var.f && f40Var2.g == f40Var.g && f40Var2.h == f40Var.h && f40Var2.i == f40Var.i) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (z && ((!z3 || z6 != z7) && (mj0Var = z40Var.r) != null)) {
                    ((b4) mj0Var).A(z40Var);
                }
                if (!z3) {
                    z40Var.L(this);
                    if (z40Var.P > 0) {
                        b4 b4Var2 = (b4) c50.a(z40Var);
                        c4 c4Var = b4Var2.c0.e;
                        c4Var.getClass();
                        if (z40Var.P > 0) {
                            ((ef0) c4Var.f).b(z40Var);
                            z40Var.O = true;
                        }
                        b4Var2.H(null);
                        return;
                    }
                    return;
                }
                return;
            }
            throw d3.t("updateLayerParameters requires a non-null layerBlock");
        }
        if (gvVar == null) {
            return;
        }
        q00.b("null layer with a non-null layerBlock");
    }

    /* JADX WARN: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean o1(long r24) {
        /*
            Method dump skipped, instructions count: 435
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ng0.o1(long):boolean");
    }

    @Override // defpackage.ob0
    public final ob0 s0() {
        return this.t;
    }

    @Override // defpackage.l40
    public final long u(long j) {
        if (!P0().r) {
            q00.b("LayoutCoordinate operations are only valid when isAttached is true");
        }
        return ((b4) c50.a(this.s)).w(Y0(j));
    }

    @Override // defpackage.ob0
    public final boolean u0() {
        if (this.B != null) {
            return true;
        }
        return false;
    }

    @Override // defpackage.ob0
    public final z40 v0() {
        return this.s;
    }

    @Override // defpackage.ob0
    public final qc0 w0() {
        qc0 qc0Var = this.B;
        if (qc0Var != null) {
            return qc0Var;
        }
        v7.o("Asking for measurement result of unmeasured layout modifier");
        return null;
    }

    @Override // defpackage.ob0
    public final ob0 x0() {
        return this.u;
    }

    @Override // defpackage.mm
    public final float y() {
        return this.s.A.y();
    }

    @Override // defpackage.ob0
    public final long y0() {
        return this.D;
    }

    @Override // defpackage.l40
    public final long z(long j) {
        long Y0 = Y0(j);
        b4 b4Var = (b4) c50.a(this.s);
        b4Var.E();
        return m20.y(b4Var.f0, Y0);
    }

    @Override // defpackage.ob0
    public final l40 t0() {
        return this;
    }
}
