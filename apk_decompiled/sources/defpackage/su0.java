package defpackage;

import java.util.ArrayList;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class su0 {
    public final bd0 a;
    public final boolean b;
    public final z40 c;
    public final nu0 d;
    public su0 e;
    public final int f;

    public su0(bd0 bd0Var, boolean z, z40 z40Var, nu0 nu0Var) {
        this.a = bd0Var;
        this.b = z;
        this.c = z40Var;
        this.d = nu0Var;
        this.f = z40Var.f;
    }

    public static /* synthetic */ List j(int i, su0 su0Var) {
        boolean z;
        boolean z2 = false;
        if ((i & 1) != 0) {
            z = !su0Var.b;
        } else {
            z = false;
        }
        if ((i & 2) == 0) {
            z2 = true;
        }
        return su0Var.i(z, z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v10, types: [bd0] */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12, types: [bd0] */
    /* JADX WARN: Type inference failed for: r1v13, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v18 */
    /* JADX WARN: Type inference failed for: r1v19 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v9 */
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
    public final wo0 a(ng0 ng0Var) {
        jm jmVar;
        su0 l = l();
        if (l == null) {
            return wo0.e;
        }
        bd0 bd0Var = l.c.H.f;
        ng0 ng0Var2 = null;
        if ((bd0Var.h & 8) != 0) {
            loop0: while (bd0Var != null) {
                if ((bd0Var.g & 8) != 0) {
                    jmVar = bd0Var;
                    ?? r5 = 0;
                    while (jmVar != 0) {
                        if (jmVar instanceof qu0) {
                            if (jmVar.u()) {
                                break loop0;
                            }
                        } else if ((jmVar.g & 8) != 0 && (jmVar instanceof jm)) {
                            bd0 bd0Var2 = jmVar.t;
                            int i = 0;
                            jmVar = jmVar;
                            r5 = r5;
                            while (bd0Var2 != null) {
                                if ((bd0Var2.g & 8) != 0) {
                                    i++;
                                    r5 = r5;
                                    if (i == 1) {
                                        jmVar = bd0Var2;
                                    } else {
                                        if (r5 == 0) {
                                            r5 = new ef0(new bd0[16]);
                                        }
                                        if (jmVar != 0) {
                                            r5.b(jmVar);
                                            jmVar = 0;
                                        }
                                        r5.b(bd0Var2);
                                    }
                                }
                                bd0Var2 = bd0Var2.j;
                                jmVar = jmVar;
                                r5 = r5;
                            }
                            if (i == 1) {
                            }
                        }
                        jmVar = k81.h(r5);
                    }
                }
                if ((bd0Var.h & 8) == 0) {
                    break;
                }
                bd0Var = bd0Var.j;
            }
        }
        jmVar = 0;
        qu0 qu0Var = (qu0) jmVar;
        if (qu0Var != null) {
            ng0Var2 = k81.B(qu0Var, 8);
        }
        if (ng0Var2 == null) {
            return l.a(ng0Var);
        }
        return ng0Var2.U(ng0Var, true);
    }

    public final su0 b(cr0 cr0Var, gv gvVar) {
        int i;
        nu0 nu0Var = new nu0();
        nu0Var.g = false;
        nu0Var.h = false;
        gvVar.e(nu0Var);
        ru0 ru0Var = new ru0(gvVar);
        int i2 = this.f;
        if (cr0Var != null) {
            i = 1000000000;
        } else {
            i = 2000000000;
        }
        su0 su0Var = new su0(ru0Var, false, new z40(i2 + i, true), nu0Var);
        su0Var.e = this;
        return su0Var;
    }

    public final void c(z40 z40Var, ArrayList arrayList) {
        ef0 v = z40Var.v();
        Object[] objArr = v.e;
        int i = v.g;
        for (int i2 = 0; i2 < i; i2++) {
            z40 z40Var2 = (z40) objArr[i2];
            if (z40Var2.E() && !z40Var2.Q) {
                if (z40Var2.H.d(8)) {
                    arrayList.add(t20.d(z40Var2, this.b));
                } else {
                    c(z40Var2, arrayList);
                }
            }
        }
    }

    public final ng0 d() {
        if (o()) {
            su0 l = l();
            if (l != null) {
                return l.d();
            }
            return null;
        }
        qu0 f = f();
        if (f != null) {
            return k81.B(f, 8);
        }
        return this.c.H.c;
    }

    public final void e(ArrayList arrayList, ArrayList arrayList2) {
        s(arrayList, false);
        int size = arrayList.size();
        for (int size2 = arrayList.size(); size2 < size; size2++) {
            su0 su0Var = (su0) arrayList.get(size2);
            if (su0Var.p()) {
                arrayList2.add(su0Var);
            } else if (!su0Var.d.h) {
                su0Var.e(arrayList, arrayList2);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final qu0 f() {
        bd0 bd0Var;
        boolean z;
        boolean z2 = this.d.g;
        Object obj = null;
        z40 z40Var = this.c;
        if (z2) {
            bd0 bd0Var2 = z40Var.H.f;
            if ((bd0Var2.h & 8) != 0) {
                bd0Var = null;
                while (bd0Var2 != null) {
                    if ((bd0Var2.g & 8) != 0) {
                        bd0 bd0Var3 = bd0Var2;
                        ef0 ef0Var = null;
                        while (bd0Var3 != null) {
                            if (bd0Var3 instanceof qu0) {
                                qu0 qu0Var = (qu0) bd0Var3;
                                if (qu0Var.u()) {
                                    if (qu0Var.i0()) {
                                        return qu0Var;
                                    }
                                    if (bd0Var == null) {
                                        bd0Var = qu0Var;
                                    }
                                }
                                z = false;
                            } else {
                                z = true;
                            }
                            if (z && (bd0Var3.g & 8) != 0 && (bd0Var3 instanceof jm)) {
                                int i = 0;
                                for (bd0 bd0Var4 = ((jm) bd0Var3).t; bd0Var4 != null; bd0Var4 = bd0Var4.j) {
                                    if ((bd0Var4.g & 8) != 0) {
                                        i++;
                                        if (i == 1) {
                                            bd0Var3 = bd0Var4;
                                        } else {
                                            if (ef0Var == null) {
                                                ef0Var = new ef0(new bd0[16]);
                                            }
                                            if (bd0Var3 != null) {
                                                ef0Var.b(bd0Var3);
                                                bd0Var3 = null;
                                            }
                                            ef0Var.b(bd0Var4);
                                        }
                                    }
                                }
                                if (i == 1) {
                                }
                            }
                            bd0Var3 = k81.h(ef0Var);
                        }
                    }
                    if ((bd0Var2.h & 8) == 0) {
                        break;
                    }
                    bd0Var2 = bd0Var2.j;
                }
                obj = bd0Var;
            }
            return (qu0) obj;
        }
        bd0 bd0Var5 = z40Var.H.f;
        if ((bd0Var5.h & 8) != 0) {
            loop3: while (bd0Var5 != null) {
                if ((bd0Var5.g & 8) != 0) {
                    bd0Var = bd0Var5;
                    ef0 ef0Var2 = null;
                    while (bd0Var != null) {
                        if (bd0Var instanceof qu0) {
                            if (((qu0) bd0Var).u()) {
                                obj = bd0Var;
                            }
                        } else if ((bd0Var.g & 8) != 0 && (bd0Var instanceof jm)) {
                            int i2 = 0;
                            for (bd0 bd0Var6 = ((jm) bd0Var).t; bd0Var6 != null; bd0Var6 = bd0Var6.j) {
                                if ((bd0Var6.g & 8) != 0) {
                                    i2++;
                                    if (i2 == 1) {
                                        bd0Var = bd0Var6;
                                    } else {
                                        if (ef0Var2 == null) {
                                            ef0Var2 = new ef0(new bd0[16]);
                                        }
                                        if (bd0Var != null) {
                                            ef0Var2.b(bd0Var);
                                            bd0Var = null;
                                        }
                                        ef0Var2.b(bd0Var6);
                                    }
                                }
                            }
                            if (i2 == 1) {
                            }
                        }
                        bd0Var = k81.h(ef0Var2);
                    }
                }
                if ((bd0Var5.h & 8) == 0) {
                    break;
                }
                bd0Var5 = bd0Var5.j;
            }
        }
        return (qu0) obj;
    }

    public final wo0 g() {
        ng0 d = d();
        if (d != null) {
            if (!d.P0().r) {
                d = null;
            }
            if (d != null) {
                return o30.n(d).U(d, true);
            }
        }
        return wo0.e;
    }

    public final wo0 h() {
        ng0 d = d();
        if (d != null) {
            if (!d.P0().r) {
                d = null;
            }
            if (d != null) {
                return o30.i(d, true);
            }
        }
        return wo0.e;
    }

    public final List i(boolean z, boolean z2) {
        if (!z && this.d.h) {
            return er.e;
        }
        ArrayList arrayList = new ArrayList();
        if (p()) {
            ArrayList arrayList2 = new ArrayList();
            e(arrayList, arrayList2);
            return arrayList2;
        }
        return s(arrayList, z2);
    }

    public final nu0 k() {
        boolean p = p();
        nu0 nu0Var = this.d;
        if (p) {
            nu0 c = nu0Var.c();
            r(new ArrayList(), c);
            return c;
        }
        return nu0Var;
    }

    public final su0 l() {
        z40 z40Var;
        su0 su0Var = this.e;
        if (su0Var != null) {
            return su0Var;
        }
        z40 z40Var2 = this.c;
        boolean z = this.b;
        if (z) {
            z40Var = z40Var2.s();
            while (z40Var != null) {
                nu0 u = z40Var.u();
                if (u != null && u.g) {
                    break;
                }
                z40Var = z40Var.s();
            }
        }
        z40Var = null;
        if (z40Var == null) {
            z40 s = z40Var2.s();
            while (true) {
                if (s != null) {
                    if (s.H.d(8)) {
                        z40Var = s;
                        break;
                    }
                    s = s.s();
                } else {
                    z40Var = null;
                    break;
                }
            }
        }
        if (z40Var == null) {
            return null;
        }
        return t20.d(z40Var, z);
    }

    public final wo0 m() {
        boolean z;
        im f = f();
        if (f == null) {
            return this.c.H.c.l1();
        }
        bd0 bd0Var = ((bd0) f).e;
        Object g = this.d.e.g(mu0.b);
        if (g == null) {
            g = null;
        }
        if (g != null) {
            z = true;
        } else {
            z = false;
        }
        return m20.q(bd0Var, z, true);
    }

    public final nu0 n() {
        return this.d;
    }

    public final boolean o() {
        if (this.e != null) {
            return true;
        }
        return false;
    }

    public final boolean p() {
        if (this.b && this.d.g) {
            return true;
        }
        return false;
    }

    public final boolean q() {
        if (!o() && j(4, this).isEmpty()) {
            z40 s = this.c.s();
            while (true) {
                if (s != null) {
                    nu0 u = s.u();
                    if (u != null && u.g) {
                        break;
                    }
                    s = s.s();
                } else {
                    s = null;
                    break;
                }
            }
            if (s == null) {
                return true;
            }
            return false;
        }
        return false;
    }

    public final void r(ArrayList arrayList, nu0 nu0Var) {
        if (!this.d.h) {
            s(arrayList, false);
            int size = arrayList.size();
            for (int size2 = arrayList.size(); size2 < size; size2++) {
                su0 su0Var = (su0) arrayList.get(size2);
                if (!su0Var.p()) {
                    nu0Var.e(su0Var.d);
                    su0Var.r(arrayList, nu0Var);
                }
            }
        }
    }

    public final List s(ArrayList arrayList, boolean z) {
        String str;
        if (o()) {
            return er.e;
        }
        c(this.c, arrayList);
        if (z) {
            nu0 nu0Var = this.d;
            ve0 ve0Var = nu0Var.e;
            Object g = ve0Var.g(wu0.w);
            if (g == null) {
                g = null;
            }
            cr0 cr0Var = (cr0) g;
            if (cr0Var != null && nu0Var.g && !arrayList.isEmpty()) {
                arrayList.add(b(cr0Var, new q2(20, cr0Var)));
            }
            av0 av0Var = wu0.a;
            if (ve0Var.c(av0Var) && !arrayList.isEmpty() && nu0Var.g) {
                Object g2 = ve0Var.g(av0Var);
                if (g2 == null) {
                    g2 = null;
                }
                List list = (List) g2;
                if (list != null) {
                    str = (String) me.T(list);
                } else {
                    str = null;
                }
                if (str != null) {
                    arrayList.add(0, b(null, new q2(21, str)));
                }
            }
        }
        return arrayList;
    }
}
