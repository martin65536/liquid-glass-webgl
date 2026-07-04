package defpackage;

import android.os.Handler;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class n50 implements xg {
    public final z40 e;
    public th f;
    public kz0 g;
    public int h;
    public int i;
    public final ve0 j;
    public final ve0 k;
    public final h50 l;
    public final e50 m;
    public final ve0 n;
    public final jz0 o;
    public final ve0 p;
    public final ef0 q;
    public int r;
    public int s;

    public n50(z40 z40Var, kz0 kz0Var) {
        this.e = z40Var;
        this.g = kz0Var;
        long[] jArr = zs0.a;
        this.j = new ve0();
        this.k = new ve0();
        this.l = new h50(this);
        this.m = new e50(this);
        this.n = new ve0();
        this.o = new jz0();
        this.p = new ve0();
        this.q = new ef0(new Object[16]);
    }

    public static final void c(n50 n50Var, Object obj) {
        z40 z40Var = n50Var.e;
        n50Var.h();
        z40 z40Var2 = (z40) n50Var.n.k(obj);
        if (z40Var2 != null) {
            if (n50Var.s <= 0) {
                q00.b("No pre-composed items to dispose");
            }
            int i = ((bf0) z40Var.n()).e.i(z40Var2);
            if (i < ((bf0) z40Var.n()).e.g - n50Var.s) {
                q00.b("Item is not in pre-composed item range");
            }
            n50Var.r++;
            n50Var.s--;
            f50 f50Var = (f50) n50Var.j.g(z40Var2);
            if (f50Var != null) {
                e(f50Var);
            }
            int i2 = (((bf0) z40Var.n()).e.g - n50Var.s) - n50Var.r;
            n50Var.j(i, i2);
            n50Var.g(i2);
        }
        if (n50Var.q.h(obj)) {
            z40.T(z40Var, true, 6);
        }
    }

    public static void e(f50 f50Var) {
        we0 we0Var;
        hl0 hl0Var = f50Var.f;
        if (hl0Var != null) {
            hl0Var.h.set(jl0.f);
            mp0 mp0Var = hl0Var.k;
            if (mp0Var.d.h()) {
                we0Var = mp0Var.d;
                we0 we0Var2 = at0.a;
                mp0Var.d = new we0();
                mp0Var.c.g();
            } else {
                we0Var = null;
            }
            mp0Var.b();
            yh yhVar = hl0Var.a;
            yhVar.u = null;
            if (we0Var != null) {
                yhVar.y.k = we0Var;
                yhVar.A = 2;
            }
            f50Var.f = null;
            yh yhVar2 = f50Var.c;
            if (yhVar2 != null) {
                yhVar2.m();
            }
            f50Var.c = null;
        }
    }

    @Override // defpackage.xg
    public final void a() {
        yh yhVar;
        z40 z40Var = this.e;
        z40Var.t = true;
        ve0 ve0Var = this.j;
        Object[] objArr = ve0Var.c;
        long[] jArr = ve0Var.a;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j) < 128 && (yhVar = ((f50) objArr[(i << 3) + i3]).c) != null) {
                            yhVar.m();
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
        z40Var.N();
        z40Var.t = false;
        ve0Var.a();
        this.k.a();
        this.s = 0;
        this.r = 0;
        this.n.a();
        h();
    }

    @Override // defpackage.xg
    public final void b() {
        i(true);
    }

    public final void d(f50 f50Var, boolean z) {
        gv gvVar;
        hl0 hl0Var = f50Var.f;
        if (hl0Var != null) {
            ww0 t = t20.t();
            if (t != null) {
                gvVar = t.e();
            } else {
                gvVar = null;
            }
            ww0 C = t20.C(t);
            try {
                z40 z40Var = this.e;
                z40Var.t = true;
                if (z) {
                    while (!hl0Var.c()) {
                        try {
                            hl0Var.e(new v7(16));
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
                hl0Var.a();
                f50Var.f = null;
                z40Var.t = false;
            } finally {
                t20.K(t, C, gvVar);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object, fz0] */
    public final fz0 f(Object obj) {
        if (!this.e.E()) {
            return new Object();
        }
        return new l50(this, obj);
    }

    public final void g(int i) {
        boolean z;
        gv gvVar;
        boolean z2 = false;
        this.r = 0;
        List n = this.e.n();
        bf0 bf0Var = (bf0) n;
        int i2 = (bf0Var.e.g - this.s) - 1;
        if (i <= i2) {
            this.o.clear();
            if (i <= i2) {
                int i3 = i;
                while (true) {
                    Object g = this.j.g((z40) bf0Var.get(i3));
                    g.getClass();
                    ((qe0) this.o.f).a(((f50) g).a);
                    if (i3 == i2) {
                        break;
                    } else {
                        i3++;
                    }
                }
            }
            this.g.d(this.o);
            ww0 t = t20.t();
            if (t != null) {
                gvVar = t.e();
            } else {
                gvVar = null;
            }
            ww0 C = t20.C(t);
            z = false;
            while (i2 >= i) {
                try {
                    z40 z40Var = (z40) ((bf0) n).get(i2);
                    Object g2 = this.j.g(z40Var);
                    g2.getClass();
                    f50 f50Var = (f50) g2;
                    Object obj = f50Var.a;
                    if (((qe0) this.o.f).c(obj)) {
                        this.r++;
                        if (((Boolean) f50Var.g.getValue()).booleanValue()) {
                            d50 d50Var = z40Var.I;
                            oc0 oc0Var = d50Var.p;
                            x40 x40Var = x40.g;
                            oc0Var.p = x40Var;
                            ub0 ub0Var = d50Var.q;
                            if (ub0Var != null) {
                                ub0Var.n = x40Var;
                            }
                            l(f50Var, false);
                            if (f50Var.h) {
                                z = true;
                            }
                        }
                    } else {
                        z40 z40Var2 = this.e;
                        z40Var2.t = true;
                        this.j.k(z40Var);
                        yh yhVar = f50Var.c;
                        if (yhVar != null) {
                            yhVar.m();
                        }
                        this.e.O(i2, 1);
                        z40Var2.t = false;
                    }
                    this.k.k(obj);
                    i2--;
                } catch (Throwable th) {
                    t20.K(t, C, gvVar);
                    throw th;
                }
            }
            t20.K(t, C, gvVar);
        } else {
            z = false;
        }
        if (z) {
            synchronized (cx0.c) {
                we0 we0Var = cx0.j.h;
                if (we0Var != null) {
                    if (we0Var.h()) {
                        z2 = true;
                    }
                }
            }
            if (z2) {
                cx0.a();
            }
        }
        h();
    }

    public final void h() {
        int i = ((bf0) this.e.n()).e.g;
        ve0 ve0Var = this.j;
        if (ve0Var.e != i) {
            q00.a("Inconsistency between the count of nodes tracked by the state (" + ve0Var.e + ") and the children count on the SubcomposeLayout (" + i + "). Are you trying to use the state of the disposed SubcomposeLayout?");
        }
        if ((i - this.r) - this.s < 0) {
            q00.a("Incorrect state. Total children " + i + ". Reusable children " + this.r + ". Precomposed children " + this.s);
        }
        ve0 ve0Var2 = this.n;
        if (ve0Var2.e == this.s) {
            return;
        }
        q00.a("Incorrect state. Precomposed children " + this.s + ". Map size " + ve0Var2.e);
    }

    public final void i(boolean z) {
        gv gvVar;
        this.s = 0;
        this.n.a();
        List n = this.e.n();
        int i = ((bf0) n).e.g;
        if (this.r != i) {
            this.r = i;
            ww0 t = t20.t();
            if (t != null) {
                gvVar = t.e();
            } else {
                gvVar = null;
            }
            ww0 C = t20.C(t);
            for (int i2 = 0; i2 < i; i2++) {
                try {
                    z40 z40Var = (z40) ((bf0) n).get(i2);
                    f50 f50Var = (f50) this.j.g(z40Var);
                    if (f50Var != null && ((Boolean) f50Var.g.getValue()).booleanValue()) {
                        d50 d50Var = z40Var.I;
                        oc0 oc0Var = d50Var.p;
                        x40 x40Var = x40.g;
                        oc0Var.p = x40Var;
                        ub0 ub0Var = d50Var.q;
                        if (ub0Var != null) {
                            ub0Var.n = x40Var;
                        }
                        l(f50Var, z);
                        f50Var.a = jc0.m;
                    }
                } catch (Throwable th) {
                    t20.K(t, C, gvVar);
                    throw th;
                }
            }
            t20.K(t, C, gvVar);
            this.k.a();
        }
        h();
    }

    public final void j(int i, int i2) {
        z40 z40Var = this.e;
        z40Var.t = true;
        z40Var.J(i, i2, 1);
        z40Var.t = false;
    }

    public final void k(Object obj, kv kvVar, boolean z) {
        z40 z40Var = this.e;
        if (z40Var.E()) {
            h();
            if (!this.k.c(obj)) {
                this.p.k(obj);
                ve0 ve0Var = this.n;
                Object g = ve0Var.g(obj);
                if (g == null) {
                    g = n(obj);
                    if (g != null) {
                        j(((bf0) z40Var.n()).e.i(g), ((bf0) z40Var.n()).e.g);
                        this.s++;
                    } else {
                        int i = ((bf0) z40Var.n()).e.g;
                        z40 z40Var2 = new z40(2);
                        z40Var.t = true;
                        z40Var.y(i, z40Var2);
                        z40Var.t = false;
                        this.s++;
                        g = z40Var2;
                    }
                    ve0Var.m(obj, g);
                }
                m((z40) g, obj, z, kvVar);
            }
        }
    }

    public final void l(f50 f50Var, boolean z) {
        yh yhVar;
        if (!z && f50Var.h) {
            f50Var.g.setValue(Boolean.FALSE);
        } else {
            f50Var.g = n30.B(Boolean.FALSE);
        }
        if (f50Var.f != null) {
            e(f50Var);
            return;
        }
        if (z) {
            yh yhVar2 = f50Var.c;
            if (yhVar2 != null) {
                yhVar2.l();
                return;
            }
            return;
        }
        ej0 m8getOutOfFrameExecutor = ((b4) c50.a(this.e)).m8getOutOfFrameExecutor();
        if (m8getOutOfFrameExecutor != null) {
            n9 n9Var = new n9(10, f50Var);
            b4 b4Var = (b4) m8getOutOfFrameExecutor;
            a8 a8Var = b4Var.m;
            boolean isEmpty = a8Var.isEmpty();
            a8Var.addLast(n9Var);
            if (isEmpty) {
                Handler handler = b4Var.getHandler();
                if (handler != null) {
                    handler.postAtFrontOfQueue(b4Var.n);
                    return;
                } else {
                    v7.m("schedule is called when outOfFrameExecutor is not available (view is detached)");
                    return;
                }
            }
            return;
        }
        if (!f50Var.h && (yhVar = f50Var.c) != null) {
            yhVar.l();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00bf A[Catch: all -> 0x008d, TryCatch #0 {all -> 0x008d, blocks: (B:37:0x0076, B:40:0x0082, B:45:0x00ad, B:47:0x00bf, B:49:0x00d4, B:51:0x00d8, B:52:0x010c, B:55:0x00e5, B:56:0x00f0, B:58:0x00f4, B:59:0x0109, B:60:0x00c2, B:63:0x0092, B:65:0x00a0, B:66:0x0116, B:67:0x0120), top: B:36:0x0076 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00d4 A[Catch: all -> 0x008d, TryCatch #0 {all -> 0x008d, blocks: (B:37:0x0076, B:40:0x0082, B:45:0x00ad, B:47:0x00bf, B:49:0x00d4, B:51:0x00d8, B:52:0x010c, B:55:0x00e5, B:56:0x00f0, B:58:0x00f4, B:59:0x0109, B:60:0x00c2, B:63:0x0092, B:65:0x00a0, B:66:0x0116, B:67:0x0120), top: B:36:0x0076 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00f0 A[Catch: all -> 0x008d, TryCatch #0 {all -> 0x008d, blocks: (B:37:0x0076, B:40:0x0082, B:45:0x00ad, B:47:0x00bf, B:49:0x00d4, B:51:0x00d8, B:52:0x010c, B:55:0x00e5, B:56:0x00f0, B:58:0x00f4, B:59:0x0109, B:60:0x00c2, B:63:0x0092, B:65:0x00a0, B:66:0x0116, B:67:0x0120), top: B:36:0x0076 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00c2 A[Catch: all -> 0x008d, TryCatch #0 {all -> 0x008d, blocks: (B:37:0x0076, B:40:0x0082, B:45:0x00ad, B:47:0x00bf, B:49:0x00d4, B:51:0x00d8, B:52:0x010c, B:55:0x00e5, B:56:0x00f0, B:58:0x00f4, B:59:0x0109, B:60:0x00c2, B:63:0x0092, B:65:0x00a0, B:66:0x0116, B:67:0x0120), top: B:36:0x0076 }] */
    /* JADX WARN: Type inference failed for: r1v3, types: [f50, java.lang.Object] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void m(defpackage.z40 r10, java.lang.Object r11, boolean r12, defpackage.kv r13) {
        /*
            Method dump skipped, instructions count: 293
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.n50.m(z40, java.lang.Object, boolean, kv):void");
    }

    public final z40 n(Object obj) {
        ve0 ve0Var;
        int i;
        if (this.r != 0) {
            bf0 bf0Var = (bf0) this.e.n();
            int i2 = bf0Var.e.g - this.s;
            int i3 = i2 - this.r;
            int i4 = i2 - 1;
            int i5 = i4;
            while (true) {
                ve0Var = this.j;
                if (i5 >= i3) {
                    Object g = ve0Var.g((z40) bf0Var.get(i5));
                    g.getClass();
                    if (((f50) g).a.equals(obj)) {
                        i = i5;
                        break;
                    }
                    i5--;
                } else {
                    i = -1;
                    break;
                }
            }
            if (i == -1) {
                while (i4 >= i3) {
                    Object g2 = ve0Var.g((z40) bf0Var.get(i4));
                    g2.getClass();
                    f50 f50Var = (f50) g2;
                    Object obj2 = f50Var.a;
                    if (obj2 != jc0.m && !this.g.i(obj, obj2)) {
                        i4--;
                    } else {
                        f50Var.a = obj;
                        i5 = i4;
                        i = i5;
                        break;
                    }
                }
                i5 = i4;
            }
            if (i == -1) {
                return null;
            }
            if (i5 != i3) {
                j(i5, i3);
            }
            this.r--;
            z40 z40Var = (z40) bf0Var.get(i3);
            Object g3 = ve0Var.g(z40Var);
            g3.getClass();
            f50 f50Var2 = (f50) g3;
            f50Var2.g = n30.B(Boolean.TRUE);
            f50Var2.e = true;
            f50Var2.d = true;
            return z40Var;
        }
        return null;
    }
}
