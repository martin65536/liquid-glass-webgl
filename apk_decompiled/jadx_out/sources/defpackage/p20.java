package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class p20 extends hq0 {
    public int f;
    public final /* synthetic */ kv g;
    public final /* synthetic */ ij h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public p20(ij ijVar, ij ijVar2, kv kvVar) {
        super(ijVar);
        this.g = kvVar;
        this.h = ijVar2;
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        int i = this.f;
        if (i != 0) {
            if (i == 1) {
                this.f = 2;
                o30.x(obj);
                return obj;
            }
            v7.o("This coroutine had already completed");
            return null;
        }
        this.f = 1;
        o30.x(obj);
        kv kvVar = this.g;
        kvVar.getClass();
        f31.n(2, kvVar);
        return kvVar.d(this.h, this);
    }
}
