package defpackage;

import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import java.util.Locale;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class hx {
    public static final x1 y;
    public final jx a;
    public Outline f;
    public float j;
    public g30 k;
    public y5 l;
    public y5 m;
    public boolean n;
    public yc o;
    public r5 p;
    public int q;
    public boolean s;
    public long t;
    public long u;
    public long v;
    public boolean w;
    public RectF x;
    public mm b = k81.b;
    public m40 c = m40.e;
    public gv d = w3.u;
    public final q2 e = new q2(14, this);
    public boolean g = true;
    public long h = 0;
    public long i = 9205357640488583168L;
    public final ud r = new Object();

    static {
        x1 x1Var;
        String lowerCase = Build.FINGERPRINT.toLowerCase(Locale.ROOT);
        lowerCase.getClass();
        if (lowerCase.equals("robolectric")) {
            x1Var = x1.M;
        } else if (Build.VERSION.SDK_INT >= 28) {
            x1Var = x1.O;
        } else {
            x1Var = x1.N;
        }
        y = x1Var;
    }

    /* JADX WARN: Type inference failed for: r4v0, types: [java.lang.Object, ud] */
    public hx(jx jxVar) {
        this.a = jxVar;
        jxVar.m(false);
        this.t = 0L;
        this.u = 0L;
        this.v = 9205357640488583168L;
    }

    public final void a() {
        Outline outline;
        if (this.g) {
            boolean z = this.w;
            Outline outline2 = null;
            jx jxVar = this.a;
            if (!z && jxVar.L() <= 0.0f) {
                jxVar.m(false);
                jxVar.z(null, 0L);
            } else {
                y5 y5Var = this.l;
                if (y5Var != null) {
                    RectF rectF = this.x;
                    if (rectF == null) {
                        rectF = new RectF();
                        this.x = rectF;
                    }
                    boolean z2 = y5Var instanceof y5;
                    if (z2) {
                        Path path = y5Var.a;
                        path.computeBounds(rectF, false);
                        int i = Build.VERSION.SDK_INT;
                        if (i <= 28 && !path.isConvex()) {
                            Outline outline3 = this.f;
                            if (outline3 != null) {
                                outline3.setEmpty();
                            }
                            this.n = true;
                            outline = null;
                        } else {
                            outline = this.f;
                            if (outline == null) {
                                outline = new Outline();
                                this.f = outline;
                            }
                            if (i >= 30) {
                                if (z2) {
                                    outline.setPath(path);
                                } else {
                                    throw new UnsupportedOperationException("Unable to obtain android.graphics.Path");
                                }
                            } else if (z2) {
                                outline.setConvexPath(path);
                            } else {
                                throw new UnsupportedOperationException("Unable to obtain android.graphics.Path");
                            }
                            this.n = !outline.canClip();
                        }
                        this.l = y5Var;
                        if (outline != null) {
                            outline.setAlpha(jxVar.r());
                            outline2 = outline;
                        }
                        jxVar.z(outline2, (4294967295L & Math.round(rectF.height())) | (Math.round(rectF.width()) << 32));
                        if (this.n && this.w) {
                            jxVar.m(false);
                            jxVar.B();
                        } else {
                            jxVar.m(this.w);
                        }
                    } else {
                        throw new UnsupportedOperationException("Unable to obtain android.graphics.Path");
                    }
                } else {
                    jxVar.m(this.w);
                    Outline outline4 = this.f;
                    if (outline4 == null) {
                        outline4 = new Outline();
                        this.f = outline4;
                    }
                    Outline outline5 = outline4;
                    long J = d20.J(this.u);
                    long j = this.h;
                    long j2 = this.i;
                    if (j2 != 9205357640488583168L) {
                        J = j2;
                    }
                    int i2 = (int) (j >> 32);
                    int i3 = (int) (j & 4294967295L);
                    int i4 = (int) (J >> 32);
                    outline5.setRoundRect(Math.round(Float.intBitsToFloat(i2)), Math.round(Float.intBitsToFloat(i3)), Math.round(Float.intBitsToFloat(i4) + Float.intBitsToFloat(i2)), Math.round(Float.intBitsToFloat((int) (J & 4294967295L)) + Float.intBitsToFloat(i3)), this.j);
                    outline5.setAlpha(jxVar.r());
                    jxVar.z(outline5, (4294967295L & Math.round(Float.intBitsToFloat(r15))) | (Math.round(Float.intBitsToFloat(i4)) << 32));
                }
            }
        }
        this.g = false;
    }

    public final void b() {
        if (this.s && this.q == 0) {
            ud udVar = this.r;
            hx hxVar = (hx) udVar.b;
            if (hxVar != null) {
                hxVar.q--;
                hxVar.b();
                udVar.b = null;
            }
            we0 we0Var = (we0) udVar.d;
            if (we0Var != null) {
                Object[] objArr = we0Var.b;
                long[] jArr = we0Var.a;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i = 0;
                    while (true) {
                        long j = jArr[i];
                        if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i2 = 8 - ((~(i - length)) >>> 31);
                            for (int i3 = 0; i3 < i2; i3++) {
                                if ((255 & j) < 128) {
                                    r11.q--;
                                    ((hx) objArr[(i << 3) + i3]).b();
                                }
                                j >>= 8;
                            }
                            if (i2 != 8) {
                                break;
                            }
                        }
                        if (i == length) {
                            break;
                        } else {
                            i++;
                        }
                    }
                }
                we0Var.b();
            }
            this.a.B();
        }
    }

    public final void c(uc ucVar, hx hxVar) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        Canvas canvas;
        boolean z5;
        float f;
        if (!this.s) {
            a();
            jx jxVar = this.a;
            if (!jxVar.M()) {
                try {
                    jxVar.v(this.b, this.c, this, this.e);
                } catch (Throwable unused) {
                }
            }
            if (jxVar.L() > 0.0f) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                ucVar.p();
            }
            Canvas a = j3.a(ucVar);
            boolean isHardwareAccelerated = a.isHardwareAccelerated();
            if (!isHardwareAccelerated) {
                long j = this.t;
                float f2 = (int) (j >> 32);
                float f3 = (int) (j & 4294967295L);
                long j2 = this.u;
                float f4 = ((int) (j2 >> 32)) + f2;
                float f5 = ((int) (j2 & 4294967295L)) + f3;
                float r = jxVar.r();
                te G = jxVar.G();
                int u = jxVar.u();
                if (r >= 1.0f && u == 3 && G == null && jxVar.E() != 1) {
                    a.save();
                    a = a;
                    f = f2;
                } else {
                    r5 r5Var = this.p;
                    if (r5Var == null) {
                        r5Var = o4.f();
                        this.p = r5Var;
                    }
                    r5Var.a(r);
                    r5Var.b(u);
                    r5Var.d(G);
                    a = a;
                    f = f2;
                    a.saveLayer(f, f3, f4, f5, o4.H(r5Var));
                }
                a.translate(f, f3);
                a.concat(jxVar.I());
            }
            if (!isHardwareAccelerated && this.w) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                ucVar.h();
                g30 e = e();
                if (e instanceof gj0) {
                    ucVar.k(((gj0) e).a);
                } else if (e instanceof hj0) {
                    y5 y5Var = this.m;
                    if (y5Var != null) {
                        y5Var.a.rewind();
                    } else {
                        y5Var = a6.a();
                        this.m = y5Var;
                    }
                    d3.l(y5Var, ((hj0) e).a);
                    ucVar.q(y5Var);
                } else if (e instanceof fj0) {
                    ucVar.q(((fj0) e).a);
                } else {
                    v7.k();
                    return;
                }
            }
            if (hxVar != null) {
                ud udVar = hxVar.r;
                if (!udVar.a) {
                    p00.a("Only add dependencies during a tracking");
                }
                we0 we0Var = (we0) udVar.d;
                if (we0Var != null) {
                    we0Var.a(this);
                } else if (((hx) udVar.b) != null) {
                    we0 we0Var2 = at0.a;
                    we0 we0Var3 = new we0();
                    hx hxVar2 = (hx) udVar.b;
                    hxVar2.getClass();
                    we0Var3.a(hxVar2);
                    we0Var3.a(this);
                    udVar.d = we0Var3;
                    udVar.b = null;
                } else {
                    udVar.b = this;
                }
                we0 we0Var4 = (we0) udVar.e;
                if (we0Var4 != null) {
                    z5 = !we0Var4.l(this);
                } else if (((hx) udVar.c) != this) {
                    z5 = true;
                } else {
                    udVar.c = null;
                    z5 = false;
                }
                if (z5) {
                    this.q++;
                }
            }
            if (!((i3) ucVar).a.isHardwareAccelerated()) {
                yc ycVar = this.o;
                if (ycVar == null) {
                    ycVar = new yc();
                    this.o = ycVar;
                }
                r7 r7Var = ycVar.f;
                mm mmVar = this.b;
                m40 m40Var = this.c;
                long J = d20.J(this.u);
                mm s = r7Var.s();
                m40 u2 = r7Var.u();
                uc q = r7Var.q();
                z4 = z2;
                canvas = a;
                long v = r7Var.v();
                z3 = z;
                hx hxVar3 = (hx) r7Var.g;
                r7Var.E(mmVar);
                r7Var.F(m40Var);
                r7Var.D(ucVar);
                r7Var.G(J);
                r7Var.g = this;
                ucVar.h();
                try {
                    d(ycVar);
                } finally {
                    ucVar.f();
                    r7Var.E(s);
                    r7Var.F(u2);
                    r7Var.D(q);
                    r7Var.G(v);
                    r7Var.g = hxVar3;
                }
            } else {
                z3 = z;
                z4 = z2;
                canvas = a;
                jxVar.D(ucVar);
            }
            if (z4) {
                ucVar.f();
            }
            if (z3) {
                ucVar.j();
            }
            if (!isHardwareAccelerated) {
                canvas.restore();
            }
        }
    }

    public final void d(up upVar) {
        ud udVar = this.r;
        udVar.c = (hx) udVar.b;
        we0 we0Var = (we0) udVar.d;
        if (we0Var != null && we0Var.h()) {
            we0 we0Var2 = (we0) udVar.e;
            if (we0Var2 == null) {
                we0 we0Var3 = at0.a;
                we0Var2 = new we0();
                udVar.e = we0Var2;
            }
            we0Var2.j(we0Var);
            we0Var.b();
        }
        udVar.a = true;
        this.d.e(upVar);
        udVar.a = false;
        hx hxVar = (hx) udVar.c;
        if (hxVar != null) {
            hxVar.q--;
            hxVar.b();
        }
        we0 we0Var4 = (we0) udVar.e;
        if (we0Var4 != null && we0Var4.h()) {
            Object[] objArr = we0Var4.b;
            long[] jArr = we0Var4.a;
            int length = jArr.length - 2;
            if (length >= 0) {
                int i = 0;
                while (true) {
                    long j = jArr[i];
                    if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                        int i2 = 8 - ((~(i - length)) >>> 31);
                        for (int i3 = 0; i3 < i2; i3++) {
                            if ((255 & j) < 128) {
                                r9.q--;
                                ((hx) objArr[(i << 3) + i3]).b();
                            }
                            j >>= 8;
                        }
                        if (i2 != 8) {
                            break;
                        }
                    }
                    if (i == length) {
                        break;
                    } else {
                        i++;
                    }
                }
            }
            we0Var4.b();
        }
    }

    public final g30 e() {
        g30 gj0Var;
        g30 g30Var = this.k;
        y5 y5Var = this.l;
        if (g30Var != null) {
            return g30Var;
        }
        if (y5Var != null) {
            fj0 fj0Var = new fj0(y5Var);
            this.k = fj0Var;
            return fj0Var;
        }
        long J = d20.J(this.u);
        long j = this.h;
        long j2 = this.i;
        if (j2 != 9205357640488583168L) {
            J = j2;
        }
        float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L));
        float intBitsToFloat3 = Float.intBitsToFloat((int) (J >> 32)) + intBitsToFloat;
        float intBitsToFloat4 = Float.intBitsToFloat((int) (J & 4294967295L)) + intBitsToFloat2;
        if (this.j > 0.0f) {
            gj0Var = new hj0(m20.e(intBitsToFloat, intBitsToFloat2, intBitsToFloat3, intBitsToFloat4, (Float.floatToRawIntBits(r0) << 32) | (4294967295L & Float.floatToRawIntBits(r0))));
        } else {
            gj0Var = new gj0(new wo0(intBitsToFloat, intBitsToFloat2, intBitsToFloat3, intBitsToFloat4));
        }
        this.k = gj0Var;
        return gj0Var;
    }

    public final void f(mm mmVar, m40 m40Var, long j, gv gvVar) {
        boolean a = c20.a(this.u, j);
        jx jxVar = this.a;
        if (!a) {
            this.u = j;
            long j2 = this.t;
            jxVar.J((int) (j2 >> 32), (int) (j2 & 4294967295L), j);
            if (this.i == 9205357640488583168L) {
                this.g = true;
                a();
            }
        }
        this.b = mmVar;
        this.c = m40Var;
        this.d = gvVar;
        jxVar.v(mmVar, m40Var, this, this.e);
    }

    public final void g(float f) {
        jx jxVar = this.a;
        if (jxVar.r() == f) {
            return;
        }
        jxVar.b(f);
    }

    public final void h(long j, long j2, float f) {
        if (ch0.c(this.h, j) && mw0.a(this.i, j2) && this.j == f && this.l == null) {
            return;
        }
        this.k = null;
        this.l = null;
        this.g = true;
        this.n = false;
        this.h = j;
        this.i = j2;
        this.j = f;
        a();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object i(defpackage.jj r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof defpackage.gx
            if (r0 == 0) goto L13
            r0 = r5
            gx r0 = (defpackage.gx) r0
            int r1 = r0.j
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.j = r1
            goto L18
        L13:
            gx r0 = new gx
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.h
            int r1 = r0.j
            r2 = 1
            if (r1 == 0) goto L2c
            if (r1 != r2) goto L25
            defpackage.o30.x(r5)
            goto L3c
        L25:
            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r4)
            r4 = 0
            return r4
        L2c:
            defpackage.o30.x(r5)
            r0.j = r2
            x1 r5 = defpackage.hx.y
            java.lang.Object r5 = r5.c(r4, r0)
            ik r4 = defpackage.ik.e
            if (r5 != r4) goto L3c
            return r4
        L3c:
            android.graphics.Bitmap r5 = (android.graphics.Bitmap) r5
            o5 r4 = new o5
            r4.<init>(r5)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.hx.i(jj):java.lang.Object");
    }
}
