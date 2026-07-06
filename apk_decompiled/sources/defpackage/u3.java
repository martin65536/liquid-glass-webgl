package defpackage;

import android.view.KeyEvent;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class u3 extends z30 implements vu {
    public final /* synthetic */ int f;
    public final /* synthetic */ Object g;
    public final /* synthetic */ Object h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ u3(int i, Object obj, Object obj2) {
        super(0);
        this.f = i;
        this.g = obj;
        this.h = obj2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v17, types: [bd0] */
    /* JADX WARN: Type inference failed for: r0v19 */
    /* JADX WARN: Type inference failed for: r0v20, types: [bd0] */
    /* JADX WARN: Type inference failed for: r0v21, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r0v22 */
    /* JADX WARN: Type inference failed for: r0v23 */
    /* JADX WARN: Type inference failed for: r0v24 */
    /* JADX WARN: Type inference failed for: r0v25 */
    /* JADX WARN: Type inference failed for: r0v27 */
    /* JADX WARN: Type inference failed for: r0v28 */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12, types: [ef0] */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v15, types: [ef0] */
    /* JADX WARN: Type inference failed for: r6v18 */
    /* JADX WARN: Type inference failed for: r6v19 */
    /* JADX WARN: Type inference failed for: r6v20 */
    /* JADX WARN: Type inference failed for: r6v21 */
    /* JADX WARN: Type inference failed for: r6v9 */
    @Override // defpackage.vu
    public final Object a() {
        boolean dispatchKeyEvent;
        float f;
        float f2;
        su0 su0Var;
        z40 z40Var;
        wo0 wo0Var;
        int i = this.f;
        boolean z = false;
        x31 x31Var = x31.a;
        Object obj = this.h;
        Object obj2 = this.g;
        switch (i) {
            case 0:
                dispatchKeyEvent = super/*android.view.ViewGroup*/.dispatchKeyEvent((KeyEvent) obj);
                return Boolean.valueOf(dispatchKeyEvent);
            case 1:
                h4 h4Var = (h4) obj;
                kt0 kt0Var = (kt0) obj2;
                et0 et0Var = kt0Var.i;
                et0 et0Var2 = kt0Var.j;
                Float f3 = kt0Var.g;
                Float f4 = kt0Var.h;
                if (et0Var != null && f3 != null) {
                    f = ((Number) et0Var.a.a()).floatValue() - f3.floatValue();
                } else {
                    f = 0.0f;
                }
                if (et0Var2 != null && f4 != null) {
                    f2 = ((Number) et0Var2.a.a()).floatValue() - f4.floatValue();
                } else {
                    f2 = 0.0f;
                }
                if (f != 0.0f || f2 != 0.0f) {
                    int s = h4Var.s(kt0Var.e);
                    uu0 uu0Var = (uu0) h4Var.k().b(h4Var.o);
                    if (uu0Var != null) {
                        try {
                            k1 k1Var = h4Var.q;
                            if (k1Var != null) {
                                k1Var.a.setBoundsInScreen(h4Var.c(uu0Var));
                            }
                        } catch (IllegalStateException unused) {
                        }
                    }
                    uu0 uu0Var2 = (uu0) h4Var.k().b(h4Var.p);
                    if (uu0Var2 != null) {
                        try {
                            k1 k1Var2 = h4Var.r;
                            if (k1Var2 != null) {
                                k1Var2.a.setBoundsInScreen(h4Var.c(uu0Var2));
                            }
                        } catch (IllegalStateException unused2) {
                        }
                    }
                    h4Var.h.invalidate();
                    uu0 uu0Var3 = (uu0) h4Var.k().b(s);
                    if (uu0Var3 != null && (su0Var = uu0Var3.a) != null && (z40Var = su0Var.c) != null) {
                        if (et0Var != null) {
                            h4Var.t.h(s, et0Var);
                        }
                        if (et0Var2 != null) {
                            h4Var.u.h(s, et0Var2);
                        }
                        h4Var.o(z40Var);
                    }
                }
                if (et0Var != null) {
                    kt0Var.g = (Float) et0Var.a.a();
                }
                if (et0Var2 != null) {
                    kt0Var.h = (Float) et0Var2.a.a();
                }
                return x31Var;
            case 2:
                vu vuVar = (vu) obj2;
                if (vuVar != null && (wo0Var = (wo0) vuVar.a()) != null) {
                    return wo0Var;
                }
                ng0 ng0Var = (ng0) obj;
                if (!ng0Var.P0().r) {
                    ng0Var = null;
                }
                if (ng0Var == null) {
                    return null;
                }
                return t20.c(0L, d20.J(ng0Var.g));
            case 3:
                ((ep0) obj2).e = ((pt) obj).F0();
                return x31Var;
            case 4:
                ((my) obj2).d((bd0) obj);
                return x31Var;
            case 5:
                lg0 lg0Var = ((z40) obj2).H;
                ep0 ep0Var = (ep0) obj;
                if ((lg0Var.f.h & 8) != 0) {
                    for (bd0 bd0Var = lg0Var.e; bd0Var != null; bd0Var = bd0Var.i) {
                        if ((bd0Var.g & 8) != 0) {
                            jm jmVar = bd0Var;
                            ?? r6 = 0;
                            while (jmVar != 0) {
                                if (jmVar instanceof qu0) {
                                    qu0 qu0Var = (qu0) jmVar;
                                    if (qu0Var.h0()) {
                                        nu0 nu0Var = new nu0();
                                        ep0Var.e = nu0Var;
                                        nu0Var.h = true;
                                    }
                                    if (qu0Var.i0()) {
                                        ((nu0) ep0Var.e).g = true;
                                    }
                                    qu0Var.f0((bv0) ep0Var.e);
                                } else if ((jmVar.g & 8) != 0 && (jmVar instanceof jm)) {
                                    bd0 bd0Var2 = jmVar.t;
                                    int i2 = 0;
                                    jmVar = jmVar;
                                    r6 = r6;
                                    while (bd0Var2 != null) {
                                        if ((bd0Var2.g & 8) != 0) {
                                            i2++;
                                            r6 = r6;
                                            if (i2 == 1) {
                                                jmVar = bd0Var2;
                                            } else {
                                                if (r6 == 0) {
                                                    r6 = new ef0(new bd0[16]);
                                                }
                                                if (jmVar != 0) {
                                                    r6.b(jmVar);
                                                    jmVar = 0;
                                                }
                                                r6.b(bd0Var2);
                                            }
                                        }
                                        bd0Var2 = bd0Var2.j;
                                        jmVar = jmVar;
                                        r6 = r6;
                                    }
                                    if (i2 == 1) {
                                    }
                                }
                                jmVar = k81.h(r6);
                            }
                        }
                    }
                }
                return x31Var;
            default:
                pq0 pq0Var = ng0.Q;
                ((gv) obj2).e(pq0Var);
                ng0 ng0Var2 = (ng0) obj;
                boolean e = o20.e(ng0Var2.H, pq0Var.p);
                boolean z2 = ng0Var2.I;
                boolean z3 = pq0Var.q;
                if (z2 != z3) {
                    z = true;
                }
                if (!e || z) {
                    ng0Var2.H = pq0Var.p;
                    ng0Var2.I = z3;
                    if (ng0Var2.J && (z || (z3 && !e))) {
                        ng0Var2.s.C();
                    }
                }
                ng0Var2.J = true;
                pq0Var.y = pq0Var.p.b(pq0Var.s, pq0Var.u, pq0Var.t);
                return x31Var;
        }
    }
}
