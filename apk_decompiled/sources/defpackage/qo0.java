package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class qo0 extends sz0 implements kv {
    public final /* synthetic */ int i;
    public /* synthetic */ Object j;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ qo0(int i, ij ijVar, int i2) {
        super(i, ijVar);
        this.i = i2;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        switch (i) {
            case 0:
                return ((qo0) i((ij) obj2, (po0) obj)).k(x31Var);
            default:
                return ((qo0) i((ij) obj2, (gw0) obj)).k(x31Var);
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        switch (this.i) {
            case 0:
                qo0 qo0Var = new qo0(2, ijVar, 0);
                qo0Var.j = obj;
                return qo0Var;
            default:
                qo0 qo0Var2 = new qo0(2, ijVar, 1);
                qo0Var2.j = obj;
                return qo0Var2;
        }
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        boolean z = false;
        switch (this.i) {
            case 0:
                o30.x(obj);
                if (((po0) this.j) == po0.e) {
                    z = true;
                }
                return Boolean.valueOf(z);
            default:
                o30.x(obj);
                if (((gw0) this.j) != gw0.e) {
                    z = true;
                }
                return Boolean.valueOf(z);
        }
    }
}
