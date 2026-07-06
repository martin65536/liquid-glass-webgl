package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class c51 extends z30 implements kv {
    public final /* synthetic */ int f;
    public final /* synthetic */ ty[] g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ c51(ty[] tyVarArr, int i) {
        super(2);
        this.f = i;
        this.g = tyVarArr;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.f;
        ty[] tyVarArr = this.g;
        switch (i) {
            case 0:
                return Float.valueOf(g30.i((dm0) obj, true, tyVarArr, ((Number) obj2).floatValue()));
            default:
                return Float.valueOf(g30.i((dm0) obj, false, tyVarArr, ((Number) obj2).floatValue()));
        }
    }
}
