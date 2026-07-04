package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ez0 extends z30 implements kv {
    public final /* synthetic */ hz0 f;
    public final /* synthetic */ cd0 g;
    public final /* synthetic */ kv h;
    public final /* synthetic */ int i;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ez0(hz0 hz0Var, cd0 cd0Var, kv kvVar, int i) {
        super(2);
        this.f = hz0Var;
        this.g = cd0Var;
        this.h = kvVar;
        this.i = i;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        ((Number) obj2).intValue();
        int O = d20.O(this.i | 1);
        jc0.e(this.f, this.g, this.h, (bw) obj, O);
        return x31.a;
    }
}
