package defpackage;

import android.graphics.Paint;
import android.text.TextPaint;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class j6 extends TextPaint {
    public r5 a;
    public w01 b;
    public int c;
    public tv0 d;
    public se e;
    public jc0 f;
    public ym g;
    public mw0 h;
    public jc0 i;

    public final r5 a() {
        r5 r5Var = this.a;
        if (r5Var != null) {
            return r5Var;
        }
        r5 r5Var2 = new r5(this);
        this.a = r5Var2;
        return r5Var2;
    }

    public final void b(int i) {
        if (i == this.c) {
            return;
        }
        a().b(i);
        this.c = i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0035, code lost:
    
        if (r1 == false) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void c(final defpackage.jc0 r4, final long r5, float r7) {
        /*
            r3 = this;
            r0 = 0
            if (r4 != 0) goto Ld
            r3.g = r0
            r3.f = r0
            r3.h = r0
            r3.setShader(r0)
            return
        Ld:
            boolean r1 = r4 instanceof defpackage.qx0
            if (r1 == 0) goto L1d
            qx0 r4 = (defpackage.qx0) r4
            long r4 = r4.s
            long r4 = defpackage.g30.A(r4, r7)
            r3.d(r4)
            return
        L1d:
            boolean r1 = r4 instanceof defpackage.qv0
            if (r1 == 0) goto L71
            jc0 r1 = r3.f
            boolean r1 = defpackage.o20.e(r1, r4)
            if (r1 == 0) goto L37
            mw0 r1 = r3.h
            if (r1 != 0) goto L2f
            r1 = 0
            goto L35
        L2f:
            long r1 = r1.a
            boolean r1 = defpackage.mw0.a(r1, r5)
        L35:
            if (r1 != 0) goto L54
        L37:
            r1 = 9205357640488583168(0x7fc000007fc00000, double:2.247117487993712E307)
            int r1 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r1 == 0) goto L54
            r3.f = r4
            mw0 r1 = new mw0
            r1.<init>(r5)
            r3.h = r1
            i6 r1 = new i6
            r1.<init>()
            ym r4 = defpackage.n30.r(r1)
            r3.g = r4
        L54:
            r5 r4 = r3.a()
            ym r5 = r3.g
            if (r5 == 0) goto L63
            java.lang.Object r5 = r5.getValue()
            android.graphics.Shader r5 = (android.graphics.Shader) r5
            goto L64
        L63:
            r5 = r0
        L64:
            r4.c = r5
            android.graphics.Paint r4 = r4.a
            r4.setShader(r5)
            r3.e = r0
            defpackage.o20.C(r3, r7)
            return
        L71:
            defpackage.v7.k()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.j6.c(jc0, long, float):void");
    }

    public final void d(long j) {
        boolean c;
        se seVar = this.e;
        if (seVar == null) {
            c = false;
        } else {
            c = se.c(seVar.a, j);
        }
        if (!c && j != 16) {
            this.e = new se(j);
            setColor(f31.P(j));
            this.g = null;
            this.f = null;
            this.h = null;
            setShader(null);
        }
    }

    public final void e(jc0 jc0Var) {
        Paint.Join join;
        Paint.Cap cap;
        if (jc0Var != null && !o20.e(this.i, jc0Var)) {
            this.i = jc0Var;
            if (jc0Var.equals(yr.s)) {
                setStyle(Paint.Style.FILL);
                return;
            }
            if (jc0Var instanceof cz0) {
                a().e(1);
                r5 a = a();
                cz0 cz0Var = (cz0) jc0Var;
                a.a.setStrokeWidth(cz0Var.s);
                r5 a2 = a();
                a2.a.setStrokeMiter(cz0Var.t);
                r5 a3 = a();
                int i = cz0Var.v;
                Paint paint = a3.a;
                if (i == 0) {
                    join = Paint.Join.MITER;
                } else if (i == 2) {
                    join = Paint.Join.BEVEL;
                } else if (i == 1) {
                    join = Paint.Join.ROUND;
                } else {
                    join = Paint.Join.MITER;
                }
                paint.setStrokeJoin(join);
                r5 a4 = a();
                int i2 = cz0Var.u;
                Paint paint2 = a4.a;
                if (i2 == 2) {
                    cap = Paint.Cap.SQUARE;
                } else if (i2 == 1) {
                    cap = Paint.Cap.ROUND;
                } else if (i2 == 0) {
                    cap = Paint.Cap.BUTT;
                } else {
                    cap = Paint.Cap.BUTT;
                }
                paint2.setStrokeCap(cap);
                a().a.setPathEffect(null);
                return;
            }
            v7.k();
        }
    }

    public final void f(tv0 tv0Var) {
        if (tv0Var != null && !o20.e(this.d, tv0Var)) {
            this.d = tv0Var;
            if (tv0Var.equals(tv0.d)) {
                clearShadowLayer();
                return;
            }
            tv0 tv0Var2 = this.d;
            float f = tv0Var2.c;
            if (f == 0.0f) {
                f = Float.MIN_VALUE;
            }
            setShadowLayer(f, Float.intBitsToFloat((int) (tv0Var2.b >> 32)), Float.intBitsToFloat((int) (this.d.b & 4294967295L)), f31.P(this.d.a));
        }
    }

    public final void g(w01 w01Var) {
        boolean z;
        if (w01Var != null && !o20.e(this.b, w01Var)) {
            this.b = w01Var;
            int i = w01Var.a;
            boolean z2 = false;
            if ((i | 1) == i) {
                z = true;
            } else {
                z = false;
            }
            setUnderlineText(z);
            int i2 = this.b.a;
            if ((i2 | 2) == i2) {
                z2 = true;
            }
            setStrikeThruText(z2);
        }
    }
}
