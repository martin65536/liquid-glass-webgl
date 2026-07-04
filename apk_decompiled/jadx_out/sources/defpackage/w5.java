package defpackage;

import android.graphics.Typeface;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class w5 implements mv {
    public final /* synthetic */ int e;
    public final /* synthetic */ Object f;

    public /* synthetic */ w5(int i, Object obj) {
        this.e = i;
        this.f = obj;
    }

    @Override // defpackage.mv
    public final Object h(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean z;
        int i;
        int i2 = this.e;
        Object obj5 = this.f;
        switch (i2) {
            case 0:
                x5 x5Var = (x5) obj5;
                q31 b = ((xt) x5Var.e).b((sl) obj, (nu) obj2, ((lu) obj3).a, ((mu) obj4).a);
                if (!(b instanceof q31)) {
                    r7 r7Var = new r7(b, x5Var.j);
                    x5Var.j = r7Var;
                    Object obj6 = r7Var.h;
                    obj6.getClass();
                    return (Typeface) obj6;
                }
                Object obj7 = b.e;
                obj7.getClass();
                return (Typeface) obj7;
            default:
                gg ggVar = (gg) obj5;
                t50 t50Var = (t50) obj;
                ((Integer) obj2).getClass();
                bw bwVar = (bw) obj3;
                int intValue = ((Integer) obj4).intValue();
                if ((intValue & 6) == 0) {
                    if (bwVar.f(t50Var)) {
                        i = 4;
                    } else {
                        i = 2;
                    }
                    intValue |= i;
                }
                if ((intValue & 131) != 130) {
                    z = true;
                } else {
                    z = false;
                }
                if (bwVar.O(intValue & 1, z)) {
                    ggVar.c(t50Var, bwVar, Integer.valueOf(intValue & 14));
                } else {
                    bwVar.R();
                }
                return x31.a;
        }
    }
}
