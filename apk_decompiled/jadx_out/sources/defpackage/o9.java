package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class o9 extends z30 implements kv {
    public final /* synthetic */ int f = 0;
    public final /* synthetic */ cd0 g;
    public final /* synthetic */ int h;
    public final /* synthetic */ kv i;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public o9(cd0 cd0Var, gg ggVar, int i, int i2) {
        super(2);
        this.g = cd0Var;
        this.i = ggVar;
        this.h = i2;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.f;
        x31 x31Var = x31.a;
        int i2 = this.h;
        kv kvVar = this.i;
        cd0 cd0Var = this.g;
        bw bwVar = (bw) obj;
        ((Number) obj2).intValue();
        switch (i) {
            case 0:
                f31.b(cd0Var, (gg) kvVar, bwVar, d20.O(49), i2);
                return x31Var;
            default:
                jc0.d(cd0Var, kvVar, bwVar, d20.O(i2 | 1));
                return x31Var;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public o9(cd0 cd0Var, kv kvVar, int i) {
        super(2);
        this.g = cd0Var;
        this.i = kvVar;
        this.h = i;
    }
}
