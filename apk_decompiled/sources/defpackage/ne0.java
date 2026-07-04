package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ne0 extends hc0 {
    public final rl0 h;
    public Object i;

    public ne0(rl0 rl0Var, Object obj, Object obj2) {
        super(0, obj, obj2);
        this.h = rl0Var;
        this.i = obj2;
    }

    @Override // defpackage.hc0, java.util.Map.Entry
    public final Object getValue() {
        return this.i;
    }

    @Override // defpackage.hc0, java.util.Map.Entry
    public final Object setValue(Object obj) {
        int i;
        Object obj2 = this.i;
        this.i = obj;
        pl0 pl0Var = (pl0) this.h.f;
        ol0 ol0Var = pl0Var.h;
        Object obj3 = this.f;
        if (!ol0Var.containsKey(obj3)) {
            return obj2;
        }
        boolean z = pl0Var.g;
        if (z) {
            if (z) {
                b31 b31Var = pl0Var.e[pl0Var.f];
                Object obj4 = b31Var.e[b31Var.g];
                ol0Var.put(obj3, obj);
                if (obj4 != null) {
                    i = obj4.hashCode();
                } else {
                    i = 0;
                }
                pl0Var.c(i, ol0Var.f, obj4, 0);
            } else {
                v7.n();
                return null;
            }
        } else {
            ol0Var.put(obj3, obj);
        }
        pl0Var.k = ol0Var.h;
        return obj2;
    }
}
