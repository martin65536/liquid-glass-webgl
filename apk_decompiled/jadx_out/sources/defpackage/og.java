package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class og extends z30 implements gv {
    public final /* synthetic */ int f;
    public final /* synthetic */ af0 g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ og(af0 af0Var, int i) {
        super(1);
        this.f = i;
        this.g = af0Var;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        int i = this.f;
        x31 x31Var = x31.a;
        af0 af0Var = this.g;
        switch (i) {
            case 0:
                lx lxVar = (lx) obj;
                lxVar.getClass();
                long j = ((ch0) af0Var.getValue()).a;
                lxVar.n(Float.intBitsToFloat((int) (j >> 32)));
                lxVar.g(Float.intBitsToFloat((int) (4294967295L & j)));
                return x31Var;
            case 1:
                af0Var.setValue(new ch0(ch0.g(((ch0) af0Var.getValue()).a, ((ch0) obj).a)));
                return x31Var;
            case 2:
                bd bdVar = (bd) obj;
                bdVar.getClass();
                af0Var.setValue(bdVar);
                return x31Var;
            case 3:
                lx lxVar2 = (lx) obj;
                lxVar2.getClass();
                long j2 = ((ch0) af0Var.getValue()).a;
                lxVar2.n(Float.intBitsToFloat((int) (j2 >> 32)));
                lxVar2.g(Float.intBitsToFloat((int) (4294967295L & j2)));
                return x31Var;
            case 4:
                af0Var.setValue(new ch0(ch0.g(((ch0) af0Var.getValue()).a, ((ch0) obj).a)));
                return x31Var;
            case 5:
                lx lxVar3 = (lx) obj;
                lxVar3.getClass();
                long j3 = ((ch0) af0Var.getValue()).a;
                lxVar3.n(Float.intBitsToFloat((int) (j3 >> 32)));
                lxVar3.g(Float.intBitsToFloat((int) (4294967295L & j3)) - lxVar3.G(80.0f));
                return x31Var;
            case 6:
                Boolean bool = (Boolean) obj;
                bool.booleanValue();
                af0Var.setValue(bool);
                return x31Var;
            default:
                Boolean bool2 = (Boolean) obj;
                bool2.booleanValue();
                af0Var.setValue(bool2);
                return x31Var;
        }
    }
}
