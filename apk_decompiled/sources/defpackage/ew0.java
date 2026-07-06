package defpackage;

import java.util.Arrays;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class ew0 extends l0 implements os, ps, vv {
    public final int i;
    public final int j;
    public final xb k;
    public Object[] l;
    public long m;
    public long n;
    public int o;
    public int p;

    public ew0(int i, int i2, xb xbVar) {
        this.i = i;
        this.j = i2;
        this.k = xbVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0080 A[Catch: all -> 0x0036, TRY_ENTER, TryCatch #1 {all -> 0x0036, blocks: (B:14:0x002f, B:18:0x0076, B:21:0x0080, B:30:0x0093, B:33:0x009a, B:34:0x009e, B:36:0x009f, B:42:0x0047), top: B:7:0x001e }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0091 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0020  */
    /* JADX WARN: Type inference failed for: r4v1, types: [l0] */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v4, types: [ew0] */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r9v0, types: [ps] */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v15 */
    /* JADX WARN: Type inference failed for: r9v16 */
    /* JADX WARN: Type inference failed for: r9v17 */
    /* JADX WARN: Type inference failed for: r9v2, types: [m0] */
    /* JADX WARN: Type inference failed for: r9v3 */
    /* JADX WARN: Type inference failed for: r9v4 */
    /* JADX WARN: Type inference failed for: r9v5, types: [fw0] */
    /* JADX WARN: Type inference failed for: r9v8, types: [fw0] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:33:0x00ad -> B:15:0x0032). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void k(defpackage.ew0 r8, defpackage.ps r9, defpackage.ij r10) {
        /*
            boolean r0 = r10 instanceof defpackage.dw0
            if (r0 == 0) goto L13
            r0 = r10
            dw0 r0 = (defpackage.dw0) r0
            int r1 = r0.n
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.n = r1
            goto L18
        L13:
            dw0 r0 = new dw0
            r0.<init>(r8, r10)
        L18:
            java.lang.Object r10 = r0.l
            int r1 = r0.n
            r2 = 3
            r3 = 2
            if (r1 == 0) goto L5a
            r8 = 1
            if (r1 == r8) goto L4b
            if (r1 == r3) goto L3f
            if (r1 != r2) goto L39
            d30 r8 = r0.k
            fw0 r9 = r0.j
            ps r1 = r0.i
            ew0 r4 = r0.h
            defpackage.o30.x(r10)     // Catch: java.lang.Throwable -> L36
        L32:
            r10 = r1
            r1 = r8
            r8 = r4
            goto L73
        L36:
            r8 = move-exception
            goto Lb3
        L39:
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r8)
            return
        L3f:
            d30 r8 = r0.k
            fw0 r9 = r0.j
            ps r1 = r0.i
            ew0 r4 = r0.h
            defpackage.o30.x(r10)     // Catch: java.lang.Throwable -> L36
            goto L76
        L4b:
            fw0 r9 = r0.j
            ps r8 = r0.i
            ew0 r1 = r0.h
            defpackage.o30.x(r10)     // Catch: java.lang.Throwable -> L57
            r10 = r8
            r8 = r1
            goto L66
        L57:
            r8 = move-exception
            r4 = r1
            goto Lb3
        L5a:
            defpackage.o30.x(r10)
            m0 r10 = r8.a()
            fw0 r10 = (defpackage.fw0) r10
            r7 = r10
            r10 = r9
            r9 = r7
        L66:
            yj r1 = r0.f     // Catch: java.lang.Throwable -> Lb0
            r1.getClass()     // Catch: java.lang.Throwable -> Lb0
            x1 r4 = defpackage.x1.L     // Catch: java.lang.Throwable -> Lb0
            wj r1 = r1.j(r4)     // Catch: java.lang.Throwable -> Lb0
            d30 r1 = (defpackage.d30) r1     // Catch: java.lang.Throwable -> Lb0
        L73:
            r4 = r8
            r8 = r1
            r1 = r10
        L76:
            java.lang.Object r10 = r4.t(r9)     // Catch: java.lang.Throwable -> L36
            wq r5 = defpackage.jc0.l     // Catch: java.lang.Throwable -> L36
            ik r6 = defpackage.ik.e
            if (r10 != r5) goto L91
            r0.h = r4     // Catch: java.lang.Throwable -> L36
            r0.i = r1     // Catch: java.lang.Throwable -> L36
            r0.j = r9     // Catch: java.lang.Throwable -> L36
            r0.k = r8     // Catch: java.lang.Throwable -> L36
            r0.n = r3     // Catch: java.lang.Throwable -> L36
            java.lang.Object r10 = r4.i(r9, r0)     // Catch: java.lang.Throwable -> L36
            if (r10 != r6) goto L76
            goto Laf
        L91:
            if (r8 == 0) goto L9f
            boolean r5 = r8.b()     // Catch: java.lang.Throwable -> L36
            if (r5 == 0) goto L9a
            goto L9f
        L9a:
            java.util.concurrent.CancellationException r8 = r8.m()     // Catch: java.lang.Throwable -> L36
            throw r8     // Catch: java.lang.Throwable -> L36
        L9f:
            r0.h = r4     // Catch: java.lang.Throwable -> L36
            r0.i = r1     // Catch: java.lang.Throwable -> L36
            r0.j = r9     // Catch: java.lang.Throwable -> L36
            r0.k = r8     // Catch: java.lang.Throwable -> L36
            r0.n = r2     // Catch: java.lang.Throwable -> L36
            java.lang.Object r10 = r1.g(r10, r0)     // Catch: java.lang.Throwable -> L36
            if (r10 != r6) goto L32
        Laf:
            return
        Lb0:
            r10 = move-exception
            r4 = r8
            r8 = r10
        Lb3:
            r4.f(r9)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ew0.k(ew0, ps, ij):void");
    }

    @Override // defpackage.os
    public final Object b(ps psVar, ij ijVar) {
        k(this, psVar, ijVar);
        return ik.e;
    }

    @Override // defpackage.vv
    public final os c(yj yjVar, int i, xb xbVar) {
        if ((i == 0 || i == -3) && xbVar == xb.e) {
            return this;
        }
        return new gd(this, yjVar, i, xbVar);
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [m0, java.lang.Object, fw0] */
    @Override // defpackage.l0
    public final m0 d() {
        ?? obj = new Object();
        obj.a = -1L;
        return obj;
    }

    @Override // defpackage.l0
    public final m0[] e() {
        return new fw0[2];
    }

    @Override // defpackage.ps
    public final Object g(Object obj, ij ijVar) {
        ew0 ew0Var;
        Throwable th;
        ij[] n;
        cw0 cw0Var;
        if (q(obj)) {
            return x31.a;
        }
        int i = 1;
        pc pcVar = new pc(1, t20.w(ijVar));
        pcVar.s();
        ij[] ijVarArr = o4.a;
        synchronized (this) {
            try {
                if (r(obj)) {
                    try {
                        pcVar.u(x31.a);
                        n = n(ijVarArr);
                        cw0Var = null;
                        ew0Var = this;
                    } catch (Throwable th2) {
                        th = th2;
                        ew0Var = this;
                        throw th;
                    }
                } else {
                    try {
                        ew0Var = this;
                        try {
                            cw0 cw0Var2 = new cw0(ew0Var, o() + this.o + this.p, obj, pcVar);
                            ew0Var.m(cw0Var2);
                            ew0Var.p++;
                            if (ew0Var.j == 0) {
                                ijVarArr = ew0Var.n(ijVarArr);
                            }
                            n = ijVarArr;
                            cw0Var = cw0Var2;
                        } catch (Throwable th3) {
                            th = th3;
                            th = th;
                            throw th;
                        }
                    } catch (Throwable th4) {
                        ew0Var = this;
                        th = th4;
                        throw th;
                    }
                }
                if (cw0Var != null) {
                    pcVar.y(new kc(i, cw0Var));
                }
                for (ij ijVar2 : n) {
                    if (ijVar2 != null) {
                        ijVar2.u(x31.a);
                    }
                }
                Object p = pcVar.p();
                ik ikVar = ik.e;
                if (p != ikVar) {
                    p = x31.a;
                }
                if (p == ikVar) {
                    return p;
                }
                return x31.a;
            } catch (Throwable th5) {
                th = th5;
                ew0Var = this;
            }
        }
    }

    public final Object i(fw0 fw0Var, dw0 dw0Var) {
        pc pcVar = new pc(1, t20.w(dw0Var));
        pcVar.s();
        synchronized (this) {
            try {
                if (s(fw0Var) < 0) {
                    fw0Var.b = pcVar;
                } else {
                    pcVar.u(x31.a);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Object p = pcVar.p();
        if (p == ik.e) {
            return p;
        }
        return x31.a;
    }

    public final void j() {
        if (this.j != 0 || this.p > 1) {
            Object[] objArr = this.l;
            objArr.getClass();
            while (this.p > 0) {
                long o = o();
                int i = this.o;
                int i2 = this.p;
                if (objArr[((int) ((o + (i + i2)) - 1)) & (objArr.length - 1)] == jc0.l) {
                    this.p = i2 - 1;
                    jc0.f(objArr, o() + this.o + this.p, null);
                } else {
                    return;
                }
            }
        }
    }

    public final void l() {
        m0[] m0VarArr;
        Object[] objArr = this.l;
        objArr.getClass();
        jc0.f(objArr, o(), null);
        this.o--;
        long o = o() + 1;
        if (this.m < o) {
            this.m = o;
        }
        if (this.n < o) {
            if (this.f != 0 && (m0VarArr = this.e) != null) {
                for (m0 m0Var : m0VarArr) {
                    if (m0Var != null) {
                        fw0 fw0Var = (fw0) m0Var;
                        long j = fw0Var.a;
                        if (j >= 0 && j < o) {
                            fw0Var.a = o;
                        }
                    }
                }
            }
            this.n = o;
        }
    }

    public final void m(Object obj) {
        int i = this.o + this.p;
        Object[] objArr = this.l;
        if (objArr == null) {
            objArr = p(null, 0, 2);
        } else if (i >= objArr.length) {
            objArr = p(objArr, i, objArr.length * 2);
        }
        jc0.f(objArr, o() + i, obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final ij[] n(ij[] ijVarArr) {
        m0[] m0VarArr;
        fw0 fw0Var;
        pc pcVar;
        int length = ijVarArr.length;
        if (this.f != 0 && (m0VarArr = this.e) != null) {
            int length2 = m0VarArr.length;
            int i = 0;
            ijVarArr = ijVarArr;
            while (i < length2) {
                m0 m0Var = m0VarArr[i];
                if (m0Var != null && (pcVar = (fw0Var = (fw0) m0Var).b) != null && s(fw0Var) >= 0) {
                    int length3 = ijVarArr.length;
                    ijVarArr = ijVarArr;
                    if (length >= length3) {
                        ijVarArr = Arrays.copyOf(ijVarArr, Math.max(2, ijVarArr.length * 2));
                    }
                    ijVarArr[length] = pcVar;
                    fw0Var.b = null;
                    length++;
                }
                i++;
                ijVarArr = ijVarArr;
            }
        }
        return ijVarArr;
    }

    public final long o() {
        return Math.min(this.n, this.m);
    }

    public final Object[] p(Object[] objArr, int i, int i2) {
        if (i2 > 0) {
            Object[] objArr2 = new Object[i2];
            this.l = objArr2;
            if (objArr != null) {
                long o = o();
                for (int i3 = 0; i3 < i; i3++) {
                    long j = i3 + o;
                    jc0.f(objArr2, j, objArr[((int) j) & (objArr.length - 1)]);
                }
            }
            return objArr2;
        }
        v7.o("Buffer size overflow");
        return null;
    }

    public final boolean q(Object obj) {
        int i;
        boolean z;
        ij[] ijVarArr = o4.a;
        synchronized (this) {
            if (r(obj)) {
                ijVarArr = n(ijVarArr);
                z = true;
            } else {
                z = false;
            }
        }
        for (ij ijVar : ijVarArr) {
            if (ijVar != null) {
                ijVar.u(x31.a);
            }
        }
        return z;
    }

    public final boolean r(Object obj) {
        int i = this.f;
        int i2 = this.i;
        if (i == 0) {
            if (i2 != 0) {
                m(obj);
                int i3 = this.o + 1;
                this.o = i3;
                if (i3 > i2) {
                    l();
                }
                this.n = o() + this.o;
                return true;
            }
        } else {
            int i4 = this.o;
            int i5 = this.j;
            if (i4 >= i5 && this.n <= this.m) {
                int ordinal = this.k.ordinal();
                if (ordinal != 0) {
                    if (ordinal != 1) {
                        if (ordinal != 2) {
                            v7.k();
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            }
            m(obj);
            int i6 = this.o + 1;
            this.o = i6;
            if (i6 > i5) {
                l();
            }
            long o = o() + this.o;
            long j = this.m;
            if (((int) (o - j)) > i2) {
                u(1 + j, this.n, o() + this.o, o() + this.o + this.p);
            }
        }
        return true;
    }

    public final long s(fw0 fw0Var) {
        long j = fw0Var.a;
        if (j >= o() + this.o && (this.j > 0 || j > o() || this.p == 0)) {
            return -1L;
        }
        return j;
    }

    public final Object t(fw0 fw0Var) {
        Object obj;
        ij[] ijVarArr = o4.a;
        synchronized (this) {
            try {
                long s = s(fw0Var);
                if (s < 0) {
                    obj = jc0.l;
                } else {
                    long j = fw0Var.a;
                    Object[] objArr = this.l;
                    objArr.getClass();
                    Object obj2 = objArr[((int) s) & (objArr.length - 1)];
                    if (obj2 instanceof cw0) {
                        obj2 = ((cw0) obj2).g;
                    }
                    fw0Var.a = s + 1;
                    Object obj3 = obj2;
                    ijVarArr = v(j);
                    obj = obj3;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        for (ij ijVar : ijVarArr) {
            if (ijVar != null) {
                ijVar.u(x31.a);
            }
        }
        return obj;
    }

    public final void u(long j, long j2, long j3, long j4) {
        long min = Math.min(j2, j);
        for (long o = o(); o < min; o++) {
            Object[] objArr = this.l;
            objArr.getClass();
            jc0.f(objArr, o, null);
        }
        this.m = j;
        this.n = j2;
        this.o = (int) (j3 - min);
        this.p = (int) (j4 - j3);
    }

    public final ij[] v(long j) {
        long j2;
        long j3;
        long j4;
        ij[] ijVarArr;
        long j5;
        ij[] ijVarArr2;
        m0[] m0VarArr;
        wq wqVar = jc0.l;
        ij[] ijVarArr3 = o4.a;
        if (j <= this.n) {
            long o = o();
            long j6 = this.o + o;
            int i = this.j;
            if (i == 0 && this.p > 0) {
                j6++;
            }
            int i2 = 0;
            if (this.f != 0 && (m0VarArr = this.e) != null) {
                for (m0 m0Var : m0VarArr) {
                    if (m0Var != null) {
                        long j7 = ((fw0) m0Var).a;
                        if (j7 >= 0 && j7 < j6) {
                            j6 = j7;
                        }
                    }
                }
            }
            if (j6 > this.n) {
                long o2 = o() + this.o;
                int i3 = this.f;
                int i4 = this.p;
                if (i3 > 0) {
                    j2 = 1;
                    i4 = Math.min(i4, i - ((int) (o2 - j6)));
                } else {
                    j2 = 1;
                }
                long j8 = this.p + o2;
                if (i4 > 0) {
                    Object[] objArr = this.l;
                    objArr.getClass();
                    j3 = o;
                    ij[] ijVarArr4 = new ij[i4];
                    long j9 = o2;
                    while (true) {
                        if (o2 < j8) {
                            ijVarArr2 = ijVarArr4;
                            Object obj = objArr[((int) o2) & (objArr.length - 1)];
                            if (obj != wqVar) {
                                obj.getClass();
                                cw0 cw0Var = (cw0) obj;
                                j4 = j6;
                                int i5 = i2 + 1;
                                ijVarArr2[i2] = cw0Var.h;
                                jc0.f(objArr, o2, wqVar);
                                jc0.f(objArr, j9, cw0Var.g);
                                j9 += j2;
                                if (i5 >= i4) {
                                    break;
                                }
                                i2 = i5;
                            } else {
                                j4 = j6;
                            }
                            o2 += j2;
                            ijVarArr4 = ijVarArr2;
                            j6 = j4;
                        } else {
                            ijVarArr2 = ijVarArr4;
                            j4 = j6;
                            break;
                        }
                    }
                    o2 = j9;
                    ijVarArr = ijVarArr2;
                } else {
                    j3 = o;
                    j4 = j6;
                    ijVarArr = ijVarArr3;
                }
                int i6 = (int) (o2 - j3);
                if (this.f == 0) {
                    j5 = o2;
                } else {
                    j5 = j4;
                }
                long max = Math.max(this.m, o2 - Math.min(this.i, i6));
                if (i == 0 && max < j8) {
                    Object[] objArr2 = this.l;
                    objArr2.getClass();
                    if (o20.e(objArr2[((int) max) & (objArr2.length - 1)], wqVar)) {
                        o2 += j2;
                        max += j2;
                    }
                }
                u(max, j5, o2, j8);
                j();
                if (ijVarArr.length == 0) {
                    return ijVarArr;
                }
                return n(ijVarArr);
            }
        }
        return ijVarArr3;
    }
}
