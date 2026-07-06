package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class nw extends z30 implements gv {
    public final /* synthetic */ int f = 0;
    public final /* synthetic */ ek0 g;
    public final /* synthetic */ Object h;
    public final /* synthetic */ Object i;
    public final /* synthetic */ af0 j;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public nw(ek0 ek0Var, ek0 ek0Var2, ek0 ek0Var3, ek0 ek0Var4) {
        super(1);
        this.g = ek0Var;
        this.h = ek0Var2;
        this.i = ek0Var3;
        this.j = ek0Var4;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        int i = this.f;
        x31 x31Var = x31.a;
        Object obj2 = this.i;
        ek0 ek0Var = this.g;
        af0 af0Var = this.j;
        boolean z = false;
        float f = 0.0f;
        Object obj3 = this.h;
        switch (i) {
            case 0:
                np npVar = (np) obj;
                npVar.getClass();
                float b = mw0.b(npVar.g);
                ue.c(npVar, ue.a);
                o4.o(npVar, npVar.e * ek0Var.g());
                float g = ((ek0) obj3).g() * b * 0.5f;
                float g2 = ((ek0) obj2).g() * b;
                if (((ek0) af0Var).g() > 0.0f) {
                    z = true;
                }
                d20.x(npVar, g, g2, true, z);
                return x31Var;
            default:
                al alVar = (al) obj;
                gv gvVar = (gv) obj3;
                alVar.getClass();
                if (((Boolean) af0Var.getValue()).booleanValue()) {
                    if (alVar.d() >= 0.5f) {
                        f = 1.0f;
                    }
                    ek0Var.h(f);
                    if (ek0Var.g() == 1.0f) {
                        z = true;
                    }
                    gvVar.e(Boolean.valueOf(z));
                    af0Var.setValue(Boolean.FALSE);
                } else {
                    if (!((Boolean) ((vu) obj2).a()).booleanValue()) {
                        f = 1.0f;
                    }
                    ek0Var.h(f);
                    if (ek0Var.g() == 1.0f) {
                        z = true;
                    }
                    gvVar.e(Boolean.valueOf(z));
                }
                return x31Var;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public nw(gv gvVar, vu vuVar, af0 af0Var, ek0 ek0Var) {
        super(1);
        this.h = gvVar;
        this.i = vuVar;
        this.j = af0Var;
        this.g = ek0Var;
    }
}
