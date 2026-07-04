package defpackage;

import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class f80 implements sn {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;
    public final /* synthetic */ Object c;
    public final /* synthetic */ Object d;

    public /* synthetic */ f80(Object obj, Object obj2, Object obj3, int i) {
        this.a = i;
        this.b = obj;
        this.c = obj2;
        this.d = obj3;
    }

    @Override // defpackage.sn
    public final void a() {
        int i = this.a;
        Object obj = this.d;
        Object obj2 = this.c;
        Object obj3 = this.b;
        switch (i) {
            case 0:
                ((j80) obj3).f().f((e80) obj2);
                j9 j9Var = (j9) ((ep0) obj).e;
                if (j9Var != null) {
                    j9Var.a();
                    return;
                }
                return;
            default:
                ds0 ds0Var = (ds0) obj3;
                hs0 hs0Var = (hs0) obj;
                if (ds0Var.f.k(obj2) == hs0Var) {
                    Map map = ds0Var.e;
                    Map d = hs0Var.d();
                    if (d.isEmpty()) {
                        map.remove(obj2);
                        return;
                    } else {
                        map.put(obj2, d);
                        return;
                    }
                }
                return;
        }
    }
}
