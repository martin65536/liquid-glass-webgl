package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class w90 extends sz0 implements kv {
    public final /* synthetic */ int i;
    public /* synthetic */ float j;
    public final /* synthetic */ al k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ w90(al alVar, ij ijVar, int i) {
        super(2, ijVar);
        this.i = i;
        this.k = alVar;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        float floatValue = ((Number) obj).floatValue();
        ij ijVar = (ij) obj2;
        switch (i) {
            case 0:
                ((w90) i(ijVar, Float.valueOf(floatValue))).k(x31Var);
                return x31Var;
            default:
                ((w90) i(ijVar, Float.valueOf(floatValue))).k(x31Var);
                return x31Var;
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        int i = this.i;
        al alVar = this.k;
        switch (i) {
            case 0:
                w90 w90Var = new w90(alVar, ijVar, 0);
                w90Var.j = ((Number) obj).floatValue();
                return w90Var;
            default:
                w90 w90Var2 = new w90(alVar, ijVar, 1);
                w90Var2.j = ((Number) obj).floatValue();
                return w90Var2;
        }
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        int i = this.i;
        x31 x31Var = x31.a;
        al alVar = this.k;
        float f = this.j;
        switch (i) {
            case 0:
                o30.x(obj);
                if (alVar.d() != f) {
                    alVar.f(f);
                }
                return x31Var;
            default:
                o30.x(obj);
                alVar.f(f);
                return x31Var;
        }
    }
}
