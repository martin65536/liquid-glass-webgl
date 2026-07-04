package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class eg implements kv {
    public final /* synthetic */ int e;
    public final /* synthetic */ Object f;
    public final /* synthetic */ int g;
    public final /* synthetic */ Object h;

    public /* synthetic */ eg(eo0 eo0Var, gg ggVar, int i) {
        this.e = 1;
        this.h = eo0Var;
        this.f = ggVar;
        this.g = i;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.e;
        x31 x31Var = x31.a;
        int i2 = this.g;
        Object obj3 = this.h;
        Object obj4 = this.f;
        switch (i) {
            case 0:
                ((Integer) obj2).getClass();
                ((gg) obj4).g(obj3, (bw) obj, d20.O(i2) | 1);
                return x31Var;
            case 1:
                ((Integer) obj2).getClass();
                o20.a((eo0) obj3, (gg) obj4, (bw) obj, d20.O(i2 | 1));
                return x31Var;
            default:
                ((Integer) obj2).getClass();
                o20.b((eo0[]) obj4, (kv) obj3, (bw) obj, d20.O(i2 | 1));
                return x31Var;
        }
    }

    public /* synthetic */ eg(int i, int i2, Object obj, Object obj2) {
        this.e = i2;
        this.f = obj;
        this.h = obj2;
        this.g = i;
    }
}
