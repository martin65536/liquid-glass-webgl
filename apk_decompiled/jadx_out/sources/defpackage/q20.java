package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class q20 extends jj {
    public int h;
    public final /* synthetic */ kv i;
    public final /* synthetic */ ij j;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public q20(ij ijVar, yj yjVar, kv kvVar, ij ijVar2) {
        super(ijVar, yjVar);
        this.i = kvVar;
        this.j = ijVar2;
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        int i = this.h;
        if (i != 0) {
            if (i == 1) {
                this.h = 2;
                o30.x(obj);
                return obj;
            }
            v7.o("This coroutine had already completed");
            return null;
        }
        this.h = 1;
        o30.x(obj);
        kv kvVar = this.i;
        kvVar.getClass();
        f31.n(2, kvVar);
        return kvVar.d(this.j, this);
    }
}
