package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class oj extends z30 implements gv {
    public final /* synthetic */ int f;
    public final /* synthetic */ Object g;
    public final /* synthetic */ Object h;
    public final /* synthetic */ Object i;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public oj(y6 y6Var, hk hkVar, y6 y6Var2) {
        super(1);
        this.f = 0;
        this.g = y6Var;
        this.i = hkVar;
        this.h = y6Var2;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        boolean booleanValue;
        tp tpVar;
        int i = this.f;
        x31 x31Var = x31.a;
        Object obj2 = this.i;
        Object obj3 = this.h;
        Object obj4 = this.g;
        switch (i) {
            case 0:
                y6 y6Var = (y6) obj4;
                f31.G((hk) obj2, null, new nj(y6Var, (((Number) obj).floatValue() / 1000.0f) + ((Number) y6Var.d()).floatValue(), (y6) obj3, null), 3);
                return x31Var;
            case 1:
                w21 w21Var = (w21) obj;
                ho hoVar = (ho) w21Var;
                if (((a5) ((b4) k81.F((ho) obj3)).m6getDragAndDropManager()).b.contains(hoVar) && o4.h(hoVar, dl.x((j2) obj2))) {
                    ((ep0) obj4).e = w21Var;
                    return v21.g;
                }
                return v21.e;
            case 2:
                pt ptVar = (pt) obj;
                if (o20.e(ptVar, (pt) obj4)) {
                    booleanValue = false;
                } else if (!o20.e(ptVar, ((lt) obj3).c)) {
                    booleanValue = ((Boolean) ((gv) obj2).e(ptVar)).booleanValue();
                } else {
                    v7.o("Focus search landed at the root.");
                    return null;
                }
                return Boolean.valueOf(booleanValue);
            default:
                up upVar = (up) obj;
                b50 b50Var = (b50) obj4;
                yc ycVar = b50Var.e;
                tp tpVar2 = b50Var.f;
                b50Var.f = (tp) obj3;
                try {
                    mm s = upVar.J().s();
                    m40 u = upVar.J().u();
                    uc q = upVar.J().q();
                    long v = upVar.J().v();
                    hx hxVar = (hx) upVar.J().g;
                    gv gvVar = (gv) obj2;
                    mm s2 = ycVar.f.s();
                    m40 u2 = ycVar.f.u();
                    uc q2 = ycVar.f.q();
                    long v2 = ycVar.f.v();
                    r7 r7Var = ycVar.f;
                    try {
                        hx hxVar2 = (hx) r7Var.g;
                        r7Var.E(s);
                        r7Var.F(u);
                        r7Var.D(q);
                        r7Var.G(v);
                        r7Var.g = hxVar;
                        q.h();
                        try {
                            gvVar.e(b50Var);
                            q.f();
                            r7 r7Var2 = ycVar.f;
                            r7Var2.E(s2);
                            r7Var2.F(u2);
                            r7Var2.D(q2);
                            r7Var2.G(v2);
                            r7Var2.g = hxVar2;
                            b50Var.f = tpVar2;
                            return x31Var;
                        } catch (Throwable th) {
                            tpVar = tpVar2;
                            try {
                                q.f();
                                r7 r7Var3 = ycVar.f;
                                r7Var3.E(s2);
                                r7Var3.F(u2);
                                r7Var3.D(q2);
                                r7Var3.G(v2);
                                r7Var3.g = hxVar2;
                                throw th;
                            } catch (Throwable th2) {
                                th = th2;
                                b50Var.f = tpVar;
                                throw th;
                            }
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        tpVar = tpVar2;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    tpVar = tpVar2;
                }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ oj(Object obj, Object obj2, Object obj3, int i) {
        super(1);
        this.f = i;
        this.g = obj;
        this.h = obj2;
        this.i = obj3;
    }
}
