package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class f60 implements kv {
    public final /* synthetic */ int e = 0;
    public final /* synthetic */ c70 f;
    public final /* synthetic */ int g;
    public final /* synthetic */ Object h;

    public /* synthetic */ f60(int i, c70 c70Var, Object obj) {
        this.f = c70Var;
        this.g = i;
        this.h = obj;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        boolean z;
        int i = this.e;
        x31 x31Var = x31.a;
        Object obj3 = this.h;
        int i2 = this.g;
        c70 c70Var = this.f;
        bw bwVar = (bw) obj;
        Integer num = (Integer) obj2;
        switch (i) {
            case 0:
                int intValue = num.intValue();
                if ((intValue & 3) != 2) {
                    z = true;
                } else {
                    z = false;
                }
                if (bwVar.O(intValue & 1, z)) {
                    c70Var.a(i2, obj3, bwVar, 0);
                } else {
                    bwVar.R();
                }
                return x31Var;
            default:
                num.getClass();
                c70Var.a(i2, obj3, bwVar, d20.O(1));
                return x31Var;
        }
    }

    public /* synthetic */ f60(c70 c70Var, int i, Object obj, int i2) {
        this.f = c70Var;
        this.g = i;
        this.h = obj;
    }
}
