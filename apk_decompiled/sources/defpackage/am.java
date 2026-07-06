package defpackage;

import java.util.HashMap;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class am implements h80 {
    public final /* synthetic */ int e = 1;
    public final Object f;
    public final Object g;

    public am(i80 i80Var) {
        this.f = i80Var;
        zd zdVar = zd.c;
        Class<?> cls = i80Var.getClass();
        xd xdVar = (xd) zdVar.a.get(cls);
        this.g = xdVar == null ? zdVar.a(cls, null) : xdVar;
    }

    @Override // defpackage.h80
    public final void h(j80 j80Var, z70 z70Var) {
        int i = this.e;
        Object obj = this.f;
        Object obj2 = this.g;
        switch (i) {
            case 0:
                yl ylVar = (yl) obj;
                switch (zl.a[z70Var.ordinal()]) {
                    case 1:
                        ylVar.f(j80Var);
                        break;
                    case 2:
                        ylVar.c(j80Var);
                        break;
                    case 3:
                        ylVar.e(j80Var);
                        break;
                    case 4:
                        ylVar.a(j80Var);
                        break;
                    case 5:
                        ylVar.b(j80Var);
                        break;
                    case 6:
                        ylVar.d(j80Var);
                        break;
                    case 7:
                        v7.m("ON_ANY must not been send by anybody");
                        return;
                    default:
                        v7.k();
                        return;
                }
                h80 h80Var = (h80) obj2;
                if (h80Var != null) {
                    h80Var.h(j80Var, z70Var);
                    return;
                }
                return;
            case 1:
                if (z70Var == z70.ON_START) {
                    ((l80) obj).f(this);
                    ((c4) obj2).v();
                    return;
                }
                return;
            default:
                HashMap hashMap = ((xd) obj2).a;
                xd.a((List) hashMap.get(z70Var), j80Var, z70Var, obj);
                xd.a((List) hashMap.get(z70.ON_ANY), j80Var, z70Var, obj);
                return;
        }
    }

    public am(yl ylVar, h80 h80Var) {
        ylVar.getClass();
        this.f = ylVar;
        this.g = h80Var;
    }

    public am(c4 c4Var, l80 l80Var) {
        this.f = l80Var;
        this.g = c4Var;
    }
}
