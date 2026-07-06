package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class v6 implements gv {
    public final /* synthetic */ int e;
    public final /* synthetic */ Object f;
    public final /* synthetic */ Object g;
    public final /* synthetic */ Object h;
    public final /* synthetic */ Object i;

    public /* synthetic */ v6(l9 l9Var, mm mmVar, l40 l40Var, gv gvVar) {
        this.e = 1;
        this.g = l9Var;
        this.h = mmVar;
        this.i = l40Var;
        this.f = gvVar;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        int i = this.e;
        x31 x31Var = x31.a;
        Object obj2 = this.i;
        Object obj3 = this.f;
        Object obj4 = this.h;
        Object obj5 = this.g;
        switch (i) {
            case 0:
                y6 y6Var = (y6) obj5;
                d7 d7Var = (d7) obj4;
                gv gvVar = (gv) obj3;
                ap0 ap0Var = (ap0) obj2;
                b7 b7Var = (b7) obj;
                d20.P(b7Var, y6Var.c);
                ik0 ik0Var = b7Var.e;
                Object a = y6.a(y6Var, ik0Var.getValue());
                if (!o20.e(a, ik0Var.getValue())) {
                    y6Var.c.f.setValue(a);
                    d7Var.f.setValue(a);
                    if (gvVar != null) {
                        gvVar.e(y6Var);
                    }
                    b7Var.i.setValue(Boolean.FALSE);
                    b7Var.d.a();
                    ap0Var.e = true;
                } else if (gvVar != null) {
                    gvVar.e(y6Var);
                }
                return x31Var;
            case 1:
                up upVar = (up) obj;
                upVar.getClass();
                ((l9) obj5).a.b(upVar, (mm) obj4, (l40) obj2, (gv) obj3);
                return x31Var;
            case 2:
                bp0 bp0Var = (bp0) obj5;
                rl rlVar = (rl) obj2;
                b7 b7Var2 = (b7) obj;
                float floatValue = ((Number) b7Var2.e.getValue()).floatValue() - bp0Var.e;
                float a2 = ((du0) obj4).a(floatValue);
                bp0Var.e = ((Number) b7Var2.e.getValue()).floatValue();
                ((bp0) obj3).e = ((Number) ((gv) b7Var2.a.g).e(b7Var2.f)).floatValue();
                if (Math.abs(floatValue - a2) > 0.5f) {
                    b7Var2.i.setValue(Boolean.FALSE);
                    b7Var2.d.a();
                }
                rlVar.getClass();
                return x31Var;
            case 3:
                q60 q60Var = (q60) obj5;
                q60Var.c = new c9((e60) obj4, (hz0) obj3, (in0) obj2);
                return new h2(3, q60Var);
            default:
                bp0 bp0Var2 = (bp0) obj5;
                ud0 ud0Var = (ud0) obj4;
                fu0 fu0Var = (fu0) obj3;
                f2 f2Var = (f2) obj2;
                b7 b7Var3 = (b7) obj;
                ik0 ik0Var2 = b7Var3.e;
                vu vuVar = b7Var3.d;
                ik0 ik0Var3 = b7Var3.i;
                float floatValue2 = ((Number) ik0Var2.getValue()).floatValue() - bp0Var2.e;
                if (!d20.c(floatValue2)) {
                    if (!d20.c(floatValue2 - ud0Var.e(fu0Var, floatValue2))) {
                        ik0Var3.setValue(Boolean.FALSE);
                        vuVar.a();
                        return x31Var;
                    }
                    bp0Var2.e += floatValue2;
                }
                if (((Boolean) f2Var.e(Float.valueOf(bp0Var2.e))).booleanValue()) {
                    ik0Var3.setValue(Boolean.FALSE);
                    vuVar.a();
                }
                return x31Var;
        }
    }

    public /* synthetic */ v6(Object obj, Object obj2, Object obj3, Object obj4, int i) {
        this.e = i;
        this.g = obj;
        this.h = obj2;
        this.f = obj3;
        this.i = obj4;
    }
}
