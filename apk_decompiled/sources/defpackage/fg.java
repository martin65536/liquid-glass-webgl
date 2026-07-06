package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class fg extends k2 implements kv {
    public final /* synthetic */ int l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ fg(int i, Object obj, Class cls, String str, String str2, int i2, int i3) {
        super(i, i2, cls, obj, str, str2);
        this.l = i3;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.l;
        x31 x31Var = x31.a;
        Object obj3 = this.e;
        switch (i) {
            case 0:
                ((gg) obj3).f((bw) obj, ((Number) obj2).intValue());
                return x31Var;
            case 1:
                zt0 zt0Var = (zt0) obj3;
                f31.G(zt0Var.P.m(), null, new yt0(zt0Var, ((v41) obj).a, null, 2), 3);
                return x31Var;
            default:
                zt0 zt0Var2 = (zt0) obj3;
                f31.G(zt0Var2.P.m(), null, new yt0(zt0Var2, ((v41) obj).a, null, 1), 3);
                return x31Var;
        }
    }
}
