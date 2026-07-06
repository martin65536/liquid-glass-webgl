package defpackage;

import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class ze0 extends ww0 {
    public static final int[] n = new int[0];
    public final gv e;
    public final gv f;
    public int g;
    public we0 h;
    public ArrayList i;
    public ax0 j;
    public int[] k;
    public int l;
    public boolean m;

    public ze0(long j, ax0 ax0Var, gv gvVar, gv gvVar2) {
        super(j, ax0Var);
        this.e = gvVar;
        this.f = gvVar2;
        this.j = ax0.i;
        this.k = n;
        this.l = 1;
    }

    public final void A(long j) {
        synchronized (cx0.c) {
            this.j = this.j.e(j);
        }
    }

    public final void B(ax0 ax0Var) {
        synchronized (cx0.c) {
            this.j = this.j.d(ax0Var);
        }
    }

    public void C(we0 we0Var) {
        this.h = we0Var;
    }

    public ze0 D(gv gvVar, gv gvVar2) {
        ag0 ag0Var;
        if (this.c) {
            cn0.a("Cannot use a disposed snapshot");
        }
        if (this.m && this.d < 0) {
            cn0.b("Unsupported operation on a disposed or applied snapshot");
        }
        A(g());
        Object obj = cx0.c;
        synchronized (obj) {
            long j = cx0.e;
            cx0.e = j + 1;
            cx0.d = cx0.d.e(j);
            ax0 d = d();
            r(d.e(j));
            ag0Var = new ag0(j, cx0.d(d, g() + 1, j), cx0.k(gvVar, e(), true), cx0.l(gvVar2, i()), this);
        }
        if (!this.m && !this.c) {
            long g = g();
            synchronized (obj) {
                long j2 = cx0.e;
                cx0.e = j2 + 1;
                s(j2);
                cx0.d = cx0.d.e(g());
            }
            r(cx0.d(d(), g + 1, g()));
            return ag0Var;
        }
        return ag0Var;
    }

    @Override // defpackage.ww0
    public final void b() {
        cx0.d = cx0.d.b(g()).a(this.j);
    }

    @Override // defpackage.ww0
    public void c() {
        if (!this.c) {
            this.c = true;
            synchronized (cx0.c) {
                o();
            }
            l();
        }
    }

    @Override // defpackage.ww0
    public boolean f() {
        return false;
    }

    @Override // defpackage.ww0
    public int h() {
        return this.g;
    }

    @Override // defpackage.ww0
    public gv i() {
        return this.f;
    }

    @Override // defpackage.ww0
    public void k() {
        this.l++;
    }

    @Override // defpackage.ww0
    public void l() {
        if (this.l <= 0) {
            cn0.a("no pending nested snapshots");
        }
        int i = this.l - 1;
        this.l = i;
        if (i == 0 && !this.m) {
            we0 x = x();
            if (x != null) {
                if (this.m) {
                    cn0.b("Unsupported operation on a snapshot that has been applied");
                }
                C(null);
                long g = g();
                Object[] objArr = x.b;
                long[] jArr = x.a;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i2 = 0;
                    while (true) {
                        long j = jArr[i2];
                        if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i3 = 8 - ((~(i2 - length)) >>> 31);
                            for (int i4 = 0; i4 < i3; i4++) {
                                if ((255 & j) < 128) {
                                    for (py0 a = ((ny0) objArr[(i2 << 3) + i4]).a(); a != null; a = a.b) {
                                        long j2 = a.a;
                                        if (j2 == g || me.R(this.j, Long.valueOf(j2))) {
                                            ts0 ts0Var = cx0.a;
                                            a.a = 0L;
                                        }
                                    }
                                }
                                j >>= 8;
                            }
                            if (i3 != 8) {
                                break;
                            }
                        }
                        if (i2 == length) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
            }
            a();
        }
    }

    @Override // defpackage.ww0
    public void m() {
        if (!this.m && !this.c) {
            v();
        }
    }

    @Override // defpackage.ww0
    public void n(ny0 ny0Var) {
        we0 x = x();
        if (x == null) {
            we0 we0Var = at0.a;
            x = new we0();
            C(x);
        }
        x.a(ny0Var);
    }

    @Override // defpackage.ww0
    public final void p() {
        int length = this.k.length;
        for (int i = 0; i < length; i++) {
            cx0.v(this.k[i]);
        }
        o();
    }

    @Override // defpackage.ww0
    public void t(int i) {
        this.g = i;
    }

    @Override // defpackage.ww0
    public ww0 u(gv gvVar) {
        bg0 bg0Var;
        if (this.c) {
            cn0.a("Cannot use a disposed snapshot");
        }
        if (this.m && this.d < 0) {
            cn0.b("Unsupported operation on a disposed or applied snapshot");
        }
        long g = g();
        A(g());
        Object obj = cx0.c;
        synchronized (obj) {
            long j = cx0.e;
            cx0.e = j + 1;
            cx0.d = cx0.d.e(j);
            bg0Var = new bg0(j, cx0.d(d(), g + 1, j), cx0.k(gvVar, e(), true), this);
        }
        if (!this.m && !this.c) {
            long g2 = g();
            synchronized (obj) {
                long j2 = cx0.e;
                cx0.e = j2 + 1;
                s(j2);
                cx0.d = cx0.d.e(g());
            }
            r(cx0.d(d(), g2 + 1, g()));
            return bg0Var;
        }
        return bg0Var;
    }

    public final void v() {
        A(g());
        if (!this.m && !this.c) {
            long g = g();
            synchronized (cx0.c) {
                long j = cx0.e;
                cx0.e = j + 1;
                s(j);
                cx0.d = cx0.d.e(g());
            }
            r(cx0.d(d(), g + 1, g()));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00ab A[LOOP:1: B:31:0x00a9->B:32:0x00ab, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00ba A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0111 A[Catch: all -> 0x00fe, TryCatch #1 {all -> 0x00fe, blocks: (B:37:0x00ba, B:39:0x00ca, B:42:0x00d6, B:44:0x00e2, B:46:0x00ec, B:48:0x00f2, B:50:0x0100, B:56:0x0111, B:59:0x011b, B:61:0x0125, B:63:0x012f, B:65:0x0135, B:67:0x013f, B:73:0x0147, B:75:0x014a, B:77:0x014e, B:79:0x0155, B:81:0x0161, B:87:0x0108), top: B:36:0x00ba }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x014e A[Catch: all -> 0x00fe, TryCatch #1 {all -> 0x00fe, blocks: (B:37:0x00ba, B:39:0x00ca, B:42:0x00d6, B:44:0x00e2, B:46:0x00ec, B:48:0x00f2, B:50:0x0100, B:56:0x0111, B:59:0x011b, B:61:0x0125, B:63:0x012f, B:65:0x0135, B:67:0x013f, B:73:0x0147, B:75:0x014a, B:77:0x014e, B:79:0x0155, B:81:0x0161, B:87:0x0108), top: B:36:0x00ba }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public defpackage.y20 w() {
        /*
            Method dump skipped, instructions count: 363
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ze0.w():y20");
    }

    public we0 x() {
        return this.h;
    }

    @Override // defpackage.ww0
    /* renamed from: y, reason: merged with bridge method [inline-methods] */
    public gv e() {
        return this.e;
    }

    public final y20 z(long j, we0 we0Var, HashMap hashMap, ax0 ax0Var) {
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        ax0 ax0Var2;
        Object[] objArr;
        long[] jArr;
        ax0 ax0Var3;
        Object[] objArr2;
        long[] jArr2;
        int i;
        long j2;
        ArrayList arrayList4;
        py0 b;
        xj0 xj0Var;
        ArrayList arrayList5;
        ax0 d = d().e(g()).d(this.j);
        Object[] objArr3 = we0Var.b;
        long[] jArr3 = we0Var.a;
        int length = jArr3.length - 2;
        if (length >= 0) {
            int i2 = 0;
            arrayList3 = null;
            arrayList2 = null;
            while (true) {
                long j3 = jArr3[i2];
                if ((((~j3) << 7) & j3 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i3 = 8 - ((~(i2 - length)) >>> 31);
                    int i4 = 0;
                    while (i4 < i3) {
                        if ((j3 & 255) < 128) {
                            objArr2 = objArr3;
                            ny0 ny0Var = (ny0) objArr3[(i2 << 3) + i4];
                            jArr2 = jArr3;
                            py0 a = ny0Var.a();
                            i = i4;
                            ArrayList arrayList6 = arrayList3;
                            py0 t = cx0.t(a, j, ax0Var);
                            if (t == null) {
                                arrayList4 = arrayList2;
                                j2 = j3;
                            } else {
                                arrayList4 = arrayList2;
                                j2 = j3;
                                py0 t2 = cx0.t(a, g(), d);
                                if (t2 != null && t2.a != 1 && !t.equals(t2)) {
                                    ax0Var3 = d;
                                    py0 t3 = cx0.t(a, g(), d());
                                    if (t3 != null) {
                                        if (hashMap == null || (b = (py0) hashMap.get(t)) == null) {
                                            b = ny0Var.b(t2, t, t3);
                                        }
                                        if (b == null) {
                                            return new xw0(this);
                                        }
                                        if (!b.equals(t3)) {
                                            if (b.equals(t)) {
                                                if (arrayList6 == null) {
                                                    arrayList5 = new ArrayList();
                                                } else {
                                                    arrayList5 = arrayList6;
                                                }
                                                arrayList5.add(new xj0(ny0Var, t.b(g())));
                                                if (arrayList4 == null) {
                                                    arrayList2 = new ArrayList();
                                                } else {
                                                    arrayList2 = arrayList4;
                                                }
                                                arrayList2.add(ny0Var);
                                                arrayList3 = arrayList5;
                                            } else {
                                                if (arrayList6 == null) {
                                                    arrayList3 = new ArrayList();
                                                } else {
                                                    arrayList3 = arrayList6;
                                                }
                                                if (!b.equals(t2)) {
                                                    xj0Var = new xj0(ny0Var, b);
                                                } else {
                                                    xj0Var = new xj0(ny0Var, t2.b(g()));
                                                }
                                                arrayList3.add(xj0Var);
                                                arrayList2 = arrayList4;
                                            }
                                        }
                                        arrayList3 = arrayList6;
                                        arrayList2 = arrayList4;
                                    } else {
                                        cx0.s();
                                        throw null;
                                    }
                                }
                            }
                            ax0Var3 = d;
                            arrayList3 = arrayList6;
                            arrayList2 = arrayList4;
                        } else {
                            ax0Var3 = d;
                            objArr2 = objArr3;
                            jArr2 = jArr3;
                            i = i4;
                            j2 = j3;
                        }
                        j3 = j2 >> 8;
                        i4 = i + 1;
                        jArr3 = jArr2;
                        objArr3 = objArr2;
                        d = ax0Var3;
                    }
                    ax0Var2 = d;
                    objArr = objArr3;
                    jArr = jArr3;
                    if (i3 != 8) {
                        break;
                    }
                } else {
                    ax0Var2 = d;
                    objArr = objArr3;
                    jArr = jArr3;
                }
                if (i2 != length) {
                    i2++;
                    jArr3 = jArr;
                    objArr3 = objArr;
                    d = ax0Var2;
                } else {
                    arrayList = arrayList3;
                    break;
                }
            }
        } else {
            arrayList = null;
            arrayList2 = null;
        }
        arrayList3 = arrayList;
        if (arrayList3 != null) {
            v();
            int size = arrayList3.size();
            for (int i5 = 0; i5 < size; i5++) {
                xj0 xj0Var2 = (xj0) arrayList3.get(i5);
                ny0 ny0Var2 = (ny0) xj0Var2.e;
                py0 py0Var = (py0) xj0Var2.f;
                py0Var.a = j;
                synchronized (cx0.c) {
                    py0Var.b = ny0Var2.a();
                    ny0Var2.c(py0Var);
                }
            }
        }
        if (arrayList2 != null) {
            int size2 = arrayList2.size();
            for (int i6 = 0; i6 < size2; i6++) {
                we0Var.l((ny0) arrayList2.get(i6));
            }
            ArrayList arrayList7 = this.i;
            if (arrayList7 != null) {
                arrayList2 = me.b0(arrayList7, arrayList2);
            }
            this.i = arrayList2;
        }
        return yw0.a;
    }
}
