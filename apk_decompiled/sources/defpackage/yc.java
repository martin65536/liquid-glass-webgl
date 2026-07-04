package defpackage;

import android.graphics.Paint;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class yc implements up {
    public final xc e;
    public final r7 f;
    public r5 g;
    public r5 h;

    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Object, xc] */
    public yc() {
        pm pmVar = k81.b;
        ?? obj = new Object();
        obj.a = pmVar;
        obj.b = m40.e;
        obj.c = br.a;
        obj.d = 0L;
        this.e = obj;
        this.f = new r7(this);
    }

    public static r5 r(yc ycVar, long j, jc0 jc0Var, float f, int i) {
        r5 v = ycVar.v(jc0Var);
        Paint paint = v.a;
        if (f != 1.0f) {
            j = se.b(j, se.d(j) * f);
        }
        if (!se.c(f31.e(paint.getColor()), j)) {
            v.c(j);
        }
        if (v.c != null) {
            v.c = null;
            paint.setShader(null);
        }
        if (!o20.e(v.d, null)) {
            v.d(null);
        }
        if (v.b != i) {
            v.b(i);
        }
        if (paint.isFilterBitmap()) {
            return v;
        }
        paint.setFilterBitmap(true);
        return v;
    }

    @Override // defpackage.mm
    public final float B() {
        return this.e.a.B();
    }

    @Override // defpackage.mm
    public final float G(float f) {
        return B() * f;
    }

    @Override // defpackage.up
    public final r7 J() {
        return this.f;
    }

    @Override // defpackage.up
    public final void M(hx hxVar, long j, gv gvVar) {
        hxVar.f(this, this.e.b, j, new o6(5, this, gvVar));
    }

    @Override // defpackage.mm
    public final /* synthetic */ float O(long j) {
        return d3.e(this, j);
    }

    @Override // defpackage.mm
    public final /* synthetic */ int S(float f) {
        return d3.c(f, this);
    }

    @Override // defpackage.up
    public final void V(o5 o5Var, long j, long j2, long j3, float f, te teVar, int i) {
        this.e.c.c(o5Var, j, j2, j3, u(null, yr.s, f, teVar, 3, i));
    }

    @Override // defpackage.up
    public final long W() {
        return o30.p(this.f.v());
    }

    @Override // defpackage.mm
    public final /* synthetic */ long Z(long j) {
        return d3.h(this, j);
    }

    @Override // defpackage.up
    public final void a0(long j, long j2, long j3, float f, jc0 jc0Var, int i) {
        int i2 = (int) (j2 >> 32);
        int i3 = (int) (j2 & 4294967295L);
        this.e.c.m(Float.intBitsToFloat(i2), Float.intBitsToFloat(i3), Float.intBitsToFloat((int) (j3 >> 32)) + Float.intBitsToFloat(i2), Float.intBitsToFloat((int) (4294967295L & j3)) + Float.intBitsToFloat(i3), r(this, j, jc0Var, f, i));
    }

    @Override // defpackage.mm
    public final /* synthetic */ float d0(long j) {
        return d3.g(this, j);
    }

    @Override // defpackage.up
    public final m40 getLayoutDirection() {
        return this.e.b;
    }

    @Override // defpackage.up
    public final long j() {
        return this.f.v();
    }

    @Override // defpackage.mm
    public final long j0(float f) {
        return d3.i(o0(f), this);
    }

    @Override // defpackage.mm
    public final float o0(float f) {
        return f / B();
    }

    public final r5 u(jc0 jc0Var, jc0 jc0Var2, float f, te teVar, int i, int i2) {
        boolean z;
        r5 v = v(jc0Var2);
        Paint paint = v.a;
        if (jc0Var != null) {
            jc0Var.g(f, this.f.v(), v);
        } else {
            if (v.c != null) {
                v.c = null;
                paint.setShader(null);
            }
            long e = f31.e(paint.getColor());
            long j = se.b;
            if (!se.c(e, j)) {
                v.c(j);
            }
            if (paint.getAlpha() / 255.0f != f) {
                v.a(f);
            }
        }
        if (!o20.e(v.d, teVar)) {
            v.d(teVar);
        }
        if (v.b != i) {
            v.b(i);
        }
        if (paint.isFilterBitmap() == i2) {
            return v;
        }
        if (i2 == 0) {
            z = true;
        } else {
            z = false;
        }
        paint.setFilterBitmap(true ^ z);
        return v;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x009e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x009f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final defpackage.r5 v(defpackage.jc0 r9) {
        /*
            r8 = this;
            yr r0 = defpackage.yr.s
            boolean r0 = defpackage.o20.e(r9, r0)
            r1 = 0
            if (r0 == 0) goto L17
            r5 r9 = r8.g
            if (r9 != 0) goto L16
            r5 r9 = defpackage.o4.f()
            r9.e(r1)
            r8.g = r9
        L16:
            return r9
        L17:
            boolean r0 = r9 instanceof defpackage.cz0
            if (r0 == 0) goto Lb4
            r5 r0 = r8.h
            r2 = 1
            if (r0 != 0) goto L29
            r5 r0 = defpackage.o4.f()
            r0.e(r2)
            r8.h = r0
        L29:
            android.graphics.Paint r8 = r0.a
            float r3 = r8.getStrokeWidth()
            cz0 r9 = (defpackage.cz0) r9
            float r4 = r9.s
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 != 0) goto L38
            goto L3b
        L38:
            r8.setStrokeWidth(r4)
        L3b:
            android.graphics.Paint$Cap r3 = r8.getStrokeCap()
            r4 = -1
            if (r3 != 0) goto L44
            r3 = r4
            goto L4c
        L44:
            int[] r5 = defpackage.s5.a
            int r3 = r3.ordinal()
            r3 = r5[r3]
        L4c:
            r5 = 3
            r6 = 2
            if (r3 == r2) goto L59
            if (r3 == r6) goto L57
            if (r3 == r5) goto L55
            goto L59
        L55:
            r3 = r6
            goto L5a
        L57:
            r3 = r2
            goto L5a
        L59:
            r3 = r1
        L5a:
            int r7 = r9.u
            if (r3 != r7) goto L5f
            goto L73
        L5f:
            if (r7 != r6) goto L64
            android.graphics.Paint$Cap r3 = android.graphics.Paint.Cap.SQUARE
            goto L70
        L64:
            if (r7 != r2) goto L69
            android.graphics.Paint$Cap r3 = android.graphics.Paint.Cap.ROUND
            goto L70
        L69:
            if (r7 != 0) goto L6e
            android.graphics.Paint$Cap r3 = android.graphics.Paint.Cap.BUTT
            goto L70
        L6e:
            android.graphics.Paint$Cap r3 = android.graphics.Paint.Cap.BUTT
        L70:
            r8.setStrokeCap(r3)
        L73:
            float r3 = r8.getStrokeMiter()
            float r7 = r9.t
            int r3 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r3 != 0) goto L7e
            goto L81
        L7e:
            r8.setStrokeMiter(r7)
        L81:
            android.graphics.Paint$Join r3 = r8.getStrokeJoin()
            if (r3 != 0) goto L88
            goto L90
        L88:
            int[] r4 = defpackage.s5.b
            int r3 = r3.ordinal()
            r4 = r4[r3]
        L90:
            if (r4 == r2) goto L9a
            if (r4 == r6) goto L99
            if (r4 == r5) goto L97
            goto L9a
        L97:
            r1 = r2
            goto L9a
        L99:
            r1 = r6
        L9a:
            int r9 = r9.v
            if (r1 != r9) goto L9f
            return r0
        L9f:
            if (r9 != 0) goto La4
            android.graphics.Paint$Join r9 = android.graphics.Paint.Join.MITER
            goto Lb0
        La4:
            if (r9 != r6) goto La9
            android.graphics.Paint$Join r9 = android.graphics.Paint.Join.BEVEL
            goto Lb0
        La9:
            if (r9 != r2) goto Lae
            android.graphics.Paint$Join r9 = android.graphics.Paint.Join.ROUND
            goto Lb0
        Lae:
            android.graphics.Paint$Join r9 = android.graphics.Paint.Join.MITER
        Lb0:
            r8.setStrokeJoin(r9)
            return r0
        Lb4:
            defpackage.v7.k()
            r8 = 0
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.yc.v(jc0):r5");
    }

    @Override // defpackage.up
    public final void w(y5 y5Var, jc0 jc0Var, float f, jc0 jc0Var2) {
        this.e.c.e(y5Var, u(jc0Var, jc0Var2, f, null, 3, 1));
    }

    @Override // defpackage.mm
    public final float y() {
        return this.e.a.y();
    }
}
