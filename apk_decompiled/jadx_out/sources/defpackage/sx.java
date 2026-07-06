package defpackage;

import java.util.ArrayList;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class sx extends h41 {
    public float[] b;
    public final ArrayList c = new ArrayList();
    public boolean d = true;
    public long e = se.g;
    public List f;
    public boolean g;
    public y5 h;
    public gv i;
    public final q2 j;
    public String k;
    public float l;
    public float m;
    public float n;
    public float o;
    public float p;
    public float q;
    public float r;
    public boolean s;

    public sx() {
        int i = o41.a;
        this.f = er.e;
        this.g = true;
        this.j = new q2(16, this);
        this.k = "";
        this.o = 1.0f;
        this.p = 1.0f;
        this.s = true;
    }

    @Override // defpackage.h41
    public final void a(up upVar) {
        if (this.s) {
            float[] fArr = this.b;
            if (fArr == null) {
                fArr = m20.n();
                this.b = fArr;
            } else {
                m20.D(fArr);
            }
            m20.K(fArr, this.q + this.m, this.r + this.n);
            float f = this.l;
            if (fArr.length >= 16) {
                double d = f * 0.017453292519943295d;
                float sin = (float) Math.sin(d);
                float cos = (float) Math.cos(d);
                float f2 = fArr[0];
                float f3 = fArr[4];
                float f4 = (sin * f3) + (cos * f2);
                float f5 = -sin;
                float f6 = (f3 * cos) + (f2 * f5);
                float f7 = fArr[1];
                float f8 = fArr[5];
                float f9 = (sin * f8) + (cos * f7);
                float f10 = (f8 * cos) + (f7 * f5);
                float f11 = fArr[2];
                float f12 = fArr[6];
                float f13 = (sin * f12) + (cos * f11);
                float f14 = (f12 * cos) + (f11 * f5);
                float f15 = fArr[3];
                float f16 = fArr[7];
                fArr[0] = f4;
                fArr[1] = f9;
                fArr[2] = f13;
                fArr[3] = (sin * f16) + (cos * f15);
                fArr[4] = f6;
                fArr[5] = f10;
                fArr[6] = f14;
                fArr[7] = (cos * f16) + (f5 * f15);
            }
            float f17 = this.o;
            float f18 = this.p;
            if (fArr.length >= 16) {
                fArr[0] = fArr[0] * f17;
                fArr[1] = fArr[1] * f17;
                fArr[2] = fArr[2] * f17;
                fArr[3] = fArr[3] * f17;
                fArr[4] = fArr[4] * f18;
                fArr[5] = fArr[5] * f18;
                fArr[6] = fArr[6] * f18;
                fArr[7] = fArr[7] * f18;
                fArr[8] = fArr[8] * 1.0f;
                fArr[9] = fArr[9] * 1.0f;
                fArr[10] = fArr[10] * 1.0f;
                fArr[11] = fArr[11] * 1.0f;
            }
            m20.K(fArr, -this.m, -this.n);
            this.s = false;
        }
        if (this.g) {
            if (!this.f.isEmpty()) {
                y5 y5Var = this.h;
                if (y5Var == null) {
                    y5Var = a6.a();
                    this.h = y5Var;
                }
                t20.R(this.f, y5Var);
            }
            this.g = false;
        }
        r7 J = upVar.J();
        long v = J.v();
        J.q().h();
        try {
            r7 r7Var = (r7) ((j2) J.f).f;
            float[] fArr2 = this.b;
            if (fArr2 != null) {
                r7Var.q().o(fArr2);
            }
            y5 y5Var2 = this.h;
            if (!this.f.isEmpty() && y5Var2 != null) {
                r7Var.q().q(y5Var2);
            }
            ArrayList arrayList = this.c;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ((h41) arrayList.get(i)).a(upVar);
            }
        } finally {
            J.q().f();
            J.G(v);
        }
    }

    @Override // defpackage.h41
    public final gv b() {
        return this.i;
    }

    @Override // defpackage.h41
    public final void d(q2 q2Var) {
        this.i = q2Var;
    }

    public final void e(int i, h41 h41Var) {
        ArrayList arrayList = this.c;
        if (i < arrayList.size()) {
            arrayList.set(i, h41Var);
        } else {
            arrayList.add(h41Var);
        }
        g(h41Var);
        h41Var.d(this.j);
        c();
    }

    public final void f(long j) {
        if (this.d && j != 16) {
            long j2 = this.e;
            if (j2 == 16) {
                this.e = j;
                return;
            }
            int i = o41.a;
            if (se.h(j2) != se.h(j) || se.g(j2) != se.g(j) || se.e(j2) != se.e(j)) {
                this.d = false;
                this.e = se.g;
            }
        }
    }

    public final void g(h41 h41Var) {
        if (h41Var instanceof lk0) {
            lk0 lk0Var = (lk0) h41Var;
            jc0 jc0Var = lk0Var.b;
            if (this.d && jc0Var != null) {
                if (jc0Var instanceof qx0) {
                    f(((qx0) jc0Var).s);
                } else {
                    this.d = false;
                    this.e = se.g;
                }
            }
            jc0 jc0Var2 = lk0Var.g;
            if (this.d && jc0Var2 != null) {
                if (jc0Var2 instanceof qx0) {
                    f(((qx0) jc0Var2).s);
                    return;
                } else {
                    this.d = false;
                    this.e = se.g;
                    return;
                }
            }
            return;
        }
        if (h41Var instanceof sx) {
            sx sxVar = (sx) h41Var;
            if (sxVar.d && this.d) {
                f(sxVar.e);
            } else {
                this.d = false;
                this.e = se.g;
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("VGroup: ");
        sb.append(this.k);
        ArrayList arrayList = this.c;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            h41 h41Var = (h41) arrayList.get(i);
            sb.append("\t");
            sb.append(h41Var.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
