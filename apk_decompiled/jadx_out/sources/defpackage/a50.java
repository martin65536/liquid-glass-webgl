package defpackage;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class a50 {
    public final a3 a;
    public boolean c;
    public boolean d;
    public boolean e;
    public a3 f;
    public final /* synthetic */ int h;
    public boolean b = true;
    public final HashMap g = new HashMap();

    public a50(a3 a3Var, int i) {
        this.h = i;
        this.a = a3Var;
    }

    public static final void a(a50 a50Var, ry ryVar, int i, ng0 ng0Var) {
        float intBitsToFloat;
        HashMap hashMap = a50Var.g;
        float f = i;
        long floatToRawIntBits = Float.floatToRawIntBits(f) << 32;
        long floatToRawIntBits2 = Float.floatToRawIntBits(f) & 4294967295L;
        while (true) {
            long j = floatToRawIntBits | floatToRawIntBits2;
            do {
                switch (a50Var.h) {
                    case 0:
                        lj0 lj0Var = ng0Var.P;
                        if (lj0Var != null) {
                            kx kxVar = (kx) lj0Var;
                            float[] b = kxVar.b();
                            if (!kxVar.w) {
                                j = m20.y(b, j);
                            }
                        }
                        j = f31.J(j, ng0Var.D);
                        break;
                    default:
                        qb0 N0 = ng0Var.N0();
                        N0.getClass();
                        long j2 = N0.t;
                        j = ch0.g((Float.floatToRawIntBits((int) (j2 & 4294967295L)) & 4294967295L) | (Float.floatToRawIntBits((int) (j2 >> 32)) << 32), j);
                        break;
                }
                ng0Var = ng0Var.u;
                ng0Var.getClass();
                if (ng0Var.equals(a50Var.a.I())) {
                    if (ryVar instanceof ry) {
                        intBitsToFloat = Float.intBitsToFloat((int) (j & 4294967295L));
                    } else {
                        intBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
                    }
                    int round = Math.round(intBitsToFloat);
                    if (hashMap.containsKey(ryVar)) {
                        hashMap.getClass();
                        Object obj = hashMap.get(ryVar);
                        if (obj == null && !hashMap.containsKey(ryVar)) {
                            throw new NoSuchElementException("Key " + ryVar + " is missing in the map.");
                        }
                        int intValue = ((Number) obj).intValue();
                        ry ryVar2 = z2.a;
                        round = ((Number) ryVar.a.d(Integer.valueOf(intValue), Integer.valueOf(round))).intValue();
                    }
                    hashMap.put(ryVar, Integer.valueOf(round));
                    return;
                }
            } while (!a50Var.b(ng0Var).containsKey(ryVar));
            float c = a50Var.c(ng0Var, ryVar);
            long floatToRawIntBits3 = Float.floatToRawIntBits(c);
            long floatToRawIntBits4 = Float.floatToRawIntBits(c);
            floatToRawIntBits = floatToRawIntBits3 << 32;
            floatToRawIntBits2 = floatToRawIntBits4 & 4294967295L;
        }
    }

    public final Map b(ng0 ng0Var) {
        switch (this.h) {
            case 0:
                return ng0Var.w0().r();
            default:
                qb0 N0 = ng0Var.N0();
                N0.getClass();
                return N0.w0().r();
        }
    }

    public final int c(ng0 ng0Var, ry ryVar) {
        switch (this.h) {
            case 0:
                return ng0Var.r0(ryVar);
            default:
                qb0 N0 = ng0Var.N0();
                N0.getClass();
                return N0.r0(ryVar);
        }
    }

    public final boolean d() {
        if (!this.c && !this.d && !this.e) {
            return false;
        }
        return true;
    }

    public final boolean e() {
        h();
        if (this.f != null) {
            return true;
        }
        return false;
    }

    public final void f() {
        this.b = true;
        a3 a3Var = this.a;
        a3 K = a3Var.K();
        if (K == null) {
            return;
        }
        if (this.c) {
            K.requestLayout();
        }
        if (this.d) {
            a3Var.c0();
        }
        if (this.e) {
            a3Var.requestLayout();
        }
        K.r().f();
    }

    public final void g() {
        HashMap hashMap = this.g;
        hashMap.clear();
        q2 q2Var = new q2(1, this);
        a3 a3Var = this.a;
        a3Var.x(q2Var);
        hashMap.putAll(b(a3Var.I()));
        this.b = false;
    }

    public final void h() {
        a50 r;
        a50 r2;
        boolean d = d();
        a3 a3Var = this.a;
        if (!d) {
            a3 K = a3Var.K();
            if (K != null) {
                a3Var = K.r().f;
                if (a3Var == null || !a3Var.r().d()) {
                    a3 a3Var2 = this.f;
                    if (a3Var2 != null && !a3Var2.r().d()) {
                        a3 K2 = a3Var2.K();
                        if (K2 != null && (r2 = K2.r()) != null) {
                            r2.h();
                        }
                        a3 K3 = a3Var2.K();
                        if (K3 != null && (r = K3.r()) != null) {
                            a3Var = r.f;
                        } else {
                            a3Var = null;
                        }
                    } else {
                        return;
                    }
                }
            } else {
                return;
            }
        }
        this.f = a3Var;
    }
}
