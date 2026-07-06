package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class hp extends sz0 implements lv {
    public final /* synthetic */ int i;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ hp(int i, ij ijVar, int i2) {
        super(i, ijVar);
        this.i = i2;
    }

    @Override // defpackage.lv
    public final Object c(Object obj, Object obj2, Object obj3) {
        int i = this.i;
        x31 x31Var = x31.a;
        int i2 = 3;
        switch (i) {
            case 0:
                long j = ((ch0) obj2).a;
                new hp(i2, (ij) obj3, 0).k(x31Var);
                return x31Var;
            case 1:
                ((Number) obj2).floatValue();
                new hp(i2, (ij) obj3, 1).k(x31Var);
                return x31Var;
            default:
                long j2 = ((ch0) obj2).a;
                new hp(i2, (ij) obj3, 2).k(x31Var);
                return x31Var;
        }
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        int i = this.i;
        x31 x31Var = x31.a;
        switch (i) {
            case 0:
                o30.x(obj);
                return x31Var;
            case 1:
                o30.x(obj);
                return x31Var;
            default:
                o30.x(obj);
                return x31Var;
        }
    }
}
