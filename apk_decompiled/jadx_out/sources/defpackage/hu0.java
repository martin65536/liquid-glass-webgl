package defpackage;

import android.view.ViewTreeObserver;
import java.lang.reflect.Method;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class hu0 {
    public au0 a;
    public e5 b;
    public rl c;
    public dj0 d;
    public boolean e;
    public e3 f;
    public final zt0 g;
    public final wt0 h;
    public boolean i;
    public int j = 1;
    public lt0 k = n20.o;
    public final fu0 l = new fu0(this);
    public final l m = new l(15, this);

    public hu0(au0 au0Var, e5 e5Var, rl rlVar, dj0 dj0Var, boolean z, e3 e3Var, zt0 zt0Var, wt0 wt0Var) {
        this.a = au0Var;
        this.b = e5Var;
        this.c = rlVar;
        this.d = dj0Var;
        this.e = z;
        this.f = e3Var;
        this.g = zt0Var;
        this.h = wt0Var;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
    /* JADX WARN: Type inference failed for: r6v0, types: [dp0, java.lang.Object] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object a(long r11, defpackage.jj r13) {
        /*
            r10 = this;
            boolean r0 = r13 instanceof defpackage.cu0
            if (r0 == 0) goto L13
            r0 = r13
            cu0 r0 = (defpackage.cu0) r0
            int r1 = r0.k
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.k = r1
            goto L18
        L13:
            cu0 r0 = new cu0
            r0.<init>(r10, r13)
        L18:
            java.lang.Object r13 = r0.i
            int r1 = r0.k
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L34
            if (r1 != r3) goto L2d
            dp0 r11 = r0.h
            defpackage.o30.x(r13)     // Catch: java.lang.Throwable -> L29
            r5 = r10
            goto L58
        L29:
            r0 = move-exception
            r11 = r0
            r5 = r10
            goto L68
        L2d:
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r10)
            r10 = 0
            return r10
        L34:
            defpackage.o30.x(r13)
            dp0 r6 = new dp0
            r6.<init>()
            r6.e = r11
            r10.i = r3
            gf0 r13 = defpackage.gf0.e     // Catch: java.lang.Throwable -> L65
            eu0 r4 = new eu0     // Catch: java.lang.Throwable -> L65
            r9 = 0
            r5 = r10
            r7 = r11
            r4.<init>(r5, r6, r7, r9)     // Catch: java.lang.Throwable -> L62
            r0.h = r6     // Catch: java.lang.Throwable -> L62
            r0.k = r3     // Catch: java.lang.Throwable -> L62
            java.lang.Object r10 = r5.f(r13, r4, r0)     // Catch: java.lang.Throwable -> L62
            ik r11 = defpackage.ik.e
            if (r10 != r11) goto L57
            return r11
        L57:
            r11 = r6
        L58:
            r5.i = r2
            long r10 = r11.e
            v41 r12 = new v41
            r12.<init>(r10)
            return r12
        L62:
            r0 = move-exception
        L63:
            r11 = r0
            goto L68
        L65:
            r0 = move-exception
            r5 = r10
            goto L63
        L68:
            r5.i = r2
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.hu0.a(long, jj):java.lang.Object");
    }

    public final Object b(long j, boolean z, sz0 sz0Var) {
        int i;
        x31 x31Var = x31.a;
        if (!z || !(this.c instanceof rl)) {
            if (this.d == dj0.f) {
                i = 1;
            } else {
                i = 2;
            }
            long a = v41.a(j, 0.0f, 0.0f, i);
            gu0 gu0Var = new gu0(this, null);
            e5 e5Var = this.b;
            ik ikVar = ik.e;
            if (e5Var != null && (this.a.c() || this.a.a())) {
                Object b = e5Var.b(a, gu0Var, sz0Var);
                if (b == ikVar) {
                    return b;
                }
            } else {
                gu0 gu0Var2 = new gu0(gu0Var.l, sz0Var);
                gu0Var2.k = a;
                Object k = gu0Var2.k(x31Var);
                if (k == ikVar) {
                    return k;
                }
            }
        }
        return x31Var;
    }

    public final long c(lt0 lt0Var, long j, int i) {
        gg0 gg0Var;
        long j2;
        long a;
        gg0 gg0Var2 = (gg0) this.f.a;
        gg0 gg0Var3 = null;
        if (gg0Var2 != null) {
            gg0Var = gg0Var2.E0();
        } else {
            gg0Var = null;
        }
        long j3 = 0;
        if (gg0Var != null) {
            j2 = gg0Var.I0(i, j);
        } else {
            j2 = 0;
        }
        long f = ch0.f(j, j2);
        if (this.d == dj0.f) {
            a = ch0.a(1, f);
        } else {
            a = ch0.a(2, f);
        }
        long e = e(h(lt0Var.a(g(e(a)))));
        zt0 zt0Var = this.g;
        if (zt0Var.r) {
            ViewTreeObserver viewTreeObserver = ((b4) k81.F(zt0Var)).getViewTreeObserver();
            try {
                if (b4.U0 == null) {
                    Method declaredMethod = viewTreeObserver.getClass().getDeclaredMethod("dispatchOnScrollChanged", null);
                    declaredMethod.setAccessible(true);
                    b4.U0 = declaredMethod;
                }
                Method method = b4.U0;
                if (method != null) {
                    method.invoke(viewTreeObserver, null);
                }
            } catch (Exception unused) {
            }
        }
        long f2 = ch0.f(f, e);
        gg0 gg0Var4 = (gg0) this.f.a;
        if (gg0Var4 != null) {
            gg0Var3 = gg0Var4.E0();
        }
        gg0 gg0Var5 = gg0Var3;
        if (gg0Var5 != null) {
            j3 = gg0Var5.G0(i, e, f2);
        }
        return ch0.g(ch0.g(j2, e), j3);
    }

    public final float d(float f) {
        if (this.e) {
            return f * (-1.0f);
        }
        return f;
    }

    public final long e(long j) {
        if (this.e) {
            return ch0.h(j, -1.0f);
        }
        return j;
    }

    public final Object f(gf0 gf0Var, kv kvVar, jj jjVar) {
        Object d = this.a.d(gf0Var, new f(this, kvVar, null, 14), jjVar);
        if (d == ik.e) {
            return d;
        }
        return x31.a;
    }

    public final float g(long j) {
        int i;
        if (this.d == dj0.f) {
            i = (int) (j >> 32);
        } else {
            i = (int) (j & 4294967295L);
        }
        return Float.intBitsToFloat(i);
    }

    public final long h(float f) {
        if (f == 0.0f) {
            return 0L;
        }
        if (this.d == dj0.f) {
            return (Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L);
        }
        return (Float.floatToRawIntBits(f) & 4294967295L) | (Float.floatToRawIntBits(0.0f) << 32);
    }

    public final float i(long j) {
        int i = (int) (4294967295L & j);
        int i2 = (int) (j >> 32);
        double atan2 = (float) Math.atan2(Math.abs(Float.intBitsToFloat(i)), Math.abs(Float.intBitsToFloat(i2)));
        dj0 dj0Var = this.d;
        if (atan2 >= 0.7853981633974483d) {
            if (dj0Var != dj0.e) {
                return 0.0f;
            }
            return Float.intBitsToFloat(i);
        }
        if (dj0Var != dj0.f) {
            return 0.0f;
        }
        return Float.intBitsToFloat(i2);
    }
}
