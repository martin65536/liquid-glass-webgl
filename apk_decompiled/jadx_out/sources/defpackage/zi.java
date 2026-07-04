package defpackage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class zi implements gv {
    public final /* synthetic */ int e;
    public final /* synthetic */ Object f;
    public final /* synthetic */ Object g;
    public final /* synthetic */ Object h;

    public /* synthetic */ zi(cj cjVar, f41 f41Var, d30 d30Var, fu0 fu0Var) {
        this.e = 0;
        this.f = cjVar;
        this.g = d30Var;
        this.h = fu0Var;
    }

    /* JADX WARN: Type inference failed for: r10v10, types: [ep0, java.lang.Object] */
    @Override // defpackage.gv
    public final Object e(Object obj) {
        int i = this.e;
        float f = 1.0f;
        int i2 = 0;
        x31 x31Var = x31.a;
        int i3 = 1;
        Object obj2 = this.h;
        Object obj3 = this.g;
        Object obj4 = this.f;
        switch (i) {
            case 0:
                cj cjVar = (cj) obj4;
                d30 d30Var = (d30) obj3;
                fu0 fu0Var = (fu0) obj2;
                float floatValue = ((Float) obj).floatValue();
                if (!cjVar.u) {
                    f = -1.0f;
                }
                hu0 hu0Var = cjVar.t;
                long e = hu0Var.e(hu0Var.h(f * floatValue));
                hu0 hu0Var2 = fu0Var.a;
                float g = hu0Var.g(hu0Var.e(hu0Var2.c(hu0Var2.k, e, 1))) * f;
                if (Math.abs(g) < Math.abs(floatValue)) {
                    CancellationException cancellationException = new CancellationException("Scroll animation cancelled because scroll was not consumed (" + g + " < " + floatValue + ')');
                    cancellationException.initCause(null);
                    d30Var.a(cancellationException);
                }
                return x31Var;
            case 1:
                g30 g30Var = (g30) obj4;
                y5 y5Var = (y5) obj3;
                fy fyVar = (fy) obj2;
                up upVar = (up) obj;
                upVar.getClass();
                ((j2) upVar.J().f).q(1.0f, 1.0f);
                try {
                    uc q = upVar.J().q();
                    q.h();
                    n30.g(q, g30Var, y5Var);
                    o30.m(q, g30Var, fyVar.v);
                    q.f();
                    return x31Var;
                } finally {
                    ((j2) upVar.J().f).q(-1.0f, -1.0f);
                }
            case 2:
                af0 af0Var = (af0) obj4;
                ArrayList arrayList = (ArrayList) obj3;
                List list = (List) obj2;
                dm0 dm0Var = (dm0) obj;
                dm0Var.e = true;
                int size = arrayList.size();
                for (int i4 = 0; i4 < size; i4++) {
                    ((i70) arrayList.get(i4)).b(dm0Var);
                }
                int size2 = list.size();
                for (int i5 = 0; i5 < size2; i5++) {
                    ((i70) list.get(i5)).b(dm0Var);
                }
                dm0Var.e = false;
                af0Var.getValue();
                return x31Var;
            case 3:
                j80 j80Var = (j80) obj4;
                final p80 p80Var = (p80) obj3;
                final gv gvVar = (gv) obj2;
                final ?? obj5 = new Object();
                h80 h80Var = new h80() { // from class: e80
                    @Override // defpackage.h80
                    public final void h(j80 j80Var2, z70 z70Var) {
                        int i6 = g80.a[z70Var.ordinal()];
                        ep0 ep0Var = obj5;
                        if (i6 != 1) {
                            if (i6 != 2) {
                                return;
                            }
                            j9 j9Var = (j9) ep0Var.e;
                            if (j9Var != null) {
                                j9Var.a();
                            }
                            ep0Var.e = null;
                            return;
                        }
                        ep0Var.e = gvVar.e(p80.this);
                    }
                };
                j80Var.f().a(h80Var);
                return new f80(j80Var, h80Var, obj5, i2);
            default:
                ds0 ds0Var = (ds0) obj4;
                hs0 hs0Var = (hs0) obj2;
                ve0 ve0Var = ds0Var.f;
                if (!ve0Var.b(obj3)) {
                    ds0Var.e.remove(obj3);
                    ve0Var.m(obj3, hs0Var);
                    return new f80(ds0Var, obj3, hs0Var, i3);
                }
                v7.i("Key ", obj3, " was used multiple times ");
                return null;
        }
    }

    public /* synthetic */ zi(af0 af0Var, ArrayList arrayList, List list, boolean z) {
        this.e = 2;
        this.f = af0Var;
        this.g = arrayList;
        this.h = list;
    }

    public /* synthetic */ zi(Object obj, Object obj2, Object obj3, int i) {
        this.e = i;
        this.f = obj;
        this.g = obj2;
        this.h = obj3;
    }
}
