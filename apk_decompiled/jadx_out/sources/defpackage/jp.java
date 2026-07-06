package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class jp extends sz0 implements kv {
    public int i;
    public /* synthetic */ Object j;
    public final /* synthetic */ kp k;
    public final /* synthetic */ long l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public jp(kp kpVar, long j, ij ijVar) {
        super(2, ijVar);
        this.k = kpVar;
        this.l = j;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        return ((jp) i((ij) obj2, (hk) obj)).k(x31.a);
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        jp jpVar = new jp(this.k, this.l, ijVar);
        jpVar.j = obj;
        return jpVar;
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        int i = this.i;
        if (i != 0) {
            if (i == 1) {
                o30.x(obj);
            } else {
                v7.o("call to 'resume' before 'invoke' with coroutine");
                return null;
            }
        } else {
            o30.x(obj);
            hk hkVar = (hk) this.j;
            lv lvVar = this.k.P;
            ch0 ch0Var = new ch0(this.l);
            this.i = 1;
            Object c = lvVar.c(hkVar, ch0Var, this);
            ik ikVar = ik.e;
            if (c == ikVar) {
                return ikVar;
            }
        }
        return x31.a;
    }
}
